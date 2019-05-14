package com.example.liao.isuke.request;

import com.example.liao.isuke.R;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.NetUtils;
import com.example.liao.isuke.utils.UIUtils;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/12/18.
 */

public abstract class SuperBaseRequest {
    private String error_msg = "";

    public String toLoadData() throws Exception {

        Call<String> call = Call();
        if (NetUtils.getNetWorkState()==false) {
            UIUtils.toast(UIUtils.getString(R.string.phone_nonet));
            return null;
        }
        UIUtils.print("request!!!" + NetUtils.getNetWorkState());
        if (call == null) {
            UIUtils.toast(UIUtils.getString(R.string.request_failed));
            return null;
        }
        String body = call.execute().body();
        if (body == null) {
            UIUtils.toast( UIUtils.getString(R.string.request_failed));
            return null;
        }
        UIUtils.print("request!!!" + call.request().toString());
        UIUtils.print("request!!!" + call.request().headers());
        UIUtils.print("request!!!" + body.toString());


        JSONObject jsonObject = new JSONObject(body);
        final String resultcode = jsonObject.getString("code");
        if (resultcode.equals(RetrofitUtils.SUCCESS)) {
            return jsonObject.getString(String());
        } else {
//            error_msg = jsonObject.getString("msg");
        /*    UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
//                    UIUtils.toast(UIUtils.getContext(), error_msg);
                    UIUtils.checkResult(resultcode);
                }
            });*/

        }
        return null;
    }

    protected abstract String String();

    protected abstract Call<String> Call();


}