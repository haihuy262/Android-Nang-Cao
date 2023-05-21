package huynhph30022.fpoly.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    String currentMusicUri;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String musicUri = intent.getStringExtra("musicUri");
        if (musicUri != null) {
            playMusic(musicUri);
            mediaPlayer.start();
            Toast.makeText(this, "Bắt Đầu Phát Nhạc", Toast.LENGTH_SHORT).show();
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Toast.makeText(this, "Dừng Nhạc", Toast.LENGTH_SHORT).show();
        } else {
            mediaPlayer.start();
            Toast.makeText(this, "Tiếp Tục Nhạc", Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }

    public void playMusic(String musicUri) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            currentMusicUri = musicUri;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        Toast.makeText(this, "Tắt Nhạc", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
