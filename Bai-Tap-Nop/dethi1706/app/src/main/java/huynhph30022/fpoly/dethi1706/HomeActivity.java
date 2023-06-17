package huynhph30022.fpoly.dethi1706;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import huynhph30022.fpoly.dethi1706.adapter.RssAdapter;
import huynhph30022.fpoly.dethi1706.dao.RssDAO;
import huynhph30022.fpoly.dethi1706.model.Feel;
import huynhph30022.fpoly.dethi1706.rss.TinTucLoaderPH30022;
import huynhph30022.fpoly.dethi1706.service.MyService;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RssAdapter adapter;
    LinearLayoutManager layoutManager;
    RssDAO rssDAO;
    Button btnSave, btnCount;
    boolean isBound = false;
    MyService myService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        btnSave = findViewById(R.id.btnSave);
        btnCount = findViewById(R.id.btnCount);

        adapter = new RssAdapter(HomeActivity.this);
        layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        rssDAO = new RssDAO(HomeActivity.this);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Số lượng bản ghi: " + myService.soLuongBanGhi(), Toast.LENGTH_SHORT).show();
            }
        });

        String url = "https://thanhnien.vn/rss/cong-nghe-game.rss";
        DownloadTinTuc downloadTinTuc = new DownloadTinTuc();
        downloadTinTuc.execute(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(HomeActivity.this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    public class DownloadTinTuc extends AsyncTask<String, Void, ArrayList<Feel>> {
        @Override
        protected ArrayList<Feel> doInBackground(String... strings) {
            TinTucLoaderPH30022 tinTucLoader = new TinTucLoaderPH30022();
            ArrayList<Feel> list = new ArrayList<>();
            String urlRss = strings[0];
            try {
                URL url = new URL(urlRss);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    list = tinTucLoader.getTinTucListPH30022(inputStream);
                }
            } catch (IOException | XmlPullParserException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Feel> list) {
            super.onPostExecute(list);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = 0;
                    for (Feel objFeel : list) {
                        if (position % 3 == 0) {
                            rssDAO.insertData(objFeel);
                        }
                        position++;
                    }
                    myService.playMusic();
                }
            });
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
        }
    }
}
