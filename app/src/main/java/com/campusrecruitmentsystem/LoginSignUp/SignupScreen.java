package com.campusrecruitmentsystem.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusrecruitmentsystem.R;

public class SignupScreen extends AppCompatActivity {
    private TextView tvOption1, tvOption2,txtLogin;
    private ImageView imageArrowleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        tvOption1 = findViewById(R.id.tvOption1);
        tvOption2 = findViewById(R.id.tvOption2);
        imageArrowleft = findViewById(R.id.imageArrowleft);
        txtLogin = findViewById(R.id.txtLogin);

        tvOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTextViewClick(tvOption1,"student");
            }
        });

        tvOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTextViewClick(tvOption2,"company");
            }
        });
        imageArrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void handleTextViewClick(TextView textView,String value) {

        // Reset background for all TextViews
        resetBackgroundForAllTextViews();
        textView.setSelected(true);

        // Check which TextView is selected
        if (value.equalsIgnoreCase("student")) {
            // Option 1 is selected
            // Handle accordingly
//            Toast.makeText(SignupScreen.this,"student",Toast.LENGTH_SHORT).show();
        } else {
            // Option 2 is selected
            // Handle accordingly
//            Toast.makeText(SignupScreen.this,"Company 2",Toast.LENGTH_SHORT).show();

        }
        // Highlight the selected TextView
    }

    private void resetBackgroundForAllTextViews() {
        tvOption1.setSelected(false);
        tvOption2.setSelected(false);
        // Add more TextViews if needed
    }
}