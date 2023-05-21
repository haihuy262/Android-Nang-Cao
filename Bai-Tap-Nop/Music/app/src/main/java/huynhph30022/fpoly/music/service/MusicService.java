package huynhph30022.fpoly.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private String currentMusicUri;
    private boolean isPaused = false;
    private final IBinder binder = new MusicBinder();

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

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
            isPaused = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }

    public void resumeMusic() {
        if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
        }
    }

    public void stopMusic() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        currentMusicUri = null;
        isPaused = false;
    }

    public String getCurrentMusicUri() {
        return currentMusicUri;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
