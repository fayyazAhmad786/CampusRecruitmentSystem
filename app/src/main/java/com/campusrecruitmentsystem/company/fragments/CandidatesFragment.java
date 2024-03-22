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
import com.campusrecruitmentsystem.company.modules.CandidateFragment.AdapterAppliedJob;
import com.campusrecruitmentsystem.company.modules.JobFragment.AppliedJobs;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.File;
import java.util.ArrayList;
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


            AdapterAppliedJob =new AdapterAppliedJob(getActivity());
            String query = "select distinct student_full_name,job_title,company_location,sallery_range,student_profile_pic from tbl_Jobs_applied WHERE user_applied !='no' AND company_email ='"+emailcompany+"'   ";

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

                    AppliedJobs job = new AppliedJobs(student_full_name, job_title, company_location,sallery_range, student_profile_pic);
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

                                }else if (value.equalsIgnoreCase("resume")){
                                    // Perform action with clicked job
                                    Toast.makeText(getActivity(),"Item Clicker"+position,Toast.LENGTH_SHORT).show();


                                    getItemClickedData(position+1);
                                }else if (value.equalsIgnoreCase("viewresult")){

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