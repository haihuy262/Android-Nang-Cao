package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.adapter.ViewPager2Adapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager2);
        navigationBarView = findViewById(R.id.bottomNav);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_music) {
                    viewPager2.setCurrentItem(0);
                }
                if (item.getItemId() == R.id.action_favorite) {
                    viewPager2.setCurrentItem(1);
                }
                if (item.getItemId() == R.id.action_newspaper) {
                    viewPager2.setCurrentItem(2);
                }
                if (item.getItemId() == R.id.information) {
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(MainActivity.this);
        viewPager2.setAdapter(viewPager2Adapter);

        // Sự kiện khi vuốt màn hình bottomView sẽ đổi theo
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    navigationBarView.getMenu().findItem(R.id.action_music).setChecked(true);
                }
                if (position == 1) {
                    navigationBarView.getMenu().findItem(R.id.action_favorite).setChecked(true);
                }
                if (position == 2) {
                    navigationBarView.getMenu().findItem(R.id.action_newspaper).setChecked(true);
                }
                if (position == 3) {
                    navigationBarView.getMenu().findItem(R.id.information).setChecked(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có chắc muốn thoát không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
                finish();
            }
        });
        builder.setNegativeButton("Không", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}