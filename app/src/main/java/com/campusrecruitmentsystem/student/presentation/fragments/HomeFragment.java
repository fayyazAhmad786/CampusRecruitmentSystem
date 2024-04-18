package com.campusrecruitmentsystem.student.presentation.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.CustomAdapter;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.FeaturedJobs;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.Job;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.JobAdapter;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.home_module.JobAdapterFeaturedJob;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen2;

import java.io.File;
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
    ArrayList<String> serial = new ArrayList<String>();
    private AutoCompleteTextView et_serial_number;


    private JobAdapter jobAdapter;
    private JobAdapterFeaturedJob JobAdapterFeaturedjob;
    private  String Filters;
    private  ImageView imageProfile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";
    private int THUMBNAIL_SIZE = 60;

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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
         imageProfile= view.findViewById(R.id.imageProfile);
        recyclerView= view.findViewById(R.id.recycler_jobs);
        recyclerViewFeaturedJobs= view.findViewById(R.id.recyclerViewFeaturedJobs);
        et_serial_number= view.findViewById(R.id.et_serial_number);
        ImageButton btnThumbsup= view.findViewById(R.id.btnThumbsup);
        ImageButton btncross= view.findViewById(R.id.btncross);
        SharedPreferences settings_profile = getActivity().getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailstudent", ""); // Provide default value if the key is not found
        findUserName(emailcompany);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });    btnThumbsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });  btncross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_serial_number.setText("");
                loadJobsFromDatabase();
                loadFeaturedJobsFromDatabase();
            }
        });

        loadJobsFromDatabase();
        loadFeaturedJobsFromDatabase();
        Filters ="job_title";
        searchFilters(Filters);

        return view;
    }
    private void findUserName(String email) {
        try {

            String profile_pic_name = "";
            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            String query = "select distinct profile_pic from tbl_signup Where email = '"+email+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {
                while (cur.moveToNext()) {
                    profile_pic_name = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));
                }
                cur.close();
                db.close();

                showImage(profile_pic_name);

            } else {
                Toast.makeText(getActivity(),"No Record Found= ",Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(),"catch= "+e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

    private void showImage(String imageName){
        try {
            Bitmap b = null;
            String imageFile = "";
            String strFolder = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + getActivity().getString(R.string.db_folder_images) + "/";
            imageFile = strFolder + imageName;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;
//            if (!UploaderGui.path[id].equalsIgnoreCase(""))
            b = BitmapFactory.decodeFile(imageFile, options);
//            b = BitmapFactory.decodeFile(imageFile);
            if (b != null) {
                Bitmap b1 = Bitmap.createScaledBitmap(b, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                imageProfile.setImageBitmap(b1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void searchFilters(String filter){

        serial.clear();
        try {
            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            // String query = "select parcel_id from data";
//            String query = "select Existing_serial from sync_data";
            String query = "select "+filter+" from tbl_Jobs";
            System.out.println("query111= "+query);
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {
//                        Toast.makeText(context, "Parcel Number Found", Toast.LENGTH_LONG).show();
//            parcel_verify_dialog = true;
                while (cur.moveToNext()) {
                    //    parcels.add(cur.getString(cur.getColumnIndex("parcel_id")));
                    serial.add(cur.getString(cur.getColumnIndexOrThrow(filter)));
                }
                cur.close();
                db.close();
            }

            CustomAdapter customAdapter = new CustomAdapter(serial, getActivity());

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, parcels);
            et_serial_number.setThreshold(2);
            et_serial_number.setAdapter(customAdapter);
            et_serial_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("VAlueeeeeeee=");
//                    searchSrFillLWB();
                String selectedText=    et_serial_number.getText().toString();
                    loadJobsFromDatabaseFiltered(filter,selectedText);
                    loadFeaturedJobsFromDatabaseFiltered(filter,selectedText);



                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.filters_menu, popup.getMenu());

        // Iterate over each menu item and set custom view
        for (int i = 0; i < popup.getMenu().size(); i++) {
            MenuItem menuItem = popup.getMenu().getItem(i);
            View view = getLayoutInflater().inflate(R.layout.custom_menu_item, null);
            TextView textView = view.findViewById(R.id.textView);
            ImageView checkmark = view.findViewById(R.id.checkmark);

            textView.setText(menuItem.getTitle());
            checkmark.setVisibility(menuItem.isChecked() ? View.VISIBLE : View.GONE);

            menuItem.setActionView(view);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Toggle the checked state of the menu item
                item.setChecked(!item.isChecked());

                // Update the check mark icon visibility
                ImageView checkmark = item.getActionView().findViewById(R.id.checkmark);
                checkmark.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);

                // Handle your menu item click logic here
                int itemId = item.getItemId();
                if (itemId == R.id.menu_location) {
                    // Handle Location filter selection
                    et_serial_number.setText("");
                    et_serial_number.setHint("Search By Location");
                    Toast.makeText(getActivity(),"Search By Location",Toast.LENGTH_SHORT).show();
                    Filters ="company_location";
                    searchFilters(Filters);
                    return true;
                } else if (itemId == R.id.menu_job) {
                    // Handle Job filter selection
                    et_serial_number.setText("");
                    et_serial_number.setHint("Search By Job Title");
                    Toast.makeText(getActivity(),"Search By Job Title",Toast.LENGTH_SHORT).show();
                    Filters ="job_title";
                    searchFilters(Filters);
                    return true;
                } else if (itemId == R.id.menu_company) {
                    // Handle Company filter selection
                    et_serial_number.setText("");
                    et_serial_number.setHint("Search By Company Name");
                    Toast.makeText(getActivity(),"Search By Company Name",Toast.LENGTH_SHORT).show();
                    Filters ="company_name";
                    searchFilters(Filters);
                    return true;
                }

                // Return true to consume the event and prevent the PopupMenu from dismissing
                return true;
            }
        });

        popup.show();
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // If needed, handle actions when the PopupMenu is dismissed
            }
        });
    }





    // Below  is Default Code to Show Jobs
    private void loadJobsFromDatabase() {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<Job> jobList = new ArrayList<>();
            String company_location= "";
            String job_title= "";
            String company_name= "";
            String company_profile_pic= "";
            String sallery_range= "";
            String _id_pk= "";

            jobAdapter =new  JobAdapter(getActivity());
            String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic,_id_pk from tbl_Jobs WHERE job_status='active'  ";
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
                    _id_pk = cur.getString(cur.getColumnIndexOrThrow("_id_pk"));
                    Job job = new Job(company_location, job_title, company_name,sallery_range, company_profile_pic,_id_pk);
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
            String company_email= "";
            String company_type= "";
            String application_deadline= "";
            String job_id= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());

        String query = "select distinct company_email,company_location,company_type,job_title,company_name,application_deadline,sallery_range,company_profile_pic,job_description,test,job_id from tbl_Jobs WHERE job_status='active' AND _id_pk = '"+position+"'  ";
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
                job_id = cur.getString(cur.getColumnIndexOrThrow("job_id"));


            }
            cur.close();
            db.close();

            Intent intent = new Intent(getActivity(), QuizScreen2.class);
            intent.putExtra("company_email",company_email);
            intent.putExtra("company_type",company_type);
            intent.putExtra("application_deadline",application_deadline);

            intent.putExtra("company_location",company_location);
            intent.putExtra("job_title",job_title);
            intent.putExtra("company_name",company_name);
            intent.putExtra("company_profile_pic",company_profile_pic);
            intent.putExtra("sallery_range",sallery_range);
            intent.putExtra("job_description",job_description);
            intent.putExtra("test",test);
            intent.putExtra("job_id",job_id);
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

