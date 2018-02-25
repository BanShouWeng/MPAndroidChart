package com.bsw.mpandroidchart.ui;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bsw.mpandroidchart.R;
import com.bsw.mpandroidchart.XYMarkerView;
import com.bsw.mpandroidchart.base.activity.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    protected BarChart mChart;

    protected String[] values = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("条形图");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bar_chart;
    }

    @Override
    protected void findViews() {
        mChart = getView(R.id.bar_chart);
    }

    @Override
    protected void formatViews() {
        initChart();
        setOnClickListener(R.id.refresh);
    }

    private void initChart() {
        // 条形图是否选中状态监听
        mChart.setOnChartValueSelectedListener(this);

        // 是否绘制当前展示的内容顶部阴影， 颜色为默认灰色，暂时未找到设置方法
        mChart.setDrawBarShadow(false);

        /*
         * 参数显示位置，若设置为true则高于条形，若为false则低于条形，默认为true
         * 使用时参照XAxis位置，若XAxis在顶部，则设置为false，若XAxis在底部，则设置为true。以避免Label与现实的参数重叠
         */
        mChart.setDrawValueAboveBar(true);

        // 是否添加描述
        mChart.getDescription().setEnabled(false);

        // 设置可现实最大值，若超出最大值不显示超出部分
        mChart.setMaxVisibleValueCount(60);

        // 为false则单次只能纵向拉伸或者横向拉伸，为true则可以纵向横向同时拉伸放大
        mChart.setPinchZoom(true);

        // 是否绘制背景色
        mChart.setDrawGridBackground(false);
        // 设置背景色色值
        mChart.setGridBackgroundColor(Color.RED);

        // 条形图加载动画，左至右、下至上同时加载（若只设定一个方向可调用animateX或animateY方法
        mChart.animateXY(3000, 3000);

        // 横坐标
        XAxis xAxis = mChart.getXAxis();
        // 横坐标现实位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 字体
        xAxis.setTypeface(mTfLight);
        // 是否根据每一个坐标点向上绘制分割线（线在条形图正中间）
        xAxis.setDrawGridLines(false);
        // 设置X轴之间的最小间隔，当数据过密集的时候，避免X轴坐标点重叠，或提示文本重叠
        xAxis.setGranularity(1f);
        // 横坐标点的数量
        xAxis.setLabelCount(12);
        // 格式化横坐标点的显示形式
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                /*
                 * 设置横坐标显示样式
                 * 将获取到的value四舍五入，如果直接强转会导致月份重复，其他情况需要具体问题具体分析
                 */
                return values[(int) Math.rint(value) % values.length];
            }
        });

        // 左侧坐标轴
        YAxis leftAxis = mChart.getAxisLeft();
        // 纵坐标字体
        leftAxis.setTypeface(mTfLight);

        /*
         * 设置纵坐标
         * @param count：纵坐标数量
         * @param force：是否均匀分布坐标点，若为true，则纵坐标点会均匀分布，但这样会导致标签不是等比分配的（翻译的，没有尝试过效果）
         */
        leftAxis.setLabelCount(8, false);

        AxisFormatter axisFormatter = new AxisFormatter();
        // 设置坐标显示规范
        leftAxis.setValueFormatter(axisFormatter);
        // 纵坐标轴标签绘制在图表的外侧
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 坐标做大值上方预留的空白区域范围，默认为10
        leftAxis.setSpaceTop(10f);
        // 设置纵坐标最小值
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // 右侧坐标轴
        YAxis rightAxis = mChart.getAxisRight();
        // 设置右侧坐标轴不可用，默认是可用的
        rightAxis.setEnabled(false);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // 图例
        Legend l = mChart.getLegend();
        // 设置图例的垂直对齐方式
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // 设置图例的水平对其方式
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // 设置图例的排列方式
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 图例绘制位置是否在图标中
        l.setDrawInside(false);
        // 设置图例样式：NONE 不绘制样式；EMPTY 不绘制样式，但是保留位置；DEFAULT 绘制默认样式（在我的测试机上演示默认为圆形）；SQUARE 正方形；CIRCLE 圆形；LINE 横线
        l.setForm(Legend.LegendForm.NONE);
        // 样式尺寸
        l.setFormSize(9f);
        // 文字大小
        l.setTextSize(11f);
        // 横向留白空间
        l.setXEntrySpace(4f);
//        l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        // 条形被点击后的逻辑显示的提示框视图
        XYMarkerView mv = new XYMarkerView(this, axisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        // 设置参数
        setData(12, 50);
    }

    private void setData(int count, int range) {
        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }


        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // "The year 2017" 为展示标签
//            set1 = new BarDataSet(yVals1, "The year 2017");
            set1 = new BarDataSet(yVals1, "");
//
            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            List<Integer> colors = new ArrayList<>();
            int size = values.length;
            int part = 255 / size;
            for (int i = 0; i < size; i++) {
                colors.add(Color.rgb(part * i, part * (size - i), part * new Random(i).nextInt()));
            }
            set1.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                initChart();
                break;
        }
    }

    /**
     * 被选中条形坐标记录
     */
    protected RectF mOnValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 坐标显示规范
     */
    private class AxisFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
                /*
                 * 设置坐标显示样式
                 */
            return value + "%";
        }
    }
}
