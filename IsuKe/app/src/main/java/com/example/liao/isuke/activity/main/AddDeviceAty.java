package com.example.liao.isuke.activity.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityAddDeviceBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.factory.ThreadPoolFactory;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.net.UDPClient;
import com.example.liao.isuke.utils.CmdDataUtils;
import com.example.liao.isuke.utils.NetUtils;
import com.example.liao.isuke.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2018/3/22.
 */

public class AddDeviceAty extends BaseAty {

    private ActivityAddDeviceBinding mBinding;

    private boolean isLight = true;
    private int isLightNum = 0;
    private String connectWifiSsid = "";

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_device);
        UIUtils.setDaoHangLan(this, this);

        /*BaseApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                while (isLight) {

                    if (isLightNum % 2 == 0) {
                        mBinding.light.setBackgroundResource(R.mipmap.ic_promptflash01);
                    } else {
                        mBinding.light.setBackgroundResource(R.mipmap.ic_promptflash02);
                    }
                    isLightNum++;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        mHandler.sendEmptyMessageDelayed(0, 500);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isLightNum % 2 == 0) {
                mBinding.light.setBackgroundResource(R.mipmap.ic_promptflash01);
            } else {
                mBinding.light.setBackgroundResource(R.mipmap.ic_promptflash02);
            }
            isLightNum++;
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLight = false;
    }

    @Override
    protected void initData() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconpw != null && iconpw.isShowing()) {

                } else
                    finish();
            }
        });

        mBinding.titleTheme.setText(UIUtils.getString(R.string.add_device));
    }

    @Override
    public void onBackPressed() {
        if (iconpw != null && iconpw.isShowing())
            iconpw.dismiss();
        else
            super.onBackPressed();
    }

    @Override
    protected void initEvent() {


        mBinding.startConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconpw != null && iconpw.isShowing()) {
                    iconpw.dismiss();
                } else {
                    checkWifi();
                }
                requestAddDevice();
            }
        });
    }

    private void checkWifi() {
        //获取系统服务
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取状态
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //判断wifi已连接的条件
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {

            showDefultPop();
        } else {
            showPop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String s = NetUtils.getConnectWifiSsid(this);
        if (s != null)
            connectWifiSsid = s.substring(1, s.length() - 1);

        UIUtils.print("connect..." + connectWifiSsid);
    }

    private PopupWindow iconpw;

    private void showPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_config, null);
        iconpw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        iconpw.setFocusable(false);
        iconpw.setOutsideTouchable(false);
        iconpw.setBackgroundDrawable(new BitmapDrawable());
        iconpw.setAnimationStyle(R.style.myanimation);
        iconpw.setWidth(UIUtils.dp2px(this, 330));
        iconpw.showAtLocation(view, Gravity.CENTER, 0, 100);
        iconpw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(1.0f, AddDeviceAty.this);
            }
        });

        UIUtils.setBackgroundAlpha(0.5f, AddDeviceAty.this);


        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconpw.dismiss();
            }
        });

        view.findViewById(R.id.go_conn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                iconpw.dismiss();
            }
        });

    }

    private void requestAddDevice() {
        Map map = RequestData.getAddDevice("54354354354H");//54354354354H
        if (map != null)
            addDevice(map);
    }

    private void addDevice(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().relativeDevice(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body().toString();

                Log.e("成功", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result1 = (String) jsonObject.get("resultcode");

                    if (result1.equals(RetrofitUtils.SUCCESS)) {
                        UIUtils.toast(AddDeviceAty.this, UIUtils.getString(R.string.add_success));
                        HomeFragment.needRefresh = true;
                        finish();
                    } else {
                        String error_msg = (String) jsonObject.get("error_msg");
                        UIUtils.toast(AddDeviceAty.this, error_msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AddDeviceAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });
    }

    private PopupWindow defultpop;
    private EditText mNickName;

    private void showDefultPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_editwifi, null);
        mNickName = view.findViewById(R.id.nickname);
        defultpop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        defultpop.setFocusable(true);
        defultpop.setOutsideTouchable(false);
        defultpop.setBackgroundDrawable(new BitmapDrawable());
        defultpop.setAnimationStyle(R.style.myanimation);
        defultpop.setWidth(UIUtils.dp2px(this, 330));
        defultpop.showAtLocation(view, Gravity.CENTER, 0, 100);
        defultpop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.HideKeyboard(mNickName);
                UIUtils.setBackgroundAlpha(1.0f, AddDeviceAty.this);
            }
        });
        //防止虚拟软键盘被弹出菜单遮住
        defultpop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        UIUtils.setBackgroundAlpha(0.5f, AddDeviceAty.this);

        TextView wifiName = view.findViewById(R.id.wifiname);
        wifiName.setText(connectWifiSsid);
        view.findViewById(R.id.replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                defultpop.dismiss();
            }
        });
        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defultpop.dismiss();
            }
        });
        final EditText nickname = view.findViewById(R.id.nickname);
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = nickname.getText().toString().length();
                if (length < 8) {
                    UIUtils.toast(AddDeviceAty.this, UIUtils.getString(R.string.wifipwd_length));
                    return;
                }
                Intent intent = new Intent(AddDeviceAty.this, DeviceWifiAty.class);
                intent.putExtra("wifiname", connectWifiSsid);
                intent.putExtra("wifipwd", nickname.getText().toString());
                configWifiName = connectWifiSsid;
                configWifiPwd = nickname.getText().toString();
                startActivityForResult(intent, 1);

            }
        });

    }

    private String configWifiName = "";
    private String configWifiPwd = "";
    private UDPClient udpClient;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (defultpop != null)
            defultpop.dismiss();
        if (resultCode == 1) {
            udpClient = new UDPClient("192.168.4.1");
            ThreadPoolFactory.getNormalThread().executeTask(udpClient);
            ThreadPoolFactory.getNormalThread().executeTask(new Runnable() {
                @Override
                public void run() {
                    configWifi();
                }
            });


        }
    }

    private void configWifi() {
        byte[] wifiDataByte = CmdDataUtils.getWifiDataByte(configWifiName, configWifiPwd);
        UIUtils.print("udpClient.........." + CmdDataUtils.ByteArrayToHex(wifiDataByte));
        boolean send = udpClient.send(wifiDataByte);
        UIUtils.print("udpClient.........." + send);
    }
}
