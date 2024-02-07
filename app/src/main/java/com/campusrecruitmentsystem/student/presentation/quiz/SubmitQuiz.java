package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.modules.Question;
import com.campusrecruitmentsystem.student.presentation.quiz.modules.QuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubmitQuiz extends AppCompatActivity {


    private Context context;
    private Button btnApply;
    private  RecyclerView recyclerView;
    private CountDownTimer countDownTimer;
    TextView txtTime;
    private long timeInMillis = 10 * 60 * 1000; // 10 minutes in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_quiz);
        context=this;
        findViews();
        String test = getIntent().getStringExtra("test");
        showQuestions(test);
        btnApply.setEnabled(false);

        showAlertDialog(context);
    }

    private void findViews() {
        txtTime= findViewById(R.id.txtTime);
        btnApply= findViewById(R.id.btnApply);
         recyclerView = findViewById(R.id.recyclerVieww);

    }

    public void SubmitFunction(View view) {
//        Intent intent= new Intent(SubmitQuiz.this, Apply.class);
//        startActivity(intent);
    }
private void showQuestions(String test){
    List<Question> questionList = new ArrayList<>();
    try {
        JSONArray jsonArray = new JSONArray(test);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String question = jsonObject.getString("questions");
            String optionsString = jsonObject.getString("question_options");
            List<String> options = Arrays.asList(optionsString.split(",\\s*"));

            Question questionObject = new Question();
            questionObject.setQuestion(question);
            questionObject.setOptions(options);
            questionList.add(questionObject);


            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            QuestionAdapter adapter = new QuestionAdapter(questionList);
            recyclerView.setAdapter(adapter);
        }
    } catch (JSONException e) {
        e.printStackTrace();
        System.out.println("Error44443322= "+e.getMessage());
        Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
    public void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Convert milliseconds to minutes and seconds
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;

                // Update the TextView with the remaining time
                txtTime.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // Handle timer finish event if needed
                txtTime.setText("00:00");
                startCountDownTimer();
            }
        };

        countDownTimer.start(); // Start the countdown timer
    }
    private void showAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please Start Test. You have 10 minutes to complete test")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" button click
                        dialog.dismiss(); // Dismiss the dialog
                        startCountDownTimer();
                    }
                })

                .show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the countdown timer to avoid memory leaks
        }
    }
}


