package com.example.liao.isuke.activity.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.MyAdapter;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.MessageInfo;
import com.example.liao.isuke.bean.OwnDeviceList;
import com.example.liao.isuke.databinding.ActivityMsgBinding;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.runnable.RecyclerViewStop;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liao on 2017/11/30.
 * 消息中心Activity
 */

public class MessageAty extends BaseAty {
    private ActivityMsgBinding mBinding;
    private Handler mHandler;
    List<MessageInfo> msgList = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_msg);
        UIUtils.setDaoHangLan(this, this);
        mHandler = BaseApplication.getmHandler();
    }


    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.message_center));

        getMessageList();//将服务器请求到的数据赋给msgList对象


        adapter = new MyAdapter(R.layout.list_msg, msgList);

        Recycler.setRecycler(this, mBinding.msgRecyclerview, adapter, mBinding.swipeLayout);
        mBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new RecyclerViewStop(mBinding.swipeLayout), 2000);
//                toRequestCard();
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Toast.makeText(MessageAty.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessageAty.this, MessageDetailAty.class);
                startActivity(intent);
            }
        });

        mBinding.msgRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }


    private List<MessageInfo> getMessageList() {
        Map map = RequestData.getMessage();
        Call<String> call = RetrofitUtils.getRequestServies().getMessage(map);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    try {

                        String resultString = response.body().toString();//返回一个字符串集合（可能有多层）
                        JSONObject jsonObject = new JSONObject(resultString);//转换成json对象
                        String resultValue = jsonObject.getString("resultcode");//得到返回码
                        if (resultValue.equals(RetrofitUtils.SUCCESS)) {
                            Gson gson = new Gson();
                            String msgs = jsonObject.getString("messageList");
                            //Log.e("未转换的msgs:", msgs);
                            if (!msgs.isEmpty()) {
                                List<MessageInfo> mlist = gson.fromJson(msgs, new TypeToken<List<MessageInfo>>() {
                                }.getType());
//                                msgList = mlist;
                                msgList.clear();
                                msgList.addAll(mlist);
                                adapter.notifyDataSetChanged();
                                Log.e("MessageInfo:", msgList.toString());
                                mBinding.ivNullmsg.setVisibility(View.INVISIBLE);
                                mBinding.tvNullmsg.setVisibility(View.INVISIBLE);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("response.code()：", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("请求失败：", t.getMessage().toString());
            }
        });
        return msgList;
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
