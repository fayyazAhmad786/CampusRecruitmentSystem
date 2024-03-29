package com.campusrecruitmentsystem.company.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.modules.JobFragment.AdapterPostedJob;
import com.campusrecruitmentsystem.company.modules.JobFragment.PostedJobs;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerAppliedJobs;
    private AdapterPostedJob AdapterAppliedJob;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JobsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobsFragment newInstance(String param1, String param2) {
        JobsFragment fragment = new JobsFragment();
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
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        recyclerAppliedJobs= view.findViewById(R.id.recyclerAppliedJobs);
        SharedPreferences settings_profile =  getActivity().getSharedPreferences("MyPrefsProfile", 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailcompany", "");
        loadFeaturedJobsFromDatabase(emailcompany);
        return view;
    }


    private void loadFeaturedJobsFromDatabase(String emailcompany) {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<PostedJobs> jobList = new ArrayList<>();
            String company_name= "";
            String job_title= "";
            String company_location= "";
            String sallery_range= "";
            String company_profile_pic= "";
            String application_deadline= "";


            AdapterAppliedJob =new AdapterPostedJob(getActivity());
            String query = "select distinct company_name,job_title,company_location,sallery_range,company_profile_pic,application_deadline from tbl_Jobs WHERE company_email ='"+emailcompany+"'   ";

            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                    company_location = cur.getString(cur.getColumnIndexOrThrow("company_location"));
                    sallery_range = cur.getString(cur.getColumnIndexOrThrow("sallery_range"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                    application_deadline = cur.getString(cur.getColumnIndexOrThrow("application_deadline"));

                    PostedJobs job = new PostedJobs(company_name, job_title, company_location,sallery_range, company_profile_pic,application_deadline);
                    jobList.add(job);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {

                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    AdapterAppliedJob.setJobList(jobList);
                    AdapterAppliedJob.notifyDataSetChanged();

                    recyclerAppliedJobs.setAdapter(AdapterAppliedJob);


                    AdapterAppliedJob.setOnItemClickListener(new AdapterPostedJob.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, PostedJobs job) {
                            if (job != null) {
                                // Perform action with clicked job
                                Toast.makeText(getActivity(),"Item Clicker"+position,Toast.LENGTH_SHORT).show();


//                                downloadResume(position+1);
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
    public void downloadResume(int position) {
        Toast.makeText(getActivity(),"Item Clicker"+position,Toast.LENGTH_SHORT).show();

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