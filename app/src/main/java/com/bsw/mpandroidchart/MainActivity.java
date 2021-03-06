package com.bsw.mpandroidchart;

import android.os.Bundle;
import android.view.View;

import com.bsw.mpandroidchart.base.activity.BaseActivity;
import com.bsw.mpandroidchart.ui.BarChartActivity;
import com.bsw.mpandroidchart.ui.LineChartActivity;
import com.bsw.mpandroidchart.ui.PieChartActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("列表");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void formatViews() {
        setOnClickListener(R.id.pie_chart,R.id.line_chart,R.id.bar_chart);
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
            case R.id.pie_chart:
                jumpTo(PieChartActivity.class);
                break;

            case R.id.line_chart:
                jumpTo(LineChartActivity.class);
                break;

            case R.id.bar_chart:
                jumpTo(BarChartActivity.class);
                break;
        }
    }
}
