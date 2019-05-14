package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class MyAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public MyAdapter(@LayoutRes int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
      /*  String card_number = item.getCard_number();
        String banknum = "**** **** ****" + card_number.substring(card_number.length() - 4, card_number.length());

        String bank_of_accounts = item.getBank_of_accounts();
        int id;
        if (bank_of_accounts.equals(UIUtils.getString(R.string.nongye_bank)))
            id = R.mipmap.ic_07bg_green;
        else if (bank_of_accounts.equals(UIUtils.getString(R.string.zhaoshang_bank)))
            id = R.mipmap.ic_07bg_red;
        else if (bank_of_accounts.equals(UIUtils.getString(R.string.gongshang_bank)))
            id = R.mipmap.ic_07bg_blue;
        else
            id = R.mipmap.ic_08bg_orange;
        //可链式调用赋值
        helper.setText(R.id.tv_bankname, item.getAccount())
                .setText(R.id.tv_banknum, banknum)

                .setBackgroundRes(R.id.backgroun_type, id)
        ;
*/
        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }
}