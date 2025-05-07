package com.example.myisamm;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // 1. Find Views
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Set the Toolbar

        // Find the custom title TextView
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("MyISAMM");

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 2. Find the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // 3. Define Top-Level Destinations
            Set<Integer> topLevelDestinations = new HashSet<>();
            topLevelDestinations.add(R.id.navigation_home);
            topLevelDestinations.add(R.id.navigation_courses);
            topLevelDestinations.add(R.id.navigation_profile);
            topLevelDestinations.add(R.id.navigation_about);

            appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();

            // 4. Connect Toolbar and BottomNav with NavController
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavView, navController);

            // Optional: reapply title on destination change just in case
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                toolbarTitle.setText("MyISAMM");
            });

        } else {
            Log.e("MainActivity", "FATAL ERROR: NavHostFragment not found! Cannot setup navigation.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true; // No options menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navController != null) {
            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return (navController != null && NavigationUI.navigateUp(navController, appBarConfiguration))
                || super.onSupportNavigateUp();
    }
}
