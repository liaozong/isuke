package com.example.liao.isuke.activity.scene;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.adapter.AddSceneAdapter;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.SceneRule;
import com.example.liao.isuke.databinding.ActivityAddSceneBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSceneAty extends BaseAty implements LocationListener {

    private ActivityAddSceneBinding mBinding;
    private AddSceneAdapter adapter;
    private List<Integer> mRuleNum = new ArrayList<>();

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_scene);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {
        dataset = new ArrayList<>();
        mRuleNum.add(0);
        mRuleNum.add(1);
        mRuleNum.add(2);
        mRuleNum.add(3);
        mRuleNum.add(4);
        mRuleNum.add(5);
        mBinding.titleTheme.setText(UIUtils.getString(R.string.add_scene));

        com.example.liao.isuke.location.Location.toLoaction(this);

        adapter = new AddSceneAdapter(R.layout.list_addscene, mRuleNum);

        Recycler.setGrid3Recycler(mBinding.addsceneRecyclerview, adapter);

        requestSceneRule();
    }

    private void requestSceneRule() {
        if (BaseApplication.getUser() == null)
            return;
        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("language", RetrofitUtils.language);
        String timestamp = System.currentTimeMillis() + "";
        map.put("timestamp", timestamp);
        map.put("token", RetrofitUtils.getToken(timestamp));
        getSceneRule(map);
    }

    private void getSceneRule(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().querySceneCondition(map);//传入我们请求的键值对的值

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
                            Gson gson = new Gson();
                            SceneRule sceneRule = gson.fromJson(result1, SceneRule.class);


                            adapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("erro2r_msg");
                            UIUtils.toast(AddSceneAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(AddSceneAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AddSceneAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingWei();
            }
        });
        mBinding.chooseState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("关闭设备");
                dataset.add("开启设备");

                showPop();
            }
        });

      /*  mBinding.humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("干燥");
                dataset.add("舒适");
                dataset.add("潮湿");

                showPop();
            }
        });
        mBinding.air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("优");
                dataset.add("良");
                dataset.add("污染");

                showPop();
            }
        });
        mBinding.pmnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("优");
                dataset.add("良");
                dataset.add("污染");

                showPop();
            }
        });
        mBinding.weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("晴天");
                dataset.add("阴天");
                dataset.add("雨天");
                dataset.add("雪天");
                dataset.add("雾天");

                showPop();
            }
        });
        mBinding.sunset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.clear();
                dataset.add("日出");
                dataset.add("日落");

                showPop();
            }
        });*/

        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAddScene();
            }
        });
    }

    private void requestAddScene() {
        if (BaseApplication.getUser() == null)
            return;

        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("country", "country");
        map.put("city", "city");
        map.put("dicts_id", "dicts_id");
        map.put("condition", "condition");
        map.put("compare_value", "compare_value");
        map.put("deviceIds", "deviceIds");
        map.put("open_status", "open_status");
        map.put("smart_scene_name", "smart_scene_name ");
        map.put("task_excute_status", "task_excute_status ");

        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("orgId", RetrofitUtils.orgId + "");
        String timestamp = System.currentTimeMillis() + "";
        map.put("timestamp", timestamp);
        map.put("token", RetrofitUtils.getToken(timestamp));
        addScene(map);
    }

    private void addScene(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().addScene(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UIUtils.toast(AddSceneAty.this, "code......." + response.code());

                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {

                        } else {
                            String error_msg = (String) jsonObject.get("erro2r_msg");
                            UIUtils.toast(AddSceneAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(AddSceneAty.this, UIUtils.getString(R.string.request_failed));

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AddSceneAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private List<String> dataset;

    private void showPop() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String s = dataset.get(options1);
                mBinding.state.setText(s);

            }

        })
                .setSubmitText(UIUtils.getString(R.string.ok))//确定按钮文字
                .setCancelText(UIUtils.getString(R.string.cancle))//取消按钮文字
                .setTitleText("")//标题
                .setSubCalSize(16)//确定和取消文字大小
                .setTitleSize(14)//标题文字大小
                .setTitleColor(UIUtils.getColor(R.color.text_color_normal))//标题文字颜色
                .setSubmitColor(UIUtils.getColor(R.color.org_login_word))//确定按钮文字颜色
                .setCancelColor(UIUtils.getColor(R.color.text_color_normal))//取消按钮文字颜色
                .setTitleBgColor(UIUtils.getColor(R.color.baise))//标题背景颜色 Night mode
                .setBgColor(UIUtils.getColor(R.color.isuke_background))//滚轮背景颜色 Night mode
                .setContentTextSize(13)//滚轮文字大小
//                .setLinkage(true)//设置是否联动，默认true
                .setLabels("", "", "")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(dataset);// pvOptions.setPicker(options1Items);//添加数据源
        pvOptions.show();
    }

    private void dingWei() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())    //此方法必须调用
                .enableAnimation(true)    //启用动画效果
//                .setAnimationStyle(anim)    //自定义动画
                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，默认为null（定位失败
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (data != null)
                            Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 2000);
                    }
                })
                .show();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    mBinding.area.setText("定位失败");
                    break;
                case 1:
                    String locationInfo = (String) msg.obj;
                    mBinding.area.setText(locationInfo);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLocationChanged(Location location) {
        //得到纬度
        double latitude = location.getLatitude();
        //得到经度
        double longitude = location.getLongitude();

        String locationInfo = com.example.liao.isuke.location.Location.getLocationInfo(latitude, longitude);

        if (locationInfo == null) {

            mHandler.sendEmptyMessage(0);
        } else {
            Message message = mHandler.obtainMessage();
            message.obj = locationInfo;
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
