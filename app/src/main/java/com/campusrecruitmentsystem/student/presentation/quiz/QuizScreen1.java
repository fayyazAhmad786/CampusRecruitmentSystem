package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.campusrecruitmentsystem.R;

public class QuizScreen1 extends AppCompatActivity {

    private Button btnStartQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen1);

        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(QuizScreen1.this, QuizScreen2.class);
                startActivity(intent);
            }
        });
    }
}