package com.example.liao.isuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liao.isuke.R;
import com.example.liao.isuke.activity.main.PowerDetailAty;
import com.example.liao.isuke.activity.main.PowerDissipationAty;
import com.example.liao.isuke.bean.PowerData;
import com.example.liao.isuke.utils.UIUtils;

import java.util.List;

public class PowerAdapter extends BaseAdapter {

    private Context context;
    private List<PowerData.PowerMonthListBean> mDatas;
    private String month;
    private PowerData.PowerMonthListBean powerMonthListBean;

    public PowerAdapter(Context context, List mDatas) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (mDatas.get(position).getPowerValue() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            FuViewHolder fuViewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_power_fu, null);
                fuViewHolder = new FuViewHolder();
                /*得到各个控件的对象*/
                fuViewHolder.date = convertView.findViewById(R.id.date);
                convertView.setTag(fuViewHolder);//绑定ViewHolder对象
            } else {
                fuViewHolder = (FuViewHolder) convertView.getTag();//取出ViewHolder对象
            }

            fuViewHolder.date.setText(mDatas.get(position).getPowerMonth()+UIUtils.getString(R.string.year));

        } else {
            ViewHolder holder;
            //观察convertView随ListView滚动情况
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_power_zi, null);
                holder = new ViewHolder();
                /*得到各个控件的对象*/
                holder.info = convertView.findViewById(R.id.power_month);
                holder.money = convertView.findViewById(R.id.power_num);
                holder.itemclick = convertView.findViewById(R.id.itemclick);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            powerMonthListBean = mDatas.get(position);
            month = Integer.parseInt(powerMonthListBean.getPowerMonth())+ UIUtils.getString(R.string.month);
            holder.info.setText(month);
            holder.money.setText(powerMonthListBean.getPowerValue()+"");
            holder.itemclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PowerDetailAty.class);
                    intent.putExtra("title", month);
                    intent.putExtra("month", powerMonthListBean.getPowerMonth());
                    intent.putExtra("year",powerMonthListBean.getYear() );
                    context.startActivity(intent);
                }
            });

        }

        return convertView;
    }

    public class ViewHolder {
        public TextView info;
        public TextView money;
        public TextView time;
        public RelativeLayout itemclick;
    }

    public class FuViewHolder {
        public TextView date;
    }
}
