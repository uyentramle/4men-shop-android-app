package com.formenshop.Activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.formenshop.Adapters.CartAdapter;
import com.formenshop.Adapters.HomePageFragmentAdapter;
import com.formenshop.Api.ApiClient;
import com.formenshop.Api.ApiService;
import com.formenshop.Config.TokenManager;
import com.formenshop.Fragments.HomeFragment;
import com.formenshop.Fragments.ProfileSettingsFragment;
import com.formenshop.Fragments.SearchFragment;
import com.formenshop.JWT.GetUserID;
import com.formenshop.Models.CartModels;
import com.formenshop.Models.CategoriesModel;
import com.formenshop.Models.ProductsModel;
import com.formenshop.Notification.NotificationHelper;
import com.formenshop.R;
import com.formenshop.Response.CartCount;
import com.formenshop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    HomePageFragmentAdapter mSectionsPagerAdapter;
    ActivityMainBinding binding;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter viewPagerAdapter;
    TokenManager tokenManager;
    ApiService apiService;

    TextView countCart;
    CartCount cartCount;
    CartAdapter cartAdapter;
    List<CartModels> listCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Create hardcoded data
        ArrayList<ProductsModel> products = new ArrayList<>();


        // Create hardcoded category data
        ArrayList<CategoriesModel> categories = new ArrayList<>();
        categories.add(new CategoriesModel("https://m.media-amazon.com/images/I/81oBlS3rKXL._UY575_.jpg", "Category 1"));
        categories.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41lICpaGo9L._SX300_SY300_QL70_FMwebp_.jpg", "Category 2"));
        categories.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41wKsI9yrZL._SY300_SX300_QL70_FMwebp_.jpg", "Category 3"));
        categories.add(new CategoriesModel("https://m.media-amazon.com/images/I/911EKUNq1+L._SL1500_.jpg", "Category 4"));
        categories.add(new CategoriesModel("https://images-eu.ssl-images-amazon.com/images/I/41lICpaGo9L._SX300_SY300_QL70_FMwebp_.jpg", "Category 5"));

        mSectionsPagerAdapter = new HomePageFragmentAdapter(getSupportFragmentManager(), 3);
        binding.viewpager.setAdapter(mSectionsPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(0); // Set the number of offscreen pages to keep in memory
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    binding.bottomNavigation.getMenu().getItem(0).setChecked(false);

                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = binding.bottomNavigation.getMenu().getItem(position);

                int itemId = prevMenuItem.getItemId();
                if (itemId == R.id.home) {
                    binding.title.setText("Home");
                } else if (itemId == R.id.search) {
                    binding.title.setText("Search");
                } else if (itemId == R.id.profile) {
                    binding.title.setText("My Profile");
                } else if (itemId == R.id.cartView) {
                    binding.title.setText("Cart");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        handleBottomNav();

        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        countCart = findViewById(R.id.cartCount);
        apiService = ApiClient.getApiService(this);
        int userId = GetUserID.getUserIdFromToken(this);
        if(userId != 0) {
            getCartInfor(userId);
        }

        // Call API to get cart count based on userId
//        if (userId != 0) {
//            apiService.countCart(userId).enqueue(new Callback<CartCount>() {
//                @Override
//                public void onResponse(Call<CartCount> call, Response<CartCount> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        int count = response.body().getCount();
//
//                          countCart.setText(String.valueOf(count));
//                        binding.cartCount.setVisibility(View.GONE);
//
//                    } else {
//                        // Handle unsuccessful API call
//                        binding.cartCount.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CartCount> call, Throwable t) {
//                    // Handle failure
//                    binding.cartCount.setVisibility(View.GONE);
//                }
//            });
//        } else {
//            // If userId is 0, assume no items in cart
//            binding.cartCount.setVisibility(View.GONE);
//        }

        // Handler for splash layout (same as before)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.splashLayout.setVisibility(View.GONE);
            }
        }, 1500);

        setupViewPager(binding.viewpager, products, categories); // Pass products and categories to setupViewPager
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<ProductsModel> products, ArrayList<CategoriesModel> categories) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(HomeFragment.newInstance(products, categories), "Home");
        // Add other fragments here like SearchFragment, ProfileFragment
        viewPagerAdapter.addFragment(new SearchFragment(), "Search");
        viewPagerAdapter.addFragment(new ProfileSettingsFragment(), "Profile");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void handleBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.home) {
                    binding.viewpager.setCurrentItem(0);
                    binding.title.setText("Home");
                } else if (itemId == R.id.search) {
                    binding.viewpager.setCurrentItem(1);
                    binding.title.setText("Search");
                } else if (itemId == R.id.profile) {
                    binding.viewpager.setCurrentItem(2);
                    binding.title.setText("My Profile");
                } else if (itemId == R.id.cartView) {
                    binding.viewpager.setCurrentItem(3);
                    binding.title.setText("Cart");
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull androidx.fragment.app.FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getCartInfor(int userId){
        Call<List<CartModels>> call = apiService.getCart(userId);
        call.enqueue(new Callback<List<CartModels>>() {
            @Override
            public void onResponse(Call<List<CartModels>> call, Response<List<CartModels>> response) {

                listCart = response.body();
                if(listCart != null && listCart.size() >  0){
                    NotificationCart(listCart);
                }

            }

            @Override
            public void onFailure(Call<List<CartModels>> call, Throwable t) {

            }
        });
     }

     private void NotificationCart(List<CartModels> listCart){
         int totalQuantity = 0;
         double totalPrice = 0.0;

         for (CartModels item : listCart) {
             totalQuantity += item.getQuantity();
             totalPrice += item.getPrice() * item.getQuantity();
         }
         NotificationHelper notificationHelper = new NotificationHelper(MainActivity.this);
         String message = "Bạn có " + totalQuantity + " sản phẩm trong giỏ hàng, tổng cộng: " + totalPrice + " VND. Hãy thanh toán ngay.";
         notificationHelper.createNotification("Thông báo giỏ hàng", message, CartActivity.class);
     }


    private int getNotificationId() {
        return (int ) new Date().getTime();
    }

}