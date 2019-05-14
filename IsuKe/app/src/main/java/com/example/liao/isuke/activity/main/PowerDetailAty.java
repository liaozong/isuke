package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.PowerData;
import com.example.liao.isuke.bean.PowerGeneral;
import com.example.liao.isuke.databinding.ActivityPowerdetailBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PowerDetailAty extends BaseAty {

    private ActivityPowerdetailBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_powerdetail);
        UIUtils.setDaoHangLan(this, this);
    }

    private String title;
    private String month;
    private String year;

    @Override
    protected void init() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");

    }

    private void initChartsView() {
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }

    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22", "5-23", "5-22"};//X轴的标注
    float[] score = {5, 12, 2.5f, 3, 16, 18, 15, 22, 25, 10, 0, 2, 16, 5.5f, 8, 16, 1.2f};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(title);

        getPowerDetail();

        initChartsView();


    }

    private void getPowerDetail() {
        Map map = RequestData.devicePowerDetail(year, month, HomeFragment.clickDevice.getDevice_id() + "");
        if (map != null)
            devicePowerDetail(map);
    }

    private void devicePowerDetail(Map map) {
        Call<String> call = RetrofitUtils.getRequestServies().devicePowerDetail(map);//传入我们请求的键值对的值

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
                            String power = jsonObject.getString("powerDetail");
                            Gson gson = new Gson();
                            List<PowerData> mlist = gson.fromJson(power, new TypeToken<List<PowerData>>() {
                            }.getType());
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(PowerDetailAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(PowerDetailAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(PowerDetailAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#93BF67"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.DIAMOND);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setStrokeWidth(1);
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(31); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
//        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边

       /* Axis axisY = new Axis().setHasLines(true);
        axisY.setMaxLabelChars(6);//max label length, for example 60
        List<AxisValue> values = new ArrayList<>();
        for(int i = 0; i < 100; i+= 10){
            AxisValue value = new AxisValue(i);
            String label = "";
            value.setLabel(label);
            values.add(value);
        }
        axisY.setValues(values);*/

        //设置行为属性，支持缩放、滑动以及平移
        mBinding.chart.setInteractive(false);
        mBinding.chart.setZoomType(ZoomType.HORIZONTAL);
        mBinding.chart.setMaxZoom((float) 2);//最大方法比例
        mBinding.chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mBinding.chart.setLineChartData(data);
        mBinding.chart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(mBinding.chart.getMaximumViewport());
        v.left = 0;
        v.right = 31;
        mBinding.chart.setCurrentViewport(v);
    }
}
