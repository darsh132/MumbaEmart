package com.example.mumbae_mart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mumbae_mart.adapter.CartAdapter;
import com.example.mumbae_mart.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCartFragment#} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {

    public MyCartFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        RecyclerView cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        Button continueBtn = view.findViewById(R.id.cart_continue_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.ketchup,"Ketchup",2,"Rs.220/-","Rs.299/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ketchup,"Ketchup",0,"Rs.220/-","Rs.299/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ketchup,"Ketchup",2,"Rs.220/-","Rs.299/-",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.999/-","Free","Rs.250/-","Rs.999/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getContext(),AddAddressActivity.class);
                Objects.requireNonNull(getContext()).startActivity(deliveryIntent);
            }
        });
        return view;
    }
}