package huyndph30375.fpoly.foodorder3.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.adapter.HistoryAdapter;
import huyndph30375.fpoly.foodorder3.constant.SpaceItemDecorationCustomLiner;
import huyndph30375.fpoly.foodorder3.databinding.FragmentHistoryBinding;
import huyndph30375.fpoly.foodorder3.model.OrderInformation;
import huyndph30375.fpoly.foodorder3.room.FoodDatabase;


public class HistoryFragment extends Fragment {
        private FragmentHistoryBinding binding;
        private List<OrderInformation> list;
        private OrderInformation object;
        private HistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        list = FoodDatabase.getInstance(getContext()).daoOrder().getOrderList();
        adapter = new HistoryAdapter(list);

        initrecyclerviewHistory();
        list = FoodDatabase.getInstance(getContext()).daoOrder().getOrderList();
        return binding.getRoot();
    }

    private void initrecyclerviewHistory() {
        binding.recyclerviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewHistory.setAdapter(adapter);
        list = FoodDatabase.getInstance(getContext()).daoOrder().getOrderList();
        int space = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);
        SpaceItemDecorationCustomLiner itemDecoration = new SpaceItemDecorationCustomLiner(space);
        binding.recyclerviewHistory.addItemDecoration(itemDecoration);
    }
}