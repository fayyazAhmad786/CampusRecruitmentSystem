package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.campusrecruitmentsystem.R;

public class QuizScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen2);


    }

    public void clickFunction(View view) {
        Intent intent = new Intent(QuizScreen2.this,SubmitQuiz.class);
        startActivity(intent);
    }
}