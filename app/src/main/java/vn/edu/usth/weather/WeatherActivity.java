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
import android.os.AsyncTask;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        new DownloadImage().execute("https://usth.edu.vn/wp-content/uploads/2021/11/logo.png");

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



    private class DownloadImage extends AsyncTask<String,Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(WeatherActivity.this, "Starting refresh...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
// Make a request to server
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
// allow reading response code and response dataconnection.
                connection.connect();
// Receive response
                int response = connection.getResponseCode();
                Log.i("USTHWeather", "The response is: " + response);
                if (response == HttpURLConnection.HTTP_OK) { // Check if the connection was successful
                    InputStream is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close(); // Close the InputStream
                } else {
                    Log.e("DownloadImageTask", "Error in connection: " + response);
                }
                connection.disconnect();
            } catch (MalformedURLException e){
                Log.e("DownloadImageTask", "Malformed URL: " + e.getMessage());
                e.printStackTrace();
            }catch (IOException e) {
                Log.e("DownloadImageTask", "IOException: " + e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView logo = (ImageView) findViewById(R.id.logo);
            if (bitmap != null) {
                logo.setImageBitmap(bitmap);
                Toast.makeText(WeatherActivity.this, "Network connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(WeatherActivity.this, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Execute the AsyncTask


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



