package com.example.liao.isuke.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityPhoneBinding;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liao on 2018/3/20.
 */

public class PhoneAty extends BaseAty {

    private ActivityPhoneBinding mBinding;

    private List<String> dataset;
    private String type;

    @Override
    protected void init() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_phone);

    }

    @Override
    protected void initData() {
        dataset = new ArrayList<>();
        String[] stringArr = UIUtils.getStringArr(R.array.cities_data);
        dataset.clear();
        for (String s : stringArr) {
            dataset.add(s);
        }
        mBinding.areaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

    }

    private void showPop() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String s = dataset.get(options1);
                mBinding.area.setText(s.substring(0, s.indexOf("+")));
                mBinding.areaNum.setText(s.substring(s.indexOf("+"), s.length()));

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
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhone();
            }
        });
    }

    private void checkPhone() {
        String phoneNum = mBinding.phoneNum.getText().toString().trim();
        if (!Utils.isPhoneNum(phoneNum)) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.registeraty_phone_error));
            return;
        }

        String trim = mBinding.areaNum.getText().toString().trim();
        String num = trim.substring(1, trim.length());
//        UIUtils.toast(PhoneAty.this, "num..." + num);
        if (type.equals("forget")) {
            Intent intent = new Intent(PhoneAty.this, ForgetPwdAty.class);
            intent.putExtra("phone", phoneNum);
            intent.putExtra("num", num);
            startActivity(intent);
        } else if (type.equals("login")) {
            Intent intent = new Intent(PhoneAty.this, VerifyCodeLoginAty.class);
            intent.putExtra("phone", phoneNum);
            intent.putExtra("num", num);
            startActivity(intent);
        } else {
            Intent intent = new Intent(PhoneAty.this, VerifyCodeAty.class);
            intent.putExtra("phone", phoneNum);
            intent.putExtra("num", num);
            startActivity(intent);
        }
    }


}
