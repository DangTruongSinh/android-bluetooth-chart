package com.truongsinh.projectrtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truongsinh.projectrtos.DAO.TruyVanDataBase;
import com.truongsinh.projectrtos.ThongTinUser.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser,edtPassword;
    Button btnLogin;
    TextView txtThongBaoLoi;
    public static TruyVanDataBase truyVanDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUser = findViewById(R.id.editText);
        edtPassword = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.button);
        txtThongBaoLoi = findViewById(R.id.txtThongBaoLoi);
        btnLogin.setOnClickListener(this);

        truyVanDataBase = new TruyVanDataBase(this);
        truyVanDataBase.open_db();
    }

    @Override
    public void onClick(View v) {
        String tk = edtUser.getText().toString();
        String mk = edtPassword.getText().toString();
        User user = truyVanDataBase.getTTUser();
        if(tk.equals(user.getTk()) && mk.equals(user.getMk()))
        {
            edtPassword.setText("");
            edtUser.setText("");
            Intent intent = new Intent(MainActivity.this,BluetoothActivity.class);
            startActivity(intent);
        }
        else
        {
            txtThongBaoLoi.setText("Thông tin sai rồi!");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        truyVanDataBase.close_db();
    }
}
