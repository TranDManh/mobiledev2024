package vn.edu.usth.weather;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Simulated network response";
            }

            @Override
            protected void onPostExecute(String result) {
                // Display the result in a Toast
                Toast.makeText(WeatherActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        };

        // Execute the AsyncTask
        task.execute();

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



