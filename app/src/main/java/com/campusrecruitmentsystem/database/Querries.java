package com.campusrecruitmentsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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


            values.put("company_email", PostJobGetterSetter.getCompanyEmail());
            values.put("company_name", PostJobGetterSetter.getCompanyname());
            values.put("job_title", PostJobGetterSetter.getJobTitle());
            values.put("job_description", PostJobGetterSetter.getJobDescription());
            values.put("company_type", PostJobGetterSetter.getJobCompanyType());
            values.put("company_location", PostJobGetterSetter.getJobLocation());
            values.put("sallery_range", PostJobGetterSetter.getJobSallery());
            values.put("application_deadline", PostJobGetterSetter.getJobDeadline());
//            values.put("company_profile_pic", RegisterGetterSetter.getProfession());

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

}