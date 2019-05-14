package com.example.liao.isuke.request;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/12/18.
 */

public abstract class BaseBeanRequest<T> extends SuperBaseRequest {
    public T loadData() throws Exception {
        String s = toLoadData();
        if (s != null && s.length() != 0)
            return parseJson(s);

        return null;
    }

    protected abstract T parseJson(String jsonString);

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