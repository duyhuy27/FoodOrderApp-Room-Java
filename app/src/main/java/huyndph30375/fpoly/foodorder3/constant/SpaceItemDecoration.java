package huyndph30375.fpoly.foodorder3.constant;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;

        // Add left margin to the first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = space;
        }
    }
}
