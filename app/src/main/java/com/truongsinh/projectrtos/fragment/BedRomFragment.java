package com.truongsinh.projectrtos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truongsinh.projectrtos.ThongTinUser.DataForFragment;




public class BedRomFragment extends Fragment {
    DataForFragment dataForFragment = new DataForFragment();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(dataForFragment.view == null)
        {
           dataForFragment.khoitaoView(inflater);
        }

        return dataForFragment.view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.dataForFragment.context = context;
        dataForFragment.khoiTaoData(context);
        }


}
