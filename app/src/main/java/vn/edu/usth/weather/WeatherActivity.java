package vn.edu.usth.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        downloadLogo("https://usth.edu.vn/wp-content/uploads/2021/11/logo.png");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try (InputStream is = getResources().openRawResource(R.raw.if_2_beat);
             OutputStream os = new FileOutputStream(new File(getFilesDir(),"if_2_beat.mp3")))
              {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0){
                os.write(buffer, 0, length);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(new File(getFilesDir(),"if_2_beat.mp3").getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tab_layout);

        viewPager2 = findViewById(R.id.viewpager_2);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(getString(R.string.place_1));
                        break;
                    case 1:
                        tab.setText(getString(R.string.place_2));
                        break;
                    case 2:
                        tab.setText(getString(R.string.place_3));
                        break;
                }
            }
        }).attach();


        Log.i(TAG, "onCreate: Activity created");

        if (isInternetAvailable()) {
            Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show();
            // Proceed with network operations
        } else {
            Toast.makeText(this, "Internet is not available", Toast.LENGTH_SHORT).show();
            // Handle the lack of Internet access (e.g., show an error message)
        }

    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            // For devices running API level 21 and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return networkCapabilities != null &&
                        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            } else {
                // For devices running lower than API level 21
                android.net.NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }

        return false; // Return false if the connectivity manager is null
    }



    private void downloadLogo(String url) {
        // Create an ImageRequest to fetch the image
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ForecastFragment.updateLogo(response); // Assuming you have a method to update the logo
                        showToast("Logo downloaded successfully!");
                    }
                },
                0, // Width
                0, // Height
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Failed to download logo.", error);
                        showToast("Failed to download logo. Please try again.");
                    }
                });
        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(imageRequest);
    }


    private void showToast(String message) {
        Toast.makeText(WeatherActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void showAlertDialog(String title, String message) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.refesh){
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
            downloadLogo("https://usth.edu.vn/wp-content/uploads/2021/11/logo.png");
        }
        if(id == R.id.setting){
            Toast.makeText(this,"Setting", Toast.LENGTH_SHORT).show();
        }
        return true;
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



