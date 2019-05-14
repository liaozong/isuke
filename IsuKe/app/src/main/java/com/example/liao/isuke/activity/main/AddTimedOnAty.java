package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityAddTimedonBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimedOnAty extends BaseAty {


    private ActivityAddTimedonBinding mBinding;
    private List<String> mOptionsItems;
    private List<String> mOptionsItems1;
    private String click = "";

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_timedon);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String purpose = intent.getStringExtra("purpose");
    }


    private List<String> dataset;

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(R.string.add_timed);

        dataset = new ArrayList<>();
        dataset.clear();
        dataset.add(UIUtils.getString(R.string.close_device));
        dataset.add(UIUtils.getString(R.string.open_device));

        setWheelView();

        mBinding.isexecute.setText(UIUtils.getString(R.string.open_device));
    }

    private void setWheelView() {
        setData();


    }

    private void setData() {
        mBinding.wheelview.setCyclic(false);

        mOptionsItems = new ArrayList<>();
        for (int x = 0; x < 24; x++) {
            if (x < 10)
                mOptionsItems.add("0" + x);
            else
                mOptionsItems.add(x + "");

        }

        mBinding.wheelview.setAdapter(new ArrayWheelAdapter(mOptionsItems));


        mBinding.wheelview1.setCyclic(false);

        mOptionsItems1 = new ArrayList<>();
        for (int x = 0; x < 60; x++) {
            if (x < 10)
                mOptionsItems1.add("0" + x);
            else
                mOptionsItems1.add(x + "");

        }

        mBinding.wheelview1.setAdapter(new ArrayWheelAdapter(mOptionsItems1));

    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimedOnAty.this, RepeatAty.class);
                intent.putExtra("click", mBinding.repeatText.getText().toString());
                startActivityForResult(intent, 100);
            }
        });
        mBinding.execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCommit();
            }
        });

        mBinding.wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                UIUtils.print("click..." + index);
                if (index < 10) {
                    hour = "0" + index;
                } else {
                    hour = index + "";
                }
            }
        });
        mBinding.wheelview1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                UIUtils.print("click..." + index);
                if (index < 10) {
                    minute = "0" + index;
                } else {
                    minute = index + "";
                }
            }
        });
    }

    private String hour = "00";
    private String minute = "00";

    private void checkCommit() {
        if (click.length() == 0) {
            UIUtils.toast(this, UIUtils.getString(R.string.choose_date_exe));
            return;
        }
        addTimedTask();
    }

    private void addTimedTask() {
        String s = mBinding.isexecute.getText().toString();
        String action = "";
        if (s.equals(UIUtils.getString(R.string.open_device))) {
            action = "1";
        } else {
            action = "0";
        }

        Map map = RequestData.addTimedTask(HomeFragment.clickDevice.getDevice_id() + "", HomeFragment.clickDevice.getDevice_belong_type() + "", hour + minute,
                click, action);
        if (map != null)
            requestAddTimer(map);
    }

    private void requestAddTimer(Map map) {
        Call<String> call = RetrofitUtils.getRequestServies().addTimedTask(map);//传入我们请求的键值对的值

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
                            UIUtils.toast(AddTimedOnAty.this, UIUtils.getString(R.string.add_success));
                            TimedOnAty.timeRefresh = true;
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(AddTimedOnAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(AddTimedOnAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AddTimedOnAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private void showPop() {


        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String s = dataset.get(options1);
                mBinding.isexecute.setText(s);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            click = data.getStringExtra("click");
            UIUtils.print("click!!!" + click);
            if (click.length() != 0) {
                String s = UIUtils.showWeek(click);
                setRepeat(s);
            } else {
                setRepeat("");
            }
        }
    }

    private void setRepeat(String click) {
        mBinding.repeatText.setText(click);
    }
}
