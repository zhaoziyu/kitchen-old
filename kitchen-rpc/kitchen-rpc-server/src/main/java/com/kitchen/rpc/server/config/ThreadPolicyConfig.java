package com.kitchen.rpc.server.config;

/**
 * 线程策略配置
 *
 * 1、用ThreadPoolExecutor自定义线程池，看线程的用途，如果任务量不大，可以用无界队列，如果任务量非常大，要用有界队列，防止OOM
 * 2、如果任务量很大，还要求每个任务都处理成功，要对提交的任务进行阻塞提交，重写拒绝机制，改为阻塞提交。保证不抛弃一个任务
 * 3、最大线程数一般设为2N+1最好，N是CPU核数
 * 4、核心线程数，看应用，如果是任务，一天跑一次，设置为0，合适，因为跑完就停掉了，如果是常用线程池，看任务量，是保留一个核心还是几个核心线程数
 * 5、如果要获取任务执行结果，用CompletionService，但是注意，获取任务的结果的要重新开一个线程获取，如果在主线程获取，就要等任务都提交后才获取，就会阻塞大量任务结果，队列过大OOM，所以最好异步开个线程获取结果
 *
 * @date 2016-12-09
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ThreadPolicyConfig {
    /**
     * true：阻塞线程池（保证每个任务都处理成功）
     * false：非阻塞线程池（处理不了的任务就抛出异常）
     */
    public static boolean IS_BLOCK_THREAD = true;      //是否阻塞线程

    public static int SERVER_CORE_POOL_SIZE = 100;     // 服务提供者处理服务的核心线程池大小

    public static int SERVER_MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;  // 服务提供者处理服务的最大线程池大小

    public static boolean ALLOW_CORE_THREAD_TIMEOUT = false;    //是否允许核心线程超时退出

    public static BlockingQueueType SERVER_QUEUE_TYPE = BlockingQueueType.SYNCHRONOUS;//任务缓存队列的类型

    public static int WORK_QUEUE_SIZE = Integer.MAX_VALUE;   //任务缓存队列的大小

    /**
     * 是否提交到业务线程池中执行
     * false：在I/O线程中执行请求的处理过程
     */
    public static boolean IS_SUBMIT_BUSINESS_THREAD = true;
}
