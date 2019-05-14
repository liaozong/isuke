package com.example.liao.isuke.activity.mine;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityHelpBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liao on 2017/11/30.
 */
public class HelpAty extends BaseAty {

    private ActivityHelpBinding mBinding;

    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_help);
        UIUtils.setDaoHangLan(this, this);

    }

    private MyExpandableListViewAdapter mExpandableListViewAdapter;

    protected void initData() {
        fuArray.addAll(Arrays.asList(getResources().getStringArray(R.array.StringFuData)));
        ziArray.addAll(Arrays.asList(getResources().getStringArray(R.array.StringZiData)));

        mBinding.titleTheme.setText(UIUtils.getString(R.string.help));

        mBinding.expandHelpList.setFocusable(false);
        mBinding.expandHelpList.setLongClickable(false);
        mBinding.expandHelpList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mExpandableListViewAdapter = new MyExpandableListViewAdapter();
        mBinding.expandHelpList.setAdapter(mExpandableListViewAdapter);
    }

    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        requestHelp();
    }

    private void requestHelp() {
        Map<String, String> map = HashMapFactory.getHashMap();
//        if (BaseApplication.getUser() != null)
//            map.put("user_id", BaseApplication.getUser().getUser_id() + "");
//        requestCardList(MyRequest.QUERYBANK, map);
    }

   /* private void requestCardList(final String uri, final Map<String, String> params) {

        String url = MyRequest.BaseURL + uri;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            UIUtils.print("result....." + response);
                            JSONObject jsonObject = new JSONObject(response);
                            String result1 = (String) jsonObject.get("result");

                            if (result1.equals("success")) {

                                Gson gson = new Gson();
                                String addressList = jsonObject.getString("bankList");
                                List<BankCardInfo> mlist = gson.fromJson(addressList, new TypeToken<List<BankCardInfo>>() {
                                }.getType());
                                fuArray.clear();
                                ziArray.clear();
//                                fuArray.addAll();
//                                ziArray.addAll();
                                mExpandableListViewAdapter.notifyDataSetChanged();
                            } else {
                                String error_msg = (String) jsonObject.get("error_msg");
                                UIUtils.toast(UIUtils.getContext(), error_msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.login_request_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };

        MyRequest.startRequest(stringRequest);
    }*/

    private LayoutInflater mLayoutInflater;
    private List<String> fuArray = new ArrayList<>();
    private List<String> ziArray = new ArrayList<>();

    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        MyExpandableListViewAdapter() {
            mLayoutInflater = LayoutInflater.from(HelpAty.this);

        }

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos) {
            return null;
        }

        //  获得父项的数量
        @Override
        public int getGroupCount() {
            return fuArray.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos) {
            return 1;
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos) {
            return null;
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos) {
            return 0;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos) {
            return 0;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            view = mLayoutInflater.inflate(R.layout.itme_helpfudata, null);

            TextView mFuData = view.findViewById(R.id.fu_word);
            ImageView mExpanJiantou = view.findViewById(R.id.expand_jiantou);
            //箭头
            mExpanJiantou.setImageResource(R.mipmap.ic_arrowdown);
            if (!b) {
                mExpanJiantou.setImageResource(R.mipmap.ic_arrow);
            }
            mFuData.setText(fuArray.get(parentPos));

            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
            view = mLayoutInflater.inflate(R.layout.itme_helpzidata, null);
            TextView mZiData = view.findViewById(R.id.zi_word);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, UIUtils.dp2px(UIUtils.getContext(), 60));
            mZiData.setText(ziArray.get(parentPos));

            view.setLayoutParams(lp);
            return view;
        }


        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

}
