package com.example.liao.isuke.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.liao.isuke.utils.UIUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liao on 2018/3/16.
 */

public class Location {
    public static void toLoaction(LocationListener listener) {
        LocationManager locationManager = (LocationManager) UIUtils.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            /*
             * 进行定位
             * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
             * minTime:时间更新间隔。单位：ms
             * minDistance:位置刷新距离，单位：m
             * listener:用于定位更新的监听者locationListener
             */
            if (ActivityCompat.checkSelfPermission(UIUtils.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(UIUtils.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                UIUtils.toast(UIUtils.getContext(), "没有定位权限，请前往开启.");
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 2, listener);
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            UIUtils.toast(UIUtils.getContext(), "无法定位，请打开定位服务");
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            UIUtils.getContext().startActivity(i);
        }
    }

    /**
     * @param latitude  精度
     * @param longitude 纬度
     * @return 城市名称
     */
    public static String getLocationInfo(double latitude, double longitude) {
        Geocoder gc = new Geocoder(UIUtils.getContext(), Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GC 转换异常：", Log.getStackTraceString(e));
        }
        Address address = locationList.get(0);//得到Address实例
        //String countryName = address.getCountryName();//得zfyigf WDWJ到国家名称，比方：中国

        String locality = address.getLocality();//得到城市名称，比方：北京市

        return locality;
       /* for (int i = 0; address.getAddressLine(i) != null; i++) {
            String addressLine = address.getAddressLine(i);//得到周边信息。包含街道等。i=0，得到街道名称
            Log.i(TAG, "addressLine = " + addressLine);
        }*/
    }


    public static void getCityName(final double latitude, final double longitude) {


    }


    public static String getLocationArea(double latitude, double longitude) {
        Geocoder gc = new Geocoder(UIUtils.getContext(), Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = locationList.get(0);//得到Address实例
        String countryName = address.getCountryName();//得zfyigf WDWJ到国家名称，比方：中国

//        String locality = address.getLocality();//得到城市名称，比方：北京市

        return countryName;
       /* for (int i = 0; address.getAddressLine(i) != null; i++) {
            String addressLine = address.getAddressLine(i);//得到周边信息。包含街道等。i=0，得到街道名称
            Log.i(TAG, "addressLine = " + addressLine);
        }*/
    }

}
