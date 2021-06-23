package com.example.mumbae_mart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mumbae_mart.adapter.CategoryAdapter;
import com.example.mumbae_mart.adapter.MyGroceryPageAdapter;
import com.example.mumbae_mart.model.CategoryModel;
import com.example.mumbae_mart.model.HorizontalProductScrollModel;
import com.example.mumbae_mart.model.MyGroceryPageModel;
import com.example.mumbae_mart.model.SliderModel;
import com.example.mumbae_mart.model.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBqueries {

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static List<List<MyGroceryPageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();
    public static List<String> wishList = new ArrayList<>();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                                categoryModelList.add(new CategoryModel(Objects.requireNonNull(documentSnapshot.get("icon")).toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void loadFragmentData(final RecyclerView myGroceryPageRecyclerView, final Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                                if ((long)documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long)documentSnapshot.get("no_of_banners");
                                    for (long x = 1;x < no_of_banners + 1;x++) {
                                        sliderModelList.add(new SliderModel(Objects.requireNonNull(documentSnapshot.get("banner_" + x)).toString()
                                                , Objects.requireNonNull(documentSnapshot.get("banner_" + x + "_background")).toString()));
                                    }
                                    lists.get(index).add(new MyGroceryPageModel(0,sliderModelList));
                                }else if ((long)documentSnapshot.get("view_type") == 1) {
                                    lists.get(index).add(new MyGroceryPageModel(1,documentSnapshot.get("strip_ad_banner").toString()
                                            ,documentSnapshot.get("background").toString()));
                                }else if ((long)documentSnapshot.get("view_type") == 2) {

                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for (long x = 0;x < no_of_products + 1;x++) {
                                        try {
                                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(Objects.requireNonNull(documentSnapshot.get("product_ID_" + x)).toString()
                                                    , Objects.requireNonNull(documentSnapshot.get("product_image_" + x)).toString()
                                                    , Objects.requireNonNull(documentSnapshot.get("product_title_" + x)).toString()
                                                    , Objects.requireNonNull(documentSnapshot.get("product_subtitle_" + x)).toString()
                                                    , Objects.requireNonNull(documentSnapshot.get("product_price_" + x)).toString()));
                                        } catch (NullPointerException ignored) {
                                            try {
                                                viewAllProductList.add(new WishlistModel(Objects.requireNonNull(documentSnapshot.get("product_image_" + x)).toString()
                                                        , Objects.requireNonNull(documentSnapshot.get("product_full_title_" + x)).toString()
                                                        , (long) documentSnapshot.get("free_coupens_" + x)
                                                        , Objects.requireNonNull(documentSnapshot.get("average_rating_" + x)).toString()

                                                        , (long) documentSnapshot.get("total_ratings_" + x)
                                                        , Objects.requireNonNull(documentSnapshot.get("product_price_" + x)).toString()
                                                        , Objects.requireNonNull(documentSnapshot.get("cutted_price_" + x)).toString()
                                                        , (boolean) documentSnapshot.get("COD_" + x)));
                                            } catch (NullPointerException ignore) {
                                                lists.get(index).add(new MyGroceryPageModel(2, Objects.requireNonNull(documentSnapshot.get("layout_title")).toString(), Objects.requireNonNull(documentSnapshot.get("layout_background")).toString(), horizontalProductScrollModelList, viewAllProductList));
                                            }
                                        }
                                    }
                                }else if ((long)documentSnapshot.get("view_type") == 3) {
                                    List<HorizontalProductScrollModel> GridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for (long x = 1;x < no_of_products + 1;x++) {
                                        GridLayoutModelList.add(new HorizontalProductScrollModel(Objects.requireNonNull(documentSnapshot.get("product_ID_"+x)).toString()
                                                , Objects.requireNonNull(documentSnapshot.get("product_image_" + x)).toString()
                                                , Objects.requireNonNull(documentSnapshot.get("product_title_"+x)).toString()
                                                , Objects.requireNonNull(documentSnapshot.get("product_subtitle_"+x)).toString()
                                                , Objects.requireNonNull(documentSnapshot.get("product_price_"+x)).toString()));
                                    }
                                    lists.get(index).add(new MyGroceryPageModel(3, Objects.requireNonNull(documentSnapshot.get("layout_title")).toString(), Objects.requireNonNull(documentSnapshot.get("layout_background")).toString(),GridLayoutModelList));
                                }
                            }
                            MyGroceryPageAdapter myGroceryPageAdapter = new MyGroceryPageAdapter(lists.get(index));
                            myGroceryPageRecyclerView.setAdapter(myGroceryPageAdapter);
                            myGroceryPageAdapter.notifyDataSetChanged();
                            MyGroceryFragment.swipeRefreshLayout.setRefreshing(false);
                        }else {
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(context, error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public static void loadWishList(final Context context) {

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0;x < (long)task.getResult().get("list_size");x++) {
                        wishList.add(task.getResult().get("product_ID_"+x).toString());
                    }
                }else {
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}