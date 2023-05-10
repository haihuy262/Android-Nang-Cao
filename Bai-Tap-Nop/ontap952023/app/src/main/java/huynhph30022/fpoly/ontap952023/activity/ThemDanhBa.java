package huynhph30022.fpoly.ontap952023.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import huynhph30022.fpoly.ontap952023.R;
import huynhph30022.fpoly.ontap952023.database.DanhBaDAO;
import huynhph30022.fpoly.ontap952023.model.DanhBa;

public class ThemDanhBa extends AppCompatActivity {
    private DanhBaDAO danhBaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_ba);

        danhBaDAO = new DanhBaDAO(ThemDanhBa.this);

        AppCompatButton btnHuy = findViewById(R.id.btnHuyAdd);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AppCompatButton addDanhBa = findViewById(R.id.addDanhBa);
        addDanhBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edHoTen = findViewById(R.id.addHoTen);
                EditText edSoDienThoai = findViewById(R.id.addSoDienThoai);
                EditText edEmail = findViewById(R.id.addEmail);
                EditText edGhiChu = findViewById(R.id.addGhiChu);

                String hoTen = edHoTen.getText().toString().trim();
                String dienThoai = edSoDienThoai.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String ghiChu = edGhiChu.getText().toString().trim();

                DanhBa addDanhBa = new DanhBa();
                addDanhBa.setHoTen(hoTen);
                addDanhBa.setDienThoai(dienThoai);
                addDanhBa.setEmail(email);
                addDanhBa.setGhiChu(ghiChu);

                boolean check = danhBaDAO.addDatabase(addDanhBa);
                if (check) {
                    Toast.makeText(ThemDanhBa.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThemDanhBa.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}