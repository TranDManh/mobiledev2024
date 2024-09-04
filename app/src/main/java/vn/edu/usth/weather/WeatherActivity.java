package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ForecastFragment firstFragment = new ForecastFragment(); // Create an instance of ForecastFragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit(); // Add the fragment to the container


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



