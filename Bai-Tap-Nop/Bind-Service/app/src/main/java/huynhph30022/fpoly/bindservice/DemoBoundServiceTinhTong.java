package huynhph30022.fpoly.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class DemoBoundServiceTinhTong extends Service {
    IBinder iBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int Tong(int a, int b) {
        int t = a + b;
        return t;
    }

    public class LocalBinder extends Binder {
        LocalBinder getLocalBinder() {
            return LocalBinder.this; // phương thức khởi tạo khi client gọi tới các phương thức của service
        }
    }
}
