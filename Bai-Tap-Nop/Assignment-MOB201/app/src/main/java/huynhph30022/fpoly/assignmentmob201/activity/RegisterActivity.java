package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import java.util.Objects;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.service.RegisterService;

public class RegisterActivity extends AppCompatActivity {
    AppCompatEditText edSoDienThoai, edMatKhau, edReMatKhau;
    AppCompatButton btnDangKy;

    RegisterService bindService;
    boolean isBound = false;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RegisterService.MyBinder binder = (RegisterService.MyBinder) service;
            bindService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edSoDienThoai = findViewById(R.id.edSoDienThoaiRegister);
        edMatKhau = findViewById(R.id.edMatKhauRegister);
        edReMatKhau = findViewById(R.id.edNhapLaiMatKhauRegister);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soDienThoai = Objects.requireNonNull(edSoDienThoai.getText()).toString().trim();
                String matKhau = Objects.requireNonNull(edMatKhau.getText()).toString().trim();
                String reMatKhau = Objects.requireNonNull(edReMatKhau.getText()).toString().trim();

                Intent intent = new Intent(RegisterActivity.this, RegisterService.class);
                intent.putExtra("soDienThoai", soDienThoai);
                intent.putExtra("matKhau", matKhau);
                intent.putExtra("reMatKhau", reMatKhau);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });
    }
}