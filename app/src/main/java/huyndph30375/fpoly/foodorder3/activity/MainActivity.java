package huyndph30375.fpoly.foodorder3.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.databinding.ActivityMainBinding;
import huyndph30375.fpoly.foodorder3.fragment.CartFragment;
import huyndph30375.fpoly.foodorder3.fragment.ContactFragment;
import huyndph30375.fpoly.foodorder3.fragment.FeedbackFragment;
import huyndph30375.fpoly.foodorder3.fragment.HistoryFragment;
import huyndph30375.fpoly.foodorder3.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        huyndph30375.fpoly.foodorder3.databinding.ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        fragment = new HomeFragment();
        loadFragment(fragment);

        mainBinding.bottomBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.cart_bottom:
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.feedback_bottom:
                    fragment = new FeedbackFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.contact_bottom:
                    fragment = new ContactFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.history_bottom:
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    return true;
                default:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}