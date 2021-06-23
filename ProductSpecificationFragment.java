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

import com.example.mumbae_mart.adapter.ProductSpecificationAdapter;
import com.example.mumbae_mart.model.ProductSpecificationModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSpecificationFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProductSpecificationFragment extends Fragment {

    public ProductSpecificationFragment() {

    }

    public List<ProductSpecificationModel> productSpecificationModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        RecyclerView productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);

        //productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
        //productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}