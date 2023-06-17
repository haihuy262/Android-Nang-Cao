package huynhph30022.fpoly.dethi1706.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.dethi1706.database.DbHelper;
import huynhph30022.fpoly.dethi1706.model.Feel;

public class RssDAO {
    DbHelper dbHelper;

    public RssDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Feel> getAllData() {
        ArrayList<Feel> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RSS", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Feel objFeel = new Feel();
                objFeel.setId(cursor.getInt(cursor.getColumnIndexOrThrow("idPH30022")));
                objFeel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("titlePH30022")));
                objFeel.setPubDate(cursor.getString(cursor.getColumnIndexOrThrow("pubDatePH30022")));
                objFeel.setLink(cursor.getString(cursor.getColumnIndexOrThrow("linkPH30022")));
                objFeel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("descriptionPH30022")));
                list.add(objFeel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insertData(Feel objFeel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titlePH30022", objFeel.getTitle());
        values.put("pubDatePH30022", objFeel.getPubDate());
        values.put("linkPH30022", objFeel.getLink());
        values.put("descriptionPH30022", objFeel.getDescription());
        db.insert("RSS", null, values);
    }
    
}
