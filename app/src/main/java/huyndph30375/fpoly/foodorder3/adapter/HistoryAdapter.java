package huyndph30375.fpoly.foodorder3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.databinding.ItemHistoryBinding;
import huyndph30375.fpoly.foodorder3.model.OrderInformation;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<OrderInformation> list;

    public HistoryAdapter(List<OrderInformation> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        String randomCode = generateRandomCode();
        holder.binding.code.setText("Code Orders :  " +randomCode);
        holder.binding.addAddress.setText("Delivered to : " + list.get(position).getAddress());
        holder.binding.food.setText("Dish : " + list.get(position).getNameSummary());
        holder.binding.paymentMethod.setText("Payment Method : " + list.get(position).getPayment());
        holder.binding.total.setText("Total amount : " + list.get(position).getTotalPrice() + Constant.CURRENCY);
    }

    private String generateRandomCode() {
        // Define the possible characters that can be used in the code
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Generate a random code of length 6
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(possibleChars.length());
            codeBuilder.append(possibleChars.charAt(index));
        }

        return codeBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHistoryBinding binding;
        public ViewHolder(@NonNull ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
