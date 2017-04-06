package com.kitchen.rpc.server;

import com.kitchen.rpc.common.RequestMode;
import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.common.protocol.RpcResponse;
import com.kitchen.rpc.common.util.RpcStringUtil;
import com.kitchen.rpc.registry.cache.RegistryCache;
import com.kitchen.rpc.server.config.ThreadPolicyConfig;
import com.kitchen.rpc.server.thread.BusinessThread;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

/**
 * RPC 服务端的请求处理器（接收并处理RPC客户端发送的请求）
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    public RpcServerHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        if (ThreadPolicyConfig.IS_SUBMIT_BUSINESS_THREAD) {
            // 调用业务线程处理
            handlerOnBusinessThread(channelHandlerContext, rpcRequest);
        } else {
            // 在IO线程中处理
            handlerOnIoThread(channelHandlerContext, rpcRequest);
        }
    }

    /**
     * 在IO线程中处理请求
     */
    private void handlerOnIoThread(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) {
        handleRequest(channelHandlerContext, rpcRequest);
    }

    /**
     * 调用业务线程处理请求
     */
    private void handlerOnBusinessThread(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        BusinessThread.executeBusinessTask(new Runnable() {
            @Override
            public void run() {
                handleRequest(channelHandlerContext, rpcRequest);
            }
        });
    }

    private void handleRequest(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) {
        if (rpcRequest.getRequestMode() == RequestMode.SYNC || rpcRequest.getRequestMode() == RequestMode.ASYNC_CALLBACK) {
            // 创建并初始化 RPC 响应对象
            RpcResponse response = new RpcResponse();
            response.setRequestId(rpcRequest.getRequestId());
            response.setRequestMode(rpcRequest.getRequestMode());
            try {
                Object result = handleRequest0(rpcRequest);
                response.setResult(result);
            } catch (Exception e) {
                response.setException(e);
            }
            // 写入 RPC 响应对象
            channelHandlerContext.writeAndFlush(response);
        } else if (rpcRequest.getRequestMode() == RequestMode.ASYNC) {
            try {
                handleRequest0(rpcRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 无需回写响应对象
        }
    }

    /**
     * 处理请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    private Object handleRequest0(RpcRequest request) throws Exception {
        // 获取服务对象
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();
        if (RpcStringUtil.isNotEmpty(serviceVersion)) {
            serviceName += "-" + serviceVersion;
        }
        Object serviceBean = RegistryCache.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("服务提供者(" + RegistryCache.address() + ")未提供[%s]服务", serviceName));
        }
        // 获取反射调用所需的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // 使用 CGLib 执行反射调用
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("处理RPC请求时捕获异常：" + cause);
        ctx.close();
    }
}
