package com.truongsinh.projectrtos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DoiMKActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtDoiMKLan1,edtXNDMKLan2;
    Button btnXacNhan;
    TextView txtThongBaoLoi;
    public static final int RESULT_CODE_DMK = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mk);

        edtDoiMKLan1 = findViewById(R.id.edtDMKLan1);
        edtXNDMKLan2 = findViewById(R.id.edtXNDMKLan2);
        btnXacNhan = findViewById(R.id.btnXacNhanDMK);
        txtThongBaoLoi = findViewById(R.id.txtTBLMKKoKhop);
        btnXacNhan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mkLan1 = edtDoiMKLan1.getText().toString();
        String mkLan2 = edtXNDMKLan2.getText().toString();
        if(mkLan1.equals(mkLan2))
        {
            boolean result = xulyDoiMK(mkLan1);
            if(result)
            {
                setResult(RESULT_CODE_DMK);
                finish();
            }
            else
            {
                txtThongBaoLoi.setText("Change PassWord not success!");
            }

        }
        else
        {
            txtThongBaoLoi.setText("Mật khẩu không khớp!");
            edtDoiMKLan1.requestFocus();
        }
    }

    private boolean xulyDoiMK(String mk) {
        return MainActivity.truyVanDataBase.updateMK(mk);
    }
}
