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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.truongsinh.projectrtos.MainActivity;
import com.truongsinh.projectrtos.R;

import java.util.ArrayList;

public class NhietDoFragment extends Fragment {
    LineChart lineChart;
    View view;
    LineDataSet mDataSetNhietDo;
    LineDataSet mDataSetDoAm;
    ArrayList<Entry> arrayListNhietDo = new ArrayList<>();
    ArrayList<Entry> arrayListDoAm = new ArrayList<>();
    LineData lineData;
    float nhietdo =0;
    float doam=0;
    int demData = 0;
    int thoigian =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
        {


            //
            view = inflater.inflate(R.layout.fragment_nhietdo, container, false);
            lineChart = view.findViewById(R.id.lineChart);
            mDataSetNhietDo = new LineDataSet(arrayListNhietDo,"Nhiệt độ");
            mDataSetDoAm = new LineDataSet(arrayListDoAm,"Độ ẩm");
            mDataSetNhietDo.setValueFormatter(new MyFortmatNhietDo());
            mDataSetDoAm.setValueFormatter(new MyFortmatDoAm());
            thietlapMau(mDataSetNhietDo,"#FF267ABF");
            thietlapMau(mDataSetDoAm,"#FFDAB966");
            ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
            iLineDataSets.add(mDataSetNhietDo);
            iLineDataSets.add(mDataSetDoAm);
            lineData = new LineData(iLineDataSets);
            Description description = new Description();
            description.setText("Nhiệt độ - Độ ẩm");
            lineChart.setDescription(description);
            lineChart.setData(lineData);
            lineChart.invalidate();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag","Destroyview");
    }

    @Override
    public void onDestroy() {
        Log.d("tag","destroy fragment");
        super.onDestroy();

    }

    public  void upDateGiaoDien(float nhietdo,float doam)
    {
            if(demData <= 8)
            {
                if(demData == 8)
                {
                    this.nhietdo /= 8;
                    this.doam /=8;
                    MainActivity.truyVanDataBase.insertData(this.nhietdo,this.doam);
                    TempTBFragment.updateBarChart();
                    arrayListNhietDo.clear();
                    arrayListDoAm.clear();
                    notifyDataChanged();
                    this.nhietdo = 0;
                    this.doam = 0;
                    demData =0;
                    thoigian = 0;
                }
                arrayListNhietDo.add(new Entry(thoigian,nhietdo));
                arrayListDoAm.add(new Entry(thoigian,doam));
                notifyDataChanged();
                this.nhietdo += nhietdo;
                this.doam +=doam;
                thoigian = thoigian+1;
                demData = demData+ 1;
            }

    }
    private void notifyDataChanged()
    {
        mDataSetNhietDo.notifyDataSetChanged();
        mDataSetDoAm.notifyDataSetChanged();
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
    private void thietlapMau(LineDataSet mDataSet, String mau)
    {

        mDataSet.setValueTextSize(9);

        mDataSet.setCircleColors(Color.parseColor("#FFDF1C1F"));
        mDataSet.setCircleHoleColor(Color.parseColor("#FFDF1C1F"));
        mDataSet.setColor(Color.parseColor(mau));
    }
    class MyFortmatNhietDo implements IValueFormatter
    {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value+"°";
        }
    }
    class MyFortmatDoAm implements IValueFormatter
    {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value+"%";
        }
    }
}
