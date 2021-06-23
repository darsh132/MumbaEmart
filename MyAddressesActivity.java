package com.example.mumbae_mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.mumbae_mart.adapter.AddressesAdapter;
import com.example.mumbae_mart.model.AddressesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.mumbae_mart.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview);
        Button deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",true));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));
        addressesModelList.add(new AddressesModel("Darshan Bane","Saibai Nagar,Malad(east),Mumbai","400097",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS) {
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(myAddressesRecyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect,int select) {
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}