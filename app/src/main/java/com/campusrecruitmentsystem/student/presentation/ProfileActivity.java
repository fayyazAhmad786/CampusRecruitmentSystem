package com.campusrecruitmentsystem.student.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.student.presentation.quiz.QuizScreen1;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtMakearesume,txtuserName,txtProfessions;
    private ImageView imagelogout,imageArrowleft,imageProfile;
    private LinearLayout linearColumntwentyseven;
    private Context context;
    private int THUMBNAIL_SIZE = 60;

    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_one);
        context= this;
        findViews();

        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailStudent = settings_profile.getString("emailstudent", ""); // Provide default value if the key is not found
        findUserName(emailStudent);
//        Toast.makeText(context,"Email= "+emailStudent,Toast.LENGTH_LONG).show();


        txtMakearesume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfileActivity.this,ResumeActivity.class);
                startActivity(intent);
            }
        });  linearColumntwentyseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(ProfileActivity.this,ApplliedJobs.class);
//                startActivity(intent);
//
                Intent intent= new Intent(ProfileActivity.this, QuizScreen1.class);
                startActivity(intent);
            }
        });
        imageArrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });imagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(LoginScreen.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(ProfileActivity.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {


        imagelogout = findViewById(R.id.imagelogout);
        imageArrowleft = findViewById(R.id.imageArrowleft);
        imageProfile = findViewById(R.id.imageProfile);
        txtuserName = findViewById(R.id.txtuserName);
        txtProfessions = findViewById(R.id.txtProfessions);
        txtMakearesume = findViewById(R.id.txtMakearesume);
        linearColumntwentyseven = findViewById(R.id.linearColumntwentyseven);
    }

    private void findUserName(String email) {
        try {



            String user_name = "";
            String profession = "";
            String profile_pic_name = "";
            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            String query = "select distinct full_name,profession,profile_pic from tbl_signup Where email = '"+email+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {

                while (cur.moveToNext()) {

                   user_name = cur.getString(cur.getColumnIndexOrThrow("full_name"));
                    profession = cur.getString(cur.getColumnIndexOrThrow("profession"));
                    profile_pic_name = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));
                }
                cur.close();
                db.close();

                txtuserName.setText(user_name);
                txtProfessions.setText(profession);
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