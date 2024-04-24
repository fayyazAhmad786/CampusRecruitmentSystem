package com.campusrecruitmentsystem.forgetPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

public class ForgotPassword extends AppCompatActivity {

    private Context context;
    private TextView tv_your_password;
    private AppCompatButton btnGetPassword;
    private EditText et_forgot_password;
    private LinearLayout linearLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        context = this;
        tv_your_password=findViewById(R.id.tv_your_password);
        btnGetPassword=findViewById(R.id.btnGetPassword);
        et_forgot_password=findViewById(R.id.et_forgot_password);
        linearLogin=findViewById(R.id.linearLogin);
        linearLogin.setVisibility(View.GONE);


        btnGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = et_forgot_password.getText().toString();
                if (!value.equalsIgnoreCase("")){
                    findPassword(value);
                }else {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(ForgotPassword.this,"Please Enter Email to Get Password");
                }
            }
        });

    }
    private void findPassword(String email) {
        try {



            String password = "";

            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            String query = "select password from tbl_signup Where email = '"+email+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {

                while (cur.moveToNext()) {

                    password = cur.getString(cur.getColumnIndexOrThrow("password"));

                }
                cur.close();
                db.close();

                linearLogin.setVisibility(View.VISIBLE);
                tv_your_password.setText(password);


            } else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(ForgotPassword.this,"Email Does't Not Match ");
//                Toast.makeText(context,"Email Does't Not Match ",Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
//            Toast.makeText(context,"Email Does't Not Match "+e.getMessage(),Toast.LENGTH_LONG).show();
            ViewDialog alert = new ViewDialog();
            alert.showDialog(ForgotPassword.this,"Email Does't Not Match ");
        }
    }

}