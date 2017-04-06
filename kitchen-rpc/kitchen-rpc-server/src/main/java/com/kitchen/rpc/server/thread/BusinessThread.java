package com.kitchen.rpc.server.thread;

import com.kitchen.rpc.server.config.ThreadPolicyConfig;

import java.util.concurrent.*;

/**
 * RPC服务端的业务处理线程池
 *
 * @date 2016-12-09
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class BusinessThread {
    private static ThreadPoolExecutor threadPoolExecutor = null;

    static {
        if(threadPoolExecutor == null && ThreadPolicyConfig.IS_SUBMIT_BUSINESS_THREAD){
            BlockingQueue<Runnable> blockingQueue = createBlockingQueue();

            if (ThreadPolicyConfig.IS_BLOCK_THREAD) {
                // 阻塞线程
                createBlockThread(blockingQueue);
            } else {
                // 非阻塞线程
                createUnBlockThread(blockingQueue);
            }

            if (ThreadPolicyConfig.ALLOW_CORE_THREAD_TIMEOUT) {// 允许核心线程超时退出线程池
                threadPoolExecutor.allowCoreThreadTimeOut(true);
            }
        }
    }

    /**
     * 创建阻塞队列
     * @return
     */
    private static BlockingQueue<Runnable> createBlockingQueue() {
        BlockingQueue<Runnable> blockingQueue = null;
        switch (ThreadPolicyConfig.SERVER_QUEUE_TYPE) {
            case ARRAY :
                // 基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
                blockingQueue = new ArrayBlockingQueue<Runnable>(ThreadPolicyConfig.WORK_QUEUE_SIZE);
                break;
            case LINKED :
                // 基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，吞吐量通常要高于ArrayBlockingQueue。
                blockingQueue = new LinkedBlockingQueue<Runnable>(ThreadPolicyConfig.WORK_QUEUE_SIZE);
                break;
            case SYNCHRONOUS :
                // 不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue。
                blockingQueue = new SynchronousQueue<Runnable>();
                break;
            case PRIORITY:
                // 具有优先级得无限阻塞队列。
                blockingQueue = new PriorityBlockingQueue<Runnable>();
                break;
            default:
                // 默认阻塞队列
                blockingQueue = new SynchronousQueue<Runnable>();
                break;
        }
        return blockingQueue;
    }

    /**
     * 创建阻塞线程池
     * @param blockingQueue
     */
    private static void createBlockThread(BlockingQueue<Runnable> blockingQueue) {
        threadPoolExecutor = new ThreadPoolExecutor(
                ThreadPolicyConfig.SERVER_CORE_POOL_SIZE,
                ThreadPolicyConfig.SERVER_MAXIMUM_POOL_SIZE,
                60L,
                TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                new BusinessThreadRejectedExecutionHandler());
    }

    /**
     * 创建非阻塞线程，处理不了抛出异常
     * @param blockingQueue
     */
    private static void createUnBlockThread(BlockingQueue<Runnable> blockingQueue) {
        threadPoolExecutor = new ThreadPoolExecutor(
                ThreadPolicyConfig.SERVER_CORE_POOL_SIZE,
                ThreadPolicyConfig.SERVER_MAXIMUM_POOL_SIZE,
                60L,
                TimeUnit.SECONDS,
                blockingQueue);
        // 当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。
    }

    /**
     * 调起线程（无法回值）
     *
     * @param task
     * @throws Exception
     */
    public static void executeBusinessTask(Runnable task) throws Exception {
        if(threadPoolExecutor == null) {
            throw new Exception("未初始化线程池");
        }
        threadPoolExecutor.execute(task);
    }

    /**
     * 调起线程（带返回值）
     * @param task
     * @return
     * @throws Exception
     */
    public static <T> Future<T> submitBusinessTask(Callable<T> task) throws Exception {
        if(threadPoolExecutor == null) {
            throw new Exception("未初始化线程池");
        }
        return threadPoolExecutor.submit(task);
    }

    public static void shutdown() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            System.out.println("业务线程池已关闭");
        }
    }
}
