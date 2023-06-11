package huynhph30022.fpoly.assignmentmob201.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import huynhph30022.fpoly.assignmentmob201.dao.UserDAO;

public class RegisterService extends Service {
    protected UserDAO userDAO;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String soDienThoai = intent.getStringExtra("soDienThoai");
        String matKhau = intent.getStringExtra("matKhau");
        String nhapLaiMatKhau = intent.getStringExtra("nhapLaiMatKhau");
        if (matKhau.equals(nhapLaiMatKhau)) {
            boolean themTaiKhoan = themTaiKhoan(soDienThoai, matKhau);
            ketQuaDangKy(themTaiKhoan);
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    private boolean themTaiKhoan(String soDienThoai, String matKhau) {
        userDAO = new UserDAO(this);
        return userDAO.insertUser(soDienThoai, matKhau);
    }

    private void ketQuaDangKy(boolean isKetQua) {
        Intent intent = new Intent("ketQuaDangKy");
        intent.putExtra("isKetQua", isKetQua);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

