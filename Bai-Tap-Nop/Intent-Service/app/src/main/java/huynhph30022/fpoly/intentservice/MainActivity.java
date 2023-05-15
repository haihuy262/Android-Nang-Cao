package huynhph30022.fpoly.intentservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ComponentName sv_play = null;
    String TAG = "PlayMusic-zzzzzzz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentMusic = new Intent(MainActivity.this, PlayMusicService.class);

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnPlay onClick: ");
                if (sv_play == null) // chống click nhiều
                    sv_play = startService(intentMusic);
            }
        });

        Button btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnStop onClick: ");
                stopService(intentMusic);
                sv_play = null;
            }
        });
    }
}