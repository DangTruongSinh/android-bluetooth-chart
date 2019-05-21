package com.truongsinh.projectrtos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.truongsinh.projectrtos.Interface_LivingRom;
import com.truongsinh.projectrtos.R;

public class LivingRom extends Fragment implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekBar;
    View view;
    TextView txtPhanTram;
    Interface_LivingRom livingRom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_living,null);
            seekBar = view.findViewById(R.id.seek_bar);
            txtPhanTram = view.findViewById(R.id.txtPhanTram);
            seekBar.setOnSeekBarChangeListener(this);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        livingRom = (Interface_LivingRom) context;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String s = "v"+progress;
        livingRom.dieuKhienDoSang(s);
        txtPhanTram.setText(progress+"%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
