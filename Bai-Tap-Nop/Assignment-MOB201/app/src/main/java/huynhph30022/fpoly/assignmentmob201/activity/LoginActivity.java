package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.dao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    UserDAO userDAO;
    AppCompatEditText edSoDienThoai, edMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDAO = new UserDAO(LoginActivity.this);
        edSoDienThoai = findViewById(R.id.edSoDienThoaiLogin);
        edMatKhau = findViewById(R.id.edMatKhauLogin);

        AppCompatButton btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                String soDienThoai = Objects.requireNonNull(edSoDienThoai.getText()).toString().trim();
//                String matKhau = Objects.requireNonNull(edMatKhau.getText()).toString().trim();
//                boolean checkLogin = userDAO.checkLogin(soDienThoai, matKhau);
//                if (checkLogin) {
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                } else {
//                    Toast.makeText(LoginActivity.this, "Sai tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        TextView tvDangKy = findViewById(R.id.tvDangKy);
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}