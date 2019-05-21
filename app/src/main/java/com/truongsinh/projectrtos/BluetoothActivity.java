package com.truongsinh.projectrtos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUETCODE_DMK = 123;
    private static final int REQUETCODE_BLUETOOTH = 1;
    ListView lvDanhSach;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrDSBluetooth = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initListview();
        initBluetooth();
        lvDanhSach.setOnItemClickListener(this);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                String s = deviceName + "-"+deviceHardwareAddress;
                arrDSBluetooth.add(s);
                adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arrDSBluetooth);
                lvDanhSach.setAdapter(adapter);

            }
        }
    };
    private void initListview() {
        lvDanhSach = findViewById(R.id.lsBluetooth);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrDSBluetooth);
        lvDanhSach.setAdapter(adapter);
    }


    private void initBluetooth() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        findPairedBluetooth();
    }
    private void findPairedBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        else
        {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUETCODE_BLUETOOTH);

            }
            scanfOldDevice();

        }
    }
    private void scanfOldDevice()
    {
        // pair
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {

            String s ="";
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                s = deviceName + "-"+deviceHardwareAddress;
                arrDSBluetooth.add(s);

            }
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrDSBluetooth);
            lvDanhSach.setAdapter(adapter);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUETCODE_DMK && resultCode == DoiMKActivity.RESULT_CODE_DMK)
        {
            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == RESULT_OK && requestCode == REQUETCODE_BLUETOOTH)
        {
            scanfOldDevice();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listbluetooth,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuDoiMK)
        {
            Intent intent = new Intent(this,DoiMKActivity.class);
            startActivityForResult(intent,REQUETCODE_DMK);
        }
        else
        {
            arrDSBluetooth.clear();
            bluetoothAdapter.startDiscovery();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bluetoothAdapter.cancelDiscovery();

        //
        String item = arrDSBluetooth.get(position);
        String address = item.substring(item.length() - 17);
        Intent intent = new Intent(this,ControlActivity.class);
        intent.putExtra(ControlActivity.EXTRA_ADDRESS,address);
        startActivity(intent);
    }


}
