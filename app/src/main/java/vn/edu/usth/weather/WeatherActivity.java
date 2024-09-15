package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tabLayout = findViewById(R.id.tab_layout);

        viewPager2 = findViewById(R.id.viewpager_2);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Hanoi, VietNam");
                        break;
                    case 1:
                        tab.setText("Paris, France");
                        break;
                    case 2:
                        tab.setText("NewYork, America");
                        break;
                }
            }
        }).attach();


        Log.i(TAG, "onCreate: Activity created");
    }
        @Override
        protected void onStart() {
            super.onStart();
            Log.i(TAG, "onStart: Activity started");
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.i(TAG, "onResume: Activity resumed");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.i(TAG, "onPause: Activity paused");
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.i(TAG, "onStop: Activity stopped");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.i(TAG, "onDestroy: Activity destroyed");
        }
    }



