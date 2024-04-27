package com.campusrecruitmentsystem.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.fragments.CandidatesFragment;
import com.campusrecruitmentsystem.company.fragments.InterviewFragment;
import com.campusrecruitmentsystem.company.fragments.JobsFragment;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;
import com.campusrecruitmentsystem.student.presentation.fragments.CatagoriesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.CompaniesFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.HomeFragment;
import com.campusrecruitmentsystem.student.presentation.fragments.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    CircleImageView imageProfile;
    private BottomNavigationView navigationView;
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";
    private Context context;
    private int THUMBNAIL_SIZE = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        context = this;
        findViews();
        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailcompany = settings_profile.getString("emailcompany", ""); // Provide default value if the key is not found
        findUserName(emailcompany);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CompanyProfile.class);
                startActivity(intent);
                finish();
            }
        });
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_job) {
//                    actionBar.setTitle("Home");
                    JobsFragment fragment = new JobsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment, "")
                            .commit();
                    return true;
                } else if (id == R.id.nav_candidates) {
//                    actionBar.setTitle("Categories");
                    CandidatesFragment fragment1 = new CandidatesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment1)
                            .commit();
                    return true;
                } else if (id == R.id.nav_interview) {
//                    actionBar.setTitle("Companies");
                    InterviewFragment fragment2 = new InterviewFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentCompany, fragment2, "")
                            .commit();
                    return true;
                }

                return false;
            }
        });

        JobsFragment fragment = new JobsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentCompany, fragment, "");
        fragmentTransaction.commit();
    }

    private void findViews() {
        imageProfile = findViewById(R.id.imageProfile);
        navigationView = findViewById(R.id.navigationCompany);


    }

    public void PostJob(View view) {
        Intent intent= new Intent(HomeActivity.this, PostJob.class);
        intent.putExtra("jsonArray", "empty");
        startActivity(intent);
    }

    private void findUserName(String email) {
        try {

            String profile_pic_name = "";
            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
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