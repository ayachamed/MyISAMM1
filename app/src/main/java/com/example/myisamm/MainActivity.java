package com.example.myisamm;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController; // Member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Find Views
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Set the Toolbar

        // 2. Find the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            // 3. Get NavController
            navController = navHostFragment.getNavController();

            // 4. Define Top-Level Destinations (Matching menu and corrected nav graph)
            Set<Integer> topLevelDestinations = new HashSet<>();
            topLevelDestinations.add(R.id.navigation_home);
            topLevelDestinations.add(R.id.navigation_courses); // Matches menu and corrected nav graph
            topLevelDestinations.add(R.id.navigation_profile);
            topLevelDestinations.add(R.id.navigation_about);

            appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();

            // 5. Connect Toolbar with NavController
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // 6. Connect BottomNavigationView with NavController
            NavigationUI.setupWithNavController(bottomNavView, navController);

        } else {
            Log.e("MainActivity", "FATAL ERROR: NavHostFragment not found! Cannot setup navigation.");
        }
    }

    // Prevent Top Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true; // No inflation
    }

    // Handle Options Item Selection (mainly for Up button)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navController != null) {
            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle Up button navigation
    @Override
    public boolean onSupportNavigateUp() {
        return (navController != null && NavigationUI.navigateUp(navController, appBarConfiguration))
                || super.onSupportNavigateUp();
    }
}