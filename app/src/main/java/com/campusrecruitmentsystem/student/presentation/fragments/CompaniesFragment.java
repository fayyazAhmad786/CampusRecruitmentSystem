package com.campusrecruitmentsystem.student.presentation.fragments;

import android.content.Intent;
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

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.Companies;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.CompaniesAdapter;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.CompaniesAdapterFeaturedCompanies;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.FeaturedCompanies;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompaniesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CompaniesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewFeaturedCompanies;



    private CompaniesAdapter CompaniesAdapter;
    private CompaniesAdapterFeaturedCompanies companiesAdapterFeaturedCompanies;
    private  String Filters;
    private String mParam1;
    private String mParam2;

    public CompaniesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompaniesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompaniesFragment newInstance(String param1, String param2) {
        CompaniesFragment fragment = new CompaniesFragment();
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
//        return inflater.inflate(R.layout.fragment_companies, container, false);

        View view = inflater.inflate(R.layout.fragment_companies, container, false);
        ImageView imageProfile= view.findViewById(R.id.imageProfile);
        recyclerView= view.findViewById(R.id.recycler_companies);
        recyclerViewFeaturedCompanies= view.findViewById(R.id.recyclerViewFeaturedCompanies);


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
            List<Companies> companiesList = new ArrayList<>();

            String company_name= "";
            String company_email= "";
            String company_profile_pic= "";
            String _id_pk= "";

            CompaniesAdapter =new CompaniesAdapter(getActivity());
            String query = "select distinct email,company_name,profile_pic,_id_pk from tbl_signup WHERE login_type='company'  ";
            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    company_email = cur.getString(cur.getColumnIndexOrThrow("email"));
                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));
                    _id_pk = cur.getString(cur.getColumnIndexOrThrow("_id_pk"));
                    Companies job = new Companies(company_name, company_profile_pic,company_email,_id_pk);
                    companiesList.add(job);
                }
                cur.close();
                db.close();
                if (!companiesList.isEmpty()) {
                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    CompaniesAdapter.setJobList(companiesList);
                    CompaniesAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(CompaniesAdapter);
                    // Set item click listener after setting adapter
                    CompaniesAdapter.setOnItemClickListener(new CompaniesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Companies companies) {
                            // Handle item click here
                            // Example: Get the clicked job and show its details
                            if (companies != null) {
                                // Perform action with clicked job
                                Toast.makeText(getActivity(),"Item Clicker"+position,Toast.LENGTH_SHORT).show();

//                                getItemClickedData(position+1);
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

    private void loadFeaturedJobsFromDatabase() {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<FeaturedCompanies> jobList = new ArrayList<>();
            String company_location= "";
            String company_name= "";
            String company_profile_pic= "";
            String _id_pk= "";
            String company_email= "";

            companiesAdapterFeaturedCompanies =new  CompaniesAdapterFeaturedCompanies(getActivity());
//            String query = "SELECT distinct company_email, company_location,job_title,company_name,sallery_range,company_profile_pic,_id_pk FROM tbl_Jobs WHERE job_status='active' ORDER BY CASE " +
//                    "WHEN sallery_range LIKE '%-50k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
//                    "WHEN sallery_range LIKE '%-100k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
//                    "WHEN sallery_range LIKE '%-150k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
//                    "WHEN sallery_range LIKE '%-200k' THEN CAST(SUBSTR(sallery_range, 1, INSTR(sallery_range, '-') - 1) AS INTEGER) " +
//                    "WHEN sallery_range LIKE '%-plus' THEN 250000 " + // Assuming "250k-plus" is the highest range
//                    "ELSE 0 END DESC";
            String query = "select distinct email,company_name,profile_pic,_id_pk,location from tbl_signup WHERE login_type='company' ";
            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {
                    company_email = cur.getString(cur.getColumnIndexOrThrow("email"));
                    company_location = cur.getString(cur.getColumnIndexOrThrow("location"));
                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));
                    _id_pk = cur.getString(cur.getColumnIndexOrThrow("_id_pk"));

                    FeaturedCompanies job = new FeaturedCompanies(company_email,company_location, company_name, company_profile_pic,_id_pk);
                    jobList.add(job);
                }
                cur.close();
                db.close();
                if (!jobList.isEmpty()) {

                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    companiesAdapterFeaturedCompanies.setJobList(jobList);
                    companiesAdapterFeaturedCompanies.notifyDataSetChanged();

                    recyclerViewFeaturedCompanies.setAdapter(companiesAdapterFeaturedCompanies);
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