package huynhph30022.fpoly.ontap952023;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import huynhph30022.fpoly.ontap952023.activity.ThemDanhBa;
import huynhph30022.fpoly.ontap952023.adapter.DanhBaAdapter;
import huynhph30022.fpoly.ontap952023.database.DanhBaDAO;
import huynhph30022.fpoly.ontap952023.model.DanhBa;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DanhBaAdapter adapter;
    private DanhBaDAO danhBaDAO;
    private ArrayList<DanhBa> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new DanhBaAdapter(MainActivity.this);
        danhBaDAO = new DanhBaDAO(MainActivity.this);
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThemDanhBa.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = danhBaDAO.getAllDatabase();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }
}