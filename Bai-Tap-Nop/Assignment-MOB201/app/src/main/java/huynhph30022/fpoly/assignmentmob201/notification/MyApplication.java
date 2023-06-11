package huynhph30022.fpoly.assignmentmob201.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "channel_service";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannelNotification();
    }

    private void createChannelNotification() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Service", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setSound(null, null);
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }
}
