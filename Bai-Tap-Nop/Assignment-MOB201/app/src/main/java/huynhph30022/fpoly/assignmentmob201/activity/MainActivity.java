package huynhph30022.fpoly.assignmentmob201.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager2);
        navigationBarView = findViewById(R.id.bottomNav);
        fab = findViewById(R.id.fab);

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
                    fab.setVisibility(View.VISIBLE);
                }
                if (position == 1) {
                    navigationBarView.getMenu().findItem(R.id.action_favorite).setChecked(true);
                    fab.setVisibility(View.INVISIBLE);
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
}