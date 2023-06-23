package huyndph30375.fpoly.foodorder3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import huyndph30375.fpoly.foodorder3.fragment.CartFragment;
import huyndph30375.fpoly.foodorder3.fragment.ContactFragment;
import huyndph30375.fpoly.foodorder3.fragment.FeedbackFragment;
import huyndph30375.fpoly.foodorder3.fragment.HistoryFragment;
import huyndph30375.fpoly.foodorder3.fragment.HomeFragment;

public class ViewpagerMainAdapter extends FragmentStateAdapter {

    public ViewpagerMainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new FeedbackFragment();
            case 3:
                return new ContactFragment();
            case 4:
                return new HistoryFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
