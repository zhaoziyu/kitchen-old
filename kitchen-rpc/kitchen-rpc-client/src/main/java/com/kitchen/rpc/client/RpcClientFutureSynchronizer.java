package com.kitchen.rpc.client;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * RPC请求同步器
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-16
 */
public class RpcClientFutureSynchronizer extends AbstractQueuedSynchronizer {

    private static final long serialVersionUID = 1L;

    //future status
    private final int done = 1;
    private final int pending = 0;

    protected boolean tryAcquire(int acquires) {
        return getState() == done ? true : false;
    }

    protected boolean tryRelease(int releases) {
        if (getState() == pending) {
            if (compareAndSetState(pending, done)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDone() {
        getState();
        return getState() == done;
    }
}