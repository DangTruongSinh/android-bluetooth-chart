package com.truongsinh.projectrtos.ThongTinUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.truongsinh.projectrtos.DAO.AdapterDen;
import com.truongsinh.projectrtos.R;

import java.util.ArrayList;

public class DataForFragment {
    public ArrayList<Den> arrayList = new ArrayList<>();
    public AdapterDen adapterDen;
    public GridView gridView;
    public Context context;
    public View view;
    public void khoitaoView(LayoutInflater inflater)
    {
        view = inflater.inflate(R.layout.fragment_den,null);
        gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(adapterDen);
    }
    public void khoiTaoData(Context context)
    {
        arrayList.add(new Den(false));
        arrayList.add(new Den(false));
        arrayList.add(new Den(false));
        arrayList.add(new Den(false));
        adapterDen = new AdapterDen(R.layout.item_gridview,arrayList,context);
    }
}
