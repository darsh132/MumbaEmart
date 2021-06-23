package com.example.mumbae_mart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mumbae_mart.adapter.CategoryAdapter;
import com.example.mumbae_mart.adapter.MyGroceryPageAdapter;
import com.example.mumbae_mart.model.CategoryModel;
import com.example.mumbae_mart.model.HorizontalProductScrollModel;
import com.example.mumbae_mart.model.MyGroceryPageModel;
import com.example.mumbae_mart.model.SliderModel;
import com.example.mumbae_mart.model.WishlistModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.mumbae_mart.DBqueries.categoryModelList;
import static com.example.mumbae_mart.DBqueries.lists;
import static com.example.mumbae_mart.DBqueries.loadCategories;
import static com.example.mumbae_mart.DBqueries.loadedCategoriesNames;
import static com.example.mumbae_mart.DBqueries.loadFragmentData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyGroceryFragment#} factory method to
 * create an instance of this fragment.
 */
public class MyGroceryFragment extends Fragment {

    public MyGroceryFragment() {

    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @SuppressLint("StaticFieldLeak")
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private final List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private RecyclerView myGroceryPageRecyclerView;
    private final List<MyGroceryPageModel> myGroceryPageModelFakeList = new ArrayList<>();
    private MyGroceryPageAdapter adapter;
    private ImageView noInternetConnection;
    private Button retryBtn;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_grocery,container,false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        myGroceryPageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        retryBtn = view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myGroceryPageRecyclerView.setLayoutManager(testingLayoutManager);

        categoryModelFakeList.add(new CategoryModel("null", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));


        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));

        myGroceryPageModelFakeList.add(new MyGroceryPageModel(0, sliderModelFakeList));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(1, "","#dfdfdf"));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(2, "","#dfdfdf",horizontalProductScrollModelFakeList,new ArrayList<WishlistModel>()));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(3, "","#dfdfdf",horizontalProductScrollModelFakeList));

        categoryAdapter = new CategoryAdapter(categoryModelFakeList);

        adapter = new MyGroceryPageAdapter(myGroceryPageModelFakeList);

        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            myGroceryPageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView,getContext());
            }else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (lists.size() == 0) {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<MyGroceryPageModel>());

                loadFragmentData(myGroceryPageRecyclerView,getContext(),0,"Home");
            }else {
                adapter = new MyGroceryPageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            myGroceryPageRecyclerView.setAdapter(adapter);

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            myGroceryPageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });
        return view;
    }

    @SuppressLint("WrongConstant")
    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            myGroceryPageRecyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new MyGroceryPageAdapter(myGroceryPageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            myGroceryPageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView,getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<MyGroceryPageModel>());
            loadFragmentData(myGroceryPageRecyclerView,getContext(),0,"Home");

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            Toast.makeText(getContext(), "No internet Connection!", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            myGroceryPageRecyclerView.setVisibility(View.GONE);
            Glide.with(Objects.requireNonNull(getContext())).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    }
