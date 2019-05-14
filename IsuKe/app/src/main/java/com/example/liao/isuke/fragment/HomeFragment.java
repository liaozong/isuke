package com.example.liao.isuke.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.activity.main.AddDeviceTypeAty;
import com.example.liao.isuke.activity.main.SocketDeviceAty;
import com.example.liao.isuke.adapter.MainAdapter;
import com.example.liao.isuke.adapter.MainSharedAdapter;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseFragment;
import com.example.liao.isuke.bean.DeviceDetail;
import com.example.liao.isuke.bean.DeviceInfo;
import com.example.liao.isuke.click.RecyclerItemClickListener;
import com.example.liao.isuke.databinding.FragmentHomeBinding;
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
public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding mBinding;
    private Handler mHandler;
    private MainAdapter mAdapter;
    private MainSharedAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void initData() {
        isEdit();

        mHandler = BaseApplication.getmHandler();
        mBinding.titleTheme.setText("iSuKe");
        mAdapter = new MainAdapter(R.layout.list_main, mOwnDatas);
        adapter = new MainSharedAdapter(R.layout.list_main, mSharedDatas);
        Recycler.setGrid3Recycler(mBinding.homeRecyclerview, mAdapter, mBinding.swipeLayout);
        Recycler.setGrid3Recycler(mBinding.sharedRecyclerview, adapter, mBinding.swipeLayout);
    }


    /*app_user_id language，orgId*/
    private void requestDevice() {
        Map map = RequestData.getDeviceList();
        if (map != null)
            getDevice(map);
    }

    private List<DeviceInfo> mOwnDatas = new ArrayList<>();
    private List<DeviceInfo> mSharedDatas = new ArrayList<>();

    private void getDevice(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().getDeviceManage(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    mHandler.post(new RecyclerViewStop(mBinding.swipeLayout));

                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            String deviceList = jsonObject.getString("deviceList");
                            Gson gson = new Gson();
                            List<DeviceInfo> mlist = gson.fromJson(deviceList, new TypeToken<List<DeviceInfo>>() {
                            }.getType());
                            setData(mlist);

                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
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

    private void setData(List<DeviceInfo> mlist) {
        mOwnDatas.clear();
        mSharedDatas.clear();

        for (int x = 0; x < mlist.size(); x++) {
            DeviceInfo deviceInfo = mlist.get(x);
            String device_belong_type = deviceInfo.getDevice_belong_type() + "";
            if (device_belong_type.equals("0"))
                mOwnDatas.add(deviceInfo);
            else
                mSharedDatas.add(deviceInfo);
        }
        mAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        setVisible();
    }

    public static boolean needRefresh = true;

    @Override
    public void onResume() {
        super.onResume();
        setVisible();

        if (needRefresh) {
            needRefresh = false;
            requestDevice();
        }
    }

    public void setVisible() {
        if (mOwnDatas.size() != 0 || mSharedDatas.size() != 0) {
            mBinding.homeRecyclerview.setVisibility(View.VISIBLE);
            mBinding.startlifeLayout.setVisibility(View.GONE);
        } else {
            mBinding.homeRecyclerview.setVisibility(View.GONE);
            mBinding.startlifeLayout.setVisibility(View.VISIBLE);
        }
    }

    public static List<Integer> mEditHomeNum = new ArrayList<>();
    public static List<Integer> mEditSharedNum = new ArrayList<>();

    @Override
    protected void initEvent() {
        mBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new RecyclerViewStop(mBinding.swipeLayout), 3000);
                requestDevice();
            }
        });

        mBinding.wordControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UIUtils.toast(getActivity(), "语音控制");
                startActivity(new Intent(getActivity(), SocketDeviceAty.class));
              /*  try {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请开始说话");
                    startActivityForResult(intent, 1);
                }catch(ActivityNotFoundException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("语音识别");
                    builder.setMessage("您的手机暂不支持语音搜索功能，点击确定下载安装Google语音搜索软件。您也可以在各应用商店搜索“语音搜索”进行下载安装。");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//             跳转到下载语音网页
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                }*/

            }
        });

        mBinding.titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBinding.homeRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //   UIUtils.toast(getActivity(), mData.get(position).getName() + "被点击");

                if (isEdit) {
                    if (mEditHomeNum.contains(position))
                        mEditHomeNum.remove(mEditSharedNum.indexOf(position));
                    else
                        mEditHomeNum.add(position);
                    mAdapter.notifyDataSetChanged();
                } else {
                    getDeviceDetail(position, "1");
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));
        mBinding.sharedRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (isEdit) {
                    if (mEditSharedNum.contains(position))
                        mEditSharedNum.remove(mEditSharedNum.indexOf(position));
                    else
                        mEditSharedNum.add(position);
                    adapter.notifyDataSetChanged();
                } else {
                    getDeviceDetail(position, "0");
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));


        mBinding.titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddDeviceTypeAty.class));
            }
        });
        mBinding.addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddDeviceTypeAty.class));
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
        mBinding.titleDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditHomeNum.size() != 0 || mEditSharedNum.size() != 0)
                    deleteDevice();
                else
                    editFinish();
            }
        });
    }

    private void editFinish() {
        mBinding.edit.setText(UIUtils.getString(R.string.edit));
        mEditHomeNum.clear();
        mEditSharedNum.clear();
        isEdit = false;
        isEdit();
    }

    private void deleteDevice() {
        String deviceId_userId = "";
        if (mEditHomeNum.size() != 0) {
            for (int x = 0; x < mEditHomeNum.size(); x++) {
                if (x == 0)
                    deviceId_userId = mOwnDatas.get(mEditHomeNum.get(x)).getDevice_id() + "@" + mOwnDatas.get(mEditHomeNum.get(x)).getDevice_user_id();
                else
                    deviceId_userId = deviceId_userId + "," + mOwnDatas.get(mEditHomeNum.get(x)).getDevice_id() + "@" + mOwnDatas.get(mEditHomeNum.get(x)).getDevice_user_id();
            }
        }

        if (mEditSharedNum.size() != 0) {
            for (int x = 0; x < mEditSharedNum.size(); x++) {
                if (x == 0) {
                    if (mEditHomeNum.size() == 0)
                        deviceId_userId = mSharedDatas.get(mEditSharedNum.get(x)).getDevice_id() + "@" + mSharedDatas.get(mEditSharedNum.get(x)).getDevice_user_id();
                    else
                        deviceId_userId = "," + mSharedDatas.get(mEditSharedNum.get(x)).getDevice_id() + "@" + mSharedDatas.get(mEditSharedNum.get(x)).getDevice_user_id();
                } else
                    deviceId_userId = deviceId_userId + "," + mSharedDatas.get(mEditSharedNum.get(x)).getDevice_id() + "@" + mSharedDatas.get(mEditSharedNum.get(x)).getDevice_user_id();
            }
        }
        Map map = RequestData.deleteDevice(deviceId_userId);
        delDevice(map);
    }

    private void delDevice(Map<String, String> map) {

        Call<String> call = RetrofitUtils.getRequestServies().deleteDevice(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("成功", "code..." + response.code());
                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        String result = response.body().toString();
                        Log.e("成功", response.body().toString());
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            Log.e("成功", "删除成功");
                            editFinish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(getActivity(), error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", "异常");
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

    public static boolean isEdit = false;

    private void isEdit() {
        if (isEdit) {
            mBinding.titleDelete.setVisibility(View.VISIBLE);
            mBinding.titleIcon.setVisibility(View.GONE);
        } else {
            mBinding.titleDelete.setVisibility(View.GONE);
            mBinding.titleIcon.setVisibility(View.VISIBLE);
        }

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        if (adapter != null)
            adapter.notifyDataSetChanged();

    }

    public static DeviceInfo clickDevice;

    private void getDeviceDetail(int position, String s) {

        String deviceid;
        String type;
        if (s.equals("1")) {
            clickDevice = mOwnDatas.get(position);
            deviceid = clickDevice.getDevice_id() + "";
            type = "0";
        } else {
            clickDevice = mSharedDatas.get(position);
            deviceid = clickDevice.getDevice_id() + "";
            type = "1";
        }
        Intent intent = new Intent(getActivity(), SocketDeviceAty.class);
        intent.putExtra("deviceid",deviceid);
        intent.putExtra("type",type);
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editFinish();
    }
}
