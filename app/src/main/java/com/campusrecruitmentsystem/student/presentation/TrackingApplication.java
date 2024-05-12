package com.campusrecruitmentsystem.student.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusrecruitmentsystem.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrackingApplication extends AppCompatActivity {


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
    String hired= "";
    String test_assigned= "";
    String test_result= "";
    String short_listed= "";
    String joining_date= "";
    CircleImageView img_company_track;
    TextView txt_track_job_title,txt_track_sallery,txt_track_company_name,txt_track_company_location;
    ImageView imageTicket1,imageCheckmark3,imageCheckmark4,imageCheckmark5,imageArrowleft;
    View line1,line2,viewCheckmark2,line3,line4;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_application);
        context = this;
        FindViews();
        company_location = getIntent().getStringExtra("company_location");
        job_title = getIntent().getStringExtra("job_title");
        company_name = getIntent().getStringExtra("company_name");
        sallery_range = getIntent().getStringExtra("sallery_range");
        company_profile_pic = getIntent().getStringExtra("company_profile_pic");
        test = getIntent().getStringExtra("test");
        company_email = getIntent().getStringExtra("company_email");
        company_type = getIntent().getStringExtra("company_type");
        application_deadline = getIntent().getStringExtra("application_deadline");
        job_id = getIntent().getStringExtra("job_id");
        test_assigned = getIntent().getStringExtra("test_assigned");
        test_result = getIntent().getStringExtra("test_result");
        short_listed = getIntent().getStringExtra("short_listed");
        joining_date = getIntent().getStringExtra("joining_date");
        hired = getIntent().getStringExtra("hired");


        showImage(company_profile_pic);
        txt_track_job_title.setText(job_title);
        txt_track_sallery.setText(sallery_range);
        txt_track_company_name.setText(company_name);
        txt_track_company_location.setText(company_location);

        if (!test_assigned.equalsIgnoreCase("no")) {
            imageTicket1.setImageResource(R.drawable.img_checkmark_indigo_600); // Replace R.drawable.img_checkmark_indigo_600 with the drawable you want to use
            if (!test_result.equalsIgnoreCase("no")) {
                line1.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use
                viewCheckmark2.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use
                System.out.println("short_listed22="+short_listed);
                if (!short_listed.equalsIgnoreCase("no")){
                        line2.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use
                        imageCheckmark3.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use

                        if (!hired.equalsIgnoreCase("no")){
                            line4.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use

                            imageCheckmark5.setBackgroundColor(getResources().getColor(R.color.indigo_600)); // Replace R.color.gray_400 with the color you want to use

                        }else {
                            line4.setBackgroundColor(getResources().getColor(R.color.gray_400)); // Replace R.color.gray_400 with the color you want to use
                            imageCheckmark5.setBackgroundColor(getResources().getColor(R.color.gray_400)); // Replace R.color.gray_400 with the color you want to use

                        }


                    }else {
                        line2.setBackgroundColor(getResources().getColor(R.color.gray_400)); // Replace R.color.gray_400 with the color you want to use
                        imageCheckmark3.setBackgroundColor(getResources().getColor(R.color.gray_400)); // Replace R.color.gray_400 with the color you want to use

                    }


            } else {
                line1.setBackgroundColor(getResources().getColor(R.color.gray_400)); // Replace R.color.gray_400 with the color you want to use
            }
        } else {

        }


    }

    private void FindViews() {



        img_company_track= findViewById(R.id.img_company_track);
        txt_track_job_title= findViewById(R.id.txt_track_job_title);
        txt_track_sallery= findViewById(R.id.txt_track_sallery);
        txt_track_company_name= findViewById(R.id.txt_track_company_name);
        txt_track_company_location= findViewById(R.id.txt_track_company_location);
        imageArrowleft= findViewById(R.id.imageArrowleft);

        imageTicket1= findViewById(R.id.imageTicket1);
        viewCheckmark2= findViewById(R.id.viewCheckmark2);
        imageCheckmark3= findViewById(R.id.imageCheckmark3);
//        imageCheckmark4= findViewById(R.id.imageCheckmark4);
        imageCheckmark5= findViewById(R.id.imageCheckmark5);


        line1= findViewById(R.id.line1);
        line2= findViewById(R.id.line2);
//        line3= findViewById(R.id.line3);
        line4= findViewById(R.id.line4);
        imageArrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showImage(String imageName){
        try {
            Bitmap b = null;
            String imageFile = "";
            String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/";
            imageFile = strFolder + imageName;
            System.out.println("imageFile1= "+imageFile);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;
//            if (!UploaderGui.path[id].equalsIgnoreCase(""))
            b = BitmapFactory.decodeFile(imageFile, options);
//            b = BitmapFactory.decodeFile(imageFile);
            if (b != null) {
                Bitmap b1 = Bitmap.createScaledBitmap(b, 60, 60, false);
                img_company_track.setImageBitmap(b1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}