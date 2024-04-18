package com.campusrecruitmentsystem.company.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.NotificationGetterSetter;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.modules.CandidateFragment.AdapterAppliedJob;
import com.campusrecruitmentsystem.company.modules.CandidateFragment.AppliedJobs;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.database.Querries;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CandidatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidatesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerAppliedJobs;
    private AdapterAppliedJob AdapterAppliedJob;


    public CandidatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CandidatesFragment newInstance(String param1, String param2) {
        CandidatesFragment fragment = new CandidatesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidates, container, false);
        recyclerAppliedJobs= view.findViewById(R.id.recyclerAppliedJobs);
        SharedPreferences settings_profile =  getActivity().getSharedPreferences("MyPrefsProfile", 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailcompany", "");
        loadFeaturedJobsFromDatabase(emailcompany);
        return view;
        // Inflate the layout for this fragment
    }
    private void loadFeaturedJobsFromDatabase(String emailcompany) {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<AppliedJobs> jobList = new ArrayList<>();
            String student_full_name= "";
            String job_title= "";
            String company_location= "";
            String sallery_range= "";
            String student_profile_pic= "";
            String test_assigned= "";
            String short_listed= "";
            String test_result= "";


            AdapterAppliedJob =new AdapterAppliedJob(getActivity());
            String query = "select distinct student_full_name,job_title,company_location,sallery_range,student_profile_pic,test_assigned,short_listed,test_result from tbl_Jobs_applied WHERE user_applied !='no' AND company_email ='"+emailcompany+"'   ";

            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    student_full_name = cur.getString(cur.getColumnIndexOrThrow("student_full_name"));
                    job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                    company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                    sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                    student_profile_pic = cur.getString(cur.getColumnIndexOrThrow("student_profile_pic"));
                    test_assigned = cur.getString(cur.getColumnIndexOrThrow("test_assigned"));
                    short_listed = cur.getString(cur.getColumnIndexOrThrow("short_listed"));
                    test_result = cur.getString(cur.getColumnIndexOrThrow("test_result"));

                    AppliedJobs job = new AppliedJobs(student_full_name, job_title, company_location,sallery_range, student_profile_pic,test_assigned,short_listed,test_result);
                    jobList.add(job);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {

                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    AdapterAppliedJob.setJobList(jobList);
                    AdapterAppliedJob.notifyDataSetChanged();

                    recyclerAppliedJobs.setAdapter(AdapterAppliedJob);


                    AdapterAppliedJob.setOnItemClickListener(new AdapterAppliedJob.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, AppliedJobs job, String value) {
                            if (job != null) {
                                if (value.equalsIgnoreCase("test")){
                                    Toast.makeText(getActivity(),"test Clicker"+position,Toast.LENGTH_SHORT).show();
                                    updateTestStatus(position+1);
                                }else if (value.equalsIgnoreCase("resume")){
                                    // Perform action with clicked job
                                    Toast.makeText(getActivity(),"resume Clicker"+position,Toast.LENGTH_SHORT).show();


                                    getItemClickedData(position+1);
                                }else if (value.equalsIgnoreCase("test_result")){
                                    viewTestResult(position+1);

                                }else if (value.equalsIgnoreCase("short_list")){
                                    updateShortListStatus(position+1);

                                }

                            }
                        }
                    });


                } else {
                    // Handle case where no data is retrieved
                }

            } else {
//                ViewDialog alert = new ViewDialog();
//                alert.showDialog(getActivity(),"Email or Password is Incorrect 2");
            }
        } catch (Exception e) {
//            emailAvailable = false;
            ViewDialog alert = new ViewDialog();
            alert.showDialog(getActivity(),"DataBase Connection Problem"+e.getMessage());
        }
    }
    private void getItemClickedData(int position) {
        String user_resume= "";
        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        String query = "select distinct user_resume from tbl_Jobs_applied WHERE job_status='active' AND _id_pk = '"+position+"'  ";
        System.out.println("query= " + query);
        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {
                user_resume = cur.getString(cur.getColumnIndexOrThrow("user_resume"));
            }
            cur.close();
            db.close();
//            Intent intent = new Intent(getActivity(), DownloadOrViewResume.class);
//            intent.putExtra("user_resume",user_resume);
//            startActivity(intent);
            buttonShareFile(user_resume);
        }
    }

    private void viewTestResult(int position) {
        String test_result= "";
        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        String query = "select distinct test_result from tbl_Jobs_applied WHERE job_status='active' AND _id_pk = '"+position+"'  ";
        System.out.println("query= " + query);
        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {
                test_result = cur.getString(cur.getColumnIndexOrThrow("test_result"));
            }
            cur.close();
            db.close();
            System.out.println("test_result13= "+test_result);
            Toast.makeText(getActivity(),"test_Result"+test_result,Toast.LENGTH_SHORT).show();
            openCustomDialog(test_result);
        }
    }
    private void updateShortListStatus(int position) {

        try {

            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            String q = "UPDATE tbl_Jobs_applied set short_listed='Yes' where _id_pk='" + position + "'";
            db.execSQL(q);


//            String w = "UPDATE tbl_notification set short_listed='Yes' where _id_pk='" + position + "'";
//            db.execSQL(w);
        }catch (Exception e){

            Toast.makeText(getActivity(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }



    }

    private void openCustomDialog(String test_result) {
        // Split the string into lines
        String[] lines = test_result.split("\n");

        // Create a LinearLayout to hold all the question-answer pairs
        LinearLayout containerLayout = new LinearLayout(getActivity());
        containerLayout.setOrientation(LinearLayout.VERTICAL);

        // Iterate through each line and create TextViews for question-answer pairs
        for (String line : lines) {
            // Split each line into question and answer
            String[] pair = line.split("=|:");

            // Inflate custom layout for each question-answer pair
            View dialogView = getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout_for_company, null);

            // Get references to TextViews in custom layout
            TextView questionTextView = dialogView.findViewById(R.id.questionTextView);
            TextView answerTextView = dialogView.findViewById(R.id.answerTextView);

            // Set text for question and answer TextViews
            questionTextView.setText(pair[0]);
            answerTextView.setText("Answer: " + pair[1]);

            // Add custom layout for each question-answer pair to the container layout
            containerLayout.addView(dialogView);
        }

        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Question With Answers Submitted by Candidate:");

        // Set the container layout as the view for the AlertDialog
        builder.setView(containerLayout);

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateTestStatus(int position) {
        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        try {
            String q = "UPDATE tbl_Jobs_applied set test_assigned='Yes' where _id_pk='" + position + "'";
            db.execSQL(q);


            String company_email = "";
            String company_name = "";
            String job_title = "";
            String job_description = "";
            String company_type = "";
            String company_location = "";
            String sallery_range = "";
            String application_deadline = "";
            String company_profile_pic = "";
            String test = "";
            String job_status = "";
            String user_applied = "";
            String hired = "";
            String user_resume = "";
            String student_full_name = "";
            String student_profile_pic = "";
            String job_id = "";
            String test_assigned = "";
            String test_result = "";
            String short_listed = "";


            String query = "select distinct * from tbl_Jobs_applied WHERE _id_pk='" + position + "'  ";

            System.out.println("query= " + query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= " + counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    company_email = cur.getString(cur.getColumnIndexOrThrow("company_email"));
                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                    job_description = cur.getString(cur.getColumnIndexOrThrow("job_description"));
                    company_type = cur.getString(cur.getColumnIndexOrThrow("company_type"));
                    company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                    sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                    application_deadline = cur.getString(cur.getColumnIndexOrThrow("application_deadline"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                    test = cur.getString(cur.getColumnIndexOrThrow("test"));
                    job_status = cur.getString(cur.getColumnIndexOrThrow("job_status"));
                    user_applied = cur.getString(cur.getColumnIndexOrThrow("user_applied"));
                    hired = cur.getString(cur.getColumnIndexOrThrow("hired"));
                    user_resume = cur.getString(cur.getColumnIndexOrThrow("user_resume"));
                    student_full_name = cur.getString(cur.getColumnIndexOrThrow("student_full_name"));
                    student_profile_pic = cur.getString(cur.getColumnIndexOrThrow("student_profile_pic"));
                    job_id = cur.getString(cur.getColumnIndexOrThrow("job_id"));
                    test_assigned = cur.getString(cur.getColumnIndexOrThrow("test_assigned"));
                    test_result = cur.getString(cur.getColumnIndexOrThrow("test_result"));
                    short_listed = cur.getString(cur.getColumnIndexOrThrow("short_listed"));


                }
                cur.close();
                db.close();

                NotificationGetterSetter.setCompany_email(company_email);
                NotificationGetterSetter.setCompany_name(company_name);
                NotificationGetterSetter.setJob_title(job_title);
                NotificationGetterSetter.setJob_description(job_description);
                NotificationGetterSetter.setCompany_type(company_type);
                NotificationGetterSetter.setCompany_location(company_location);
                NotificationGetterSetter.setSallery_range(sallery_range);
                NotificationGetterSetter.setApplication_deadline(application_deadline);
                NotificationGetterSetter.setCompany_profile_pic(company_profile_pic);
                NotificationGetterSetter.setTest(test);
                NotificationGetterSetter.setJob_status(job_status);
                NotificationGetterSetter.setUser_applied(user_applied);
                NotificationGetterSetter.setHired(hired);
                NotificationGetterSetter.setUser_resume(user_resume);
                NotificationGetterSetter.setStudent_full_name(student_full_name);
                NotificationGetterSetter.setStudent_profile_pic(student_profile_pic);
                NotificationGetterSetter.setJob_id(job_id);
                NotificationGetterSetter.setTest_assigned(test_assigned);
                NotificationGetterSetter.setTest_result(test_result);
                NotificationGetterSetter.setShort_listed(short_listed);
                NotificationGetterSetter.setNotification_Status("new");
                NotificationGetterSetter.setNotification_Status("new");
                NotificationGetterSetter.setJoining_datee("");

                String notification_test = company_name+" have Assigned you a test for this Job " +job_title;
                NotificationGetterSetter.setNotification_text(notification_test);

                Date currentDateAndTime = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = dateFormat.format(currentDateAndTime);
                System.out.println("Current Date and Time: " + dateString);

                NotificationGetterSetter.setCurrent_date_time(dateString);

                long id = Querries.insertIntoNotification(getActivity());
                if (id > 0) {
                    SharedPreferences settings_profile = getActivity().getSharedPreferences("MyPrefsProfile", 0); // 0 - for private mode
                    String emailcompany = settings_profile.getString("emailcompany", "");
                    loadFeaturedJobsFromDatabase(emailcompany);

                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(getActivity(), "Database Connection Problem.!!!");//
                }

            }
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                db.close();
            }

        }


    public void buttonShareFile(String user_resume){

        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator +getActivity().getString(R.string.db_folder_resume) + File.separator+ user_resume);

        if (!file.exists()) {
            Toast.makeText(getActivity(), "File doesn't exist", Toast.LENGTH_LONG).show();
            return;
        }

//        Uri fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
        Uri fileUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("application/pdf");
        intentShare.putExtra(Intent.EXTRA_STREAM, fileUri);
        intentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intentShare, "Share the file ..."));

    }
}