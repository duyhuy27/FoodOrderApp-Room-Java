package huyndph30375.fpoly.foodorder3.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.databinding.FragmentFeedbackBinding;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);

        binding.submit.setOnClickListener(view -> showDialogConfirm(R.layout.dialog_order_ok));

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void showDialogConfirm(int layout) {
        dialogBuilder = new AlertDialog.Builder(requireContext());
        View layoutView = getLayoutInflater().inflate(layout, null);
        ImageView image = layoutView.findViewById(R.id.image);
        Button dialogButton = layoutView.findViewById(R.id.button_ok);
        TextView title = layoutView.findViewById(R.id.title);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        image.setImageResource(R.drawable.ic_done);
        title.setText("Your feedback has been sent. Wish you have a good experience with the restaurant");
        dialogButton.setOnClickListener(view -> {
            binding.inputEmail.setText("");
            binding.inputName.setText("");
            binding.inputPhone.setText("");
            binding.inputFeed.setText("");
            alertDialog.dismiss();
        });
    }
}