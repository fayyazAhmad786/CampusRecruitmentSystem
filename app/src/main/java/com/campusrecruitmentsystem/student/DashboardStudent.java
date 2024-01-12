package com.campusrecruitmentsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.presentation.fragments.CatagoriesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.CompaniesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.HomeFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardStudent extends AppCompatActivity {

    ActionBar actionBar;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen_student);
        actionBar = getSupportActionBar();
//        actionBar.setTitle("Profile Activity");

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
//                    actionBar.setTitle("Home");
                    HomeFragment fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment, "")
                            .commit();
                    return true;
                } else if (id == R.id.nav_catagories) {
//                    actionBar.setTitle("Categories");
                    CatagoriesFragment fragment1 = new CatagoriesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment1)
                            .commit();
                    return true;
                } else if (id == R.id.nav_company) {
//                    actionBar.setTitle("Companies");
                    CompaniesFragment fragment2 = new CompaniesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment2, "")
                            .commit();
                    return true;
                } else if (id == R.id.nav_notification) {
//                    actionBar.setTitle("Notification");
                    NotificationFragment listFragment = new NotificationFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, listFragment, "")
                            .commit();
                    return true;
                }

                return false;
            }
        });

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();
    }
}