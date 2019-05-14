package com.example.liao.isuke.net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestSerives {

    @POST("api/login")
    @FormUrlEncoded
//Call<LoginData>
    Call<String> login(
            @FieldMap Map<String, String> map
    );

    @POST("api/forgetPwd")
    @FormUrlEncoded
    Call<String> forGetPwd(
            @FieldMap Map<String, String> map
    );

    @POST("api/changePwd")
    @FormUrlEncoded
    Call<String> changePwd(
            @FieldMap Map<String, String> map
    );

    @POST("api/userAdvice")
    @FormUrlEncoded
    Call<String> userAdvice(
            @FieldMap Map<String, String> map
    );

    @POST("api/getVerifyCode")
    @FormUrlEncoded
    Call<String> getVerifyCode(
            @FieldMap Map<String, String> map
    );

    @POST("api/register")
    @FormUrlEncoded
    Call<String> register(
            @FieldMap Map<String, String> map
    );

    @POST("api/normalProblem")
    @FormUrlEncoded
    Call<String> normalProblem(
            @FieldMap Map<String, String> map

    );

    @POST("device/getDeviceManage")
    @FormUrlEncoded
    Call<String> getDeviceManage(
            @FieldMap Map<String, String> map
    );

    @POST("device/getDeviceDetail")
    @FormUrlEncoded
    Call<String> getDeviceDetail(
            @FieldMap Map<String, String> map
    );

    @POST("device/getDeviceType")
    @FormUrlEncoded
    Call<String> getDeviceType(
            @FieldMap Map<String, String> map
    );

    @POST("device/devicePower")
    @FormUrlEncoded
    Call<String> devicePower(
            @FieldMap Map<String, String> map
    );

    @POST("user/shareDeviceToUser")
    @FormUrlEncoded
    Call<String> shareDeviceToUser(
            @FieldMap Map<String, String> map
    );

    @POST("user/getSharedUser")
    @FormUrlEncoded
    Call<String> getSharedUser(
            @FieldMap Map<String, String> map
    );
    @POST("user/setShareUserAlias")
    @FormUrlEncoded
    Call<String> setShareUserAlias(
            @FieldMap Map<String, String> map
    );

    @POST("user/unShareDeviceToUser")
    @FormUrlEncoded
    Call<String> unShareDeviceToUser(
            @FieldMap Map<String, String> map
    );

    @POST("user/addShareUser")
    @FormUrlEncoded
    Call<String> addShareUser(
            @FieldMap Map<String, String> map
    );

    @POST("device/devicePowerDetail")
    @FormUrlEncoded
    Call<String> devicePowerDetail(
            @FieldMap Map<String, String> map
    );

    @POST("device/deviceAlias")
    @FormUrlEncoded
    Call<String> deviceAlias(
            @FieldMap Map<String, String> map
    );

    @POST("device/deleteDevice")
    @FormUrlEncoded
    Call<String> deleteDevice(
            @FieldMap Map<String, String> map
    );

    @POST("device/relativeDevice")
    @FormUrlEncoded
    Call<String> relativeDevice(
            @FieldMap Map<String, String> map
    );
    @POST("device/operateSwitch")
    @FormUrlEncoded
    Call<String> operateSwitch(
            @FieldMap Map<String, String> map
    );

    @POST("timedTask/getTimedTask")
    @FormUrlEncoded
    Call<String> getTimedTask(
            @FieldMap Map<String, String> map
    );

    @POST("timedTask/addTimedTask")
    @FormUrlEncoded
    Call<String> addTimedTask(
            @FieldMap Map<String, String> map
    );
    @POST("timedTask/editTimedTask")
    @FormUrlEncoded
    Call<String> editTimedTask(
            @FieldMap Map<String, String> map
    );
    @POST("timedTask/deleteTimedTask")
    @FormUrlEncoded
    Call<String> deleteTimedTask(
            @FieldMap Map<String, String> map
    );

    @POST("scene/queryScene")
    @FormUrlEncoded
    Call<String> queryScene(
            @FieldMap Map<String, String> map
    );

    @POST("scene/addScene")
    @FormUrlEncoded
    Call<String> addScene(
            @FieldMap Map<String, String> map
    );
    @POST("scene/querySceneCondition")
    @FormUrlEncoded
    Call<String> querySceneCondition(
            @FieldMap Map<String, String> map
    );

    @POST("user/modifyUserInfo")
    @FormUrlEncoded
    Call<String> modifyUserInfo(
            @FieldMap Map<String, String> map
    );

    @POST("api/messageCenter")
    @FormUrlEncoded
    Call<String> getMessage(
            @FieldMap Map<String, String> map
    );

}