package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.AppliedJobGetterSetter;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.company.PostJob;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.database.Querries;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizScreen2 extends AppCompatActivity {

    private Context context;
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
    String job_id= "";
    private CircleImageView img_quiz;
   private TextView  tv_Job_title_quiz,tv_company_name_quiz,tv_salery_range_quiz,tv_job_location_quiz,tv_job_description_quiz;
   private  Button btnGetStartedQuiz;
   private ImageView img_doc_uploaded;
    private static final int PICK_PDF_REQUEST = 1;
    public static final String PREFS_NAME_PROFILE = "MyPrefsProfile";
    String fileName= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen2);
        context = this;
        findViews();

         company_location = getIntent().getStringExtra("company_location");
         job_title = getIntent().getStringExtra("job_title");
         company_name = getIntent().getStringExtra("company_name");
         sallery_range = getIntent().getStringExtra("sallery_range");
         job_description = getIntent().getStringExtra("job_description");
        company_profile_pic = getIntent().getStringExtra("company_profile_pic");
         test = getIntent().getStringExtra("test");
        company_email = getIntent().getStringExtra("company_email");
        company_type = getIntent().getStringExtra("company_type");
        application_deadline = getIntent().getStringExtra("application_deadline");
        job_id = getIntent().getStringExtra("job_id");

        AppliedJobGetterSetter.setJobLocation(company_location);
        AppliedJobGetterSetter.setJobTitle(job_title);
        AppliedJobGetterSetter.setCompanyname(company_name);
        AppliedJobGetterSetter.setJobSallery(sallery_range);
        AppliedJobGetterSetter.setJobDescription(job_description);
        AppliedJobGetterSetter.setProfile_pic_company(company_profile_pic);
        AppliedJobGetterSetter.setJobTest(test);
        AppliedJobGetterSetter.setCompanyEmail(company_email);
        AppliedJobGetterSetter.setJobCompanyType(company_type);
        AppliedJobGetterSetter.setJobDeadline(application_deadline);
        AppliedJobGetterSetter.setJob_id(job_id);


         tv_Job_title_quiz.setText(job_title);
        tv_company_name_quiz.setText(company_name);
        tv_salery_range_quiz.setText(sallery_range);
        tv_job_location_quiz.setText(company_location);
        tv_job_description_quiz.setText(job_description);
        showImage(company_profile_pic);
        System.out.println("test1111222= "+test);
//        Toast.makeText(context,"test=" +test,Toast.LENGTH_SHORT).show();


    }

    private void CheckAppliedJobs(String emailStudent,String full_name,String profile_pic,String job_id) {
        String job_titlee= "";
        String company_emaill= "";
        String user_applied= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

//        String query = "select distinct job_title,company_email,user_applied from tbl_Jobs_applied WHERE user_applied='"+emailStudent+"'  ";
        String query = "select distinct job_title,company_email,user_applied from tbl_Jobs_applied WHERE job_id='"+job_id+"' AND user_applied LIKE '"+emailStudent+"'  ";
        System.out.println("query= " + query);


        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {

                company_emaill = cur.getString(cur.getColumnIndexOrThrow("company_email"));
                job_titlee = cur.getString(cur.getColumnIndexOrThrow("job_title"));
                user_applied = cur.getString(cur.getColumnIndexOrThrow("user_applied"));

            }
            cur.close();
            db.close();


            ViewDialog alert = new ViewDialog();
            alert.showDialog(QuizScreen2.this,"You Have Already Applied To This Job 1");
//            if (company_emaill != null && job_titlee != null){
//                AppliedJobGetterSetter.setUserApplied(emailStudent);
//                AppliedJobGetterSetter.setUserResume(fileName);
//                AppliedJobGetterSetter.setStudentFullName(full_name);
//                AppliedJobGetterSetter.setStudentProfilePic(profile_pic);
//                long id = Querries.insertIntoJobApplied(context);
//                if (id > 0) {
//                    ViewDialog alert = new ViewDialog();
//                    alert.showDialog(QuizScreen2.this, "You Have Applied To Job Successfully.!!!");//
//
//                }else {
//                    ViewDialog alert = new ViewDialog();
//                    alert.showDialog(QuizScreen2.this, "Database Connection Problem.!!!");//
//                }
//            }else {
//                ViewDialog alert = new ViewDialog();
//                alert.showDialog(QuizScreen2.this,"You Have Already Applied To This Job");
//            }


        }else {
            AppliedJobGetterSetter.setUserApplied(emailStudent);
            AppliedJobGetterSetter.setUserResume(fileName);
            AppliedJobGetterSetter.setStudentFullName(full_name);
            AppliedJobGetterSetter.setStudentProfilePic(profile_pic);
            AppliedJobGetterSetter.setJob_id(job_id);
            long id = Querries.insertIntoJobApplied(context);
            if (id > 0) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(QuizScreen2.this, "You Have Applied To Job Successfully.!!!");//

            }else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(QuizScreen2.this, "Database Connection Problem.!!!");//
            }
        }
    }


    private void findViews() {
        img_quiz= findViewById(R.id.img_quiz);
        tv_Job_title_quiz= findViewById(R.id.tv_Job_title_quiz);
        tv_company_name_quiz= findViewById(R.id.tv_company_name_quiz);
        tv_salery_range_quiz= findViewById(R.id.tv_salery_range_quiz);
        tv_job_location_quiz= findViewById(R.id.tv_job_location_quiz);
        tv_job_description_quiz= findViewById(R.id.tv_job_description_quiz);
        btnGetStartedQuiz= findViewById(R.id.btnGetStartedQuiz);
        img_doc_uploaded= findViewById(R.id.img_doc_uploaded);
    }
    public void resumeupload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();
            System.out.println("pdfUri= "+ pdfUri);

            // Save the PDF URI to shared preferences
