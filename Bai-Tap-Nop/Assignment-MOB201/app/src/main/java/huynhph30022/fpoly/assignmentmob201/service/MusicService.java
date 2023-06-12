package huynhph30022.fpoly.assignmentmob201.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
    public static final String ACTION_UPDATE_SEEKBAR = "huynhph30022.fpoly.UPDATE_SEEK_BAR";
    public static final String ACTION_UPDATE_UI = "huynhph30022.fpoly.UPDATE_UI";
    protected int totalTime;
    protected int currentTime;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String musicUri = intent.getStringExtra("link");
            playMusic(musicUri);
        }
        return START_NOT_STICKY;
    }

    private void playMusic(String musicUri) {
        Uri uriMusic = Uri.parse(musicUri);
        mediaPlayer = new MediaPlayer();
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        mediaPlayer.setAudioAttributes(audioAttributes);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uriMusic);
            mediaPlayer.prepare();
            mediaPlayer.start();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
