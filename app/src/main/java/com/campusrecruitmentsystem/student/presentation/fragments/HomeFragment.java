package com.campusrecruitmentsystem.student.presentation.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.HomeActivity;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.DashboardStudent;
import com.campusrecruitmentsystem.student.modules.FeaturedJobs;
import com.campusrecruitmentsystem.student.modules.Job;
import com.campusrecruitmentsystem.student.modules.JobAdapter;
import com.campusrecruitmentsystem.student.modules.JobAdapterFeaturedJob;
import com.campusrecruitmentsystem.student.modules.MyLinearLayoutManager;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen1;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen2;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewFeaturedJobs;
    private JobAdapter jobAdapter;
    private JobAdapterFeaturedJob JobAdapterFeaturedjob;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // In your activity or fragment


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        return inflater.inflate(R.layout.fragment_home, container, false);


        View view = inflater.inflate(R.layout.fragment_home, container, false);
// In your activity or fragment
        ImageView imageProfile= view.findViewById(R.id.imageProfile);
        recyclerView= view.findViewById(R.id.recycler_jobs);
        recyclerViewFeaturedJobs= view.findViewById(R.id.recyclerViewFeaturedJobs);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        loadJobsFromDatabase();
        loadFeaturedJobsFromDatabase();

        return view;
    }

    private void loadJobsFromDatabase() {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<Job> jobList = new ArrayList<>();
            String company_location= "";
            String job_title= "";
            String company_name= "";
            String company_profile_pic= "";
            String sallery_range= "";

            jobAdapter =new  JobAdapter(getActivity());
            String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic from tbl_Jobs WHERE job_status='active'  ";
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
                    Job job = new Job(company_location, job_title, company_name,sallery_range, company_profile_pic);
                    jobList.add(job);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {
                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    jobAdapter.setJobList(jobList);
                    jobAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(jobAdapter);
                    // Set item click listener after setting adapter
                    jobAdapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Job job) {
                            // Handle item click here
                            // Example: Get the clicked job and show its details
                            if (job != null) {
                                // Perform action with clicked job
                                Toast.makeText(getActivity(),"Item Clicker"+position,Toast.LENGTH_SHORT).show();

                                getItemClickedData(position+1);
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
            String company_location= "";
            String job_title= "";
            String company_name= "";
            String company_profile_pic= "";
            String sallery_range= "";
            String job_description= "";
            String test= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());

        String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic,job_description,test from tbl_Jobs WHERE job_status='active' AND _id_pk = '"+position+"'  ";
        System.out.println("query= " + query);


        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {

                company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                job_description = cur.getString(cur.getColumnIndexOrThrow("job_description"));
                test = cur.getString(cur.getColumnIndexOrThrow("test"));

            }
            cur.close();
            db.close();

            Intent intent = new Intent(getActivity(), QuizScreen2.class);
            intent.putExtra("company_location",company_location);
            intent.putExtra("job_title",job_title);
            intent.putExtra("company_name",company_name);
            intent.putExtra("company_profile_pic",company_profile_pic);
            intent.putExtra("sallery_range",sallery_range);
            intent.putExtra("job_description",job_description);
            intent.putExtra("test",test);
            startActivity(intent);
        }
    }

    private void loadFeaturedJobsFromDatabase() {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<FeaturedJobs> jobList = new ArrayList<>();
            String company_location= "";
            String job_title= "";
            String company_name= "";
            String company_profile_pic= "";
            String sallery_range= "";

            JobAdapterFeaturedjob =new  JobAdapterFeaturedJob(getActivity());
//            String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic from tbl_Jobs WHERE job_status='active' order by sallery_range ASC   ";
            String query = "SELECT distinct company_location,job_title,company_name,sallery_range,company_profile_pic FROM tbl_Jobs WHERE job_status='active' ORDER BY CASE " +
                    "WHEN sallery_range LIKE '%-50k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
                    "WHEN sallery_range LIKE '%-100k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
                    "WHEN sallery_range LIKE '%-150k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
                    "WHEN sallery_range LIKE '%-200k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
                    "WHEN sallery_range LIKE '%-plus' THEN 250000 " + // Assuming "250k-plus" is the highest range
                    "ELSE 0 END DESC";
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
                    FeaturedJobs job = new FeaturedJobs(company_location, job_title, company_name,sallery_range, company_profile_pic);
                    jobList.add(job);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {

                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    JobAdapterFeaturedjob.setJobList(jobList);
                    JobAdapterFeaturedjob.notifyDataSetChanged();

                    recyclerViewFeaturedJobs.setAdapter(JobAdapterFeaturedjob);
//                    recyclerViewFeaturedJobs.setLayoutManager(new MyLinearLayoutManager(getActivity(),1,false));

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



}