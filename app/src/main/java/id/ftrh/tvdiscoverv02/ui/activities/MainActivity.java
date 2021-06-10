package id.ftrh.tvdiscoverv02.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import id.ftrh.tvdiscoverv02.R;
import id.ftrh.tvdiscoverv02.ui.fragments.FavoriteFragment;
import id.ftrh.tvdiscoverv02.ui.fragments.TvShowFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bnv_main);
        bottomNav.setOnNavigationItemSelectedListener(this);
        setSelectedItem(bottomNav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        String sortBy = null;
        switch (menuItem.getItemId()) {
            case R.id.item_airing_today:
                setActionBar(getString(R.string.app_name), R.drawable.tv);
                sortBy = "airing_today";
                fragment = new TvShowFragment();
                break;
//            case R.id.item_popular:
//                setActionBar(getString(R.string.popular), R.drawable.ic_popular_white);
//                sortBy = "popular";
//                fragment = new TvShowFragment();
//                break;
            case R.id.item_top_rated:
                setActionBar(getString(R.string.app_name), R.drawable.tv);
                sortBy = "top_rated";
                fragment = new TvShowFragment();
                break;
            case R.id.item_favorite:
                setActionBar(getString(R.string.app_name), R.drawable.tv);
                fragment = new FavoriteFragment();
                break;
        }
        if (fragment != null) {
            // Method that handle which data to show base on @sortBy params
            startFragment(fragment, sortBy);
            return true;
        }
        return false;
    }

    private void setActionBar(String title, int logo) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("\t" + title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(logo);
        }
    }

    private void setSelectedItem(BottomNavigationView bottomNavigationView) {
        if (getIntent().getStringExtra("SELECTED_FRAGMENT") != null) {
            switch (getIntent().getStringExtra("SELECTED_FRAGMENT")) {
                case "airing_today":
                    bottomNavigationView.setSelectedItemId(R.id.item_airing_today);
                    break;
//                case "popular":
//                    bottomNavigationView.setSelectedItemId(R.id.item_popular);
//                    break;
                case "top_rated":
                    bottomNavigationView.setSelectedItemId(R.id.item_top_rated);
                    break;
                case "favorite":
                    bottomNavigationView.setSelectedItemId(R.id.item_favorite);
                    break;
            }
        } else {
            bottomNavigationView.setSelectedItemId(R.id.item_airing_today);
        }
    }

    private void startFragment(Fragment fragment, String bundle) {
        if (bundle != null) {
            Bundle sortBy = new Bundle();
            sortBy.putString("SORT_BY", bundle);
            fragment.setArguments(sortBy);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main, fragment)
                .commit();
    }
}
