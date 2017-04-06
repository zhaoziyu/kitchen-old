package com.kitchen.rpc.server.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 业务线程被拒绝时的处理
 *
 * @date 2016-12-09
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class BusinessThreadRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);//重新加入到执行队列中
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
