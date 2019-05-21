package com.truongsinh.projectrtos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import  android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.github.mikephil.charting.data.Entry;
import com.truongsinh.projectrtos.DAO.TruyVanDataBase;
import com.truongsinh.projectrtos.ThongTinUser.Den;
import com.truongsinh.projectrtos.ThongTinUser.User;
import com.truongsinh.projectrtos.fragment.BedRomFragment;
import com.truongsinh.projectrtos.fragment.KitchenRom;
import com.truongsinh.projectrtos.fragment.LivingRom;
import com.truongsinh.projectrtos.fragment.NhietDoFragment;
import com.truongsinh.projectrtos.fragment.TempTBFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

public class ControlActivity extends AppCompatActivity implements Interface_LivingRom,Runnable{
    public static final String EXTRA_ADDRESS = "adress";
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    static BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    boolean islive = true;
    int soluongByte;
    InputStream inputStream;
    //
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    NhietDoFragment nhietDoFragment;
    KitchenRom kitchenRom;
    LivingRom livingRom;
    BedRomFragment bedRomFragment;
    public TempTBFragment tempTBFragment;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Intent intent = getIntent();
        address = intent.getStringExtra(EXTRA_ADDRESS);
        new ConnectBT().execute();// Khởi tạo 1 đối tượng kế thừa từ AsyncTask và cho nó thực thi
        SectionPaperAdapter sectionPaperAdapter = new SectionPaperAdapter(getSupportFragmentManager());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.viewpaper);
        viewPager.setAdapter(sectionPaperAdapter);
        tabLayout = findViewById(R.id.tablelayout);
        tabLayout.setupWithViewPager(viewPager);

        //
        nhietDoFragment = new NhietDoFragment();
        kitchenRom = new KitchenRom();
        livingRom = new LivingRom();
        bedRomFragment = new BedRomFragment();
        tempTBFragment = new TempTBFragment();
        thread = new Thread(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        int x = calendar.get(Calendar.DAY_OF_WEEK);
        MainActivity.truyVanDataBase.updateViTriData(x);
    }

    @Override
    public void dieuKhienDoSang(String s) {
        xulySuKienDen(s);
    }

    // Handler
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;
            float x1, x2;
            String s1 = s.substring(0, 2) + "." + s.substring(2, 3);
            String s2 = s.substring(3, 5) + "." + s.substring(5, 6);
            Log.d("tag","s1 :"+s1);
            Log.d("tag","s2 :"+s2);
            x1 = Float.parseFloat(s1);
            x2 = Float.parseFloat(s2);
            Log.d("tag", "nhiet do " + x1 + " do am " + x2);
            nhietDoFragment.upDateGiaoDien(x1, x2);

        }
    };
    @Override
    public void run() {
        String s;
        for(int i = 0 ; i< 2;i++)
        try
        {
            inputStream = btSocket.getInputStream();
            soluongByte = inputStream.available();
            byte[] arr = new byte[soluongByte];
            inputStream.read(arr);
            Log.d("tag","so luong byte "+soluongByte);
            Thread.sleep(1000);
        }
            catch (Exception ex) {

        }
        while (islive)
        {
                try {

                    inputStream = btSocket.getInputStream();
                    soluongByte = inputStream.available();
                    byte[] arr = new byte[soluongByte];
                    //   Log.i("tag",soluongByte+"");
                    //Tinh so luong byte cho phu hop
                    if (soluongByte >= 6) {
                        inputStream.read(arr);
                        s = new String(arr, "US-ASCII");
                       // xulySuKienDen(s);
                        Log.d("tag",s);
                        Message message = new Message();
                        message.obj = s;
                        handler.sendMessage(message);
                        Log.i("tag", s + "     " + soluongByte + "   ");


                    }
                }
             catch (Exception ex) {
                ex.printStackTrace();
            } }
            Log.d("tag","thread da die");

    }


    class SectionPaperAdapter extends FragmentPagerAdapter {

        public SectionPaperAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {

                case 0:return  nhietDoFragment;
                case 1: return tempTBFragment;
                case 2: return bedRomFragment;
                case 3: return kitchenRom;
                case 4: return livingRom;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0: return "Temperature";
                case 1: return "Average Temp";
                case 2: return "Bed Room";
                case 3: return "Kitchen Room";
                case 4: return "Living Room";
            }
            return super.getPageTitle(position);
        }
    }
    private class ConnectBT extends AsyncTask<Void, Void, Void> // UI thread
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ControlActivity.this, "Connecting...", "Vui lòng đợi!!!");
        }
        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice bluetoothDevice = myBluetooth.getRemoteDevice(address);
                    btSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                Toast.makeText(ControlActivity.this, "Kết nối thất bại!", Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                Toast.makeText(ControlActivity.this, "Kết nối thành công!", Toast.LENGTH_LONG).show();
                isBtConnected = true;

            }
            progress.dismiss(); // Ẩn thanh progress
            TempTBFragment.initGiaoDien();
            Den.TT = 1;
            thread.start();

        }
    }

    @Override
    protected void onDestroy() {

        try {
            btSocket.close();
            Log.d("tag","destroy");
            islive =false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    public static void xulySuKienDen(String s)
    {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            s = s + ".";
            outputStream.write(s.getBytes());
            Log.i("tag","gui thanh cong "  + s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
