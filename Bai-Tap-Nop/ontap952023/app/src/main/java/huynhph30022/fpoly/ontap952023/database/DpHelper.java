package huynhph30022.fpoly.ontap952023.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DpHelper extends SQLiteOpenHelper {
    public DpHelper(Context context) {
        super(context, "QUANLYDANHBA", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE DANHBA(idDanhBa INTEGER PRIMARY KEY AUTOINCREMENT, hoTen TEXT, dienThoai TEXT, email TEXT, ghiChu TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS DANHBA");
            onCreate(db);
        }
    }
}