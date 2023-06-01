package huynhph30022.fpoly.src.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "contentprovidersrc.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSanPham = "CREATE TABLE IF NOT EXISTS SanPham(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        db.execSQL(createTableSanPham);

        String data = "INSERT INTO SanPham VALUES(1,'Kem danh rang'),(2,'Dien Thoai'),(3,'May Tinh')";
        db.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS SanPham");
        }
    }
}
