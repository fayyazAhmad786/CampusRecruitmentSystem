package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.CompanyProfile;
import com.campusrecruitmentsystem.company.HomeActivity;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.student.DashboardStudent;
import com.campusrecruitmentsystem.student.modules.Question;
import com.campusrecruitmentsystem.student.presentation.fragments.NotificationFragment;
import com.campusrecruitmentsystem.student.presentation.quiz.modules.QuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubmitQuiz extends AppCompatActivity {

    private List<Pair<String, String>> questionOptionPairs; // List to store question and selected option pairs

    private Context context;
    private Button btnApply;
    private  RecyclerView recyclerView;
    private CountDownTimer countDownTimer;
    TextView txtTime;
    private long timeInMillis = 10 * 60 * 1000; // 10 minutes in milliseconds
    private LinearLayout questionLayout;
    ImageView imageArrowleftQuiz;
    private TextView tv_time_up;
    String job_id;
    int _id_pk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_quiz);
        context=this;
        questionLayout = findViewById(R.id.questionLayout);
        imageArrowleftQuiz = findViewById(R.id.imageArrowleftQuiz);
        questionOptionPairs = new ArrayList<>();
        findViews();
        String test = getIntent().getStringExtra("test");
         job_id = getIntent().getStringExtra("job_id");
        _id_pk = getIntent().getIntExtra("_id_pk",0);
        System.out.println("_id_pk22="+_id_pk);
        showQuestions(test);


        showAlertDialog(context);
        imageArrowleftQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitQuiz.this, DashboardStudent.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        txtTime= findViewById(R.id.txtTime);
        btnApply= findViewById(R.id.btnApply);
        tv_time_up= findViewById(R.id.tv_time_up);
//         recyclerView = findViewById(R.id.recyclerVieww);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SubmitQuiz.this, DashboardStudent.class);
        startActivity(intent);
        finish();
    }


//    private void showQuestions(String test) {
//        try {
//            JSONArray jsonArray = new JSONArray(test);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String questionText = jsonObject.getString("questions");
//                String optionsString = jsonObject.getString("question_options");
//                List<String> options = Arrays.asList(optionsString.split(",\\s*"));
//
//                // Create TextView for question
//                TextView questionTextView = new TextView(this);
//                questionTextView.setText(questionText);
//                questionLayout.addView(questionTextView);
//
//                // Create RadioButtons for options
//                RadioGroup optionsRadioGroup = new RadioGroup(this);
//                optionsRadioGroup.setOrientation(LinearLayout.VERTICAL);
//                for (String option : options) {
//                    RadioButton radioButton = new RadioButton(this);
//                    radioButton.setText(option);
//                    optionsRadioGroup.addView(radioButton);
//                }
//                questionLayout.addView(optionsRadioGroup);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            System.out.println("Error44443322= " + e.getMessage());
//            Toast.makeText(context, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private void showQuestions(String test) {
        try {
            JSONArray jsonArray = new JSONArray(test);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String questionText = jsonObject.getString("questions");
                String optionsString = jsonObject.getString("question_options");
                List<String> options = Arrays.asList(optionsString.split(",\\s*"));

                // Create TextView for question
                TextView questionTextView = new TextView(this);
                questionTextView.setText(questionText);
                questionLayout.addView(questionTextView);

                // Create RadioButtons for options
                RadioGroup optionsRadioGroup = new RadioGroup(this);
                optionsRadioGroup.setOrientation(LinearLayout.VERTICAL);
                for (String option : options) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(option);
                    optionsRadioGroup.addView(radioButton);
                }
                questionLayout.addView(optionsRadioGroup);

                // Add question and selected option pair to the list
                optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton selectedRadioButton = findViewById(checkedId);
                    if (selectedRadioButton != null) {
                        String selectedOption = selectedRadioButton.getText().toString();
                        questionOptionPairs.add(new Pair<>(questionText, selectedOption));
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error44443322= " + e.getMessage());
            Toast.makeText(context, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String convertQuestionOptionPairsToString(List<Pair<String, String>> questionOptionPairs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<String, String> pair : questionOptionPairs) {
            stringBuilder.append(pair.first); // Append question
            stringBuilder.append(": ");
            stringBuilder.append(pair.second); // Append selected option
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
                tv_time_up.setVisibility(View.VISIBLE);
                setLayoutEnabled(questionLayout, false);
            }
        };

        countDownTimer.start(); // Start the countdown timer
    }
    private void setLayoutEnabled(ViewGroup viewGroup, boolean enabled) {
        viewGroup.setEnabled(enabled);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                setLayoutEnabled((ViewGroup) child, enabled); // Recursively disable child layouts
            } else {
                child.setEnabled(enabled); // Disable individual child views
            }
        }
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

    @SuppressLint("NotConstructor")
    public void SubmitQuiz(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_back);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Are you Sure You Want to Submit Quiz");

        Button btn_dialog_cancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        Button btn_dialog_ok = (Button) dialog.findViewById(R.id.btn_dialog_ok);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionOptionPairsString = convertQuestionOptionPairsToString(questionOptionPairs);

                System.out.println("questionOptionPairsString= "+questionOptionPairsString);
                getRequiredDataForQuiz(questionOptionPairsString);
                dialog.dismiss();
                Intent intent = new Intent(SubmitQuiz.this, DashboardStudent.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
    private void getRequiredDataForQuiz(String questionOptionPairsString) {

        try {
            System.out.println("_id_pkgetRequiredDataForQuiz= "+_id_pk);
            System.out.println("job_idgetRequiredDataForQuiz= "+job_id);
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

            String q = "UPDATE tbl_notification set test_result='"+questionOptionPairsString+"' where _id_pk='" + _id_pk + "'";
            db.execSQL(q);
            String w = "UPDATE tbl_Jobs_applied set test_result='"+questionOptionPairsString+"' where job_id='" + job_id + "'";
            db.execSQL(w);

            String qq = "UPDATE tbl_notification set notification_status='old' where _id_pk='" + _id_pk + "'";
            db.execSQL(qq);
            System.out.println("Qsdlkfsldfj=" +q);
            System.out.println("Qsdlkfsldfjqq=" +qq);
        }catch (Exception e){

            Toast.makeText(context,"value="+e.getMessage(),Toast.LENGTH_SHORT).show();

        }



    }

}


