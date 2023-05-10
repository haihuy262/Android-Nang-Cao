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

public class SuaLienHe extends AppCompatActivity {
    private DanhBaDAO danhBaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_lien_he);

        danhBaDAO = new DanhBaDAO(SuaLienHe.this);

        EditText edHoTen = findViewById(R.id.editHoTen);
        EditText edEmail = findViewById(R.id.editEmail);
        EditText edSoDienThoai = findViewById(R.id.editSoDienThoai);
        EditText edGhiChu = findViewById(R.id.editGhiChu);

        String hoTen = getIntent().getStringExtra("hoTen");
        String soDienThoai = getIntent().getStringExtra("soDienThoai");
        String email = getIntent().getStringExtra("email");
        String ghiChu = getIntent().getStringExtra("ghiChu");

        edHoTen.setText(hoTen);
        edEmail.setText(email);
        edSoDienThoai.setText(soDienThoai);
        edGhiChu.setText(ghiChu);

        AppCompatButton btnSua = findViewById(R.id.editDanhBa);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTenEdit = edHoTen.getText().toString().trim();
                String soDienThoaiEdit = edSoDienThoai.getText().toString().trim();
                String emailEdit = edEmail.getText().toString().trim();
                String ghiChuEdit = edGhiChu.getText().toString().trim();

                DanhBa danhBaEdit = new DanhBa();
                danhBaEdit.setIdDanhBa(getIntent().getIntExtra("idDanhBa", 0));
                danhBaEdit.setHoTen(hoTenEdit);
                danhBaEdit.setEmail(emailEdit);
                danhBaEdit.setDienThoai(soDienThoaiEdit);
                danhBaEdit.setGhiChu(ghiChuEdit);
                boolean check = danhBaDAO.updateDatabase(danhBaEdit);
                if (check) {
                    Toast.makeText(SuaLienHe.this, "Đã cập nhật liên hệ!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SuaLienHe.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppCompatButton btnHuy = findViewById(R.id.btnHuyEdit);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}