package huynhph30022.fpoly.assignmentmob201.newspaper;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.adapter.NewspaperAdapter;
import huynhph30022.fpoly.assignmentmob201.model.Newspaper;

public class KhoaHocFragment extends Fragment {
    protected DownloadTinTuc downloadTinTuc;
    private RecyclerView recyclerKhoaHoc;
    private NewspaperAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoa_hoc, container, false);
        recyclerKhoaHoc = view.findViewById(R.id.recycler_khoaHoc);
        adapter = new NewspaperAdapter(requireContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerKhoaHoc.setLayoutManager(layoutManager);
        downloadTinTuc = new DownloadTinTuc(this);

        String urlRss = "https://vnexpress.net/rss/khoa-hoc.rss";
        downloadTinTuc.execute(urlRss);
        return view;
    }

    public class DownloadTinTuc extends AsyncTask<String, Void, ArrayList<Newspaper>> {
        KhoaHocFragment khoaHocFragment = null;

        public DownloadTinTuc(KhoaHocFragment khoaHocFragment) {
            this.khoaHocFragment = khoaHocFragment;
        }

        @Override
        protected ArrayList<Newspaper> doInBackground(String... strings) {
            TinTucLoader tinTucLoader = new TinTucLoader();
            ArrayList<Newspaper> list = new ArrayList<>();
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
        protected void onPostExecute(ArrayList<Newspaper> newspapers) {
            super.onPostExecute(newspapers);
            adapter.setData(newspapers);
            recyclerKhoaHoc.setAdapter(adapter);
        }
    }
}