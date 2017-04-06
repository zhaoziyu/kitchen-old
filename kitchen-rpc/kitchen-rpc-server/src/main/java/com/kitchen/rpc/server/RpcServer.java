package com.kitchen.rpc.server;

import com.kitchen.rpc.common.codec.RpcDecoder;
import com.kitchen.rpc.common.codec.RpcEncoder;
import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.common.protocol.RpcResponse;
import com.kitchen.rpc.common.util.RpcStringUtil;
import com.kitchen.rpc.registry.RpcServiceRegistry;
import com.kitchen.rpc.registry.cache.RegistryCache;
import com.kitchen.rpc.registry.zookeeper.registry.ZooKeeperServiceRegistry;
import com.kitchen.rpc.server.config.RpcServerConfig;
import com.kitchen.rpc.server.exception.RpcServiceException;
import com.kitchen.rpc.server.thread.BusinessThread;
import com.kitchen.rpc.server.util.RpcServerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * RPC 服务器（用于发布 RPC 服务）
 * 单例模式
 *
 * 调用顺序：
 * 1、RpcServer()
 * 2、setApplicationContext()
 * 3、afterPropertiesSet()
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {
    // Spring上下文
    public static ClassPathXmlApplicationContext APPLICATION_CONTEXT;

    private static boolean OPEN_RPC = true;

    /**
     * 当前服务提供者的服务器地址 [ip:port]
     * 通过Spring配置文件初始化
     */
    private String serverAddress;

    private Integer serverWeight;

    /**
     * RPC服务注册的服务器（默认为ZooKeeper）
     * 通过Spring配置文件初始化
     */
    private static RpcServiceRegistry rpcServiceRegistry;

    private static ChannelFuture future = null;
    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workerGroup = null;

    public RpcServer(String serverAddress, Integer serverWeight, String registryCenterAddress, boolean open) {
        OPEN_RPC = open;
        if (OPEN_RPC) {
            this.serverAddress = serverAddress;
            this.serverWeight = serverWeight;
            RegistryCache.address(this.serverAddress);
            RegistryCache.weight(this.serverWeight);
            // 初始化ZooKeeper目录服务 TODO 注册和发现通过抽象工厂实现
            rpcServiceRegistry = new ZooKeeperServiceRegistry(registryCenterAddress);
        } else {
            System.out.println("未启用RPC服务，如需启用RPC服务，请在rpc-provider.properties中设置rpc.provider.open为true");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (OPEN_RPC) {
            // 扫描带有 RpcService 注解的类并初始化 RegistryCache 对象
            int count = 0;
            Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
            if (MapUtils.isNotEmpty(serviceBeanMap)) {
                for (Object serviceBean : serviceBeanMap.values()) {
                    // 获取RpcService注解类的实现接口
                    Class<?>[] interfaces = serviceBean.getClass().getInterfaces();
                    if (interfaces.length != 1) {
                        try {
                            throw new RpcServiceException("RPC服务接口实现错误:" + serviceBean.getClass().getName());
                        } catch (RpcServiceException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);

                    String serviceName = interfaces[0].getName();
                    String serviceVersion = rpcService.version();
                    if (RpcStringUtil.isNotEmpty(serviceVersion)) {
                        serviceName += "-" + serviceVersion;
                    }

                    // 验证是否存在相同服务接口相同版本号的服务实现
                    if (RegistryCache.get().containsKey(serviceName)) {
                        try {
                            throw new RpcServiceException("重复的RPC服务接口实现:" + serviceName);
                        } catch (RpcServiceException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    RegistryCache.put(serviceName, serviceBean);
                    count++;
                }
            }
            RpcServerUtil.setContext(applicationContext);
            System.out.println("完成服务扫描，共扫描到" + count + "个RpcService服务：");
            for (String key : serviceBeanMap.keySet()) {
                System.out.println(key);
            }
            System.out.println("——————————————————————");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (OPEN_RPC) {
            // 创建并初始化 Netty 服务端 Bootstrap 对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置并绑定Reactor线程池（注：也可以只创建一个共享线程池），Netty默认线程数是“Java虚拟机可用的处理器数量的2倍”
            bossGroup = new NioEventLoopGroup();// 用来接收客户端的连接（bossGroup中有多个NioEventLoop线程，每个NioEventLoop绑定一个端口，也就是说，如果程序只需要监听1个端口的话，bossGroup里面只需要有一个NioEventLoop线程就行了。）
            workerGroup = new NioEventLoopGroup(RpcServerConfig.RPC_SERVER_WORK_THREADS);// 用来处理已经接收的客户端连接
            bootstrap.group(bossGroup, workerGroup);

            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RpcEncoder(RpcResponse.class)); // 编码 RPC 响应
                    //pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0)); //TODO 防止黏包
                    pipeline.addLast(new RpcDecoder(RpcRequest.class));  // 解码 RPC 请求
                    pipeline.addLast(new RpcServerHandler());             // 处理 RPC 请求
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, RpcServerConfig.CHANNEL_BACKLOG);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);//通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//保持长连接状态

            // 获取 RPC 服务器的 IP 地址与端口号
            String host = RpcStringUtil.getHost(serverAddress);
            int port = RpcStringUtil.getPort(serverAddress);

            // 启动 RPC 服务器
            future = bootstrap.bind(host, port).sync();
            System.out.println("服务提供者(" + serverAddress + ")已启动完毕");

            // 注册 RPC 服务地址到ZooKeeper服务器
            RegistryCache.registryCacheService(rpcServiceRegistry);

            // 等待...直到关闭
            future.channel().closeFuture().sync();
        }
    }

    /**
     * 优雅关闭服务提供者
     */
    public static void shutdownGracefully() {
        if (OPEN_RPC) {
            if (future != null) {
                future.channel().close();
            }
            if (rpcServiceRegistry != null) {
                rpcServiceRegistry.stop();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
                System.out.println("Reactor线程池：完成优雅关闭（workerGroup）");
            }
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
                System.out.println("Reactor线程池：完成优雅关闭（bossGroup）");
            }

            // 关闭业务线程池
            BusinessThread.shutdown();
            System.out.println("RPC 服务器：已关闭");
        }
        // 关闭Spring上下文
        APPLICATION_CONTEXT.destroy();
    }
}
