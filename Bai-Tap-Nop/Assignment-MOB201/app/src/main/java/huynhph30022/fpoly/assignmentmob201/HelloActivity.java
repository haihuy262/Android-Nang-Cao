package huynhph30022.fpoly.assignmentmob201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import huynhph30022.fpoly.assignmentmob201.activity.LoginActivity;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        Glide.with(HelloActivity.this).load(R.mipmap.giphy2).into(imgLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelloActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}