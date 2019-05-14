package com.example.liao.isuke.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.liao.isuke.bean.AppUserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by qydda on 2017/2/14.
 * qydq ..updata no changes
 */

public class PreferenceUtil {
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEditor = null;

    public static void init(Context context) {
        if (null == mSharedPreferences) {
            mSharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void removeKey(String key) {
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void removeAll() {
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }

    public static void commitString(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key, String faillValue) {
        if (mSharedPreferences != null)
            return mSharedPreferences.getString(key, faillValue);

        return "";
    }

    public static void commitInt(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key, int failValue) {
        return mSharedPreferences.getInt(key, failValue);
    }

    public static void commitLong(String key, long value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public static long getLong(String key, long failValue) {
        return mSharedPreferences.getLong(key, failValue);
    }

    public static void commitBoolean(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static Boolean getBoolean(String key, boolean failValue) {
        return mSharedPreferences.getBoolean(key, failValue);
    }

    public static void setRingingstate(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static boolean getRingingstate(String key, boolean failValue) {
        return mSharedPreferences.getBoolean(key, failValue);
    }
    public static void saveUser(Context context, AppUserInfo user) {
        try {
            if (user != null) {
                SharedPreferences preferencesD = context.getSharedPreferences(Utils.USER_INFO_CONFIG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorD = preferencesD.edit();
                Gson gson = new Gson();
                String user_info = gson.toJson(user);
                editorD.putString("user", user_info);
                editorD.commit();
            }
        } catch (Exception e) {

        }
    }
    public static AppUserInfo getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Utils.USER_INFO_CONFIG, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String userStr = preferences.getString("user", "");
        AppUserInfo user = gson.fromJson(userStr, new TypeToken<AppUserInfo>() {
        }.getType());

        return user;
    }
    /**
     * 将arrayList的内容保存到sp里
     */
    public static void saveArrayList(ArrayList searchList, String content) {
        //searchList里“无数据”
        if (searchList.size() == 0) {
            //直接存
        } else {
            //searchList里“有数据”
            //但不包含这条数据，直接在0的位置加上这条数据
            if (!searchList.contains(content)) {
            } else {
                //包含了这条数据，就删除掉，并放在0位置或者原位置（自由选择）。
                for (int i = 0; i < searchList.size(); i++) {
                    if (searchList.get(i).equals(content)) {
                        searchList.remove(i);
                    }
                }
            }
        }

        //定义SP.Editor和文件名称
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //将结果放入文件，关键是把集合大小放入，为了后面的取出判断大小。
        editor.putInt("searchNums", searchList.size());
        for (int i = 0; i < searchList.size(); i++) {
            //用条目+i,代表键，解决键的问题，也方便等一下取出，值也对应。
            editor.putString("item_" + i, searchList.get(i) + "");
        }
        editor.commit();
    }

    /**
     * 读取sp里的数组
     */
    public static ArrayList<String> getSearchArrayList() {
        //定义一个集合等下返回结果
        ArrayList<String> list = new ArrayList<>();
        //刚才存的大小此时派上用场了
        int searchNums = mSharedPreferences.getInt("searchNums", 0);
        //根据键获取到值。
        for (int i = 0; i < searchNums; i++) {
            String searchItem = mSharedPreferences.getString("item_" + i, null);
            //放入新集合并返回
            list.add(searchItem);
        }
        return list;
    }
}
