package huynhph30022.fpoly.phone_sms;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAO {
    private final SQLiteDatabase sqLite;
    private final SQLiteDatabase sqLite1;

    public DAO(Context context) {
        DbHelper helper = new DbHelper(context);
        sqLite = helper.getWritableDatabase();
        sqLite1 = helper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Contact> getData(String sql, String... SelectAvgs) {
        ArrayList<Contact> lst = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            Contact ct = new Contact();
            ct.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            ct.setName(cursor.getString(cursor.getColumnIndex("name")));
            ct.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            lst.add(ct);
        }
        return lst;
    }

    public ArrayList<Contact> getAllData() {
        String sql = "SELECT * FROM tbl_contact";
        return getData(sql);
    }

    public long insert(Contact ct) {
        ContentValues values = new ContentValues();
        values.put("name", ct.getName());
        values.put("phone", ct.getPhone());
        return sqLite.insert("tbl_contact", null, values);
    }

    public boolean isContactExists(Contact contact) {
        String query = "SELECT * FROM tbl_contact WHERE phone = ?";
        String[] selectionArgs = {contact.getPhone()};
        Cursor cursor = sqLite1.rawQuery(query, selectionArgs);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}
