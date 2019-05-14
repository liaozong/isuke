/**
 * @Title: Utils.java
 * @Package com.yanzhi.healthcare.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author wangkun
 * @date 2014-8-27 下午5:00:39
 */
package com.example.liao.isuke.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String USER_INFO_CONFIG = "user_info";
    public static final String USER_LOGIN_CONFIG = "user_login";

    public static String stringMD5(String input) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveAvatarFile(Context context, Bitmap bm, String dir, String fileName) throws IOException {
        UIUtils.print("saveAvatarFile..." + dir);
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(dir + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        UIUtils.print("存头像..." + bos);
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    // 下面这个函数用于将字节数组换成成16进制的字符串

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    //判断是否是邮箱
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    //判断是否是手机号码
    public static boolean isPhoneNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(14[0,9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    //"yyyy-MM-dd HH:mm:ss");
    public static String getStringDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dayString = sdf.format(date);
        return dayString;
    }

    public static String getCurrentTimeString() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
        String dayString = sdf.format(date);
        return dayString;
    }

    /**
     * 得到流数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();

    }

    /**
     * 通过路径获取图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] getImage(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");   //设置请求方法为GET
        conn.setReadTimeout(5 * 1000);    //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据
        byte[] data = readInputStream(inputStream);     //获得图片的二进制数据
        return data;

    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Utils.USER_INFO_CONFIG,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearUserPassword(Context context, String userName) {
        try {
//            SharedPreferences preferencesD = context.getSharedPreferences(Utils.USER_INFO_CONFIG, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editorD = preferencesD.edit();
//            Gson gson = new Gson();
//            String userStr = preferencesD.getString("user", "");
//            User user = gson.fromJson(userStr, new TypeToken<User>() {}.getType());
//            user.setPassword("");//把密码设置为空
//            String user_info = gson.toJson(user);
//            editorD.putString("user", user_info);
            SharedPreferences preferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorD = preferences.edit();
            editorD.putString("pwd", "");
            editorD.apply();
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param s 字符串
     * @return 是否含有非法字符？1:0
     */
    public static  boolean illegalChar(String s){
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.find();
    }


    public static void saveBitmap(Bitmap bm, String dir, String picName) {
        File f = new File(dir, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

//TODO  byte转int
/*    public static int byteToInt(byte[] arr) {
            int x = ((arr[a] & 0xFF) << 24)
                    | ((arr[a + 1] & 0xFF) << 16)
                    | ((arr[a + +2] & 0xFF) << 8)
                    | (arr[a + 3] & 0xFF);
        return x;
    }*/

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * @param @param  date
     * @param @return 设定文件
     * @return Date    返回类型
     * @Title: getNowDay
     * @author wangkun
     * @Description: TODO 获取今天时间
     */
    public static Date getNowDay() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day);
        Date nowDay = c.getTime();
        return nowDay;
    }


    public static int[] hexStringToInt(String hex) {
        int len = (hex.length() / 2);
        char[] achar = hex.toUpperCase().toCharArray();
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = toByte(achar[pos]) * 16 + toByte(achar[pos + 1]);
        }
        return result;
    }

    public static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

}
