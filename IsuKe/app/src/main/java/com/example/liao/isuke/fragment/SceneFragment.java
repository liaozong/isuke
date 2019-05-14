package com.example.liao.isuke.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.activity.scene.AddSceneAty;
import com.example.liao.isuke.activity.scene.HomeSceneAty;
import com.example.liao.isuke.adapter.SceneAdapter;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseFragment;
import com.example.liao.isuke.bean.MainListData;
import com.example.liao.isuke.bean.OwnDeviceList;
import com.example.liao.isuke.bean.SharedDeviceList;
import com.example.liao.isuke.databinding.FragmentSceneBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.runnable.RecyclerViewStop;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by liao on 2017/5/28.
 */
public class SceneFragment extends BaseFragment {
    private FragmentSceneBinding mBinding;
    private Handler mHandler;
    private SceneAdapter adapter;
public static boolean needRefresh= true;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scene, container, false);
        return mBinding.getRoot();
    }

    List<MainListData> mData = new ArrayList<>();

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.iSCN));
        isEdit();

        mData.clear();
        mData.add(new MainListData(1, "回家模式"));
        mData.add(new MainListData(2, "离家模式"));
        mData.add(new MainListData(3, "自定义模式"));

        mHandler = BaseApplication.getmHandler();
        adapter = new SceneAdapter(R.layout.list_scene, mData);
        Recycler.setGrid2Recycler(mBinding.sceneRecyclerview, adapter, mBinding.swipeLayout);

        requestScene();
    }

    private void isEdit() {
        if (isEdit) {
            mBinding.titleDelete.setVisibility(View.VISIBLE);
            mBinding.titleIcon.setVisibility(View.GONE);
        } else {
            mBinding.titleDelete.setVisibility(View.GONE);
            mBinding.titleIcon.setVisibility(View.VISIBLE);
        }

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    public static boolean isEdit = false;

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isEdit) {
                    if (mEditSceneNum.contains(position))
                        mEditSceneNum.remove(mEditSceneNum.indexOf(position));
                    else
                        mEditSceneNum.add(position);
                    adapter.notifyDataSetChanged();
                } else {
                    UIUtils.toast(getActivity(), mData.get(position).getName() + "");

                    startActivity(new Intent(getActivity(), HomeSceneAty.class));
                }
            }
        });

        mBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = mBinding.edit.getText().toString().trim();
                if (trim.equals(UIUtils.getString(R.string.edit))) {
                    mBinding.edit.setText(UIUtils.getString(R.string.exit_edit));
                    isEdit = true;
                    isEdit();
                } else {

                    editFinish();
                }
            }
        });

        mBinding.titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddSceneAty.class));
            }
        });
        mBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new RecyclerViewStop(mBinding.swipeLayout), 3000);
                requestScene();
            }
        });
    }

    public static List<Integer> mEditSceneNum = new ArrayList<>();

    private void editFinish() {
        mBinding.edit.setText(UIUtils.getString(R.string.edit));
        mEditSceneNum.clear();
        isEdit = false;
        isEdit();
    }

    private void requestScene() {
        Map map = RequestData.requestScene();
        if (map != null)
            getScene(map);
    }

    private void getScene(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().queryScene(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mHandler.post(new RecyclerViewStop(mBinding.swipeLayout));
                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            String senceList = jsonObject.getString("senceList");
                            Gson gson = new Gson();
                       /*     List<OwnDeviceList> mlist = gson.fromJson(ownDeviceList, new TypeToken<List<OwnDeviceList>>() {
                            }.getType());
                            mOwnDatas.clear();
                            mOwnDatas.addAll(mlist);*/
                            adapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("erro2r_msg");
                            UIUtils.toast(getActivity(), error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(getActivity(), UIUtils.getString(R.string.request_failed));

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(getActivity(), UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editFinish();
    }
}
