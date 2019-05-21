package com.truongsinh.projectrtos.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.truongsinh.projectrtos.MainActivity;
import com.truongsinh.projectrtos.R;

import java.util.ArrayList;

public class TempTBFragment extends Fragment {
    static BarChart barChart;
    public static View view;
    static ArrayList<Float> arrayListDoAm;
    static ArrayList<Float> arrayListNhietDo;
    static BarDataSet barDataSetNhietDo;
    static BarDataSet barDataSetDoAm;
    static BarData barData;
    static ArrayList<BarEntry> barEntryArrayListNhietDo = new ArrayList<>();
    static ArrayList<BarEntry> barEntryArrayListDoAm = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_temptb,null);
            barChart = view.findViewById(R.id.bar_chart);
        }
        return view;
    }
    public static void initGiaoDien()
    {

        arrayListNhietDo = MainActivity.truyVanDataBase.getAllDataTemp();
        arrayListDoAm = MainActivity.truyVanDataBase.getAllDataDoAm();
        datavalues1();
        barDataSetNhietDo = new BarDataSet(barEntryArrayListNhietDo,"Nhiệt độ");
        barDataSetNhietDo.setValueTextSize(10);
        barDataSetNhietDo.setColor(Color.parseColor("#FF267ABF"));
        barDataSetDoAm = new BarDataSet(barEntryArrayListDoAm,"Độ ẩm");
        barDataSetDoAm.setValueTextSize(10);
        barDataSetDoAm.setColor(Color.parseColor("#FFDAB966"));
        //
        float barWidth = 0.35f; // x2 dataset
        barData = new BarData(barDataSetNhietDo,barDataSetDoAm);
        barData.setBarWidth(barWidth);
        barChart.setData(barData);
        // thiết lập trục x
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String days[] = {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        //
        thietlapKhoangCach();
        Description description = new Description();
        description.setText("Nhiệt độ - Độ ẩm");
        barChart.setDescription(description);


        barChart.invalidate();
    }
    public static void updateBarChart()
    {
        barEntryArrayListNhietDo.clear();
        barEntryArrayListDoAm.clear();
        arrayListNhietDo = MainActivity.truyVanDataBase.getAllDataTemp();
        arrayListDoAm = MainActivity.truyVanDataBase.getAllDataDoAm();
        datavalues1();
        barDataSetNhietDo.notifyDataSetChanged();
        barDataSetDoAm.notifyDataSetChanged();
        barData.notifyDataChanged();
        barChart.notifyDataSetChanged();
        thietlapKhoangCach();
        barChart.invalidate();
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private static void thietlapKhoangCach()
    {
        float groupSpace = 0.22f;
        float barSpace = 0.04f; // x2 dataset
        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*7);
        barChart.getAxisLeft().setAxisMinimum(0);
    }
    private static void datavalues1()
    {
        int i = 0;
        for(Float x: arrayListNhietDo)
        {
            barEntryArrayListNhietDo.add(new BarEntry(i,x));
            i++;
        }
        i = 0;
        for(Float x: arrayListDoAm)
        {
            barEntryArrayListDoAm.add(new BarEntry(i,x));
            i++;
        }
    }

}
