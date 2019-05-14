package com.example.liao.isuke.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.liao.isuke.R;
import com.example.liao.isuke.factory.ThreadPoolFactory;
import com.example.liao.isuke.utils.UIUtils;


public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_NONE = -1;            // 默认状态
    public static final int STATE_LOADING = 0;            // 加载中
    public static final int STATE_EMPTY = 1;            // 空
    public static final int STATE_ERROR = 2;            // 错误
    public static final int STATE_SUCCESS = 3;            // 成功


    private int mCurState = STATE_NONE;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPager(Context context) {
        super(context);

        initCommonView();
    }

    private void initCommonView() {

        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);


        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 重新加载数据
                triggerLoadData();
            }
        });

        // ③ 空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        refreshUIByState();
    }


    private void refreshUIByState() {

        mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? View.VISIBLE : View.GONE);


        mErrorView.setVisibility((mCurState == STATE_ERROR) ? View.VISIBLE  : View.GONE );


        mLoadingView.setVisibility((mCurState == STATE_LOADING) || (mCurState == STATE_NONE) ? View.VISIBLE  : View.GONE);


        if (mCurState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

        if (mSuccessView != null) {

            mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? View.VISIBLE  : View.GONE);
        }

    }


    public void triggerLoadData() {

        if (mCurState != STATE_SUCCESS && mCurState != STATE_LOADING) {
            mCurState = STATE_LOADING;
            refreshUIByState();

            ThreadPoolFactory.getNormalThread().executeTask(new LoadDataTask());
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            LoadedResult tempState = initData();

            mCurState = tempState.getState();

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshUIByState();
                }
            });
        }
    }

    protected abstract LoadedResult initData();

    protected abstract View initSuccessView();

    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), EMPTY(STATE_EMPTY), ERROR(STATE_ERROR);
        int state;

        public int getState() {
            return state;
        }

        private LoadedResult(int state) {
            this.state = state;
        }
    }

}
