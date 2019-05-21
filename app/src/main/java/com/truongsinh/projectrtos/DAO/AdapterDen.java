package com.truongsinh.projectrtos.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;

import com.truongsinh.projectrtos.ControlActivity;

import com.truongsinh.projectrtos.R;
import com.truongsinh.projectrtos.ThongTinUser.Den;

import java.util.ArrayList;

public class AdapterDen  extends BaseAdapter {
    int layout;
    ArrayList<Den> arrayList;
    Context context;
    boolean checked;

    public AdapterDen(int layout, ArrayList<Den> arrayList, Context context) {
        this.layout = layout;
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Den den = arrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        final ImageView imageView = convertView.findViewById(R.id.imgDen);
        final Switch aSwitch = convertView.findViewById(R.id.switch_bar);
        imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xulySuKienNhan(den,imageView,aSwitch);
                }
            });
        aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xulySuKienNhan(den,imageView,aSwitch);
                }
            });
        if(den.isLight())
        {
            aSwitch.setChecked(true);
            imageView.setImageResource(R.drawable.ledsang);
        }
        else
        {
            aSwitch.setChecked(false);
            imageView.setImageResource(R.drawable.ledtat);
        }

        return convertView;
    }
    private void xulySuKienNhan(Den den, ImageView imageView, Switch aSwitch)
    {
        if(!den.isLight())
        {
            checked = true;
            imageView.setImageResource(R.drawable.ledsang);

        }
        else
        {
            checked = false;
            imageView.setImageResource(R.drawable.ledtat);
        }
        aSwitch.setChecked(checked);
        den.setLight(checked);
        ControlActivity.xulySuKienDen(String.valueOf(den.getSoTT()));
    }

}
