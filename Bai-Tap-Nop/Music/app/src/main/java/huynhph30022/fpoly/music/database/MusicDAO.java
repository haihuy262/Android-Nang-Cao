package huynhph30022.fpoly.music.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.music.model.Music;

public class MusicDAO {
    DbHelper dbHelper;

    public MusicDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Music> getAllDatabase() {
        ArrayList<Music> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MP3", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Music music = new Music();
                music.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                music.setTenNhac(cursor.getString(cursor.getColumnIndexOrThrow("ten")));
                music.setLinkNhac(cursor.getString(cursor.getColumnIndexOrThrow("link")));
                list.add(music);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean addDatabase(Music obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten", obj.getTenNhac());
        values.put("link", obj.getLinkNhac());
        long check = db.insert("MP3", null, values);
        return check != 1;
    }
}
