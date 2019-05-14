package com.example.liao.isuke;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.liao.isuke.factory.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.List;

public class demo extends Activity {

    private List<String> mdata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mdata = new ArrayList();
        mdata.add("1");
        setData(mdata);

        ThreadPoolFactory.getNormalThread().executeTask(new Runnable() {
            @Override
            public void run() {
                mdata = new ArrayList();
            }
        });
    }

    private void setData(List mdata) {

    }

}
