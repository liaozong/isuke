package com.example.liao.isuke.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UIUtils {

    //打印方法
    public static void print(String str) {

        System.out.println("liao...." + str);

    }
    public static void toast(final String str) {
        postTaskSafely(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    LinearLayout toastView = (LinearLayout) mToast.getView();
                    ImageView imageCodeProject = new ImageView(getContext());
                    imageCodeProject.setImageResource(R.mipmap.ic_about);
                    toastView.addView(imageCodeProject, 0);
                } else {
                    mToast.setText(str);
                }
                mToast.show();
            }
        });

    }

    private static Toast mToast = null;

    //吐司提示
    public static void toast(Context context, String str) {
        if (context != null) {
            if (mToast == null) {
                mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(str);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }


    /**
     * 得到上下文
     */
    public static Context getContext() {
        return BaseApplication.getmContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中定义的字符
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中定义的字符数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml中定义的颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到主线程的id
     */
    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 得到主线中中的一个handler
     */
    public static Handler getMainThreadHandler() {
        return BaseApplication.getmHandler();
    }

    //检查字符串是否包含以下非法特殊字符
    public static boolean islegalInput(String txt) {
        String regEx = "[`~!@#$%^&*()+=|{}\':;\',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(txt);
        return !m.find();
    }

    /**
     * 安全的执行一个task
     */
    public static void postTaskSafely(Runnable task) {
        // 得到当前线程的线程id
        long curThreadId = android.os.Process.myTid();
        /**
         T:thread
         P:process
         U:user
         */
        // 如果当前线程的线程id==主线程线程id
        if (curThreadId == getMainThreadId()) {
            task.run();
        } else { // 如果当前线程的线程id!=主线程线程id
            getMainThreadHandler().post(task);
        }

    }

    //设置系统状态栏
    public static void setDaoHangLan(Context context, Activity activity) {
        //判断版本是4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);

            SystemStatusManager tintManager = new SystemStatusManager(activity);
            //打开系统状态栏控制
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.drawable.touming_bg);//设置背景

            View layoutAll = activity.findViewById(R.id.view);
            //设置系统栏需要的内偏移
            layoutAll.setPadding(0, ScreenUtils.getStatusHeight(context), 0, 0);

        }
    }


    /**
     * dp 转 px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        // 1px = 1dp * (dpi / 160)

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;

        return (int) (dp * (dpi / 160f) + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        // 1dp = 1px * 160 / dpi

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        int dpi = metrics.densityDpi;
        return (int) (px * 160f / dpi + 0.5f);
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /*设置背景透明度*/
    public static void setBackgroundAlpha(float alpha, Activity activity) {
        WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
        lp.alpha = alpha;
        (activity).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        (activity).getWindow().setAttributes(lp);
    }

    /*藏起软键盘*/
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断软键盘是否弹出
     */
    public boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public boolean softInput(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        return true;

    }

    public static void buttonClick(final View v){
       v.setEnabled(false);
        BaseApplication.getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
              v.setEnabled(true);
            }
        }, 2000);
    }

    public static String showWeek(String timedtask_days) {
        String time = "";
        if (timedtask_days.contains("1"))
            time = time + UIUtils.getString(R.string.Monday) + ",";
        if (timedtask_days.contains("2"))
            time = time + UIUtils.getString(R.string.Tuesday) + ",";
        if (timedtask_days.contains("3"))
            time = time + UIUtils.getString(R.string.Wednesday) + ",";
        if (timedtask_days.contains("4"))
            time = time + UIUtils.getString(R.string.Thursday) + ",";
        if (timedtask_days.contains("5"))
            time = time + UIUtils.getString(R.string.Friday) + ",";
        if (timedtask_days.contains("6"))
            time = time + UIUtils.getString(R.string.Saturday) + ",";
        if (timedtask_days.contains("7"))
            time = time + UIUtils.getString(R.string.Sunday) + ",";

        time = time.substring(0, time.length() - 1);
        return time;
    }
}
