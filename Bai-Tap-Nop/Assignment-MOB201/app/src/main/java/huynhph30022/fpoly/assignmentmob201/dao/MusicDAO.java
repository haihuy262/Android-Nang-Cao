package huynhph30022.fpoly.assignmentmob201.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.assignmentmob201.database.DbHelper;
import huynhph30022.fpoly.assignmentmob201.model.Song;

public class MusicDAO {
    DbHelper dbHelper;

    public MusicDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Song> getAllDatabase() {
        ArrayList<Song> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Music", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Song(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
