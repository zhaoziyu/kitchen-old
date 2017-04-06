package com.kitchen.rpc.common.protocol;

import com.kitchen.rpc.common.RequestMode;

/**
 * 封装 RPC 请求对象
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcRequest {
    // 请求的唯一标识
    private String requestId;
    // 请求的服务接口名称
    private String interfaceName;
    // 请求的服务接口版本
    private String serviceVersion;
    // 请求的服务接口方法
    private String methodName;
    // 请求方法的参数
    private Object[] parameters;
    // 请求方法的参数类型
    private Class<?>[] parameterTypes;
    // 请求类型（方式）
    private RequestMode requestMode;

    // 发起请求的IP地址（在非HTTP请求下，可能为空）
    private String requestIp;
    // 发起请求的SessionId（在非HTTP请求下，可能为空）
    private String requestSessionId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String className) {
        this.interfaceName = className;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public RequestMode getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(RequestMode requestMode) {
        this.requestMode = requestMode;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestSessionId() {
        return requestSessionId;
    }

    public void setRequestSessionId(String requestSessionId) {
        this.requestSessionId = requestSessionId;
    }
}
