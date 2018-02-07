package com.bsw.mpandroidchart.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;

import com.bsw.mpandroidchart.R;
import com.bsw.mpandroidchart.base.activity.BaseActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class PieChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    private PieChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("扇形图");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    protected void findViews() {
        mChart = getView(R.id.pie_chart);
        setOnClickListener(R.id.refresh);
    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void formatData() {
        initPieChart();
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                initPieChart();
                break;
        }
    }

    private void initPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(true);
        // 设置图表外，布局内显示的偏移量
        mChart.setExtraOffsets(5, 10, 5, 5);

        //设置可以手动旋转
        mChart.setRotationEnabled(true);
        // 拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mChart.setDragDecelerationEnabled(true);
        //与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        mChart.setDragDecelerationFrictionCoef(0.95f);

        // 设置字体
        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        // 是否显示中间文本区域
        mChart.setDrawHoleEnabled(false);
        // 中间文本区域背景色
        mChart.setHoleColor(Color.WHITE);
        // 设置中间文本区域范围
        mChart.setHoleRadius(50f);

        // 过度区域颜色
        mChart.setTransparentCircleColor(Color.WHITE);
        // 过度区域透明度
        mChart.setTransparentCircleAlpha(5);
        // 设置过度区域的范围（有效果区域为：setTransparentCircleRadius - setHoleRadius）
        mChart.setTransparentCircleRadius(60f);

        // 中间文本是否显示
        mChart.setDrawCenterText(false);

        // 生成扇区时的动画起始角度
        mChart.setRotationAngle(0);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(2, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        // 图例显示位置
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 扇区内，图例文字颜色
        mChart.setEntryLabelColor(Color.WHITE);
        // 设置字体
        mChart.setEntryLabelTypeface(mTfRegular);
        // 设置大小
        mChart.setEntryLabelTextSize(12f);
    }

    private void setData(int count, float range) {

        float mult = range;

        /*
         * PieEntry参数：
         * value 当前扇区占据百分比；
         * label 当前扇区所属部分；
         * icon  扇区对应图标。
         */
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // 设置扇形图每个部分的名称
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "选取结果");

        // 是否扇区对应图标
        dataSet.setDrawIcons(false);

        // 扇区间间隔区域宽度
        dataSet.setSliceSpace(1f);

        // 扇区对应图标相对扇区中心处偏移量
        dataSet.setIconsOffset(new MPPointF(0, 0));
        // 扇区选中状态宽度
        dataSet.setSelectionShift(10f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());

        colors.add(ColorTemplate.rgb("#FF4B56"));
        colors.add(ColorTemplate.rgb("#FFB71D"));

        // 选中状态颜色值
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        // 文本格式化
        data.setValueFormatter(new PercentFormatter());
        // 设置扇区上说明字体大小
        data.setValueTextSize(11f);
        // 设置扇区上说明字体颜色
        data.setValueTextColor(Color.WHITE);
        // 设置扇区上说明字体
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // 构造扇形图
        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
