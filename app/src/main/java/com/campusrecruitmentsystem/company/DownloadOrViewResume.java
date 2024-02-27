package com.campusrecruitmentsystem.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.campusrecruitmentsystem.R;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;

public class DownloadOrViewResume extends AppCompatActivity {

    private ImageView imageView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_or_view_resume);
        context = this;
         imageView = findViewById(R.id.imageView);
     String  user_resume = getIntent().getStringExtra("user_resume");
        openPDF(user_resume);
    }

    private void openPDF(String user_resume){

        // Path to the PDF file
        // Path to the PDF file
        File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator +context.getString(R.string.db_folder_resume) + File.separator+ user_resume);


        try {
            // Load the PDF document
            PDDocument document = PDDocument.load(pdfFile);

            // Create a PDFRenderer object
            PDFRenderer pdfRenderer = new PDFRenderer(document);


            Bitmap bitmap = pdfRenderer.renderImage(0);
            imageView.setImageBitmap(bitmap);

            // Close the document
            document.close();


            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}