package huyndph30375.fpoly.foodorder3.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import huyndph30375.fpoly.foodorder3.databinding.FoodImageBinding;
import huyndph30375.fpoly.foodorder3.databinding.ItemFoodBinding;
import huyndph30375.fpoly.foodorder3.model.FoodImage;

public class PhotoViewpager2Adapter extends RecyclerView.Adapter<PhotoViewpager2Adapter.PhotoViewHolder> {
    private List<FoodImage> foodImages;

    public PhotoViewpager2Adapter(List<FoodImage> foodImages) {
        this.foodImages = foodImages;
    }

    @NonNull
    @Override
    public PhotoViewpager2Adapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodImageBinding binding = FoodImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewpager2Adapter.PhotoViewHolder holder, int position) {
        FoodImage foodImage = foodImages.get(position);
        if (foodImage == null){
            return;
        }
        holder.binding.foodPic.setImageResource(foodImage.getResourceId());
    }

    @Override
    public int getItemCount() {
        return foodImages.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        FoodImageBinding binding;
        public PhotoViewHolder(@NonNull FoodImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
