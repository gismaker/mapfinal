package com.mapfinal.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyong
 *
 */
public final class ThreadPool {

    /**
     * 最大线程个数.
     */
    private static final int MAX_SIZE = 32;

    /**
     * 当前实例.
     */
    private static final ThreadPool INSTANCE = new ThreadPool();

    /**
     * 缓存的线程池.
     * 线程可以复用
     */
    private ExecutorService executors;// = Executors.newCachedThreadPool();

    public ThreadPool() {
        executors = new ThreadPoolExecutor(1, MAX_SIZE, 20L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(64),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 获取实际例子.
     * 
     * @return ThreadPool
     */
    public static ThreadPool getInstance() {
        return INSTANCE;
    }


    public Future submit(Runnable runnable) {
        return executors.submit(runnable);
    }

    public void execute(Runnable runnable) {
        executors.execute(runnable);
    }
}