package com.kitchen.rpc.client;

import com.kitchen.rpc.common.RequestMode;
import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.common.protocol.RpcResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * RPC客户端调用的返回结果接受类（异步结果）
 *
 * @date 2016-12-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcClientFuture implements Future<Object> {
    private RpcClientFutureSynchronizer rpcFutureSync;
    private RpcRequest request;
    private String serviceAddress;
    private RpcResponse response;
    private long startTime;

    private List<RpcClientCallback> callbacks;

    private long responseTimeThreshold = 5000;

    public RpcClientFuture(RpcRequest request, String serviceAddress) {
        this.rpcFutureSync = new RpcClientFutureSynchronizer();
        this.request = request;
        this.serviceAddress = serviceAddress;
        this.startTime = System.currentTimeMillis();
    }

    public RpcRequest getRequest() {
        return this.request;
    }
    public RpcResponse getResponse() {
        return this.response;
    }
    public String getServiceAddress() {
        return this.serviceAddress;
    }

    public void setCallbacks(List<RpcClientCallback> callbacks) {
        this.callbacks = callbacks;
    }

    public List<RpcClientCallback> getCallbacks() {
        return callbacks;
    }

    @Override
    public boolean isDone() {
        return rpcFutureSync.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        if (request.getRequestMode() != RequestMode.SYNC) {
            return null;
        }
        rpcFutureSync.acquire(-1);
        if (this.response != null) {
            return this.response.getResult();
        } else {
            return null;
        }
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (request.getRequestMode() != RequestMode.SYNC) {
            return null;
        }
        boolean success = rpcFutureSync.tryAcquireNanos(-1, unit.toNanos(timeout));
        if (success) {
            if (this.response != null) {
                return this.response.getResult();
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Timeout exception. Request id: " + this.request.getRequestId()
                    + ". Request class name: " + this.request.getInterfaceName()
                    + ". Request method: " + this.request.getMethodName());
        }
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    public void done(RpcResponse reponse) {
        this.response = reponse;
        if (request.getRequestMode() != RequestMode.SYNC) {
            return;
        }

        // 释放同步锁
        rpcFutureSync.release(1);

        // Threshold
        long responseTime = System.currentTimeMillis() - startTime;
        if (responseTime > this.responseTimeThreshold) {
            System.out.println("Service response time is too slow. Request id = " + reponse.getRequestId() + ". Response Time = " + responseTime + "ms");
        }
    }
}
