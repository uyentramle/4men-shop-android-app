package com.formenshop.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.formenshop.Adapters.ProductAdapter;
import com.formenshop.Fragments.ProfileSettingsFragment;
import com.formenshop.Fragments.SearchFragment;
import com.formenshop.Models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.formenshop.R;
import com.formenshop.Fragments.HomeFragment;
import com.formenshop.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MenuItem prevMenuItem;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupViewPager(binding.viewpager);
        binding.viewpager.setOffscreenPageLimit(3);  // Set the number of offscreen pages to keep in memory
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

//                switch(prevMenuItem.getItemId()) {
//                    case R.id.home:
//                        binding.title.setText("Home");
//                        break;
//                    case R.id.search:
//                        binding.title.setText("Search");
//                        break;
//                    case R.id.profile:
//                        binding.title.setText("My Profile");
//                        break;
//                }
                int itemId = prevMenuItem.getItemId();
                if (itemId == R.id.home) {
                    binding.title.setText("Home");
                } else if (itemId == R.id.search) {
                    binding.title.setText("Search");
                } else if (itemId == R.id.profile) {
                    binding.title.setText("My Profile");
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
                // No action for now
            }
        });

        // Hardcoded data logic
        ArrayList<String> cartItems = new ArrayList<>();
        cartItems.add("Item 1");
        cartItems.add("Item 2");

        if (cartItems.size() == 0) {
            binding.cartCount.setVisibility(View.GONE);
        } else {
            binding.cartCount.setText(String.valueOf(cartItems.size()));
            binding.cartCount.setVisibility(View.VISIBLE);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.splashLayout.setVisibility(View.GONE);
            }
        }, 1500);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new HomeFragment(), "Home");
        // Add other fragments here like SearchFragment, ProfileFragment
        viewPagerAdapter.addFragment(new SearchFragment(), "Search");
        viewPagerAdapter.addFragment(new ProfileSettingsFragment(), "Profile");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void handleBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        binding.viewpager.setCurrentItem(0);
//                        binding.title.setText("Home");
//                        break;
//                    case R.id.search:
//                        binding.viewpager.setCurrentItem(1);
//                        binding.title.setText("Search");
//                        break;
//                    case R.id.profile:
//                        binding.viewpager.setCurrentItem(2);
//                        binding.title.setText("My Profile");
//                        break;
//                }
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
}
