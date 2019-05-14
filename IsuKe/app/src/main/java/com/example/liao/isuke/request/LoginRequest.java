package com.example.liao.isuke.request;

import com.example.liao.isuke.base.Constant;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;
import com.google.gson.Gson;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/12/18.
 */

public class LoginRequest extends BaseBeanRequest<AppUserInfo> {
    private String username;
    private String pwd;
    private String type;

    public LoginRequest(String username, String pwd, String type) {
        this.username = username;
        if (type.equals("0"))
            this.pwd = Utils.stringMD5(pwd);
        else
            this.pwd = pwd;
        this.type = type;
    }

    @Override
    protected AppUserInfo parseJson(String jsonString) {
        UIUtils.print("request!!!return..jsonString.." + jsonString);
        return new Gson().fromJson(jsonString, AppUserInfo.class);
    }

    @Override
    protected String setString() {
        return Constant.Data;
    }

    @Override
    protected Call<String> setCall() {
        return RetrofitUtils.getRequestServies().login(RequestData.getLoginParameter(username, pwd, type));
    }


}
