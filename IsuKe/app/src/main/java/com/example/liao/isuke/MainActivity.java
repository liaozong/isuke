package com.example.liao.isuke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liao.isuke.activity.login.LoginAty;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;

import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.databinding.ActivityMainBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.fragment.SceneFragment;
import com.example.liao.isuke.helper.MainUiHelper;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.PreferenceUtil;
import com.example.liao.isuke.utils.UIUtils;

public class MainActivity extends BaseAty {
    private ActivityMainBinding mBinding;
    private MainUiHelper mainDataHelper;

    @Override
    protected void inits(Bundle savedInstanceState) {
        super.inits(savedInstanceState);
        if (savedInstanceState != null) {
            Boolean isLogin = PreferenceUtil.getBoolean("isLogin", false);
            String netIp = PreferenceUtil.getString("netIp", "");
            if (netIp.length() != 0)
                RetrofitUtils.setIp(netIp);
            if (isLogin == true) {
                AppUserInfo user = PreferenceUtil.getUser(this);
                BaseApplication.setUser(user);

                initView();
            }
        }
        register();
    }

    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("loginout");
        timeReceiver = new mainReceiver();
        registerReceiver(timeReceiver, intentFilter);
    }

    mainReceiver timeReceiver;

    public class mainReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("loginout")) {
                showExitPop();
            }
        }
    }

    private PopupWindow popupWindow;

    //退出提示框
    private void showExitPop() {
        View contentview = getLayoutInflater().inflate(R.layout.pop_loginout, null);
//        View rootview = getLayoutInflater().inflate(R.layout.activity_setting, null);
        popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
//        popupWindow.setWidth(UIUtils.dp2px(this, 250));
        popupWindow.setAnimationStyle(R.style.myanimation);

        popupWindow.showAtLocation(contentview, Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.style.myanimation);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
                    return true;
                }*/
                return false;
            }
        });
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        TextView mText = contentview.findViewById(R.id.tv_warning);
        mText.setText(R.string.login_again);
        contentview.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.commitBoolean("isLogin", false);
                exit();
                SceneFragment.needRefresh = true;
                HomeFragment.needRefresh = true;
                startActivity(new Intent(MainActivity.this, LoginAty.class));
            }
        });
        UIUtils.setBackgroundAlpha(0.5f, MainActivity.this);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(1.0f, MainActivity.this);
            }
        });
    }

    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        UIUtils.setTransparentStatusBar(this);

//        ProjectUtils.setLanguageData(this);
        MainHelper();

       /* if (BaseApplication.getUser() != null) {
            JPushInterface.setAlias(this, 10, BaseApplication.getUser().getAppUserId() + "");
            UIUtils.print("JPushInterface.setAlias" + BaseApplication.getUser().getAppUserId() + "");
        }*/
    }


    private void MainHelper() {
        if (mainDataHelper != null)
            mainDataHelper.clear();
        mainDataHelper = new MainUiHelper(this);
        mainDataHelper.init(mBinding.viewpage, getSupportFragmentManager(), mBinding.bottomBar);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainDataHelper.clear();
        if (timeReceiver != null)
            unregisterReceiver(timeReceiver);
    }
}
/*public class MainActivity extends BaseAty implements View.OnClickListener {

    private MainPagerAdapter mPagerAdapter;
    private CustomViewPager mViewPager;
    //首页
    private LinearLayout mHome;
    //理财
    private LinearLayout mMoney;
    //我的
    private LinearLayout mMine;
    //用来存放Viewpager中的view
    private List<View> mViews = new ArrayList<View>();
    private ImageButton mHomeIcon;
    private ImageButton mMineIcon;
    private ImageButton mMoneyIcon;
    private String[] mMainTitle;
    private TextView mHomeWord;
    private TextView mMineWord;
    private TextView mMoneyWord;

    protected void init() {
        mMainTitle = UIUtils.getStringArr(R.array.main_arr);
    }

    protected void initView() {
        setContentView(R.layout.activity_main);
        UIUtils.setDaoHangLan(this, this);

        mViewPager = findViewById(R.id.viewpage);
        mViewPager.setScanScroll(false);
        mHome = findViewById(R.id.home);
        mMoney = findViewById(R.id.money);
        mMine = findViewById(R.id.mine);

        mHomeIcon = findViewById(R.id.iv_home);
        mMineIcon = findViewById(R.id.iv_mine);
        mMoneyIcon = findViewById(R.id.iv_money);

        mHomeWord = findViewById(R.id.tv_home_word);
        mMineWord = findViewById(R.id.tv_mine_word);
        mMoneyWord = findViewById(R.id.tv_money_word);

        // 初始化布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View tab01 = mLayoutInflater.inflate(R.layout.fragment_home, null);
        View tab02 = mLayoutInflater.inflate(R.layout.fragment_scene, null);
        View tab03 = mLayoutInflater.inflate(R.layout.fragment_mine, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);

    }

    protected void initEvent() {

        {
            mHome.setOnClickListener(this);
            mMoney.setOnClickListener(this);
            mMine.setOnClickListener(this);
            mViewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    int currentItem = mViewPager.getCurrentItem();
                    switch (currentItem) {
                        case 0:
                            resetImg();
                            mHomeIcon.setImageResource(R.mipmap.ic_01equipment_click);
                            mHomeWord.setTextColor(UIUtils.getColor(R.color.org_login_word));

                            break;
                        case 1:
                            resetImg();
                            mMoneyIcon.setImageResource(R.mipmap.ic_02mode_click);
                            mMoneyWord.setTextColor(UIUtils.getColor(R.color.org_login_word));

                            break;
                        case 2:
                            resetImg();
                            mMineIcon.setImageResource(R.mipmap.ic_03user_click);
                            mMineWord.setTextColor(UIUtils.getColor(R.color.org_login_word));

                            break;

                        default:
                            break;
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
        }
    }

    protected void initData() {

        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                mViewPager.setCurrentItem(0);
                resetImg();
                mHomeIcon.setImageResource(R.mipmap.ic_01equipment_click);
                mHomeWord.setTextColor(UIUtils.getColor(R.color.org_login_word));
                break;
            case R.id.money:
                clickMoney();
                break;
            case R.id.mine:
                mViewPager.setCurrentItem(2);
                resetImg();
                mMineIcon.setImageResource(R.mipmap.ic_03user_click);
                mMineWord.setTextColor(UIUtils.getColor(R.color.org_login_word));
                break;
            default:
                break;
        }
    }

    private void clickMoney() {
        mViewPager.setCurrentItem(1);
        resetImg();
        mMoneyIcon.setImageResource(R.mipmap.ic_02mode_click);
        mMoneyWord.setTextColor(UIUtils.getColor(R.color.org_login_word));
    }


    private void resetImg() {
        mHomeIcon.setImageResource(R.mipmap.ic_01equipment);
        mMineIcon.setImageResource(R.mipmap.ic_03user);
        mMoneyIcon.setImageResource(R.mipmap.ic_02mode);

        mHomeWord.setTextColor(UIUtils.getColor(R.color.homeword_noclick));
        mMineWord.setTextColor(UIUtils.getColor(R.color.homeword_noclick));
        mMoneyWord.setTextColor(UIUtils.getColor(R.color.homeword_noclick));
    }

    //FragmentPagerAdapter 会缓存fragment 占内存大,FragmentStatePagerAdapter  每次选择都会重新加载fragment
    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);

        }

        @Override
        public int getCount() {
            if (mMainTitle != null)
                return mMainTitle.length;
            return 0;

        }

        //不复写的话  tabstrip 会报空指针
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitle[position];
        }
    }
}*/

