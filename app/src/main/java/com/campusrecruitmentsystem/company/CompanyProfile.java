package com.campusrecruitmentsystem.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;

public class CompanyProfile extends AppCompatActivity {

    private Button btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        findViews();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(LoginScreen.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(CompanyProfile.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        btnLogOut =findViewById(R.id.btnLogOut);
    }
}