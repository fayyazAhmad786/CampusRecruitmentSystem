package com.campusrecruitmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.campusrecruitmentsystem.company.CreateTest;
import com.campusrecruitmentsystem.company.HomeActivity;
import com.campusrecruitmentsystem.company.PostJob;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;
import com.campusrecruitmentsystem.student.DashboardStudent;

public class LoginScreen extends AppCompatActivity {

    private TextView txtHaventanacco;
    private EditText et_login_email,et_login_password;
    private Button btnLogIn;
    private Context context;
    String Email,Password,login_type;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        context = this;
        findView();

        txtHaventanacco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(intent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String   Email = et_login_email.getText().toString();
              String  Password = et_login_password.getText().toString();
                if (Email.equalsIgnoreCase("")){
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginScreen.this,"Please Enter Email");
                }else if (Password.equalsIgnoreCase("")){
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginScreen.this,"Please Enter Password");
                }else {
                    VerifyUser(Email,Password);
                }

            }
        });


    }
    @Override
    public void onBackPressed() {
//        finish();
    }
    private void findView() {
        et_login_email= findViewById(R.id.et_login_email);
        et_login_password= findViewById(R.id.et_login_password);
        txtHaventanacco=findViewById(R.id.txtHaventanacco);
        btnLogIn=findViewById(R.id.btnLogIn);
    }

    private void VerifyUser(String Email,String Password) {
        try {



            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

            String full_name= "";

            String query = "select distinct email,password,login_type,full_name from tbl_signup WHERE email='"+Email+"' AND password='"+Password+"'  ";
            System.out.println("query= "+query);


            Cursor cur = db.rawQuery(query, null);
            int counted = cur.getCount();
            System.out.println("counteddd= "+counted);
            if (counted > 0) {
                while (cur.moveToNext()) {

                    Email = cur.getString(cur.getColumnIndexOrThrow("email"));
                    Password = cur.getString(cur.getColumnIndexOrThrow("password"));
                    login_type = cur.getString(cur.getColumnIndexOrThrow("login_type"));
                    full_name = cur.getString(cur.getColumnIndexOrThrow("full_name"));
                }
                cur.close();
                db.close();
                if (Email.equalsIgnoreCase(Email) && Password.equalsIgnoreCase(Password)){
                    SharedPreferences settings = getSharedPreferences(LoginScreen.PREFS_NAME, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = settings.edit();

                    SharedPreferences settings_profile = getSharedPreferences(LoginScreen.PREFS_NAME_PROFILE, 0); // 0 - for private mode
                    SharedPreferences.Editor editor_profile = settings_profile.edit();
                    if (login_type.equalsIgnoreCase("student")){
                        Intent intent= new Intent(LoginScreen.this, DashboardStudent.class);
                        startActivity(intent);
                        editor.putBoolean("hasLoggedInStudent", true);
                        editor_profile.putString("emailstudent", Email);
                        editor.commit();
                        editor_profile.commit();
                        finish();
                    }else if (login_type.equalsIgnoreCase("company")){
                        Intent intent= new Intent(LoginScreen.this, HomeActivity.class);
                        startActivity(intent);
                        editor.putBoolean("hasLoggedInCompany", true);
                        editor_profile.putString("emailcompany", Email);
                        editor_profile.putString("fullNamecompany", full_name);
                        editor.commit();
                        editor_profile.commit();
                        finish();

                    }

                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LoginScreen.this,"Email or Password is Incorrect 1");
                }

            } else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(LoginScreen.this,"Email or Password is Incorrect 2");
            }
        } catch (Exception e) {
//            emailAvailable = false;
            ViewDialog alert = new ViewDialog();
            alert.showDialog(LoginScreen.this,"DataBase Connection Problem"+e.getMessage());
        }

    }
}