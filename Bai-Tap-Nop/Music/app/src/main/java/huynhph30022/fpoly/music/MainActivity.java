package huynhph30022.fpoly.music;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import huynhph30022.fpoly.music.adapter.MusicAdapter;
import huynhph30022.fpoly.music.database.MusicDAO;
import huynhph30022.fpoly.music.model.Music;
import huynhph30022.fpoly.music.service.MusicService;

public class MainActivity extends AppCompatActivity {
    MusicDAO musicDAO;
    MusicAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Music> list;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTieuDe = findViewById(R.id.tvTieuDe);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MusicAdapter(MainActivity.this, tvTieuDe);
        musicDAO = new MusicDAO(MainActivity.this);
        list = new ArrayList<>();
        fab = findViewById(R.id.fab);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.dialog_them_bai_hat, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCancelable(false);

                Button btnHuyThemBaiHat = view.findViewById(R.id.btnHuyThemBaiHat);
                Button btnThemBaiHat = view.findViewById(R.id.btnThemBaiHat);

                btnThemBaiHat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edTenBaiHat = view.findViewById(R.id.edTenBaiHat);
                        EditText edLinkBaiHat = view.findViewById(R.id.edLinkBaiHat);

                        String tenBaiHat = edTenBaiHat.getText().toString().trim();
                        String link = edLinkBaiHat.getText().toString().trim();

                        Music addMusic = new Music();
                        addMusic.setTenNhac(tenBaiHat);
                        addMusic.setLinkNhac(link);

                        boolean check = musicDAO.addDatabase(addMusic);
                        if (check) {
                            Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            onResume();
                        } else {
                            Toast.makeText(MainActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnHuyThemBaiHat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        ImageView imgPlay = findViewById(R.id.imgPlayMusic);
        ImageView imgPause = findViewById(R.id.imgPauseMusic);
        ImageView imgStop = findViewById(R.id.imgStopMusic);

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = musicDAO.getAllDatabase();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }
}