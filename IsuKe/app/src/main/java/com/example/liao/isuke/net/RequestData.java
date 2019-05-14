package com.example.liao.isuke.net;

import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.utils.Utils;

import java.util.Map;

public class RequestData {

    public static Map getToken() {
        Map<String, String> map = HashMapFactory.getHashMap();
        String timestamp = System.currentTimeMillis() + "";
        map.put("timestamp", timestamp);
        map.put("phone", BaseApplication.getUser().getPhone());
        map.put("token", RetrofitUtils.getToken(timestamp));
        return map;
    }

    public static Map getVerifyParameter(String phone, String num, String tag) {
        Map<String, String> map = HashMapFactory.getHashMap();

        map.put("phone", phone);
        map.put("language", RetrofitUtils.language);
        map.put("country_code", num);
        map.put("orgId", RetrofitUtils.orgId + "");
        map.put("tag", tag);//register , reset, login
        return map;
    }

    public static Map getForgetPwdParameter(String phone, String verifyCode, String pwd) {
        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("phone", phone);
        map.put("verifycode", verifyCode);
        map.put("language", RetrofitUtils.language);
        map.put("password", Utils.stringMD5(pwd));
        map.put("orgId", RetrofitUtils.orgId + "");
        return map;
    }

    public static Map getRgisterParameter(String phone, String num, String verifyCode, String pwd, String domian) {
        Map<String, String> map = HashMapFactory.getHashMap();

        map.put("phone", phone);
        map.put("language", RetrofitUtils.language);
        map.put("country_code", num);
        map.put("password", Utils.stringMD5(pwd));
        map.put("verifycode", verifyCode);
        map.put("appSerialNum", RetrofitUtils.appSerialNum);
        map.put("appOrgCode", RetrofitUtils.appOrgCode);
        map.put("orgId", RetrofitUtils.orgId + "");
        map.put("time_domain", domian);
        return map;
    }
    public static Map getLoginParameter(String phone, String pwd, String type) {//String verifyCode,
        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("phone", phone);
        map.put("val", pwd);
//        map.put("verifyCode", verifyCode);
        map.put("type", type);
        return map;
    }

    /*public static Map getLoginParameter(String phone, String num, String verifyCode, String pwd, String domian, String type) {
        Map<String, String> map = HashMapFactory.getHashMap();
        String timestamp = System.currentTimeMillis() + "";
        map.put("language", RetrofitUtils.language);
        map.put("login_phone", phone);
        map.put("password", Utils.stringMD5(pwd));
        map.put("login_timestamp", timestamp);
        if (pwd.length() == 0)
            map.put("login_token", Utils.stringMD5(phone + verifyCode + timestamp + "app"));
        else
            map.put("login_token", Utils.stringMD5(phone + Utils.stringMD5(pwd) + timestamp + "app"));
        map.put("type", type);
        map.put("country_code", num);
        map.put("verifycode", verifyCode);
        map.put("appSerialNum", RetrofitUtils.appSerialNum);
        map.put("appOrgCode", RetrofitUtils.appOrgCode);
        map.put("orgId", RetrofitUtils.orgId + "");
        map.put("time_domain", domian);
        return map;
    }*/

    public static Map getDeviceList() {
        if (BaseApplication.getUser() == null)
            return null;

        Map map = getToken();
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("orgId", RetrofitUtils.orgId + "");

        return map;
    }

    public static Map getTimedTask(String deviceid) {
        if (BaseApplication.getUser() == null)
            return null;

        Map map = getToken();
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("device_id", deviceid);
        return map;
    }

    public static Map deleteTimedTask(String timedtask_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("timedtask_id", timedtask_id);
        return map;
    }

