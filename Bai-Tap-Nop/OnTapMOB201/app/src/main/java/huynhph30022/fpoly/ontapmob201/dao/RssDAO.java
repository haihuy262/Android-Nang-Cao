package huynhph30022.fpoly.ontapmob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.ontapmob201.database.DatabaseHelper;
import huynhph30022.fpoly.ontapmob201.model.Feel;

public class RssDAO {
    DatabaseHelper dbHelper;

    public RssDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public ArrayList<Feel> getAllRss() {
        ArrayList<Feel> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RSS", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Feel objFeel = new Feel();
                objFeel.setIdRss(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                objFeel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                objFeel.setPubDate(cursor.getString(cursor.getColumnIndexOrThrow("pubDate")));
                objFeel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                objFeel.setLink(cursor.getString(cursor.getColumnIndexOrThrow("link")));
                list.add(objFeel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insertRss(Feel objFeel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", objFeel.getTitle());
        values.put("description", objFeel.getDescription());
        values.put("pubDate", objFeel.getPubDate());
        values.put("link", objFeel.getLink());
        db.insert("RSS", null, values);
    }

    public boolean checkLink(Feel objFeel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RSS WHERE link =?", new String[]{String.valueOf(objFeel.getLink())});
        boolean check = cursor.getCount() > 0;
        cursor.close();
        return check;
    }
}
