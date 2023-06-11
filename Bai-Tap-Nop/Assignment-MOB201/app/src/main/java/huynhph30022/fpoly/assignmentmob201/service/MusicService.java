package huynhph30022.fpoly.assignmentmob201.service;

import static huynhph30022.fpoly.assignmentmob201.notification.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.fragment.MusicFragment;
import huynhph30022.fpoly.assignmentmob201.model.Song;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Song objSong = (Song) bundle.get("object_song");
            if (objSong != null) {
                sendNotification(objSong);
                startMusic(objSong);
            }
        }
        return START_NOT_STICKY;
    }

    private void startMusic(Song objSong) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), objSong.getResource());
        }
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void sendNotification(Song objSong) {
        Intent intent = new Intent(MusicService.this, MusicFragment.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), objSong.getImage());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_custom);
        remoteViews.setTextViewText(R.id.tvTitleSong, objSong.getTitle());
        remoteViews.setTextViewText(R.id.tvSingleSong, objSong.getSingle());
        remoteViews.setImageViewBitmap(R.id.imgSong, bitmap);
        remoteViews.setImageViewResource(R.id.imgPlay, R.drawable.ic_pause);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();
        startForeground(1, notification);
    }
}
