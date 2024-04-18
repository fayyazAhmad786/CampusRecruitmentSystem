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
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.Companies;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.companies_module.CompaniesAdapter;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.notification.Notification;
import com.campusrecruitmentsystem.student.presentation.fragment_modules.notification.NotificationAdapter;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen2;
import com.campusrecruitmentsystem.student.presentation.quiz.SubmitQuiz;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recycler_notification;
    private NotificationAdapter NotificationAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recycler_notification = view.findViewById(R.id.recycler_notification);

        loadJobsFromDatabase();

        return view;

    }

    private void loadJobsFromDatabase() {
        try {


            SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());
            List<Notification> notificationList = new ArrayList<>();

            String company_name = "";
            String company_email = "";
            String company_profile_pic = "";
            String notification_text = "";
            String current_date_time = "";
            String job_id = "";
            String notification_status = "";
            String _id_pk = "";

            NotificationAdapter = new NotificationAdapter(getActivity());
            String query = "select company_email,company_name,company_profile_pic,notification_text,_id_pk,current_date_time,job_id,notification_status from tbl_notification   ";
            System.out.println("query= " + query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd33= " + counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    company_email = cur.getString(cur.getColumnIndexOrThrow("company_email"));
                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    company_profile_pic = cur.getString(cur.getColumnIndexOrThrow("company_profile_pic"));
                    notification_text = cur.getString(cur.getColumnIndexOrThrow("notification_text"));
                    notification_status = cur.getString(cur.getColumnIndexOrThrow("notification_status"));
                    current_date_time = cur.getString(cur.getColumnIndexOrThrow("current_date_time"));
                    job_id = cur.getString(cur.getColumnIndexOrThrow("job_id"));
                    _id_pk = cur.getString(cur.getColumnIndexOrThrow("_id_pk"));
                    Notification notification = new Notification(company_email, company_name, company_profile_pic, notification_text, current_date_time, job_id,notification_status, _id_pk);
                    notificationList.add(notification);
                }
                cur.close();
                db.close();
                System.out.println("notificationList00= "+notificationList.size());
                if (!notificationList.isEmpty()) {
                    // Assuming jobAdapter is an instance variable of your Fragment or Activity
                    NotificationAdapter.setJobList(notificationList);
                    NotificationAdapter.notifyDataSetChanged();
                    recycler_notification.setAdapter(NotificationAdapter);
                    // Set item click listener after setting adapter
                    NotificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, Notification companies, String value) {
                            // Handle item click here
                            // Example: Get the clicked job and show its details
                            if (companies != null) {
                                if (value.equalsIgnoreCase("new")){
                                    Toast.makeText(getActivity(), "new" + position, Toast.LENGTH_SHORT).show();
                                getItemClickedData(position+1);
                                    System.out.println("valueNew= " +value);

                                }else if (value.equalsIgnoreCase("new_non_click")){
                                    // Perform action with clicked job
                                    Toast.makeText(getActivity(), "new_non_click" + position, Toast.LENGTH_SHORT).show();
                                    System.out.println("valueNON= " +value);
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
            alert.showDialog(getActivity(), "DataBase Connection Problem" + e.getMessage());
        }
    }

    private void getItemClickedData(int position) {
        String job_id= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());

        String query = "select distinct job_id from tbl_notification WHERE  _id_pk = '"+position+"'  ";
        System.out.println("query= " + query);


        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {
                job_id = cur.getString(cur.getColumnIndexOrThrow("job_id"));
            }
            cur.close();
            db.close();

            getRequiredDataForQuiz(job_id);

        }
    }



    private void getRequiredDataForQuiz(String  job_id) {
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
        String job_idd= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(getActivity());

        String query = "select distinct company_email,company_location,company_type,job_title,company_name,application_deadline,sallery_range,company_profile_pic,job_description,test,job_id from tbl_Jobs WHERE job_id = '"+job_id+"'  ";
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
                job_idd = cur.getString(cur.getColumnIndexOrThrow("job_id"));


            }
            cur.close();
            db.close();

            Intent intent = new Intent(getActivity(), SubmitQuiz.class);
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
            intent.putExtra("job_id",job_idd);
            startActivity(intent);
        }
    }

}