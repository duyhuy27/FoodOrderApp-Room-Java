package huyndph30375.fpoly.foodorder3.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.activity.FinishOrderActivity;
import huyndph30375.fpoly.foodorder3.adapter.FoodPopularAdapter;
import huyndph30375.fpoly.foodorder3.adapter.PhotoViewpager2Adapter;
import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.constant.GlobalFunction;
import huyndph30375.fpoly.foodorder3.constant.SpaceItemDecoration;
import huyndph30375.fpoly.foodorder3.databinding.FragmentHomeBinding;
import huyndph30375.fpoly.foodorder3.model.Food;
import huyndph30375.fpoly.foodorder3.model.FoodImage;
import huyndph30375.fpoly.foodorder3.room.FoodDatabase;

public class HomeFragment extends Fragment implements FoodPopularAdapter.IFood {

    private FragmentHomeBinding binding;
    private List<Food> foodList;
    private FoodPopularAdapter adapter;
    private Food food;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        List<FoodImage> foodImages = getListBanner();
        foodList = new ArrayList<>();
        adapter = new FoodPopularAdapter(this);
        food = new Food();
        initRecyclerview();
        countNotification();

        PhotoViewbpager2Adapter adapter = new PhotoViewpager2Adapter(foodImages);
        binding.viewpager2Banner.setAdapter(adapter);
        binding.Indicator3.setViewPager(binding.viewpager2Banner);

        binding.cart.setOnClickListener(view -> startActivity(new Intent(getContext(), FinishOrderActivity.class)));
        binding.searchBar.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH){
                searchFoodPopular();
            }
            return false;
        });
        return binding.getRoot();
    }

    private void searchFoodPopular() {
        String keyword = binding.searchBar.getText().toString();

        if (keyword.isEmpty()){
            initRecyclerview();
        }
        foodList = new ArrayList<>();
        foodList = FoodDatabase.getInstance(getContext()).daoFood().search(keyword);
        adapter.setData(foodList);
        GlobalFunction.hideSoftKeyboard(getActivity());
    }

    private void initRecyclerview() {
        int space = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(space);
        binding.recyclerviewFood.addItemDecoration(itemDecoration);

        foodList = getFoodList();
        adapter = new FoodPopularAdapter(this);
        binding.recyclerviewFood.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerviewFood.setAdapter(adapter);

    }

    private List<FoodImage> getListBanner() {
        List<FoodImage> foodImages = new ArrayList<>();
        foodImages.add(new FoodImage(R.drawable.shrimp));
        foodImages.add(new FoodImage(R.drawable.ss_01));
        foodImages.add(new FoodImage(R.drawable.ss_basic));
        foodImages.add(new FoodImage(R.drawable.tomato_soup));
        return foodImages;
    }

    private List<Food> getFoodList() {
        foodList = new ArrayList<>();
        foodList.add(new Food(
                "Temaki",
                "Small rice balls with fish, shellfish, etc. on top. There are countless varieties of nigirizushi, some of the most common ones being tuna, shrimp, eel, squid, octopus and fried egg.",
                80,
                R.drawable.ss_basic
        ));

        foodList.add(new Food(
                "Nigiri",
                "Small cups made of sushi rice and dried seaweed filled with seafood, etc. There are countless varieties of gunkanzushi, some of the most common ones being sea urchin and various kinds of fish eggs.",
                149,
                R.drawable.ss_basic_01
        ));

        foodList.add(new Food(
                "Inari",
                "Inarizushi is a simple and inexpensive type of sushi, in which sushi rice is filled into small bags of deep fried tofu (aburaage).",
                175,
                R.drawable.ss_01
        ));

        foodList.add(new Food(
                "Classic Shrimp Scampi",
                "Small rice balls with fish, shellfish, etc. on top. There are countless varieties of nigirizushi, some of the most common ones being tuna, shrimp, eel, squid, octopus and fried egg.",
                235,
                R.drawable.shrimp
        ));

        foodList.add(new Food(
                "Tomato Soup",
                "Progresso Vegetable Classics Tomato Basil Soup is a classic tomato soup packed with heartwarming goodness for days when you crave a trusted and easy meal option",
                205,
                R.drawable.tomato_soup
        ));

        return foodList;
    }

    @Override
    public int FoodSize() {
        return foodList.size();
    }

    @Override
    public void clickItem(int position, View view) {
        food = foodList.get(position);
        food.goToFoodDetail(view);
    }

    @Override
    public void clickDelete(int position) {
    }

    @Override
    public Food getDataFood(int position) {
        return foodList.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void countNotification() {
        List<Food> foodList = FoodDatabase.getInstance(getContext()).daoFood().getListFood();
        int countItem = 0;
        for (Food food : foodList) {
            countItem += food.getNumberInCart();
        }
        binding.countItem.setText(String.valueOf(countItem));
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
