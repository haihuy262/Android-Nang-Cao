package huynhph30022.fpoly.bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DemoBoundServiceTinhTong serviceTinhTong;

    ServiceConnection sv_conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DemoBoundServiceTinhTong.LocalBinder localBinder = (DemoBoundServiceTinhTong.LocalBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceTinhTong = new DemoBoundServiceTinhTong();
        Intent intentTinhTong = new Intent(MainActivity.this, DemoBoundServiceTinhTong.class);

        bindService(intentTinhTong, sv_conn, Context.BIND_AUTO_CREATE);

        Button btnTinhTong = findViewById(R.id.btnTinhTong);
        btnTinhTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tong = serviceTinhTong.Tong(2, 8);
                Toast.makeText(MainActivity.this, "Tổng = " + tong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}