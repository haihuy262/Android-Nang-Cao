package huynhph30022.fpoly.phone_sms;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1;
    DbHelper db = new DbHelper(this);
    private RecyclerView recyclerView;
    private Button buttonSync;
    private ContactAdapter adapter;
    private ArrayList<Contact> list;
    private DAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyContact);
        buttonSync = findViewById(R.id.btn_dongbo);

        adapter = new ContactAdapter(list, this);
        addData();

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });
    }

    private void addData() {
        dao = new DAO(getApplicationContext());
        list = dao.getAllData();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getList() {
        //                // check quyền truy cập danh bạ
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập nếu chưa được cấp
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_READ_CONTACTS);
        } else {
            list = new ArrayList<>();
            ContentResolver contentResolver = getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            Cursor cursor = contentResolver.query(uri, projection, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.
                            CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.
                            CommonDataKinds.Phone.NUMBER));
                    dao = new DAO(getApplicationContext());
                    Contact contact = new Contact(name, phone);
                    if (!dao.isContactExists(contact)) {
                        dao.insert(contact);
                        addData();
                        adapter.setData(list);
                    }
//                    if (dao.insert(contact)>0){
//                        addData();
//                        adapter.setData(list);
//                    }
                }
                cursor.close();

            }
//                    for (Contact contact : list) {
//                            dao.insert(contact);
//                    }
        }
    }
}