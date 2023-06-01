package huynhph30022.fpoly.assignmentmob201.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import huynhph30022.fpoly.assignmentmob201.dao.UserDAO;

public class RegisterService extends Service {
    MyBinder myBinder = new MyBinder();
    UserDAO userDAO;

    @Override
    public void onCreate() {
        Log.e("RegisterService", "onCrete");
        super.onCreate();
        userDAO = new UserDAO(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("RegisterService", "onBind");
        String soDienThoai = intent.getStringExtra("soDienThoai");
        String matKhau = intent.getStringExtra("matKhau");
        String reMatKhau = intent.getStringExtra("reMatKhau");
        if (!reMatKhau.equals(matKhau)) {
            Log.e("RegisterService", "Nhập lại mật khẩu không đúng");
        } else {
            Log.e("RegisterService", "Mật khẩu đúng");
        }
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("RegisterService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("RegisterService", "onDestroy");
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public RegisterService getService() {
            return RegisterService.this;
        }
    }
}

