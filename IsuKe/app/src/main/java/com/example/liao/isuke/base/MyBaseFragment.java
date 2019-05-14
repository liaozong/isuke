package com.example.liao.isuke.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.example.liao.isuke.utils.UIUtils;

import java.util.List;
import java.util.Map;


public abstract class MyBaseFragment extends Fragment {
	private LoadingPager	mLoadingPager;

	public LoadingPager getLoadingPager() {
		return mLoadingPager;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		if (mLoadingPager == null) {// 第一次
			mLoadingPager = new LoadingPager(UIUtils.getContext()) {

				@Override
				public LoadedResult initData() {
					return MyBaseFragment.this.initData();
				}


				@Override
				protected View initSuccessView() {
					return MyBaseFragment.this.initSuccessView(inflater,container);
				}
			};
		} else {
			ViewParent parent = mLoadingPager.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				((ViewGroup) parent).removeView(mLoadingPager);
			}
		}
		return mLoadingPager;// -->viewGroup
	}


	protected abstract LoadingPager.LoadedResult initData();


	protected abstract View initSuccessView(LayoutInflater inflater, ViewGroup container);


	public LoadingPager.LoadedResult checkState(Object resResult) {
		if (resResult == null) {
			return LoadingPager.LoadedResult.EMPTY;
		}
		if (resResult instanceof List) {
			if (((List) resResult).size() == 0) {
				return LoadingPager.LoadedResult.EMPTY;
			}
		}
		if (resResult instanceof Map) {
			if (((Map) resResult).size() == 0) {
				return LoadingPager.LoadedResult.EMPTY;
			}
		}
		return LoadingPager.LoadedResult.SUCCESS;
	}
}