    public static Map editTimedTask(String timedtask_id, String timedtask_status,
                                    String timedtask_time, String timedtask_days, String timedtask_action) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("timedtask_id", timedtask_id);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id());
        map.put("timedtask_status", timedtask_status);
        map.put("timedtask_time", timedtask_time);
        map.put("timedtask_days", timedtask_days);
        map.put("timedtask_action", timedtask_action);
        return map;
    }

    public static Map addTimedTask(String deviceid, String device_belong_type,
                                   String timedtask_time, String timedtask_days, String timedtask_action) {
        if (BaseApplication.getUser() == null)
            return null;

        Map map = getToken();
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("device_id", deviceid);
        map.put("device_belong_type", device_belong_type);//自己的还是分享的设备0自己 1分享
        map.put("timedtask_time", timedtask_time);//时间 ：0935 必须四位
        map.put("timedtask_days", timedtask_days);//124567 周几执行
        map.put("timedtask_action", timedtask_action);//关闭还是开启设备 0关闭 1开启
        return map;
    }

    public static Map getAddDevice(String device_no) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("device_no", device_no);
        map.put("orgId", RetrofitUtils.orgId + "");
        return map;
    }

    public static Map operateSwitch(String device_id, String total_switch_exist, String switch_type, String device_belong_type,
                                    String device_sub_id, String device_sub_status, String device_sub_alias, String device_sub_num) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("device_id", device_id);
        map.put("language", RetrofitUtils.language);
        map.put("total_switch_exist", total_switch_exist);//是否有总开关0：没有，1:有
        map.put("switch_type", switch_type);//操作类型 0：总开关，1子开关
        map.put("device_belong_type", device_belong_type);//操作自己的设备还是操作共享的设备 0：自己操作，1共享操作
        map.put("device_sub_id", device_sub_id);//子id
        map.put("device_sub_status", device_sub_status);//0：关闭，1：开启
        map.put("device_sub_alias", device_sub_alias);
        map.put("device_sub_num", device_sub_num);
        return map;
    }

    public static Map deleteDevice(String deviceId_userId) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("deviceId_userId", deviceId_userId);
        map.put("orgId", RetrofitUtils.orgId + "");
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        return map;
    }

    public static Map getDeviceDetail(String deviceid, String type) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("device_id", deviceid);
        map.put("device_belong_type", type);
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("orgId", RetrofitUtils.orgId + "");
        return map;
    }

    public static Map requestScene() {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();

        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("orgId", RetrofitUtils.orgId + "");

        return map;
    }


    public static Map setDeviceAlise(String device_sub_id, String name, String device_belong_type) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("device_sub_id", device_sub_id);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("language", RetrofitUtils.language);
        map.put("device_sub_alias", name);
        map.put("device_belong_type", device_belong_type);

        return map;
    }

    public static Map getDevicePowerInfo(String device_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("device_id", device_id);

        return map;
    }

    public static Map devicePowerDetail(String year, String month, String device_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("year", year);
        map.put("month", month);
        map.put("language", RetrofitUtils.language);
        map.put("device_id", device_id);
        return map;
    }

    public static Map getSharedUser(String device_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("device_id", device_id);
        return map;
    }

    public static Map setShareUserAlias(String share_user_id, String share_user_alias) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("share_user_id", share_user_id);
        map.put("share_user_alias", share_user_alias);
        return map;
    }

    public static Map addShareUser(String share_user_phone, String phone, String device_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("share_user_phone", share_user_phone);
        map.put("phone", phone);
        map.put("device_id", device_id);
        map.put("orgId", RetrofitUtils.orgId);
        return map;
    }

    public static Map unShareDeviceToUser(String share_user_id, String device_id) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("share_user_id", share_user_id);
        map.put("device_id", device_id);
        return map;
    }

    /**
     * 1language
     * zh_CN(中文),en_US(英文)….(其他语言到时再定)
     * String	N
     * 2	app_user_id
     * 当前登录用户Id	int	Y
     * 3	nickname
     *
     * @return
     */
    public static Map getNickName(String nickname) {
        if (BaseApplication.getUser() == null)
            return null;
        Map map = getToken();

        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("nickname", nickname);

        return map;
    }

    public static Map getMessage() {
        if (BaseApplication.getUser() == null)
            return null;
        Map<String, String> map = HashMapFactory.getHashMap();

        map.put("phone", BaseApplication.getUser().getPhone());
        String timestamp = System.currentTimeMillis() + "";
        map.put("timestamp", timestamp);
        map.put("token", RetrofitUtils.getToken(timestamp));

        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");

        return map;
    }

    public static Map getAdviceMap(String content, String phoneOrEmail, String device_id) {
        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("phone", BaseApplication.getUser().getPhone());
        String timestamp = System.currentTimeMillis() + "";
        map.put("timestamp", timestamp);
        map.put("token", RetrofitUtils.getToken(timestamp));

        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        map.put("content", content);
        map.put("phoneOrEmail", phoneOrEmail);
        map.put("device_id", device_id);

        return map;
    }
}
