package huynhph30022.fpoly.play_music_service_broadcast_pause_resume;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MusicService extends Service {
    public final static String ACTION_PAUSE = "net.spx.demomusic.ACTION_PAUSE";
    public final static String ACTION_RESUME = "net.spx.demomusic.ACTION_RESUME";
    MediaPlayer musicPlayer;

    public MusicService() {


    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("zzzzzzz", "onBind: ");
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return  null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // Gọi hàm đăng ký broadcast cho 2 hành động pause và resume 
        register_pausePlayingAudio();
        register_resumePlayingAudio();

        musicPlayer = MediaPlayer.create(this, R.raw.tayduky);
        musicPlayer.setLooping(false);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Bắt đầu service ", Toast.LENGTH_LONG).show();

        int song = intent.getIntExtra("song",0);

        if(song != 0){
            Log.d("zzzzz", "onStartCommand: song = " + song);
            musicPlayer = MediaPlayer.create(this, song);
        }

        musicPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
        Toast.makeText(this, "Dừng service", Toast.LENGTH_LONG).show();
    }


    // viết cái này ở đây thì không cần phải tạo file mới, và nó tương tác trực tiếp được với biến của service này

    private BroadcastReceiver pausePlayingAudio = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction(); // lấy action và đối chiếu thao tác
            Log.d("zzzzzz", "onReceive: " + act);

            switch (act){
                case MusicService.ACTION_PAUSE:
                    musicPlayer.pause();
                    break;
                case MusicService.ACTION_RESUME:
                    musicPlayer.start();
                    break;
                default:
                    // khong lam gi
            }

        }
    };


    // Đăng ký cái Receiver bằng code java, việc làm này không cần khai báo ở mainifest nữa.
    // Ở đây đăng ký 2 hoạt động là pause và resume, khi activity gửi broadcast thì service sẽ xử lý tương ứng.

    private void register_pausePlayingAudio(){
        IntentFilter intentFilter = new IntentFilter(MusicService.ACTION_PAUSE) ;
        this.registerReceiver(pausePlayingAudio , intentFilter) ;
    }
    private void register_resumePlayingAudio(){
        IntentFilter intentFilter = new IntentFilter(MusicService.ACTION_RESUME) ;
        this.registerReceiver(pausePlayingAudio , intentFilter) ;
    }
}