package huynhph30022.fpoly.play_music_service_broadcast_pause_resume;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    boolean isPlaying = false;
    // biến này xác định trạng thái đang chạy hoặc dừng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         final TextView text = findViewById(R.id.tv01);

        // tạo intent để khởi động service 
        Intent intentMusic = new Intent(MainActivity.this, MusicService.class);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
                // pause
                if(isPlaying){
                    // đang chạy
                    text.setText("Paused");
                    // thông báo cho service biết là hành động dừng
                    sendBroadcast(new Intent(MusicService.ACTION_PAUSE));
                    isPlaying = false;
                }else{
                    text.setText("Playing...");
                    sendBroadcast(new Intent(MusicService.ACTION_RESUME));
                    isPlaying = true;
                }
            }
        });

        final TextView nhacchuong = findViewById(R.id.tv_nhacchuong);
        final TextView tayduky = findViewById(R.id.tv_tayduky);


        nhacchuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // chạy bản nhạc chuông, nếu đang play thì dừng xong mới chạy
                if (isMyServiceRunning(MusicService.class)) {
                    text.setText("Stoped");
                    stopService(intentMusic);
                }

                text.setText("Started");
                intentMusic.putExtra("song", R.raw.nhac_chuong);
                startService(intentMusic);

                isPlaying = true;

            }
        });

        tayduky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chạy bản nhạc tây du ký, nếu đang play thì dừng xong mới chạy
                if (isMyServiceRunning(MusicService.class)) {
                    text.setText("Stoped");
                    stopService(intentMusic);
                }

                text.setText("Started");
                intentMusic.putExtra("song", R.raw.tayduky);
                startService(intentMusic);
 
                isPlaying = true;
            }
        });

    }

    // hàm kiểm tra service có đang chạy hay không, tránh bị xảy ra gọi nhiều lần service 
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {

        // Nếu activity bị hủy thì có thể gọi lại service cho tự động chạy lại.
        // Chú ý: Không phải lúc nào cũng làm như vậy, trường hợp đặc biệt thì mới làm vậy. 

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);

        this.sendBroadcast(broadcastIntent);

        super.onDestroy();
    }

   

}

