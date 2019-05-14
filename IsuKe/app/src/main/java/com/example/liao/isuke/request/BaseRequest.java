package com.example.liao.isuke.request;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import retrofit2.Call;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * Created by Administrator on 2018/12/18.
 */

public abstract class BaseRequest<T> extends SuperBaseRequest {
    public T loadData() throws Exception {
        String s = toLoadData();
        if (s != null && !s.equals("[]"))
            return parseJson(s);

        return null;
    }

    private T parseJson(String jsonString) {
        Gson gson = new Gson();
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return gson.fromJson(jsonString, actualTypeArguments[0]);
    }

    @Override
    protected String String() {
        return setString();
    }

    @Override
    protected Call<String> Call() {
        return setCall();
    }

    protected abstract String setString();

    protected abstract Call<String> setCall();
}