// Below is Filtered Jobs
private void loadJobsFromDatabaseFiltered(String Filter, String selectedText) {
    try {



        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
        List<Job> jobList = new ArrayList<>();
        String company_location= "";
        String job_title= "";
        String company_name= "";
        String company_profile_pic= "";
        String sallery_range= "";
        String _id_pk= "";

        jobAdapter =new  JobAdapter(getActivity());
        String query = "select distinct company_location,job_title,company_name,sallery_range,company_profile_pic,_id_pk from tbl_Jobs WHERE job_status='active' AND "+Filter+" = '"+selectedText+"'  ";
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
                _id_pk = cur.getString(cur.getColumnIndexOrThrow("_id_pk"));
                Job job = new Job(company_location, job_title, company_name,sallery_range, company_profile_pic,_id_pk);
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

                            String ps = job.get_id_pk();
                            Toast.makeText(getActivity(),"Item Clicker"+ps,Toast.LENGTH_SHORT).show();

                            int pss=Integer.parseInt(ps);
                            //                            downloadResume(position+1);
                            getItemClickedData(pss);
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
    private void loadFeaturedJobsFromDatabaseFiltered(String Filter, String selectedText) {
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
            String query = "SELECT distinct company_location,job_title,company_name,sallery_range,company_profile_pic FROM tbl_Jobs WHERE job_status='active' AND "+Filter+" = '"+selectedText+"' ORDER BY CASE " +
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