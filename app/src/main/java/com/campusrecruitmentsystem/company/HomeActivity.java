package com.campusrecruitmentsystem.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.fragments.CandidatesFragment;
import com.campusrecruitmentsystem.company.fragments.InterviewFragment;
import com.campusrecruitmentsystem.company.fragments.JobsFragment;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;
import com.campusrecruitmentsystem.student.presentation.fragments.CatagoriesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.CompaniesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.HomeFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    ImageView imageProfile;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        findViews();
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CompanyProfile.class);
                startActivity(intent);
            }
        });
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_job) {
//                    actionBar.setTitle("Home");
                    JobsFragment fragment = new JobsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment, "")
                            .commit();
                    return true;
                } else if (id == R.id.nav_candidates) {
//                    actionBar.setTitle("Categories");
                    CandidatesFragment fragment1 = new CandidatesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment1)
                            .commit();
                    return true;
                } else if (id == R.id.nav_interview) {
//                    actionBar.setTitle("Companies");
                    InterviewFragment fragment2 = new InterviewFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment2, "")
                            .commit();
                    return true;
                }

                return false;
            }
        });

        JobsFragment fragment = new JobsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentCompany, fragment, "");
        fragmentTransaction.commit();
    }

    private void findViews() {
        imageProfile = findViewById(R.id.imageProfile);
        navigationView = findViewById(R.id.navigationCompany);


    }

    public void PostJob(View view) {
        Intent intent= new Intent(HomeActivity.this, PostJob.class);
        intent.putExtra("jsonArray", "empty");
        startActivity(intent);
    }
}