package com.kitchen.rpc.server.deploy.port.random;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.port.PortFetcher;
import com.kitchen.rpc.server.deploy.port.tool.PortVerifier;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 随机选择系统空闲端口
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RandomPortFetcher implements PortFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomPortFetcher.class);

    private static final int PORT_BEGIN = 8000;
    private static final int PORT_END = 65535;

    @Override
    public Integer getPort(Integer portArg) throws ProviderDeployException {
        boolean already = false;
        Integer useablePort = null;
        while (!already) {
            int port = generateIntValueByRange(PORT_BEGIN, PORT_END);
            System.out.println("正在尝试端口（" + port + "）的可用性");
            Pair<Boolean, String> result = PortVerifier.availability(port);
            if (result.getValue0()) {
                already = true;
                useablePort = port;
            }
        }

        if (useablePort == null) {
            throw new ProviderDeployException("初始化异常：未找到可用端口");
        }

        return useablePort;
    }

    /**
     * 平均概率生成指定范围内的随机数，包含最大值和最小值
     * @param min 范围——最小值
     * @param max 范围——最大值
     * @return
     */
    private int generateIntValueByRange(int min, int max) {
        Random random = new Random();
        int randomValue = random.nextInt(max - min + 1) + min;
        return randomValue;
    }
}
