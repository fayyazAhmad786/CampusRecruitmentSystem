package com.campusrecruitmentsystem.company.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.NotificationGetterSetter;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.modules.Interview.AdapterInterview;
import com.campusrecruitmentsystem.company.modules.Interview.Interview;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.database.Querries;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InterviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recycler_interview;
    private AdapterInterview AdapterInterview;


    public InterviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InterviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterviewFragment newInstance(String param1, String param2) {
        InterviewFragment fragment = new InterviewFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interview, container, false);
        recycler_interview= view.findViewById(R.id.recycler_interview);
        SharedPreferences settings_profile =  getActivity().getSharedPreferences("MyPrefsProfile", 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailcompany", "");
        loadShortListedCandidatesFromDatabase(emailcompany);
        return view;

    }


    private void loadShortListedCandidatesFromDatabase(String emailcompany) {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<Interview> jobList = new ArrayList<>();
            String student_full_name= "";
            String job_title= "";
            String company_location= "";
            String sallery_range= "";
            String student_profile_pic= "";
            String test_assigned= "";
            String short_listed= "";
            String hired= "";
            String user_applied= "";
            String joining_date= "";
            int _id_pk;


            AdapterInterview =new AdapterInterview(getActivity());
            String query = "select distinct student_full_name,job_title,company_location,sallery_range,student_profile_pic,test_assigned,short_listed,hired,_id_pk,user_applied,joining_date from tbl_Jobs_applied WHERE short_listed =='Yes' AND company_email ='"+emailcompany+"'   ";

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
                    hired = cur.getString(cur.getColumnIndexOrThrow("hired"));
                    user_applied = cur.getString(cur.getColumnIndexOrThrow("user_applied"));
                    joining_date = cur.getString(cur.getColumnIndexOrThrow("joining_date"));
                    _id_pk = cur.getInt(cur.getColumnIndexOrThrow("_id_pk"));

                    Interview interview = new Interview(student_full_name, job_title, company_location,sallery_range, student_profile_pic,test_assigned,short_listed,hired,user_applied,joining_date,_id_pk);
                    jobList.add(interview);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {

                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    AdapterInterview.setJobList(jobList);
                    AdapterInterview.notifyDataSetChanged();

                    recycler_interview.setAdapter(AdapterInterview);


                    AdapterInterview.setOnItemClickListener(new AdapterInterview.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Interview job, String value, String joining_date, Integer id_pk) {
                            if (job != null) {
                                if (value.equalsIgnoreCase("reject")){
                                    Toast.makeText(getActivity(),"reject Clicker"+id_pk,Toast.LENGTH_SHORT).show();
                                    updateJobRejectStatus(id_pk);
                                }else if (value.equalsIgnoreCase("accept")){
                                    Toast.makeText(getActivity(),"accept Clicker"+position,Toast.LENGTH_SHORT).show();
                                    updateJobAcceptedStatus(id_pk,joining_date);
                                }else if (value.equalsIgnoreCase("email")){
                                    Toast.makeText(getActivity(),"email Clicker"+position,Toast.LENGTH_SHORT).show();
                                    openGmailApp(joining_date);
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

    private void openGmailApp(String email) {
//         Create an Intent with ACTION_SENDTO action
        try {



        Toast.makeText(getActivity(), "emai=l"+email, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

        intent.putExtra(Intent.EXTRA_SUBJECT, "Interview Letter");
        intent.putExtra(Intent.EXTRA_TEXT, "Please Type Interview Letter Here");

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Select email"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateJobRejectStatus(int position) {
        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        try {
            String q = "UPDATE tbl_Jobs_applied set hired='rejected' where _id_pk='" + position + "'";
            db.execSQL(q);
            System.out.println("query11212121212= " + q);


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
            String joining_datee = "";


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
                    joining_datee = cur.getString(cur.getColumnIndexOrThrow("joining_date"));


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
                NotificationGetterSetter.setNotification_Status("new_non_click");
                NotificationGetterSetter.setJoining_datee(joining_datee);

                String notification_test = "Unfortunately "+company_name+ " have Rejected You for this Post " +job_title;
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
                    loadShortListedCandidatesFromDatabase(emailcompany);

                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(getActivity(), "Database Connection Problem.!!!");//
                }

            }
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();

        } finally{
            db.close();
        }

    }
    private void updateJobAcceptedStatus(int position, String joining_date) {
        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        try {
            String q = "UPDATE tbl_Jobs_applied set hired='accepted' where _id_pk='" + position + "'";
            db.execSQL(q);

            String w = "UPDATE tbl_Jobs_applied set joining_date='"+joining_date+"' where _id_pk='" + position + "'";
            db.execSQL(w);


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
            String joining_datee = "";


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
                    joining_datee = cur.getString(cur.getColumnIndexOrThrow("joining_date"));


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
                NotificationGetterSetter.setHired("accepted");
                NotificationGetterSetter.setUser_resume(user_resume);
                NotificationGetterSetter.setStudent_full_name(student_full_name);
                NotificationGetterSetter.setStudent_profile_pic(student_profile_pic);
                NotificationGetterSetter.setJob_id(job_id);
                NotificationGetterSetter.setTest_assigned(test_assigned);
                NotificationGetterSetter.setTest_result(test_result);
                NotificationGetterSetter.setShort_listed(short_listed);
                NotificationGetterSetter.setNotification_Status("new_non_click");
                NotificationGetterSetter.setJoining_datee(joining_datee);

                String notification_test = "Congatulations "+company_name+ " have Hired You for this Post " +job_title;
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
                    loadShortListedCandidatesFromDatabase(emailcompany);

                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(getActivity(), "Database Connection Problem.!!!");//
                }

            }
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        } finally{
            db.close();
        }

    }


}