package huynhph30022.fpoly.ontapmob201;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.ComponentName;

import android.content.Intent;

import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import huynhph30022.fpoly.ontapmob201.adapter.RssAdapter;
import huynhph30022.fpoly.ontapmob201.dao.RssDAO;
import huynhph30022.fpoly.ontapmob201.model.Feel;
import huynhph30022.fpoly.ontapmob201.rss.ServiceTinTuc;
import huynhph30022.fpoly.ontapmob201.rss.TinTucLoader;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewRss;
    LinearLayoutManager layoutManager;
    RssDAO rssDAO;
    ArrayList<Feel> list;
    RssAdapter adapter;
    boolean isBound = false;
    ServiceTinTuc serviceTinTuc;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceTinTuc.MyBinder binder = (ServiceTinTuc.MyBinder) service;
            serviceTinTuc = binder.getService();
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
        setContentView(R.layout.activity_main);

        recyclerViewRss = findViewById(R.id.recyclerViewRss);

        adapter = new RssAdapter(getApplicationContext());
        list = new ArrayList<>();
        rssDAO = new RssDAO(getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRss.setLayoutManager(layoutManager);

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.hieu_ung);
        animatorSet.setTarget(recyclerViewRss);
        animatorSet.start();

        String urlRss = "https://thanhnien.vn/rss/thoi-su.rss";
        DownloadTinTuc downloadTinTuc = new DownloadTinTuc(MainActivity.this);
        downloadTinTuc.execute(urlRss);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, ServiceTinTuc.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    public class DownloadTinTuc extends AsyncTask<String, Void, ArrayList<Feel>> {
        MainActivity activity = null;

        public DownloadTinTuc(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected ArrayList<Feel> doInBackground(String... strings) {
            TinTucLoader tinTucLoader = new TinTucLoader();
            ArrayList<Feel> list = new ArrayList<>();

            String urlRss = strings[0];
            try {
                URL url = new URL(urlRss);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    list = tinTucLoader.getTinTucList(inputStream);
                }
            } catch (IOException | XmlPullParserException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Feel> list) {
            super.onPostExecute(list);
            for (Feel objFeel : list) {
                if (!rssDAO.checkLink(objFeel)) // if != true
                    rssDAO.insertRss(objFeel);
            }
            list = rssDAO.getAllRss();
            adapter.setData(list);
            recyclerViewRss.setAdapter(adapter);
            serviceTinTuc.playMusic();
        }
    }
}

