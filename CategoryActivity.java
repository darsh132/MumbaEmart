package com.example.mumbae_mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.mumbae_mart.adapter.MyGroceryPageAdapter;
import com.example.mumbae_mart.model.HorizontalProductScrollModel;
import com.example.mumbae_mart.model.MyGroceryPageModel;
import com.example.mumbae_mart.model.SliderModel;
import com.example.mumbae_mart.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.mumbae_mart.DBqueries.lists;
import static com.example.mumbae_mart.DBqueries.loadFragmentData;
import static com.example.mumbae_mart.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private final List<MyGroceryPageModel> myGroceryPageModelFakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "",""));

        myGroceryPageModelFakeList.add(new MyGroceryPageModel(0, sliderModelFakeList));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(1, "","#ffffff"));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(2, "","#ffffff",horizontalProductScrollModelFakeList,new ArrayList<WishlistModel>()));
        myGroceryPageModelFakeList.add(new MyGroceryPageModel(3, "","#ffffff",horizontalProductScrollModelFakeList));

        RecyclerView categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        MyGroceryPageAdapter adapter = new MyGroceryPageAdapter(myGroceryPageModelFakeList);

        int listPosition = 0;
        for (int x = 0;x < loadedCategoriesNames.size();x++) {
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())) {
                listPosition = x;
            }
        }
        if (listPosition == 0) {
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<MyGroceryPageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNames.size() - 1,title);
        }else {
            adapter = new MyGroceryPageAdapter(lists.get(listPosition));
        }
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            return true;
        }else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}