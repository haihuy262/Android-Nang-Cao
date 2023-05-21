package huynhph30022.fpoly.music.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "music.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMusic = "CREATE TABLE IF NOT EXISTS MP3 (id INTEGER PRIMARY KEY AUTOINCREMENT, ten TEXT, link TEXT)";
        db.execSQL(createTableMusic);

        db.execSQL("INSERT INTO MP3 VALUES (1,'Giữ Em Thật Lâu','https://www.nhaccuatui.com/bai-hat/giu-em-that-lau-naod.TPDciU83yMeU.html')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS MP3");
            onCreate(db);
        }
    }
}
