package huyndph30375.fpoly.foodorder3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.databinding.ItemFoodBinding;
import huyndph30375.fpoly.foodorder3.model.Food;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodViewHolder> {
    private final IFood iFood;

    public FoodPopularAdapter(IFood iFood) {
        this.iFood = iFood;
    }

    @NonNull
    @Override
    public FoodPopularAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Food> list){
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodPopularAdapter.FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food food = iFood.getDataFood(position);
        holder.binding.title.setText(food.getTitle());
        holder.binding.fee.setText(food.getPrice() + Constant.CURRENCY);
        holder.binding.pic.setImageResource(food.getPicsFood());
        holder.itemView.setOnClickListener(v -> iFood.clickItem(position, v));
    }

    @Override
    public int getItemCount() {
        return iFood.FoodSize();
    }
    public interface IFood {
        int FoodSize();
        void clickItem(int position, View view);
        void clickDelete(int position);
        Food getDataFood(int position);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ItemFoodBinding binding;
        public FoodViewHolder(@NonNull ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
