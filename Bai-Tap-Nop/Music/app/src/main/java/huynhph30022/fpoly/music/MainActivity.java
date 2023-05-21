package huynhph30022.fpoly.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import huynhph30022.fpoly.music.adapter.MusicAdapter;
import huynhph30022.fpoly.music.database.MusicDAO;
import huynhph30022.fpoly.music.model.Music;

public class MainActivity extends AppCompatActivity {
    MusicDAO musicDAO;
    MusicAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Music> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MusicAdapter(MainActivity.this);
        musicDAO = new MusicDAO(MainActivity.this);
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = musicDAO.getAllDatabase();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }
}