package com.campusrecruitmentsystem.student.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.Job;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.JobAdapter;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.JobAdapterFeaturedJob;
import com.campusrecruitmentsystem.student.presentation.job_applied_module.AppliedJobAdapter;
import com.campusrecruitmentsystem.student.presentation.job_applied_module.GettersetterAppliedJobs;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen2;

import java.util.ArrayList;
import java.util.List;

public class ApplliedJobs extends AppCompatActivity {

    private AppliedJobAdapter AppliedJobAdapter;
    private Context context;
    private RecyclerView recyclerApplied;
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allied_jobs);
        recyclerApplied = findViewById(R.id.recyclerApplied);
        context = this;
        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailStudent = settings_profile.getString("emailstudent", ""); // Provide default value if the key is not found
        loadJobsFromDatabase(emailStudent);
    }


    private void loadJobsFromDatabase(String emailStudent) {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            List<GettersetterAppliedJobs> gettersetterAppliedJobsList = new ArrayList<>();
            String company_location= "";
            String job_title= "";
            String company_name= "";
            String company_profile_pic= "";
            String sallery_range= "";
            String application_deadline= "";
            int _id_pk;

            AppliedJobAdapter =new AppliedJobAdapter(context);
            String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic,application_deadline,_id_pk from tbl_Jobs_applied WHERE user_applied='"+emailStudent+"'   ";
            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                    job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                    sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                    application_deadline = cur.getString(cur.getColumnIndexOrThrow("application_deadline"));
                    _id_pk = cur.getInt(cur.getColumnIndexOrThrow("_id_pk"));
                        GettersetterAppliedJobs job = new GettersetterAppliedJobs(company_location, job_title, company_name,sallery_range,company_profile_pic,application_deadline,_id_pk);
                    gettersetterAppliedJobsList.add(job);
                }
                cur.close();
                db.close();
                if (!gettersetterAppliedJobsList.isEmpty()) {
                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    AppliedJobAdapter.setJobList(gettersetterAppliedJobsList);
                    AppliedJobAdapter.notifyDataSetChanged();
                    recyclerApplied.setAdapter(AppliedJobAdapter);
                    // Set item click listener after setting adapter
                    AppliedJobAdapter.setOnItemClickListener(new AppliedJobAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, GettersetterAppliedJobs job,int _id_pk) {
                            // Handle item click here
                            // Example: Get the clicked job and show its details
                            if (job != null) {
                                // Perform action with clicked job
                                Toast.makeText(context,"Item Clicker"+position,Toast.LENGTH_SHORT).show();

                                getItemClickedData(_id_pk);
                            }
                        }
                    });

                } else {
                    // Handle case where no data is retrieved
                }

            } else {
//                ViewDialog alert = new ViewDialog();
//                alert.showDialog(context,"Email or Password is Incorrect 2");
            }
        } catch (Exception e) {
//            emailAvailable = false;
            ViewDialog alert = new ViewDialog();
            alert.showDialog(ApplliedJobs.this,"DataBase Connection Problem"+e.getMessage());
        }
    }
    private void getItemClickedData(int position) {
        String company_location= "";
        String job_title= "";
        String company_name= "";
        String company_profile_pic= "";
        String sallery_range= "";
        String test= "";
        String company_email= "";
        String company_type= "";
        String application_deadline= "";
        String job_id= "";
        String hired= "";
        String test_assigned= "";
        String test_result= "";
        String short_listed= "";
        String joining_date= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

        String query = "select distinct company_name,job_title,company_location,application_deadline,sallery_range,company_profile_pic,test,hired,test_assigned,test_result,short_listed,short_listed,joining_date,job_id from tbl_Jobs_applied WHERE _id_pk = '"+position+"'  ";
        System.out.println("query= " + query);


        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {


                company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                application_deadline = cur.getString(cur.getColumnIndexOrThrow("application_deadline"));
                sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                test = cur.getString(cur.getColumnIndexOrThrow("test"));
                hired = cur.getString(cur.getColumnIndexOrThrow("hired"));
                test_assigned = cur.getString(cur.getColumnIndexOrThrow("test_assigned"));
                test_result = cur.getString(cur.getColumnIndexOrThrow("test_result"));
                short_listed = cur.getString(cur.getColumnIndexOrThrow("short_listed"));
                joining_date = cur.getString(cur.getColumnIndexOrThrow("joining_date"));

                job_id = cur.getString(cur.getColumnIndexOrThrow("job_id"));


            }
            cur.close();
            db.close();

            Intent intent = new Intent(context, TrackingApplication.class);
            intent.putExtra("company_email",company_email);
            intent.putExtra("company_type",company_type);
            intent.putExtra("application_deadline",application_deadline);

            intent.putExtra("company_location",company_location);
            intent.putExtra("job_title",job_title);
            intent.putExtra("company_name",company_name);
            intent.putExtra("company_profile_pic",company_profile_pic);
            intent.putExtra("sallery_range",sallery_range);
            intent.putExtra("test",test);
            intent.putExtra("job_id",job_id);
            intent.putExtra("hired",hired);
            intent.putExtra("test_assigned",test_assigned);
            intent.putExtra("test_result",test_result);
            intent.putExtra("short_listed",short_listed);
            intent.putExtra("joining_date",joining_date);
            startActivity(intent);
        }
    }

}