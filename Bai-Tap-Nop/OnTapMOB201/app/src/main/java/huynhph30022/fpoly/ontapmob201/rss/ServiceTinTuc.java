package huynhph30022.fpoly.ontapmob201.rss;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import huynhph30022.fpoly.ontapmob201.R;

public class ServiceTinTuc extends Service {
    IBinder binder = new MyBinder();
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public ServiceTinTuc getService() {
            return ServiceTinTuc.this;
        }
    }

    public void playMusic() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaPlayer.start();
    }
}
