package com.example.liao.isuke.base;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.liao.isuke.utils.NetUtils;

public class NetConnectionChangeReceiver extends BroadcastReceiver {

    NetUtils.NetWorkListener listener ;

    public NetConnectionChangeReceiver(NetUtils.NetWorkListener listener){
        this.listener = listener;
    }

    AlertDialog.Builder builder;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            listener.onDisconnected();
        }else {
            listener.onConnected();
        }
    }
}
