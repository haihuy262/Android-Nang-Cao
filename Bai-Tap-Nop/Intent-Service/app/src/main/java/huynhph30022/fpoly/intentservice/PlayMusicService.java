package huynhph30022.fpoly.intentservice;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PlayMusicService extends android.app.IntentService {
    MediaPlayer player = null;
    String TAG = "PlayMusic-zzzzzzz";
    int ID_SV;

    public PlayMusicService() {
        super("PlayMusicService");
        Log.d(TAG, "Gọi hàm khởi tạo.... ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "Gọi hàm onStartCommand id service = " + startId);
        ID_SV = startId;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (PlayMusicService.this) {
            Log.d(TAG, "Gọi hàm onHandleIntent - id = " + ID_SV);
            if (player == null) {
                player = MediaPlayer.create(this, R.raw.a);
                player.start();
            }
            Toast.makeText(this, "onHandleIntent", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onHandleIntent: " + player.toString());

            while (player != null && player.isPlaying()) {
                try {
                    wait(1);
                    Log.d(TAG, "onHandleIntent: nhạc vẫn chạy… ");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopSelf(ID_SV);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Gọi hàm onDestroy - id = " + ID_SV);
        if (player != null) {
            Log.d(TAG, "player != null ");
            player.stop();
            player.release();// giải phóng playler
            player = null;
            Toast.makeText(this, "Hết nhạc rồi", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
