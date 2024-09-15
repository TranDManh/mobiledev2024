package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WeatherAndForecastFragment_1();
            case 1:
                return new WeatherAndForecastFragment_2();
            case 2:
                return new WeatherAndForecastFragment_3();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
