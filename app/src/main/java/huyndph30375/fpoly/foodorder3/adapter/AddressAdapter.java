package huyndph30375.fpoly.foodorder3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.databinding.ItemAddressBinding;
import huyndph30375.fpoly.foodorder3.model.Address;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private final IAddress iAddress;
    int row_id = -1;

    public AddressAdapter(IAddress iAddress) {
        this.iAddress = iAddress;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Address> list) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddressBinding binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Address address = iAddress.getDataAddress(position);
        holder.binding.name.setText(address.getName());
        holder.binding.detailsAddress.setText(address.getDetailsAddress());
        holder.binding.details.setOnClickListener(view -> iAddress.clickToEdit(position));

        if (row_id == position) {
            holder.binding.backgroundItemAddress.setBackgroundResource(R.drawable.select_item_menu);

        } else {
            holder.binding.backgroundItemAddress.setBackgroundResource(R.drawable.unselect_item_menu);
        }

        holder.itemView.setOnClickListener(v -> {
            iAddress.clickChooseAddress(v, position);

            // Update row_id to the clicked position and notify the adapter
            int previousRowId = row_id;
            row_id = position;
            notifyItemChanged(previousRowId);
            notifyItemChanged(row_id);
        });

//        // Set the background of the first item as selected by default
//        if (position == 0 && row_id == -1) {
//            holder.binding.backgroundItemAddress.setBackgroundResource(R.drawable.select_item_menu);
//            row_id = 0;
//            holder.itemView.setOnClickListener(v -> iAddress.clickChooseAddress(v, position));
//        }
    }

    @Override
    public int getItemCount() {
        return iAddress.getItemSize();
    }

    public interface IAddress {
        int getItemSize();

        Address getDataAddress(int position);

        void clickToEdit(int position);

        void clickChooseAddress(View v, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemAddressBinding binding;

        public ViewHolder(@NonNull ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
