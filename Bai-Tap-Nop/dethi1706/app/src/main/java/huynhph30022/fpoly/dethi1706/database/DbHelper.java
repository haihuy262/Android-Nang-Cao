package huynhph30022.fpoly.dethi1706.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "ontap1706.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS RSS(idPH30022 INTEGER PRIMARY KEY AUTOINCREMENT,titlePH30022 TEXT, descriptionPH30022 TEXT, pubDatePH30022 TEXT, linkPH30022 TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
