package com.example.mumbae_mart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mumbae_mart.adapter.MyOrderAdapter;
import com.example.mumbae_mart.model.MyOrderItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrdersFragment#} factory method to
 * create an instance of this fragment.
 */
public class MyOrdersFragment extends Fragment {

    public MyOrdersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders,container,false);

        RecyclerView myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product_image,"2","Ketchup","Delivered on Mon,15th JAN 2021"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.maggy,"1","Ketchup","Delivered on Mon,15th Jan 2021"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ketchup,"0","Ketchup","Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.maggy,"4","Ketchup","Delivered on Mon,15th Jan 2021"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        Objects.requireNonNull(getActivity()).setTitle("My Orders");
    }
}