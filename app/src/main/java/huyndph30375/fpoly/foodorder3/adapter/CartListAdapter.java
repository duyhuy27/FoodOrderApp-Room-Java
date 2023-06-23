package huyndph30375.fpoly.foodorder3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huyndph30375.fpoly.foodorder3.activity.FoodDetailActivity;
import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.databinding.ItemFoodCartBinding;
import huyndph30375.fpoly.foodorder3.model.Food;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    private final ICart iCart;
    private List<Food> list;

    public CartListAdapter(ICart iCart) {
        this.iCart = iCart;
    }

    @NonNull
    @Override
    public CartListAdapter.CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodCartBinding binding = ItemFoodCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartListViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Food> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.CartListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food food = iCart.getDataFood(position);
        holder.binding.picFood.setImageResource(food.getPicsFood());
        holder.binding.title.setText(food.getTitle());
        holder.binding.feetotalItem.setText(food.getTotalPrice() + Constant.CURRENCY);
        holder.binding.quantityTextview.setText(String.valueOf(food.getNumberInCart()));
        holder.binding.tangButton.setOnClickListener(v -> iCart.ClickTang(position));
        holder.binding.giamButton.setOnClickListener(v -> iCart.ClickGiam(position));
        holder.binding.checkbox.setChecked(food.isSelected()); // Set the checkbox state
        holder.binding.checkbox.setOnClickListener(v -> {
            boolean isSelected = ((CheckBox) v).isChecked();
            iCart.clickCheckbox(position, isSelected);
        });
    }

    @Override
    public int getItemCount() {
        return iCart.cartSize();
    }

    public interface ICart {
        int cartSize();

        Food getDataFood(int position);

        void ClickTang(int position);

        void ClickGiam(int position);

        void clickDeleteFood(int position);

        void clickCheckbox(int position, boolean isSelected);
    }

    public static class CartListViewHolder extends RecyclerView.ViewHolder {
        ItemFoodCartBinding binding;

        public CartListViewHolder(@NonNull ItemFoodCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
