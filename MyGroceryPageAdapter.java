package com.example.mumbae_mart.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mumbae_mart.ProductDetailsActivity;
import com.example.mumbae_mart.R;
import com.example.mumbae_mart.ViewAllActivity;
import com.example.mumbae_mart.model.HorizontalProductScrollModel;
import com.example.mumbae_mart.model.MyGroceryPageModel;
import com.example.mumbae_mart.model.SliderModel;
import com.example.mumbae_mart.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyGroceryPageAdapter extends RecyclerView.Adapter {

    private final List<MyGroceryPageModel> myGroceryPageModelList;
    private final RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition = -1;

    public MyGroceryPageAdapter(List<MyGroceryPageModel> myGroceryPageModelList) {
        this.myGroceryPageModelList = myGroceryPageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (myGroceryPageModelList.get(position).getType()) {
            case 0:
                return MyGroceryPageModel.BANNER_SLIDER;

            case 1:
                return MyGroceryPageModel.STRIP_AD_BANNER;

            case 2:
                return MyGroceryPageModel.HORIZONTAL_PRODUCT_VIEW;

            case 3:
                return MyGroceryPageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case MyGroceryPageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup, false);
                return new BannerSliderViewholder(bannerSliderView);
            case MyGroceryPageModel.STRIP_AD_BANNER:
                View stripAdView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout, viewGroup, false);
                return new StripAdBannerViewholder(stripAdView);
            case MyGroceryPageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup, false);
                return new HorizontalProductViewholder(horizontalProductView);
            case MyGroceryPageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewholder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (myGroceryPageModelList.get(position).getType()) {
            case MyGroceryPageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = myGroceryPageModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder)viewHolder).setBannerSliderViewPager(sliderModelList);
                break;

            case MyGroceryPageModel.STRIP_AD_BANNER:
                String resource = myGroceryPageModelList.get(position).getResource();
                String color = myGroceryPageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewholder)viewHolder).setStripAd(resource, color);
                break;

            case MyGroceryPageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutColor = myGroceryPageModelList.get(position).getBackgroundColor();
                String horizontalLayoutTitle = myGroceryPageModelList.get(position).getTitle();
                List<WishlistModel> viewAllProductList = myGroceryPageModelList.get(position).getViewAllProductList();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = myGroceryPageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewholder)viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList, horizontalLayoutTitle, layoutColor,viewAllProductList);
                break;

                case MyGroceryPageModel.GRID_PRODUCT_VIEW:
                    String gridLayoutColor = myGroceryPageModelList.get(position).getBackgroundColor();
                    String gridLayoutTitle = myGroceryPageModelList.get(position).getTitle();
                    List<HorizontalProductScrollModel> gridProductScrollModelList = myGroceryPageModelList.get(position).getHorizontalProductScrollModelList();
                    ((GridProductViewholder)viewHolder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle,gridLayoutColor);
            default:
                return;
        }

        if (lastPosition < position) {
        Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
        viewHolder.itemView.setAnimation(animation);
        lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return myGroceryPageModelList.size();
    }

    public static class BannerSliderViewholder extends RecyclerView.ViewHolder{

        private final ViewPager bannerSliderViewPager;
        private int currentPage;
        private Timer timer;
        private List<SliderModel> arrangedList;

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);
        }

        @SuppressLint("ClickableViewAccessibility")
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null){
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            for (int x = 0;x < sliderModelList.size(); x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    currentPage = i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (i == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(arrangedList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangedList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });

        }
        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                sliderModelList.size();
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer = new Timer();
            long DELAY_TIME = 3000;
            long PERIOD_TIME = 3000;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            } , DELAY_TIME, PERIOD_TIME);
        }
        private void stopBannerSlideShow() {
            timer.cancel();
        }
    }

    public static class StripAdBannerViewholder extends RecyclerView.ViewHolder {

        private final ImageView stripAdImage;
        private final ConstraintLayout stripAdContainer;

        public StripAdBannerViewholder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner_placeholder)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }

    }

    public class HorizontalProductViewholder extends RecyclerView.ViewHolder{

        private final ConstraintLayout container;
        private final TextView horizontalLayoutTitle;
        private final Button horizontalLayoutViewAllBtn;
        private final RecyclerView horizontalRecyclerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.Container);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_view_all_btn);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title, String color, final List<WishlistModel> viewAllProductList) {
            try {
                container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            }catch (NullPointerException ignored) {
                horizontalLayoutTitle.setText(title);
                if (horizontalProductScrollModelList.size() > 8) {
                    horizontalLayoutViewAllBtn.setVisibility(View.VISIBLE);
                    horizontalLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewAllActivity.wishlistModelList = viewAllProductList;
                            Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                            viewAllIntent.putExtra("layout_code", 0);
                            viewAllIntent.putExtra("title", title);
                            itemView.getContext().startActivity(viewAllIntent);
                        }
                    });
                } else {
                    horizontalLayoutViewAllBtn.setVisibility(View.INVISIBLE);
                }
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public static class GridProductViewholder extends RecyclerView.ViewHolder{

        private final ConstraintLayout container;
        private final TextView gridLayoutTitle;
        private final Button gridLayoutViewAllBtn;
        private final GridLayout gridProductLayout;

        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.Container);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }

        @SuppressLint("SetTextI18n")
        private void setGridProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title, String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for (int x = 0;x < 4;x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.icon_placeholder)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDescription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(x).getProductPrice()+"/-");
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));

                if (!title.equals("")) {
                    final int finalX = x;
                    gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                            productDetailsIntent.putExtra("PRODUCT_ID", horizontalProductScrollModelList.get(finalX).getProductID());
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });
                }
            }

            if (!title.equals("")) {
                gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layo`ut_code", 1);
                        viewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }
        }
    }
    }