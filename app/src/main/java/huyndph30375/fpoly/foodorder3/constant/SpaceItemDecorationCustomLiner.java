package huyndph30375.fpoly.foodorder3.constant;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecorationCustomLiner extends RecyclerView.ItemDecoration{
    private int space;

    public  SpaceItemDecorationCustomLiner(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = space;

        // Add top margin to the first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
    }
}
