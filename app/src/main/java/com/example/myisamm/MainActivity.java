package com.example.myisamm;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable; // Added for OnDestinationChangedListener
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination; // Added for OnDestinationChangedListener
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp; // Usually not needed if auto-init is enabled

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView toolbarTitle; // Make it a member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FirebaseApp.initializeApp(this); // Typically, Firebase auto-initializes.
        // You can remove this line if you've set up
        // Firebase correctly (e.g., via google-services.json and build.gradle).
        // If you encounter issues without it, you can leave it.
        setContentView(R.layout.activity_main);

        // 1. Find Views
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view); // Ensure this ID is in activity_main.xml
        MaterialToolbar toolbar = findViewById(R.id.toolbar); // Ensure this ID is in activity_main.xml
        setSupportActionBar(toolbar);

        // Find the custom title TextView
        toolbarTitle = findViewById(R.id.toolbar_title); // Ensure this ID is in your toolbar layout
        // Note: Setting a default title here is fine.
        // The addOnDestinationChangedListener below will override it.
        // If you want NavigationUI to manage titles based on fragment labels,
        // you might not need this custom toolbarTitle handling.

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 2. Find the NavHostFragment
        // Ensure R.id.nav_host_fragment is the ID of NavHostFragment in activity_main.xml
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // 3. Define Top-Level Destinations
            // These are the destinations accessible directly from the BottomNavigationView
            // and typically don't show an "Up" button in the Toolbar.
            Set<Integer> topLevelDestinations = new HashSet<>();
            topLevelDestinations.add(R.id.navigation_home);
            // IMPORTANT: Update this ID to match your 'Mes cours' destination in mobile_navigation.xml
            // and the menu item ID in bottom_nav_menu.xml
            topLevelDestinations.add(R.id.navigation_courses_department); // CHANGED FROM R.id.navigation_courses
            topLevelDestinations.add(R.id.navigation_profile);
            topLevelDestinations.add(R.id.navigation_about);

            appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();

            // 4. Connect Toolbar and BottomNav with NavController
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavView, navController);

            // 5. Handle Toolbar Title Changes (Optional based on your needs)
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController controller,
                                                 @NonNull NavDestination destination,
                                                 @Nullable Bundle arguments) {
                    // Option 1: Always set a static title
                    // if (toolbarTitle != null) {
                    //     toolbarTitle.setText("MyISAMM");
                    // }

                    // Option 2: Use the label from the navigation graph (recommended for dynamic titles)
                    // If you use this, NavigationUI.setupActionBarWithNavController will handle it,
                    // and you might not need a custom toolbarTitle TextView at all, or you can
                    // hide the default ActionBar title and only use your custom one.
                    // If you keep your custom TextView, you can set its text to destination.getLabel().
                    if (toolbarTitle != null && destination.getLabel() != null) {
                        // If using dynamic labels like "{title}" from arguments:
                        if (arguments != null && destination.getLabel().toString().contains("{title}")) {
                            String dynamicTitle = arguments.getString("title");
                            if (dynamicTitle != null) {
                                toolbarTitle.setText(dynamicTitle);
                            } else {
                                toolbarTitle.setText(destination.getLabel()); // Fallback to static label
                            }
                        } else {
                            toolbarTitle.setText(destination.getLabel());
                        }
                    } else if (toolbarTitle != null) {
                        // Fallback if no label
                        toolbarTitle.setText("MyISAMM");
                    }

                    // To use the default ActionBar title mechanism provided by NavigationUI and hide your custom one:
                    // if (getSupportActionBar() != null) {
                    //    getSupportActionBar().setTitle(destination.getLabel());
                    //    if(toolbarTitle != null) toolbarTitle.setVisibility(View.GONE); // Hide custom title
                    // }
                    // And remove your custom toolbar_title TextView from the toolbar layout or ensure it's not
                    // overlapping with the default title.
                }
            });

        } else {
            Log.e("MainActivity", "FATAL ERROR: NavHostFragment not found! Cannot setup navigation.");
            // Consider showing an error message to the user or finishing the activity
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // If you have an options menu, inflate it here.
        // getMenuInflater().inflate(R.menu.main_options_menu, menu);
        return true; // Return true if you have an options menu, false otherwise
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle ActionBar item clicks here.
        // The NavController will automatically handle clicks on the Up button
        // if it's connected with setupActionBarWithNavController.
        // For other menu items, you can use NavigationUI.onNavDestinationSelected
        // or handle them manually.
        if (navController != null) {
            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // This ensures the Up button in the Toolbar works correctly with the NavController.
        return (navController != null && NavigationUI.navigateUp(navController, appBarConfiguration))
                || super.onSupportNavigateUp();
    }
}