package huyndph30375.fpoly.foodorder3.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.databinding.ActivityFoodDetailBinding;
import huyndph30375.fpoly.foodorder3.model.Food;
import huyndph30375.fpoly.foodorder3.room.FoodDatabase;

public class FoodDetailActivity extends AppCompatActivity {
    private Food mFood;
    private ActivityFoodDetailBinding binding;
    private static int numberOrder = 1;
    private static int totalPrice;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDataIntent();
        listener();
        countNotification();
    }

    @SuppressLint("SetTextI18n")
    private void listener() {
        binding.add.setOnClickListener(v -> {
            increaseQuantity(1);
        });

        binding.giam.setOnClickListener(v -> {
            decreaseQuantity(1);
        });
        // Set an onClickListener for the "Add to Cart" button
        binding.buttonAddCart.setOnClickListener(v -> {
            List<Food> foodList = FoodDatabase.getInstance(getApplicationContext()).daoFood().getListFood();
            boolean inCart = false;
            int n = -1;
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getTitle().equals(mFood.getTitle())) {
                    inCart = true;
                    n = i;
                    break;
                }
            }
            if (inCart) {
                int newCount = foodList.get(n).getNumberInCart() + numberOrder;
                totalPrice = mFood.getPrice() * newCount;
                foodList.get(n).setNumberInCart(newCount);
                foodList.get(n).setTotalPrice(totalPrice);
                FoodDatabase.getInstance(getApplicationContext()).daoFood().updateNote(foodList.get(n));
                countNotification();
            } else {
                totalPrice = mFood.getPrice() * numberOrder;
                mFood.setNumberInCart(numberOrder);
                mFood.setTotalPrice(totalPrice);
                FoodDatabase.getInstance(getApplicationContext()).daoFood().insertNote(mFood);
                countNotification();
            }
            showDialogConfirm(R.layout.dialog_order_ok);
        });
        binding.back.setOnClickListener(v -> onBackPressed());

    }

    @SuppressLint("SetTextI18n")
    private void showDialogConfirm(int layout) {
        dialogBuilder = new AlertDialog.Builder(FoodDetailActivity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.button_ok);
        TextView title = layoutView.findViewById(R.id.title);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        title.setText("The ' " + mFood.getTitle() + " ' has been successfully added to the cart. Please check your cart to pay!");
        dialogButton.setOnClickListener(view -> alertDialog.dismiss());
    }

    @SuppressLint("SetTextI18n")
    private void decreaseQuantity(int i) {
        if (numberOrder > 1) {
            numberOrder = numberOrder - 1;
        }
        totalPrice = mFood.getPrice() * numberOrder;
        mFood.setNumberInCart(numberOrder);
        binding.quantityTextview.setText(String.valueOf(numberOrder));
        mFood.setTotalPrice(totalPrice);
        binding.totalPrice.setText(totalPrice + Constant.CURRENCY);
    }

    @SuppressLint("SetTextI18n")
    private void increaseQuantity(int i) {
        numberOrder = numberOrder + 1;
        totalPrice = numberOrder * mFood.getPrice();
        mFood.setNumberInCart(numberOrder);
        binding.quantityTextview.setText(String.valueOf(numberOrder));
        mFood.setTotalPrice(totalPrice);
        binding.totalPrice.setText(totalPrice + Constant.CURRENCY);
    }

    @SuppressLint("SetTextI18n")
    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mFood = (Food) bundle.get("food");
            binding.pic.setImageResource(mFood.getPicsFood());
            binding.title.setText(mFood.getTitle());
            binding.description.setText(mFood.getDescription());
            binding.price.setText(mFood.getPrice() + Constant.CURRENCY);
            binding.totalPrice.setText(mFood.getPrice() + Constant.CURRENCY);
            binding.quantityTextview.setText(String.valueOf(numberOrder));
            mFood.setNumberInCart(numberOrder);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void countNotification() {
        List<Food> foodList = FoodDatabase.getInstance(getApplicationContext()).daoFood().getListFood();
        int countItem = 0;
        for (Food food : foodList) {
            countItem += food.getNumberInCart();
        }
        binding.countItem.setText(String.valueOf(countItem));
    }

//    private void countNOtification() {
//        List<Food> foodList = FoodDatabase.getInstance(getApplicationContext()).daoFood().getListFood();
//        int countItem = 0;
//        for (Food f : foodList) {
//            if (f.isSelected()) {
//                countItem += f.getId();
//            }
//        }
//        binding.countItem.setText(String.valueOf(countItem));
//    }
}