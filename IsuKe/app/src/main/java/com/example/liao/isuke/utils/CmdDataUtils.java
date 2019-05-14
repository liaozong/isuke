package com.example.liao.isuke.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class CmdDataUtils {

    /**
     * 蓝牙配置wifi设备账号密码
     *
     * @param wifiname
     * @param wifipwd
     * @return
     */
    public static byte[] getWifiDataByte(String wifiname, String wifipwd) {
        try {
            wifiname = URLDecoder.decode(wifiname, "UTF-8");
            wifipwd = URLDecoder.decode(wifipwd, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] wifinameBytes = wifiname.getBytes();
        byte[] wifipwdBytes = wifipwd.getBytes();

        byte arr[] = new byte[wifinameBytes.length + wifipwdBytes.length + 6];
        arr[0] = 0x10;
        arr[1] = (byte) wifinameBytes.length;
        arr[2] = (byte) wifipwdBytes.length;
        int temp;
        /*src:源数组；	srcPos:源数组要复制的起始位置；
dest:目的数组；	destPos:目的数组放置的起始位置；	length:复制的长度*/
        System.arraycopy(wifinameBytes, 0, arr, 3, wifinameBytes.length);
        System.arraycopy(wifipwdBytes, 0, arr, 3 + wifinameBytes.length, wifipwdBytes.length);
        arr[wifinameBytes.length + wifipwdBytes.length + 3] = (byte) 0xFF;
        arr[wifinameBytes.length + wifipwdBytes.length + 4] = 0x69;
        arr[wifinameBytes.length + wifipwdBytes.length + 5] = 0x42;

        byte data[] = new byte[arr.length + 4];
        data[0] = 0x24;
        data[1] = 0x06;
        data[2] = 0x01;
        data[3] = (byte) (arr.length - 2);
        System.arraycopy(arr, 0, data, 4, arr.length);
        return data;
    }

    public static String ByteArrayToHex(byte[] arry) {
        String str = "";
        StringBuilder sb = new StringBuilder(str);
        for (byte element : arry) {
            sb.append(String.format("%02X ", element));
        }
        return sb.toString();
    }

    /**
     * byte数组拆分
     *
     * @param bytes  需要拆分的数据
     * @param length 拆分的字节数
     * @return byte[][]拆分后的字节数组
     */
    public static List splitBytes(byte[] bytes, int length) {
        int listsize = bytes.length % (length) == 0 ? bytes.length / (length) : bytes.length / (length) + 1;
        List<byte[]> list = new ArrayList<byte[]>();
        int from, to;
        for (int i = 0; i < listsize; i++) {
            byte[] splitbyte = new byte[length];
            from = (int) (i * length);
            to = (int) (from + length);
            if (to > bytes.length)
                to = bytes.length;
            splitbyte = Arrays.copyOfRange(bytes, from, to);
            list.add(splitbyte);
        }
        return list;
    }
}
