package huynhph30022.fpoly.ontap952023.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.ontap952023.model.DanhBa;

public class DanhBaDAO {
    public DpHelper dpHelper;

    public DanhBaDAO(Context context) {
        dpHelper = new DpHelper(context);
    }

    public ArrayList<DanhBa> getAllDatabase() {
        ArrayList<DanhBa> list = new ArrayList<>();
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DANHBA", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                DanhBa danhBa = new DanhBa();
                danhBa.setIdDanhBa(cursor.getInt(cursor.getColumnIndexOrThrow("idDanhBa")));
                danhBa.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("hoTen")));
                danhBa.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("dienThoai")));
                danhBa.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                danhBa.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("ghiChu")));
                list.add(danhBa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean addDatabase(DanhBa danhBa) {
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", danhBa.getHoTen());
        values.put("dienThoai", danhBa.getDienThoai());
        values.put("email", danhBa.getEmail());
        values.put("ghiChu", danhBa.getGhiChu());
        long check = db.insert("DANHBA", null, values);
        return check != -1;
    }

    public boolean updateDatabase(DanhBa danhBa) {
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", danhBa.getHoTen());
        values.put("dienThoai", danhBa.getDienThoai());
        values.put("email", danhBa.getEmail());
        values.put("ghiChu", danhBa.getGhiChu());
        int check = db.update("DANHBA", values, "idDanhBa=?", new String[]{String.valueOf(danhBa.getIdDanhBa())});
        return check != -1;
    }

    public boolean deleteDatabase(int idDanhBa) {
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        int check = db.delete("DANHBA", "idDanhBa=?", new String[]{String.valueOf(idDanhBa)});
        return check != -1;
    }
}
