package huynhph30022.fpoly.assignmentmob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import huynhph30022.fpoly.assignmentmob201.database.DbHelper;

public class UserDAO {
    DbHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertUser(String soDienThoai, String matKhau) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soDienThoai", soDienThoai);
        values.put("matKhau", matKhau);
        long check = db.insert("User", null, values);
        return check != -1;
    }

    public boolean checkLogin(String soDienThoai, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE soDienThoai =? AND matKhau =?", new String[]{soDienThoai, matKhau});
        boolean checkLogin = cursor.getCount() > 0;
        cursor.close();
        return checkLogin;
    }

    public boolean checkNhapLaiMatKhau(String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE matKhau =?", new String[]{matKhau});
        boolean checkNhapLaiMatKhau = cursor.getCount() > 0;
        cursor.close();
        return checkNhapLaiMatKhau;
    }
}