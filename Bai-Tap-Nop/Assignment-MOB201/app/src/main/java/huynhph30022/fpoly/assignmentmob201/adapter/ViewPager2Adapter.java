package huynhph30022.fpoly.assignmentmob201.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import huynhph30022.fpoly.assignmentmob201.fragment.FavoriteFragment;
import huynhph30022.fpoly.assignmentmob201.fragment.MusicFragment;
import huynhph30022.fpoly.assignmentmob201.fragment.NewspaperFragment;
import huynhph30022.fpoly.assignmentmob201.fragment.UserFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new MusicFragment();
        } else if (position == 1) {
            return new FavoriteFragment();
        } else if (position == 2) {
            return new NewspaperFragment();
        } else {
            return new UserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
