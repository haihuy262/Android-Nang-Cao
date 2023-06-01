package huynhph30022.fpoly.src.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import huynhph30022.fpoly.src.database.DbHelper;

public class SanPhamDAO {
    DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Cursor getAllProvider(String[] column, String selection, String[] args, String orderBy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("SanPham", column, selection, args, null, null, orderBy);
        return cursor;
    }
}
