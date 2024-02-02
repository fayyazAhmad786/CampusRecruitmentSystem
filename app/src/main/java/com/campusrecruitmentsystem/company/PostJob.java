package com.campusrecruitmentsystem.company;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.PostJobGetterSetter;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.Querries;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import org.json.JSONArray;

import java.util.Calendar;

public class PostJob extends AppCompatActivity {

    private EditText et_application_deadline;
    Spinner spn_company,spn_location,spn_sallery;
    private EditText et_job_title,et_job_description;
    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private  Context context;
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";
    private JSONArray jsonArray; // Declare JSONArray variable
    String jsonArrayAsString = "";
    private Button PostJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        findViews();
        context= this;

        et_application_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  // hide the soft keyboard
                }

                // Get current date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog and set minimum date
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostJob.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_application_deadline.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);

                // Set minimum date (current date)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                // Show the dialog
                datePickerDialog.show();
            }
        });
        savetoDatabase();

    }

    private void savetoDatabase() {

        PostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                    SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
                    String emailcompany = settings_profile.getString("emailcompany", ""); // Provide default value if the key is not found
                    String fullNamecompany = settings_profile.getString("fullNamecompany", ""); // Provide default value if the key is not found

                    String JobTitle = et_job_title.getText().toString();
                    String JobDescription = et_job_description.getText().toString();
                    String company = spn_company.getSelectedItem().toString();
                    String location = spn_location.getSelectedItem().toString();
                    String sallery = spn_sallery.getSelectedItem().toString();
                    String JobDeadline = et_application_deadline.getText().toString();

                    Intent intent = getIntent();
                    jsonArrayAsString = intent.getStringExtra("jsonArray");
                    if (!jsonArrayAsString.equalsIgnoreCase("empty")){
                        if (!JobTitle.isEmpty()){
                            if (!JobDescription.isEmpty()){
                                if (!company.equalsIgnoreCase("Select Company")){
                                    if (!location.equalsIgnoreCase("Select Location")){
                                        if (!sallery.equalsIgnoreCase("Select Sallery")){
                                            if (!JobDeadline.isEmpty()){

                                                PostJobGetterSetter.setQuestions(jsonArrayAsString);
                                                PostJobGetterSetter.setJobTitle(JobTitle);
                                                PostJobGetterSetter.setJobDescription(JobDescription);
                                                PostJobGetterSetter.setJobCompanyType(company);
                                                PostJobGetterSetter.setJobLocation(location);
                                                PostJobGetterSetter.setJobSallery(sallery);
                                                PostJobGetterSetter.setJobDeadline(JobDeadline);
                                                PostJobGetterSetter.setCompanyname(fullNamecompany);
                                                PostJobGetterSetter.setCompanyEmail(emailcompany);



                                                long id = Querries.insertIntoJobPost(context);
                                                if (id > 0) {
                                                    ViewDialog alert = new ViewDialog();
                                                    alert.showDialog(PostJob.this, "Job Posted Successfully.!!!");//
                                                    clearAllfields();
                                                }else {
                                                    ViewDialog alert = new ViewDialog();
                                                    alert.showDialog(PostJob.this, "Database Connection Problem.!!!");//
                                                }


                                            }else {
                                                et_application_deadline.setError("Enter Job Deadline");
                                                et_application_deadline.requestFocus();
                                            }
                                        }else {
                                            spn_sallery.requestFocus();
                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(PostJob.this, "Please Select Sallery Range.!!!");//
                                        }
                                    }else {
                                        spn_location.requestFocus();
                                        ViewDialog alert = new ViewDialog();
                                        alert.showDialog(PostJob.this, "Please Select Location.!!!");//
                                    }
                                }else {
                                    spn_company.requestFocus();
                                    ViewDialog alert = new ViewDialog();
                                    alert.showDialog(PostJob.this, "Please Select Company.!!!");//
                                }
                            }else {
                                et_job_description.setError("Enter Job Description");
                                et_job_description.requestFocus();
                            }

                        }else {
                            et_job_title.setError("Enter Job Title");
                            et_job_title.requestFocus();
                        }

                    }else {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(PostJob.this,"Please Create Test Before Job Posting");
                    }



//                } catch (Exception e) {
//                    e.printStackTrace();
//                    // Handle JSON parsing exception
//                    Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }
    private void clearAllfields() {
        et_job_title.setError(null);
        et_job_description.setError(null);
        et_application_deadline.setError(null);

        et_job_title.setText("");
        et_job_description.setText("");
        spn_company.setSelection(0);
        spn_location.setSelection(0);
        spn_sallery.setSelection(0);
        et_application_deadline.setText("");
        jsonArrayAsString = "empty";
    }

    private void findViews() {
        et_job_title = findViewById(R.id.et_job_title);
        et_job_description = findViewById(R.id.et_job_description);
        spn_company = findViewById(R.id.spn_company);
        spn_location = findViewById(R.id.spn_location);
        spn_sallery = findViewById(R.id.spn_sallery);

        et_application_deadline = findViewById(R.id.et_application_deadline);
        PostJob = findViewById(R.id.PostJob);
    }

    public void CreateTest(View view) {
        Intent  intent = new Intent(PostJob.this, CreateTest.class);
        startActivity(intent);
    }
}