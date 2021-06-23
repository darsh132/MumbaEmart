package com.example.mumbae_mart.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mumbae_mart.ProductDescriptionFragment;
import com.example.mumbae_mart.ProductSpecificationFragment;
import com.example.mumbae_mart.model.ProductSpecificationModel;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private final int totalTabs;
    private final String productDescription;
    private final String productOtherDetails;
    private final List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(FragmentManager fm, int totalTabs, String productDescription, String productOtherDetails, List<ProductSpecificationModel> productSpecificationModelList){
        super(fm);
        this. totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i)  {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1  = new ProductDescriptionFragment ();
                productDescriptionFragment1.body = productDescription;
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment  = new ProductSpecificationFragment  ();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment ;
            case 2:
                ProductDescriptionFragment  productDescriptionFragment2 = new ProductDescriptionFragment ();
                productDescriptionFragment2.body = productOtherDetails;
                return productDescriptionFragment2;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return  totalTabs ;
    }
}