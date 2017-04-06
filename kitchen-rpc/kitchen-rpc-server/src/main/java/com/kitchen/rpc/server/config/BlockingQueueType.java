package com.kitchen.rpc.server.config;

/**
 * 线程池阻塞队列的类型
 *
 * @date 2017-03-01
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum BlockingQueueType {
    ARRAY,          //基于数组的先进先出队列，此队列创建时必须指定大小
    LINKED,         //基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE
    SYNCHRONOUS,   //不会保存提交的任务，而是将直接新建一个线程来执行新来的任务
    PRIORITY;       //具有优先级得无限阻塞队列
}
