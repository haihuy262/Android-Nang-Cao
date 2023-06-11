package huynhph30022.fpoly.assignmentmob201.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import huynhph30022.fpoly.assignmentmob201.newspaper.KhoaHocFragment;
import huynhph30022.fpoly.assignmentmob201.newspaper.KinhDoanhFragment;
import huynhph30022.fpoly.assignmentmob201.newspaper.TheThaoFragment;

public class NewspaperViewPager2Adapter extends FragmentStateAdapter {
    public NewspaperViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new KhoaHocFragment();
        } else if (position == 1) {
            return new KinhDoanhFragment();
        } else {
            return new TheThaoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
