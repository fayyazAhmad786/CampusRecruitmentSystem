package com.campusrecruitmentsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.AppliedJobGetterSetter;
import com.campusrecruitmentsystem.GetterSetter.NotificationGetterSetter;
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
            values.put("test_assigned", "no");
            values.put("job_id", PostJobGetterSetter.getJob_id());

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
            values.put("job_id", AppliedJobGetterSetter.getJob_id());
            values.put("test_assigned", "no");
            values.put("test_result", "no");
            values.put("short_listed", "no");

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

    public static long insertIntoNotification(Context context) {
        long _id_pk = 0;
        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
        try {
            ContentValues values = new ContentValues();


            values.put("test", NotificationGetterSetter.getTest());
            values.put("company_email", NotificationGetterSetter.getCompany_email());
            values.put("company_name", NotificationGetterSetter.getCompany_name());
            values.put("job_title", NotificationGetterSetter.getJob_title());
            values.put("job_description", NotificationGetterSetter.getJob_description());
            values.put("company_type", NotificationGetterSetter.getCompany_type());
            values.put("company_location", NotificationGetterSetter.getCompany_location());
            values.put("sallery_range", NotificationGetterSetter.getSallery_range());
            values.put("application_deadline", NotificationGetterSetter.getApplication_deadline());
            values.put("company_profile_pic", NotificationGetterSetter.getCompany_profile_pic());
            values.put("job_status", NotificationGetterSetter.getJob_status());
            values.put("user_applied", NotificationGetterSetter.getUser_applied());
            values.put("hired", NotificationGetterSetter.getHired());
            values.put("user_resume", NotificationGetterSetter.getUser_resume());
            values.put("student_full_name", NotificationGetterSetter.getStudent_full_name());
            values.put("student_profile_pic", AppliedJobGetterSetter.getStudentProfilePic());
            values.put("job_id", NotificationGetterSetter.getJob_id());
            values.put("test_assigned", NotificationGetterSetter.getTest_assigned());
            values.put("test_result", NotificationGetterSetter.getTest_result());
            values.put("short_listed", NotificationGetterSetter.getShort_listed());
            values.put("notification_text", NotificationGetterSetter.getNotification_text());
            values.put("notification_assigned", "yes");
            values.put("notification_status",  NotificationGetterSetter.getNotification_Status());
            values.put("current_date_time", NotificationGetterSetter.getCurrent_date_time());
            values.put("joining_date", NotificationGetterSetter.getJoining_datee());

            try {
                _id_pk = db.insertWithOnConflict(Constants.TABLE_NOTIFICATION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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