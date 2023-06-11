package huynhph30022.fpoly.assignmentmob201.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.adapter.NewspaperViewPager2Adapter;

public class NewspaperFragment extends Fragment {
    protected TabLayout tabLayoutNewspaper;
    protected ViewPager2 viewPager2Newspaper;
    protected NewspaperViewPager2Adapter newspaperViewPager2Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newspaper, container, false);
        
        tabLayoutNewspaper = view.findViewById(R.id.tabLayout);
        viewPager2Newspaper = view.findViewById(R.id.viewPager2Newspaper);
        newspaperViewPager2Adapter = new NewspaperViewPager2Adapter(requireActivity());
        viewPager2Newspaper.setAdapter(newspaperViewPager2Adapter);
        new TabLayoutMediator(tabLayoutNewspaper, viewPager2Newspaper, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Khoa học");
                } else if (position == 1) {
                    tab.setText("Kinh doanh");
                } else {
                    tab.setText("Thể thao");
                }
            }
        }).attach();
        return view;
    }
}