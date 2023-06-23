package huyndph30375.fpoly.foodorder3.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.FacebookSdk;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactBinding.inflate(inflater, container, false);

        FacebookSdk.setApplicationId("your_facebook_app_id");
        FacebookSdk.sdkInitialize(getContext());

        binding.facebook.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100089163822124"));
            startActivity(intent);
        });

        binding.Call.setOnClickListener(view -> dialPhoneNumber("0398922772"));

        binding.Gmail.setOnClickListener(view -> compose(new String[]{"shenglongmob@gmail.com"}, "Subject"));

        binding.Zalo.setOnClickListener(view -> composeZalo("0398922772", " Hello"));
        return binding.getRoot();
    }

    private void composeZalo(String number, String s1) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://zalo.me/" + number));
        intent.putExtra(Intent.EXTRA_TEXT, s1);
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void compose(String[] address, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void dialPhoneNumber(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: " + s));
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}