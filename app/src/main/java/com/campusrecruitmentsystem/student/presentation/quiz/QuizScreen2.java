package com.campusrecruitmentsystem.student.presentation.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.LoginScreen;
import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.File;

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
    private CircleImageView img_quiz;
   private TextView  tv_Job_title_quiz,tv_company_name_quiz,tv_salery_range_quiz,tv_job_location_quiz,tv_job_description_quiz;
   private  Button btnGetStartedQuiz;
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

         tv_Job_title_quiz.setText(job_title);
        tv_company_name_quiz.setText(company_name);
        tv_salery_range_quiz.setText(sallery_range);
        tv_job_location_quiz.setText(company_location);
        tv_job_description_quiz.setText(job_description);
        showImage(company_profile_pic);
        System.out.println("test1111222= "+test);
//        Toast.makeText(context,"test=" +test,Toast.LENGTH_SHORT).show();


    }

    private void findViews() {
        img_quiz= findViewById(R.id.img_quiz);
        tv_Job_title_quiz= findViewById(R.id.tv_Job_title_quiz);
        tv_company_name_quiz= findViewById(R.id.tv_company_name_quiz);
        tv_salery_range_quiz= findViewById(R.id.tv_salery_range_quiz);
        tv_job_location_quiz= findViewById(R.id.tv_job_location_quiz);
        tv_job_description_quiz= findViewById(R.id.tv_job_description_quiz);
        btnGetStartedQuiz= findViewById(R.id.btnGetStartedQuiz);
    }

    public void clickFunction(View view) {
//        Intent intent = new Intent(QuizScreen2.this,SubmitQuiz.class);
//        intent.putExtra("test",test);
//        startActivity(intent);

        ViewDialog alert = new ViewDialog();
        alert.showDialog(QuizScreen2.this,"Applied to this Job Please Move back to View More Jobs");
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