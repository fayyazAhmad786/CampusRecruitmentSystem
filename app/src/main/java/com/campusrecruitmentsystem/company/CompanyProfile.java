package com.campusrecruitmentsystem.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.student.DashboardStudent;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;

import java.io.File;

public class CompanyProfile extends AppCompatActivity {

    private Button btnLogOut;
    private Context context;
    private int THUMBNAIL_SIZE = 60;

    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";
    private TextView tv_no_of_employees,tv_posted_jobs,tv_com_name;
    private ImageView imageProfile,imageArrowleftcomprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        context= this;

        findViews();
        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailcompany", ""); // Provide default value if the key is not found
        findUserName(emailcompany);
        findPostedJobs(emailcompany);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(LoginScreen.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(CompanyProfile.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        imageArrowleftcomprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyProfile.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findPostedJobs(String emailcompany) {
        try {



            String job_title = "";

            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            String query = "select  job_title from tbl_Jobs Where company_email = '"+emailcompany+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {

                while (cur.moveToNext()) {

                    job_title = cur.getString(cur.getColumnIndexOrThrow("job_title"));

                }
                cur.close();
                db.close();
                String s=String.valueOf(counted);//Now it will return "10"

                tv_posted_jobs.setText(s);


            } else {
                Toast.makeText(context,"No Record Found= ",Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(context,"catch= "+e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    private void findViews() {
        btnLogOut =findViewById(R.id.btnLogOut);
        tv_no_of_employees =findViewById(R.id.tv_no_of_employees);
        tv_posted_jobs =findViewById(R.id.tv_posted_jobs);
        tv_com_name =findViewById(R.id.tv_com_name);
        imageProfile =findViewById(R.id.imageProfile);
        imageArrowleftcomprofile =findViewById(R.id.imageArrowleftcomprofile);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompanyProfile.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void findUserName(String email) {
        try {



            String company_name = "";
            String number_of_employee = "";
            String profile_pic_name = "";
            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            String query = "select distinct company_name,number_of_employee,profile_pic from tbl_signup Where email = '"+email+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {

                while (cur.moveToNext()) {

                    company_name = cur.getString(cur.getColumnIndexOrThrow("company_name"));
                    number_of_employee = cur.getString(cur.getColumnIndexOrThrow("number_of_employee"));
                    profile_pic_name = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));
                }
                cur.close();
                db.close();

                tv_com_name.setText(company_name);
                tv_no_of_employees.setText(number_of_employee);
                showImage(profile_pic_name);

            } else {
                Toast.makeText(context,"No Record Found= ",Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(context,"catch= "+e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }
    private void showImage(String imageName){
        try {
            Bitmap b = null;
            String imageFile = "";
            String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/";
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

}