package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.dao.UserDAO;
import huynhph30022.fpoly.assignmentmob201.service.RegisterService;

public class RegisterActivity extends AppCompatActivity {
    protected AppCompatButton btnDangKy;
    protected UserDAO userDAO;
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isKetQua = intent.getBooleanExtra("isKetQua", false);
            if (isKetQua) {
                Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private AppCompatEditText edSoDienThoai, edMatKhau, edReMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edSoDienThoai = findViewById(R.id.edSoDienThoaiRegister);
        edMatKhau = findViewById(R.id.edMatKhauRegister);
        edReMatKhau = findViewById(R.id.edNhapLaiMatKhauRegister);
        btnDangKy = findViewById(R.id.btnDangKy);
        userDAO = new UserDAO(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soDienThoai = Objects.requireNonNull(edSoDienThoai.getText()).toString().trim();
                String matKhau = Objects.requireNonNull(edMatKhau.getText()).toString().trim();
                String nhapLaiMatKhau = Objects.requireNonNull(edReMatKhau.getText()).toString().trim();
                if (matKhau.equals(nhapLaiMatKhau)) {
                    Intent intent = new Intent(RegisterActivity.this, RegisterService.class);
                    intent.putExtra("soDienThoai", soDienThoai);
                    intent.putExtra("matKhau", matKhau);
                    intent.putExtra("nhapLaiMatKhau", nhapLaiMatKhau);
                    startService(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký Broadcast Receiver
        IntentFilter intentFilter = new IntentFilter("ketQuaDangKy");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Huỷ đăng ký Broadcast Receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}