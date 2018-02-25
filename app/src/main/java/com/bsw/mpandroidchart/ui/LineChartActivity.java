package com.bsw.mpandroidchart.ui;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bsw.mpandroidchart.MyMarkerView;
import com.bsw.mpandroidchart.R;
import com.bsw.mpandroidchart.base.activity.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class LineChartActivity extends BaseActivity implements OnChartValueSelectedListener, OnChartGestureListener {

    private LineChart lineChart;
    protected String[] values = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("折线图");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    @Override
    protected void findViews() {
        lineChart = getView(R.id.line_chart);
    }

    @Override
    protected void formatViews() {
        initChart();
        setOnClickListener(R.id.refresh);
    }

    /**
     * 初始化折线图
     */
    private void initChart() {

        // 折线图被选中监听
        lineChart.setOnChartValueSelectedListener(this);

        // 是否绘制表格X轴、Y轴选中范围背景色
        lineChart.setDrawGridBackground(false);
        lineChart.setGridBackgroundColor(Color.GRAY);
        // 设置表格背景色
//        lineChart.setBackgroundColor(Color.GRAY);

        setDescription(false);

        // 触碰折线图时是否触发事件
        lineChart.setTouchEnabled(true);
        // 手势监听，setTouchEnabled为false时，该接口无法起作用，同时折线图无法缩放，平移
        lineChart.setOnChartGestureListener(this);
        // 折线图是否允许拖拽
        lineChart.setDragEnabled(true);
        // 折线图是否允许缩放
        lineChart.setScaleEnabled(true);
        // 折线图是否允许横向缩放
        // mChart.setScaleXEnabled(true);
        // 折线图是否允许纵向缩放
        // mChart.setScaleYEnabled(true);
        // 是否允许横向纵向同时缩放
        lineChart.setPinchZoom(true);

        // 当表格被点击后，弹窗的样式
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart

//        // X轴的边界线（值以及显示的文本）
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);//显示X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setTextSize(10f);//设置字体
        xAxis.setTextColor(Color.BLACK);//设置字体颜色
        /*
         * 图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
         * 设置超出部分会向里缩，可能导致与相邻坐标说明堆叠
         */
//        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelCount(12);// 设置横坐标数量
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
//        xAxis.addLimitLine(llXAxis); // add x-axis limit line

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // 限制线（值以及说明文本）
        LimitLine ll1 = new LimitLine(80f, "Upper Limit");
        // 线宽
        ll1.setLineWidth(4f);
        // 虚线样式（实线长度、间隔长度、偏移量）
        ll1.enableDashedLine(10f, 10f, 5f);
        // 说明文本位置
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        // 说明文本字号
        ll1.setTextSize(10f);
        // 说明文本字体
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(tf);

        // Y轴的左边界线获取
        YAxis leftAxis = lineChart.getAxisLeft();
        /*
         * getAxisLeft会根据边界线上下限（setAxisMaximum、setAxisMinimum）范围显示，而getAxisRight则不会，同时不显示限制线（LimitLine），而是根据所要显示的数据展示范围
         * 不知是当前包的问题，还是设置的源码的问题
         */
//        YAxis leftAxis = lineChart.getAxisRight();
        // 移除所有限制线
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        // 添加限制线
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        // 设置边界线上下限
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        // 纵坐标数量
        leftAxis.setLabelCount(10);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                /*
                 * 设置纵坐标显示样式
                 */
                return value + "%";
            }
        });

        // 设置边界线偏移量
        //leftAxis.setYOffset(20f);

        // 边界线样式设置（实线长度、间隔长度、偏移量）
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // 绘制0线
        leftAxis.setDrawZeroLine(true);

        // http://blog.csdn.net/dt235201314/article/details/52222088
        leftAxis.setDrawLimitLinesBehindData(true);

        // 右侧边界线不显示
        lineChart.getAxisRight().setEnabled(false);

        //lineChart.getViewPortHandler().setMaximumScaleY(2f);
        //lineChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(values.length, 100);

//        lineChart.setVisibleXRange(20);
//        lineChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        lineChart.centerViewTo(20, 50, AxisDependency.LEFT);

        // 生成表格时的动画时长
        lineChart.animateX(1000);
        //lineChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // lineChart.invalidate();
    }

    /**
     * 是否添加表格描述
     *
     * @param enabled 是否添加
     */
    private void setDescription(boolean enabled) {
        Description description = lineChart.getDescription();
        // 折线图是否添加描述
        description.setEnabled(enabled);
        // 描述文本
        description.setText("我是个描述");
        // 描述位置
//        description.setPosition(500, 100);
        // 描述字体颜色
        description.setTextColor(Color.RED);
        // 描述字体大小
        description.setTextSize(20);
        // 描述对齐方式
//        description.setTextAlign(Paint.Align.CENTER);
        // 描述字体
        description.setTypeface(mTfRegular);
        // 偏移量与位置描述冲突
        // 描述X偏移量
        description.setXOffset(100);
        // 描述Y偏移量
        description.setYOffset(0);
    }

    /**
     * 参数设置
     *
     * @param count 点数
     * @param range 随机数范围
     */
    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

//            float val = (float) (Math.random() * range) + 3;
//            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));

            values.add(new Entry(i, range = range - 5, getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
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

    // 触碰按下点
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    // 触碰抬起点
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            lineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    // 长按折线图
    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    // 双击折线图
    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    // 单击
    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    // 在线图上平行滑动的速度（与折线图平移不可共存：折线图放大时为平移；折线图完全展示时为监听此方法）
    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY + ", me1: " + me1 + ", me2: " + me2);
    }

    // 折线图缩放
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    // 折线图平移
    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    // 当参数被选中
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + lineChart.getLowestVisibleX() + ", high: " + lineChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + lineChart.getXChartMin() + ", xmax: " + lineChart.getXChartMax() + ", ymin: " + lineChart.getYChartMin() + ", ymax: " + lineChart.getYChartMax());
    }

    // 当未选中折线图
    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
