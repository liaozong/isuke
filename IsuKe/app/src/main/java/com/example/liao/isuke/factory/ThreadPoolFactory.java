package com.example.liao.isuke.factory;



/**
 * Created by liao on 2017/8/19.
 */

public class ThreadPoolFactory {

    private static ThreadProxy mNormalThread;
    private static ThreadProxy mDownloadThread;

    public static ThreadProxy getNormalThread() {

        if (mNormalThread == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mNormalThread == null)
                    mNormalThread = new ThreadProxy(3, 3, 3000);
            }
        }

        return mNormalThread;

    }

    public static ThreadProxy getDownloadThread() {
        if (mDownloadThread == null) {
            synchronized (ThreadPoolFactory.class) {
                if (mDownloadThread == null)
                    mDownloadThread = new ThreadProxy(5, 5, 3000);
            }
        }
        return mDownloadThread;
    }

}
