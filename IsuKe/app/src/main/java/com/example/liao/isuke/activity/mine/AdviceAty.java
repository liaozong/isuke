package com.example.liao.isuke.activity.mine;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.databinding.ActivityAdviceBinding;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2018/3/19.
 */

public class AdviceAty extends BaseAty {

    private ActivityAdviceBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_advice);
        UIUtils.setDaoHangLan(this,this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.suggestion));
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubmit();

            }
        });
    }

    private void clickSubmit() {
        if(mBinding.etAdvice.getText().toString().trim().isEmpty()){
            Toasty.info(AdviceAty.this,"请填写建议").show();
            return;
        }

        Map map = RequestData.getAdviceMap(mBinding.etAdvice.getText().toString().trim(),mBinding.etContact.getText().toString().trim(),"device_id");
        getAdviceResult(map);

    }

    private String result = "";
    private String getAdviceResult(Map map) {
        Call<String> call = RetrofitUtils.getRequestServies().userAdvice(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        result = response.body().toString();
                        Log.e("服务器响应成功", result);

                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        /**修改成功后的处理：将BaseApplication的nickname修改后保存
                         * 更新 个人信息 的的名字显示，更新 个人中心 的名字显示
                         */
                        if (result1.equals(RetrofitUtils.SUCCESS)) {

                            Toasty.success(AdviceAty.this,getString(R.string.device_success), Toast.LENGTH_SHORT,false).show();
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(AdviceAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", e.toString());
                    }

                } else {
                    UIUtils.toast(AdviceAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AdviceAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });
        return result;
    }
}
