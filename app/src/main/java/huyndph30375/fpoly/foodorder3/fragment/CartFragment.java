package huyndph30375.fpoly.foodorder3.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import huyndph30375.fpoly.foodorder3.adapter.CartListAdapter;
import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.constant.SpaceItemDecorationCustomLiner;
import huyndph30375.fpoly.foodorder3.databinding.FragmentCartBinding;
import huyndph30375.fpoly.foodorder3.model.Food;
import huyndph30375.fpoly.foodorder3.model.FoodSummary;
import huyndph30375.fpoly.foodorder3.model.OrderInformation;
import huyndph30375.fpoly.foodorder3.room.FoodDatabase;


public class CartFragment extends Fragment implements CartListAdapter.ICart {

    private FragmentCartBinding binding;
    private CartListAdapter adapter;
    private List<Food> foodList;
    private Food object;
    private int numberOfFood;

    private OrderInformation orderInformation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);

        adapter = new CartListAdapter(this);
        foodList = FoodDatabase.getInstance(getContext()).daoFood().getListFood();

        orderInformation = new OrderInformation();

        initRecyclerview();
        loadDataFoodInCart();
        listener();


        return binding.getRoot();

    }

    private void listener() {
        binding.oderButton.setOnClickListener(view -> {
            // Create an ArrayList of selected food summaries
            ArrayList<FoodSummary> selectedSummaries = new ArrayList<>();
            for (Food f : foodList) {
                if (f.isSelected()) {
                    String nameSummary = f.getTitle();
                    int count = f.getNumberInCart();
                    int totalPrice = f.getTotalPrice();

                    FoodSummary existingSummary = FoodDatabase.getInstance(getContext()).daoSummary().getSummaryByName(nameSummary);

                    if (existingSummary != null) {
                        int newCount = existingSummary.getCountFood() + count;
                        int newTotalPrice = existingSummary.getPrice() + totalPrice;
                        existingSummary.setCountFood(newCount);
                        existingSummary.setPrice(newTotalPrice);
                        orderInformation.setQuantity(newCount);
                        FoodDatabase.getInstance(getContext()).daoSummary().updateSummary(existingSummary);
                        selectedSummaries.add(existingSummary);
                        existingSummary.goToFinishOrder(view);
                    } else {
                        FoodSummary foodSummary = new FoodSummary(nameSummary, count, totalPrice);
                        FoodDatabase.getInstance(getContext()).daoSummary().insertSummary(foodSummary);
                        selectedSummaries.add(foodSummary);
                        Log.d("Food intent", "listener: " + foodSummary.getGetNameFood());
                        foodSummary.goToFinishOrder(view);
                    }
                }
            }
        });
    binding.textDelete.setOnClickListener(view -> deleteSelectedFoods());

    }


    private void initRecyclerview() {
        binding.recyclerviewListCard.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewListCard.setAdapter(adapter);
        loadDataFoodInCart();

        int space = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);
        SpaceItemDecorationCustomLiner itemDecoration = new SpaceItemDecorationCustomLiner(space);
        binding.recyclerviewListCard.addItemDecoration(itemDecoration);
    }

    @Override
    public int cartSize() {
        return foodList.size();
    }

    @Override
    public Food getDataFood(int position) {
        return foodList.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ClickTang(int position) {
        loadDataFoodInCart();
        object = foodList.get(position);
        numberOfFood = object.getNumberInCart();
        numberOfFood = object.getNumberInCart() + 1;
        int priceTotal = object.getPrice() * numberOfFood;
        object.setTotalPrice(priceTotal);
        object.setNumberInCart(numberOfFood);
        FoodDatabase.getInstance(getContext()).daoFood().updateNote(object);
        adapter.notifyItemChanged(position);
        updateTotalPrice();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void ClickGiam(int position) {
        loadDataFoodInCart();
        object = foodList.get(position);
        numberOfFood = object.getNumberInCart();
        if (numberOfFood > 1) {
            numberOfFood = object.getNumberInCart() - 1;

        } else {
            clickDeleteFood(position);
        }

        int priceTotal = object.getPrice() * numberOfFood;
        object.setTotalPrice(priceTotal);
        object.setNumberInCart(numberOfFood);
        FoodDatabase.getInstance(getContext()).daoFood().updateNote(object);
        adapter.notifyItemChanged(position);
        updateTotalPrice();
    }

    @Override
    public void clickDeleteFood(int position) {
        object = foodList.get(position);
        FoodDatabase.getInstance(getContext()).daoFood().deleteNote(object);
        foodList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void deleteSelectedFoods() {
        List<Food> selectedFoods = new ArrayList<>();
        for (Food f : foodList) {
            if (f.isSelected()) {
                selectedFoods.add(f);
            }
        }
        if (!selectedFoods.isEmpty()) {
            for (Food f : selectedFoods) {
                FoodDatabase.getInstance(getContext()).daoFood().deleteNote(f);
                int position = foodList.indexOf(f);
                foodList.remove(f);
                adapter.notifyItemRemoved(position);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void clickCheckbox(int position, boolean isSelected) {
        Food food = foodList.get(position);
        food.setSelected(isSelected);
        updateTotalPrice();
    }


    private void loadDataFoodInCart() {
        foodList = FoodDatabase.getInstance(getContext()).daoFood().getListFood();
        adapter.setData(foodList);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        foodList = FoodDatabase.getInstance(getContext()).daoFood().getListFood();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void updateTotalPrice() {
        int totalPrice = 0;
        for (Food f : foodList) {
            if (f.isSelected()) {
                totalPrice += f.getTotalPrice();
            }
        }
        binding.feeBills.setText(totalPrice + Constant.CURRENCY);
    }

}