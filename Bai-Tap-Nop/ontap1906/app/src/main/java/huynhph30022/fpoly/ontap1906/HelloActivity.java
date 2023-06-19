package huynhph30022.fpoly.ontap1906;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ImageView imgLogoPH30022 = findViewById(R.id.imgLogo);

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(HelloActivity.this, R.animator.hieu_ung);
        animatorSet.setTarget(imgLogoPH30022);
        animatorSet.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelloActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}