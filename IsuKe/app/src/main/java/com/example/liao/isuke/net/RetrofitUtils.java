
package com.example.liao.isuke.net;

import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.utils.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static Retrofit stringRetrofit;
    public static int orgId = -1;
    public static String appSerialNum = "MIS0812778904";
    public static String appOrgCode = "0";
    public static String language = "zh_CN";
    public static String SUCCESS = "SYS000";
    public static void setIp(String ip) {
        Ip = ip;
        requestSerives = null;
        stringRetrofit = null;
    }

    public static String getToken(String timestamp) {
        return Utils.stringMD5(BaseApplication.getUser().getPhone() + timestamp + "app");
    }

    public static Boolean isSuccess(Response<String> response) {
        if (response.code() == 200)
            return true;
        return false;
    }

    //private static String Ip = "117.78.48.143";
    private static String Ip = "192.168.8.249:8080";

    public static Retrofit getStringRetrofit() {
        /*http://192.168.8.109:8081/appServer/sleepReport/test?user_id=4&usertets=5*/
        if (stringRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (stringRetrofit == null)
                    stringRetrofit = new Retrofit.Builder()
                            .baseUrl("http://" + Ip + "/iSukeServer/")//"http://117.78.48.143/iSukeServer/"  "http://192.168.8.229:8080/iSukeServer/"
                            .client(getOKHttpClient())
                            //增加返回值为String的支持
                            .addConverterFactory(ScalarsConverterFactory.create())
                            //增加返回值为Gson的支持(以实体类返回)
//                        .addConverterFactory(GsonConverterFactory.create())
                            //增加返回值为Oservable<T>的支持
//                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
            }
        }
        return stringRetrofit;
    }

    private static RequestSerives requestSerives;

    public static RequestSerives getRequestServies() {
        //这里采用的是Java的动态代理模式
        if (requestSerives == null) {
            synchronized (RetrofitUtils.class) {
                if (requestSerives == null)
                    requestSerives = getStringRetrofit().create(RequestSerives.class);

            }
        }
        return requestSerives;
    }

    /*  private static Retrofit beanRetrofit;

      public static Retrofit getBeanRetrofit() {

          if (beanRetrofit == null) {
              synchronized (RetrofitUtils.class) {
                  if (beanRetrofit == null)
                      beanRetrofit = new Retrofit.Builder()
                              .baseUrl(HttpRestClient.BASE_URL)
                              .client(getOKHttpClient())
                              //增加返回值为String的支持
  //                            .addConverterFactory(ScalarsConverterFactory.create())
                              //增加返回值为Gson的支持(以实体类返回)
                              .addConverterFactory(GsonConverterFactory.create())
                              //增加返回值为Oservable<T>的支持
  //                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                              .build();
              }
          }
          return beanRetrofit;
      }
  */
    private static OkHttpClient okHttpClient;
    private final static int DEFAULT_TIME_OUT = 5;

    private static OkHttpClient getOKHttpClient() {

        if (okHttpClient == null) {
            synchronized (RetrofitUtils.class) {
                if (okHttpClient == null)
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接超时时间
                            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//读操作超时时间
                            .build();
            }
        }
        return okHttpClient;
    }

}