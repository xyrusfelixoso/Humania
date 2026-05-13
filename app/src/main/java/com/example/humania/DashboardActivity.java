package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        NavigationView sideNav = findViewById(R.id.side_navigation);

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), R.id.nav_home);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return loadFragment(new HomeFragment(), id);
            if (id == R.id.nav_browse) return loadFragment(new BrowseFragment(), id);
            if (id == R.id.nav_messages) return loadFragment(new MessagesFragment(), id);
            if (id == R.id.nav_profile) return loadFragment(new ProfileFragment(), id);
            return false;
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, DonateActivity.class);
            startActivity(intent);
        });

        sideNav.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private boolean loadFragment(Fragment fragment, int itemId) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            bottomNav.getMenu().findItem(itemId).setChecked(true);
            return true;
        }
        return false;
    }

    public void switchToBrowse() {
        bottomNav.setSelectedItemId(R.id.nav_browse);
    }

    public void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
