package com.campusrecruitmentsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.AppliedJobGetterSetter;
import com.campusrecruitmentsystem.GetterSetter.PostJobGetterSetter;
import com.campusrecruitmentsystem.GetterSetter.RegisterGetterSetter;
import com.campusrecruitmentsystem.helperClases.Constants;


public class Querries {

    public static long insertIntoSignUp(Context context) {
        long _id_pk = 0;
        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
        try {
            ContentValues values = new ContentValues();


            values.put("login_type", RegisterGetterSetter.getSignUp_Type());
            values.put("full_name", RegisterGetterSetter.getFirst_name());

            values.put("email", RegisterGetterSetter.getEmail_address());
            values.put("password", RegisterGetterSetter.getPassword());
            values.put("company_name", RegisterGetterSetter.getCompany_name());

            values.put("number_of_employee", RegisterGetterSetter.getNo_of_employees());
            values.put("location", RegisterGetterSetter.getLocation());
            values.put("profession", RegisterGetterSetter.getProfession());
            values.put("profile_pic", RegisterGetterSetter.getStrFileName());

            try {
                _id_pk = db.insertWithOnConflict(Constants.TABLE_SIGNUP, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return _id_pk;
    }
    public static long insertIntoJobPost(Context context) {
        long _id_pk = 0;
        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
        try {
            ContentValues values = new ContentValues();


            values.put("test", PostJobGetterSetter.getQuestions());
            values.put("company_email", PostJobGetterSetter.getCompanyEmail());
            values.put("company_name", PostJobGetterSetter.getCompanyname());
            values.put("job_title", PostJobGetterSetter.getJobTitle());
            values.put("job_description", PostJobGetterSetter.getJobDescription());
            values.put("company_type", PostJobGetterSetter.getJobCompanyType());
            values.put("company_location", PostJobGetterSetter.getJobLocation());
            values.put("sallery_range", PostJobGetterSetter.getJobSallery());
            values.put("application_deadline", PostJobGetterSetter.getJobDeadline());
            values.put("company_profile_pic", PostJobGetterSetter.getProfile_pic_company());
            values.put("job_status", "active");
            values.put("user_applied", "no");
            values.put("hired", "no");

            try {
                _id_pk = db.insertWithOnConflict(Constants.TABLE_POSTJOBS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return _id_pk;
    }
    public static long insertIntoJobApplied(Context context) {
        long _id_pk = 0;
        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
        try {
            ContentValues values = new ContentValues();


            values.put("test", AppliedJobGetterSetter.getQuestions());
            values.put("company_email", AppliedJobGetterSetter.getCompanyEmail());
            values.put("company_name", AppliedJobGetterSetter.getCompanyname());
            values.put("job_title", AppliedJobGetterSetter.getJobTitle());
            values.put("job_description", AppliedJobGetterSetter.getJobDescription());
            values.put("company_type", AppliedJobGetterSetter.getJobCompanyType());
            values.put("company_location", AppliedJobGetterSetter.getJobLocation());
            values.put("sallery_range", AppliedJobGetterSetter.getJobSallery());
            values.put("application_deadline", AppliedJobGetterSetter.getJobDeadline());
            values.put("company_profile_pic", AppliedJobGetterSetter.getProfile_pic_company());
            values.put("job_status", "active");
            values.put("user_applied", AppliedJobGetterSetter.getUserApplied());
            values.put("hired", "no");
            values.put("user_resume", AppliedJobGetterSetter.getUserResume());
            values.put("student_full_name", AppliedJobGetterSetter.getStudentFullName());
            values.put("student_profile_pic", AppliedJobGetterSetter.getStudentProfilePic());

            try {
                _id_pk = db.insertWithOnConflict(Constants.TABLE_APPLIEDJOBS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
        return _id_pk;
    }

}