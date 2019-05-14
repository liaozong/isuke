package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.MyAdapter;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.activity.login.ForgetPwdAty;
import com.example.liao.isuke.activity.login.LoginAty;
import com.example.liao.isuke.adapter.SharedUserAdapter;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.DeviceInfo;
import com.example.liao.isuke.bean.ShareUser;
import com.example.liao.isuke.databinding.ActivityShareduserBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.runnable.RecyclerViewStop;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SharedUserAty extends BaseAty {
    private Handler mHandler;
    private SharedUserAdapter sharedUserAdapter;

    @Override
    protected void init() {
        mHandler = BaseApplication.getmHandler();
    }

    private ActivityShareduserBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shareduser);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {

        mBinding.titleTheme.setText(UIUtils.getString(R.string.shared_user));

//        mBinding.msgRecyclerview.setItemViewSwipeEnabled(true); // 策划删除，默认关闭。
        // 设置监听器。
        mBinding.msgRecyclerview.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        mBinding.msgRecyclerview.setSwipeMenuItemClickListener(mMenuItemClickListener);

        sharedUserAdapter = new SharedUserAdapter(R.layout.list_shareduser, mData);

//        mBinding.msgRecyclerview.startDrag(ViewHolder);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mBinding.msgRecyclerview.setLayoutManager(layoutManager);
        mBinding.msgRecyclerview.setAdapter(sharedUserAdapter);

        requestDataInfo();
    }

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            // Item被拖拽时，交换数据，并更新adapter。
//            Collections.swap(mDataList, fromPosition, toPosition);
            sharedUserAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
//            mDataList.remove(position);
            sharedUserAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new RecyclerViewStop(mBinding.swipeLayout), 3000);
            }
        });
        mBinding.msgRecyclerview.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        sharedUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                UIUtils.toast(SharedUserAty.this, "点击了..." + position);
            }
        });


        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(SharedUserAty.this, AddSharedUserAty.class), 1);
            }
        });
    }

    private void requestDataInfo() {
        Map map = RequestData.getSharedUser(HomeFragment.clickDevice.getDevice_id() + "");
        if (map != null)
            getUser(map);
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
              /*  SwipeMenuItem deleteItem = new SwipeMenuItem(SharedUserAty.this);
             // 各种文字和图标属性设置。
                leftMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。*/
            SwipeMenuItem deleteItem = new SwipeMenuItem(SharedUserAty.this);
            deleteItem.setText("备注");
            deleteItem.setHeight(MATCH_PARENT);
            deleteItem.setWidth(UIUtils.dp2px(SharedUserAty.this, 48));
            deleteItem.setTextColor(UIUtils.getColor(R.color.baise));
            deleteItem.setBackgroundColor(UIUtils.getColor(R.color.huise));
            rightMenu.addMenuItem(deleteItem);
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(SharedUserAty.this);
            deleteItem1.setHeight(MATCH_PARENT);
            deleteItem1.setWidth(UIUtils.dp2px(SharedUserAty.this, 48));
            deleteItem.setTextColor(UIUtils.getColor(R.color.heise));
            deleteItem1.setText("删除");

            deleteItem.setBackgroundColor(UIUtils.getColor(R.color.org_login_word));
            deleteItem1.setBackgroundColor(UIUtils.getColor(R.color.delete_color));
            // 各种文字和图标属性设置。
            // 在Item右侧添加一个菜单。
            rightMenu.addMenuItem(deleteItem1);

            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };

    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//            UIUtils.toast(SharedUserAty.this, direction + "。。。" + adapterPosition + "..." + menuPosition);

            if (menuPosition == 0) {
                showEditNamePop(adapterPosition);
            } else {
                showDeletePop(adapterPosition);
            }
        }
    };

    private void showDeletePop(final int adapterPosition) {
        View view = getLayoutInflater().inflate(R.layout.pop_delete_shareduser, null);

        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        pw.setFocusable(true);
        pw.setOutsideTouchable(false);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setAnimationStyle(R.style.myanimation);
        pw.setWidth(UIUtils.dp2px(this, 330));
        pw.showAtLocation(view, Gravity.CENTER, 0, 100);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(1.0f, SharedUserAty.this);
            }
        });
        //防止虚拟软键盘被弹出菜单遮住
//        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        UIUtils.setBackgroundAlpha(0.5f, SharedUserAty.this);

        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });

        view.findViewById(R.id.commit_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSharedUser(adapterPosition);
                pw.dismiss();
            }
        });

    }

    private void deleteSharedUser(int adapterPosition) {
        ShareUser shareUser = mData.get(adapterPosition);
        Map map = RequestData.unShareDeviceToUser(shareUser.getShare_user_id() + "", HomeFragment.clickDevice.getDevice_id() + "");
        if (map != null)
            deleteUser(map, adapterPosition);
    }

    private void deleteUser(final Map map, final int adapterPosition) {
        Call<String> call = RetrofitUtils.getRequestServies().unShareDeviceToUser(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            mData.remove(adapterPosition);
                            sharedUserAdapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SharedUserAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private PopupWindow iconpw;
    private PopupWindow pw;
    private EditText mDeviceName;

    private void showEditNamePop(final int adapterPosition) {
        View view = getLayoutInflater().inflate(R.layout.pop_edit_devicename, null);

        iconpw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        iconpw.setFocusable(true);
        iconpw.setOutsideTouchable(false);
        iconpw.setBackgroundDrawable(new BitmapDrawable());
        iconpw.setAnimationStyle(R.style.myanimation);
        iconpw.setWidth(UIUtils.dp2px(this, 330));
        iconpw.showAtLocation(view, Gravity.CENTER, 0, 100);
        iconpw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.HideKeyboard(mDeviceName);
                UIUtils.setBackgroundAlpha(1.0f, SharedUserAty.this);
            }
        });
        //防止虚拟软键盘被弹出菜单遮住
        iconpw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        UIUtils.setBackgroundAlpha(0.5f, SharedUserAty.this);

        mDeviceName = view.findViewById(R.id.devicename);
        String s = mData.get(adapterPosition).getFriend_alias();
        mDeviceName.setText(s);
        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconpw.dismiss();
                UIUtils.HideKeyboard(mDeviceName);
            }
        });

        view.findViewById(R.id.commit_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mDeviceName.getText().toString().trim();
                setShareUserAlias(adapterPosition, name);
                iconpw.dismiss();
            }
        });

    }

    private void setShareUserAlias(int adapterPosition, String name) {
        Map map = RequestData.setShareUserAlias(mData.get(adapterPosition).getShare_user_id() + "", name);
        if (map != null)
            setAlias(map, adapterPosition);

    }

    private void setAlias(final Map<String, String> map, final int adapterPosition) {
        Call<String> call = RetrofitUtils.getRequestServies().setShareUserAlias(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            ShareUser shareUser = mData.get(adapterPosition);
                            shareUser.setFriend_alias(map.get("share_user_alias"));
                            mData.set(adapterPosition, shareUser);
                            sharedUserAdapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SharedUserAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private List<ShareUser> mData = new ArrayList<>();

    private void getUser(Map<String, String> params) {

        Call<String> call = RetrofitUtils.getRequestServies().getSharedUser(params);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            String shareUserList = jsonObject.getString("shareUserList");
                            Gson gson = new Gson();
                            List<ShareUser> mlist = gson.fromJson(shareUserList, new TypeToken<List<ShareUser>>() {
                            }.getType());
                            mData.clear();
                            mData.addAll(mlist);
                            sharedUserAdapter.notifyDataSetChanged();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SharedUserAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SharedUserAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            requestDataInfo();
        }
    }
}
