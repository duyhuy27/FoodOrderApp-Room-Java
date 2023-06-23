package huyndph30375.fpoly.foodorder3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.databinding.ItemSummaryBinding;
import huyndph30375.fpoly.foodorder3.model.FoodSummary;

public class FoodSummaryAdapter extends RecyclerView.Adapter<FoodSummaryAdapter.ViewHolder> {
    private List<FoodSummary> list;
    private ISummary iSummary;

    public FoodSummaryAdapter(ISummary iSummary) {
        this.iSummary = iSummary;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<FoodSummary> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSummaryBinding binding = ItemSummaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodSummaryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodSummary foodSummary = iSummary.getDataSummary(position);
        holder.binding.quantityName.setText(foodSummary.getGetNameFood());
        holder.binding.orderNumber.setText(String.valueOf(foodSummary.getIdSummary()));
        holder.binding.sl.setText(String.valueOf(foodSummary.getCountFood()));
        holder.binding.price.setText(foodSummary.getPrice() + Constant.CURRENCY);
        holder.binding.delete.setOnClickListener(view -> iSummary.ClickDelete(position));
    }

    public interface ISummary {
        int countSummary();

        FoodSummary getDataSummary(int position);

        void ClickDelete(int position);
    }

    @Override
    public int getItemCount() {
        return iSummary.countSummary();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSummaryBinding binding;

        public ViewHolder(@NonNull ItemSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
