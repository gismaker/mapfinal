package com.mapfinal.task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 高并发异步和缓冲队列
 * <br>高并发时，复杂的任务要异步化，线程池满了后不会拒绝添加任务，需要有个缓冲队列机制。
 * @author yangyong
 *
 */
public final class ThreadPool {

    /**
     * LOG.
     */
    //private static final ILog LOG = LogFactory.getLog(ThreadPool.class);

    /**
     * 最大线程个数.
     */
    private static final int MAX_SIZE = 32;

    /**
     * 最大缓冲大小.
     */
    private static final int MAX_BUFFER_SIZE = 16;

    /**
     * 当前个数.
     */
    private int size = 0;

    /**
     * 锁.
     */
    private final Object lock = new Object();

    /**
     * 并发队列.
     */
    private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<Runnable>();

    /**
     * 当前实例.
     */
    private static final ThreadPool INSTANCE = new ThreadPool();

    /**
     * 缓存的线程池.
     * 线程可以复用
     */
    private ExecutorService executors = Executors.newCachedThreadPool();

    /**
     * 获取实际例子.
     * 
     * @return ThreadPool
     */
    public static ThreadPool getInstance() {
        return INSTANCE;
    }

    /**
     * 提交.
     * 
     * @param runnable
     *            runnable
     */
    public void submit(final Runnable runnable) {
        final int queueSize = queue.size();
        
        // 缓冲队列满，同步执行，（如果网络资源等其它无压力，可以不同步）
        if (queueSize >= MAX_BUFFER_SIZE) {
            runnable.run();
            return;
        }

        final Runnable run = add(new Runnable() {

            @Override
            public void run() {

                try {
                    runnable.run();
                } finally {
                    release();
                }

            }
        });
        execute(run);
    }

    /**
     * 执行.
     * 
     * @param run
     *            run
     */
    private void execute(final Runnable run) {
        if (run == null) {
            return;
        }
        executors.execute(run);
    }

    /**
     * 增加.
     * 
     * @param runnable
     *            runnable
     * @return Runnable
     */
    private Runnable add(final Runnable runnable) {
        queue.add(runnable);

        synchronized (lock) {
            // 有空闲消费者
            if (size < MAX_SIZE) {
                final Runnable run = queue.poll();
                if (run != null) {
                    size++;
                }
                return run;
            }
        }

        return null;
    }

    /**
     * 释放了一个线程.
     */
    public void release() {
        final Runnable run = queue.poll();
        synchronized (lock) {
            if (run != null) {
                size--;
            }
            if (size == 0) {
//                if (LOG.isInfoEnabled()) {
//                    LOG.info("已经全部释放");
//                }
            }
        }
        this.execute(run);
    }

}