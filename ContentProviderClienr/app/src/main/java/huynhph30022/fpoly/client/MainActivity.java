package huynhph30022.fpoly.client;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // APP Client
        Uri uri_san_pham = Uri.parse("content://huynhph30022.fpoly.src.provider.SanPhamProvider/SanPham");
        Cursor cursor = getContentResolver().query(uri_san_pham,null,null,null,null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Log.e("nguyenhaihuy260","ID " + cursor.getInt(0) + "Name" + cursor.getString(1));
            }while (cursor.moveToNext());
        }else {
            Log.e("nguyenhaihuy260","Con tro null");
        }
    }
}