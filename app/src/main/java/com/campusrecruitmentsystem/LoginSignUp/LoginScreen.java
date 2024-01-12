package com.campusrecruitmentsystem.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.DashboardStudent;

public class LoginScreen extends AppCompatActivity {

    private TextView txtHaventanacco;
    private Button btnLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        txtHaventanacco=findViewById(R.id.txtHaventanacco);
        btnLogIn=findViewById(R.id.btnLogIn);
        txtHaventanacco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(intent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginScreen.this, DashboardStudent.class);
                startActivity(intent);
            }
        });
    }
}