package huynhph30022.fpoly.assignmentmob201.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
    public static final String ACTION_UPDATE_SEEKBAR = "huynhph30022.fpoly.UPDATE_SEEK_BAR";
    public static final String ACTION_UPDATE_UI = "huynhph30022.fpoly.UPDATE_UI";
    public static final String ACTION_PLAY_SONG = "huynhph30022.fpoly.ACTION_PLAY";
    public static final String ACTION_STOP_SONG = "huynhph30022.fpoly.ACTION_STOP";
    protected int totalTime;
    protected int currentTime;
    MyBinder binder = new MyBinder();
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private boolean isPlaying = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equalsIgnoreCase(ACTION_PLAY_SONG)) {
                    String urlSong = intent.getStringExtra("link");
                    playMusic(urlSong);
                }
            }
        }
        return START_NOT_STICKY;
    }

    private void playMusic(String musicUri) {
        Uri uriMusic = Uri.parse(musicUri);
        stopMusic();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getApplicationContext(), uriMusic);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;

            totalTime = mediaPlayer.getDuration();
            updateSeekBar(totalTime);

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        currentTime = mediaPlayer.getCurrentPosition();
                        updateUI(currentTime);
                        handler.postDelayed(this, 1000);
                    }
                }
            };
            handler.postDelayed(runnable, 1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    private void updateUI(int currentTime) {
        Intent intent = new Intent(ACTION_UPDATE_UI);
        intent.putExtra("currentTime", currentTime);
        sendBroadcast(intent);
    }

    private void updateSeekBar(int totalTime) {
        Intent intent = new Intent(ACTION_UPDATE_SEEKBAR);
        intent.putExtra("totalTime", totalTime);
        sendBroadcast(intent);
    }

    public void pauseOrPlay() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        } else if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
