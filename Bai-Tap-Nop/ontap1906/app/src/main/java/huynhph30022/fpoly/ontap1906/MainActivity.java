package huynhph30022.fpoly.ontap1906;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import huynhph30022.fpoly.ontap1906.adapter.RssAdapter;
import huynhph30022.fpoly.ontap1906.model.Feel;
import huynhph30022.fpoly.ontap1906.rss.RssLoader;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RssAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RssAdapter(MainActivity.this);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        String url = "https://zezo.dev/sitemap.xml";
        DownloadTinTuc downloadTinTuc = new DownloadTinTuc();
        downloadTinTuc.execute(url);
    }

    public class DownloadTinTuc extends AsyncTask<String, Void, ArrayList<Feel>> {
        @Override
        protected ArrayList<Feel> doInBackground(String... strings) {
            RssLoader rssLoader = new RssLoader();
            ArrayList<Feel> list = new ArrayList<>();
            String urlRss = strings[0];
            try {
                URL url = new URL(urlRss);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    list = rssLoader.getTinTucListPH30022(inputStream);
                }
            } catch (IOException | XmlPullParserException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Feel> list) {
            super.onPostExecute(list);
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
        }
    }
}