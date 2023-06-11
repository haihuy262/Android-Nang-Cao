package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.dao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    protected TextView tvDangKy;
    protected AppCompatButton btnDangNhap;
    protected CheckBox cboLuuTaiKhoan;
    private UserDAO userDAO;
    private AppCompatEditText edSoDienThoai, edMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDAO = new UserDAO(LoginActivity.this);
        edSoDienThoai = findViewById(R.id.edSoDienThoaiLogin);
        edMatKhau = findViewById(R.id.edMatKhauLogin);
        cboLuuTaiKhoan = findViewById(R.id.cboLuuTaiKhoan);
        tvDangKy = findViewById(R.id.tvDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", MODE_PRIVATE);
        boolean isAccountSaved = sharedPreferences.getBoolean("IsAccountSaved", false);
        String saveUserName = sharedPreferences.getString("soDienThoai", null);
        String savePassword = sharedPreferences.getString("matKhau", null);

        cboLuuTaiKhoan.setChecked(isAccountSaved);
        if (isAccountSaved && saveUserName != null && savePassword != null) {
            edSoDienThoai.setText(saveUserName);
            edMatKhau.setText(savePassword);
        }
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soDienThoai = Objects.requireNonNull(edSoDienThoai.getText()).toString().trim();
                String matKhau = Objects.requireNonNull(edMatKhau.getText()).toString().trim();
                if (userDAO.checkLogin(soDienThoai, matKhau)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cboLuuTaiKhoan.isChecked()) {
                        editor.putBoolean("IsAccountSaved", true);
                        editor.putString("soDienThoai", soDienThoai);
                        editor.putString("matKhau", matKhau);
                    } else {
                        editor.putBoolean("IsAccountSaved", false);
                        editor.remove("soDienThoai");
                        editor.remove("matKhau");
                    }
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}