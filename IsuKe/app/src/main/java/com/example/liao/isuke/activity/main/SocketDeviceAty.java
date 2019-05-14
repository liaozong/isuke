package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.adapter.SocketAdapter;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.DeviceDetail;
import com.example.liao.isuke.databinding.ActivitySocketDeviceBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2018/3/26.
 */

public class SocketDeviceAty extends BaseAty {

    private ActivitySocketDeviceBinding mBinding;
    public String type;
    private String deviceid;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_socket_device);
        UIUtils.setDaoHangLan(this, this);

    }

    private List<DeviceDetail> mDeviceInfo = new ArrayList<>();

    @Override
    protected void init() {
        Intent intent = getIntent();
        deviceid = intent.getStringExtra("deviceid");
        type = intent.getStringExtra("type");
    }

    private SocketAdapter mAdapter;
    private List<DeviceDetail> mData = new ArrayList();

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.socket));

        Map map = RequestData.getDeviceDetail(deviceid, type);
        if (map != null)
            requestDeviceDetail(map);

        mAdapter = new SocketAdapter(R.layout.list_socket, mData);

        Recycler.setRecycler(this, mBinding.socketRecyclerview, mAdapter);
        startMain();
    }

    private void setBackgroundHight() {
        ViewGroup.LayoutParams layoutParams = mBinding.socketBackground.getLayoutParams();
        layoutParams.height = UIUtils.dp2px(this, 80 * mData.size() + 50);
        mBinding.socketBackground.setLayoutParams(layoutParams);
    }


    private boolean isOpenMain = false;

    @Override
    protected void initEvent() {

        mBinding.titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SocketDeviceAty.this, DeviceOperateAty.class));
            }
        });

        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.mainswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpenMain) {
                    closeMain();
                } else {
                    startMain();
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                switch (id) {
                    case R.id.socket_name_edit:
                        showEditNamePop(position);
                        break;
                    case R.id.socket:

                        DeviceDetail deviceDetail = mData.get(position);
                        String device_sub_status = deviceDetail.getDevice_sub_status() + "";
                        if (device_sub_status.equals("0")) {
                            device_sub_status = "1";
                        } else {
                            device_sub_status = "0";
                        }
                        requestOperateSwitch(position, device_sub_status);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void requestOperateSwitch(int position, String device_sub_status) {
        DeviceDetail deviceDetail = mData.get(position);
        Map map = RequestData.operateSwitch(deviceid, deviceDetail.getTotal_switch_exist() + "", deviceDetail.getSwitch_type() + "", HomeFragment.clickDevice.getDevice_belong_type() + "",
                deviceDetail.getDevice_sub_id() + "", device_sub_status, deviceDetail.getDevice_sub_alias(), deviceDetail.getDevice_sub_num() + "");
        UIUtils.print("map..." + map.toString());
        if (map != null)
            operateSwitch(map, position, device_sub_status);
    }

    private void operateSwitch(Map map, final int position, final String device_sub_status) {
        Call<String> call = RetrofitUtils.getRequestServies().operateSwitch(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            DeviceDetail deviceDetail = mData.get(position);
                            deviceDetail.setDevice_sub_status(Integer.parseInt(device_sub_status));
                            mData.set(position, deviceDetail);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SocketDeviceAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }


    private void startMain() {
        isOpenMain = true;
        mBinding.mainswitch.setBackgroundResource(R.mipmap.ic_turnonturnoff1);
        mBinding.mainstate.setText(UIUtils.getString(R.string.smartpower_start));
    }

    private void closeMain() {
        isOpenMain = false;
        mBinding.mainswitch.setBackgroundResource(R.mipmap.ic_turnonturnoff2);
        mBinding.mainstate.setText(UIUtils.getString(R.string.smartpower_stop));
    }

    public static boolean isFinish = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (isFinish) {
            isFinish = true;
            finish();

        }
    }

    private PopupWindow iconpw;
    private EditText mDeviceName;

    private void showEditNamePop(final int position) {
        View view = getLayoutInflater().inflate(R.layout.pop_edit_devicename, null);
        mDeviceName = view.findViewById(R.id.devicename);

        iconpw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        iconpw.setFocusable(true);
        iconpw.setOutsideTouchable(false);
        iconpw.setBackgroundDrawable(new BitmapDrawable());
        iconpw.setAnimationStyle(R.style.myanimation);
        iconpw.setWidth(UIUtils.dp2px(this, 330));
        iconpw.showAtLocation(view, Gravity.CENTER, 0, 100);
        iconpw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.HideKeyboard(mDeviceName);
                UIUtils.setBackgroundAlpha(1.0f, SocketDeviceAty.this);
            }
        });
        //防止虚拟软键盘被弹出菜单遮住
        iconpw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        UIUtils.setBackgroundAlpha(0.5f, SocketDeviceAty.this);


      /*  if (clickNum == 1)
            mDeviceName.setText(mBinding.socket1Name.getText().toString());
        else if (clickNum == 2)
            mDeviceName.setText(mBinding.socket2Name.getText().toString());
        else
            mDeviceName.setText(mBinding.socket3Name.getText().toString());*/
        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconpw.dismiss();
                UIUtils.HideKeyboard(mDeviceName);
            }
        });

        view.findViewById(R.id.commit_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mDeviceName.getText().toString().trim();
                requestDeviceAlias(position, name);
                iconpw.dismiss();
            }
        });

    }

    /* 为设备设置别名 device/deviceAlias   device_sub_id  ,app_user_id  ,language,alias，device_share_id ，type（own ,share）*/
    private void requestDeviceAlias(int position, String name) {
        DeviceDetail deviceDetail = mData.get(position);
        Map map = RequestData.setDeviceAlise(deviceDetail.getDevice_sub_id() + "", name, type);
        if (map != null)
            changeDeviceAlias(map, position);
    }

    private void changeDeviceAlias(final Map<String, String> map, final int position) {

        Call<String> call = RetrofitUtils.getRequestServies().deviceAlias(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        String result = response.body().toString();
                        Log.e("成功", response.body().toString());
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            DeviceDetail deviceDetail = mData.get(position);
                            deviceDetail.setDevice_sub_alias(map.get("device_sub_alias"));
                            mData.set(position, deviceDetail);
                            mAdapter.notifyDataSetChanged();
                            UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.change_success));
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SocketDeviceAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", "异常");
                    }
                } else {
                    UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }

    private void requestDeviceDetail(Map<String, String> map) {

        Call<String> call = RetrofitUtils.getRequestServies().getDeviceDetail(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        String result = response.body().toString();
                        Log.e("成功", response.body().toString());
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            String deviceDetail = jsonObject.getString("deviceDetail");
                            Gson gson = new Gson();
                            List<DeviceDetail> mlist = gson.fromJson(deviceDetail, new TypeToken<List<DeviceDetail>>() {
                            }.getType());
                            mDeviceInfo.clear();
                            mDeviceInfo.addAll(mlist);

                            setData();

                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SocketDeviceAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", "异常");
                    }
                } else {
                    UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SocketDeviceAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }

    private void setData() {
        mData.clear();
        for (int x = 0; x < mDeviceInfo.size(); x++) {
            String s = mDeviceInfo.get(x).getDevice_sub_num() + "";
            if (!s.equals("0"))
                mData.add(mDeviceInfo.get(x));
        }
        mAdapter.notifyDataSetChanged();

        setBackgroundHight();
    }

}