//            savePdfUriToPreferences(pdfUri);
            // Get the file path from the Uri
            savePdfToFolder(pdfUri);
            img_doc_uploaded.setVisibility(View.VISIBLE);

        }
    }
    private  void  savePdfToFolder(Uri pdfUri){
        String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_resume) + "/";
        File folder= new File(strFolder);
        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }
        SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
        String emailStudent = settings_profile.getString("emailstudent", ""); // Provide default value if the key is not found
          String pdfName =  emailStudent+ job_title+ ".pdf";
        File pdfFile = new File(folder, pdfName);

        try {
            // Copy the PDF file to the new location
            copyFile(getApplicationContext(), pdfUri, pdfFile);

             fileName = pdfFile.getName();



        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    private void copyFile(Context context, Uri sourceUri, File destinationFile) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);
        OutputStream outputStream = new FileOutputStream(destinationFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void clickFunction(View view) {
//        Intent intent = new Intent(QuizScreen2.this,SubmitQuiz.class);
//        intent.putExtra("test",test);
//        startActivity(intent);
        if (img_doc_uploaded.getVisibility() == View.VISIBLE){
            SharedPreferences settings_profile = getSharedPreferences(PREFS_NAME_PROFILE, 0); // 0 - for private mode
            String emailStudent = settings_profile.getString("emailstudent", "");
            getStudentData(emailStudent);
        }else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(QuizScreen2.this,"Please Upload Your Resume First");
        }

    }

    private void getStudentData(String emailStudent) {
        String full_name= "";
        String profile_pic= "";

        SQLiteDatabase db = DataBaseSQlite.connectToDb(context);

        String query = "select distinct full_name,profile_pic from tbl_signup WHERE email='"+emailStudent+"'  ";
        System.out.println("query= " + query);


        Cursor cur = db.rawQuery(query, null);
        int counted = cur.getCount();
        System.out.println("counteddd= " + counted);
        if (counted > 0) {
            while (cur.moveToNext()) {


                full_name = cur.getString(cur.getColumnIndexOrThrow("full_name"));
                profile_pic = cur.getString(cur.getColumnIndexOrThrow("profile_pic"));

            }
            cur.close();
            db.close();
            CheckAppliedJobs(emailStudent,full_name,profile_pic,job_id);



        }else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(QuizScreen2.this,"You Have Already Applied To This Job 2");
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
                Bitmap b1 = Bitmap.createScaledBitmap(b, 60, 60, false);
                img_quiz.setImageBitmap(b1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}