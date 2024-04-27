package com.campusrecruitmentsystem.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.PostJob;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.presentation.ProfileActivity;

public class UpdateProfile_Student extends AppCompatActivity {

    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";

    EditText et_userName,et_login_password,et_profession;
    AppCompatButton btnUpdate;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_student);

        context=this;
        et_userName=findViewById(R.id.et_userName);
        et_login_password=findViewById(R.id.et_login_password);
        et_login_password=findViewById(R.id.et_login_password);
        et_profession=findViewById(R.id.et_profession);
        btnUpdate=findViewById(R.id.btnUpdate);
        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailStudent = settings_profile.getString("emailstudent", ""); // Provide default value if the key is not found

       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String username= et_userName.getText().toString();
               String password= et_login_password.getText().toString();
               String profession= et_profession.getText().toString();

           if (!username.equalsIgnoreCase("")){

                   if (!password.equalsIgnoreCase("")){

                       if (!profession.equalsIgnoreCase("")){
                           UpdateProfile(username,password,profession,emailStudent);

                       }else {
                           ViewDialog alert = new ViewDialog();
                           alert.showDialog(UpdateProfile_Student.this, "Please Enter Profession.!!!");//
                       }
                   }else {
                       ViewDialog alert = new ViewDialog();
                       alert.showDialog(UpdateProfile_Student.this, "Please Enter Username.!!!");//
                   }
               }else {
                   ViewDialog alert = new ViewDialog();
                   alert.showDialog(UpdateProfile_Student.this, "Please Enter Username.!!!");//
               }


           }
       });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateProfile_Student.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    private void UpdateProfile(String username,String password,String profession,String emailStudent) {
       try {
           SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

           String q = "UPDATE tbl_signup set full_name='"+username+"' where email='" + emailStudent + "'";
           db.execSQL(q);

           String qq = "UPDATE tbl_signup set password='"+password+"' where email='" + emailStudent + "'";
           db.execSQL(qq);

           String qqq = "UPDATE tbl_signup set profession='"+profession+"' where email='" + emailStudent + "'";
           db.execSQL(qqq);
           ViewDialog alert = new ViewDialog();
           alert.showDialog(UpdateProfile_Student.this, "Your Status Updated Successfully!!!");//


       }catch (Exception e){

           Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
       }



    }
}