package com.example.liao.isuke.factory;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liao on 2017/8/19.
 */

public class ThreadProxy {

    ThreadPoolExecutor mExecutor;
    int mPoolSize;//1.核心线程数
    int mMaxnum;//2.最大线程数
    long mLiveTime;//3.保持时间

    public ThreadProxy(int mPoolSize, int mMaxnum, long mLiveTime) {
        this.mPoolSize = mPoolSize;
        this.mMaxnum = mMaxnum;
        this.mLiveTime = mLiveTime;
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initExecutor() {
        /*1.核心线程数 2.最大线程数 3.保持时间 4.时间单位 5.任务队列 6.线程工厂 7.异常捕获器*/

        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated())
                    mExecutor = new ThreadPoolExecutor(mPoolSize, mMaxnum, mLiveTime, unit, workQueue, threadFactory, handler);
            }

        }

    }


    //提交任务
    public void submitTask(Runnable task) {
        initExecutor();
        mExecutor.submit(task);

    }


    //执行任务
    public void executeTask(Runnable task) {
        initExecutor();
        mExecutor.execute(task);
    }

    //移除任务
    public void removeTask(Runnable task) {
        initExecutor();
        mExecutor.remove(task);

    }
}
