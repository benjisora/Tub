package com.projects.benjisora.tubapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.fragment.FavoritesFragment;
import com.projects.benjisora.tubapp.fragment.MapsFragment;
import com.projects.benjisora.tubapp.fragment.PathFinderFragment;
import com.projects.benjisora.tubapp.fragment.SchedulesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    /**
     * {@inheritDoc}
     * Binds the data, initializes the NavigationDrawer, and loads the default fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Class fragmentClass = SchedulesFragment.class;
        try {
            toolbar.setTitle("Schedules");
            loadFragment(fragmentClass);
        } catch (Exception e) {
            Log.e(getString(R.string.main_activity), getString(R.string.log_error), e);
            Toast.makeText(this, R.string.error_loading_fragment,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * {@inheritDoc}
     * Closes the drawer if already open
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * {@inheritDoc}
     * Manages the classes to load depending on the choice made
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class fragmentClass = null;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_schedules:
                toolbar.setTitle("Schedules");
                fragmentClass = SchedulesFragment.class;
                break;
            case R.id.nav_map:
                toolbar.setTitle("Map");
                fragmentClass = MapsFragment.class;
                break;
            case R.id.nav_favorites:
                toolbar.setTitle("Favorites");
                fragmentClass = FavoritesFragment.class;
                break;
            case R.id.nav_pathfinder:
                toolbar.setTitle("PathFinder");
                fragmentClass = PathFinderFragment.class;
                break;
            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                toolbar.setTitle("Schedules");
                fragmentClass = SchedulesFragment.class;
                break;
        }

        try {
            loadFragment(fragmentClass);
        } catch (Exception e) {
            Log.e(getString(R.string.main_activity), getString(R.string.log_error), e);
            Toast.makeText(this, R.string.error_loading_fragment,Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the Fragment class
     * @param classToLoad The class to load
     * @throws Exception
     */
    public void loadFragment(Class classToLoad) throws Exception {
        Fragment fragment = null;
        if (classToLoad != null) {
            fragment = (Fragment) classToLoad.newInstance();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.getTag()).commit();
        }
    }

}
