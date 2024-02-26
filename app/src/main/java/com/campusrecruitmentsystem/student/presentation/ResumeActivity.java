package com.campusrecruitmentsystem.student.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.campusrecruitmentsystem.R;

import java.io.IOException;

public class ResumeActivity extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPdfFromPreferences();
            }
        });
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

            // Save the PDF URI to shared preferences
            savePdfUriToPreferences(pdfUri);
        }
    }

    private void savePdfUriToPreferences(Uri pdfUri) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("pdfUri", pdfUri.toString());
        editor.apply();

        displayPdfFromPreferences();
    }

    private void displayPdfFromPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String pdfUriString = prefs.getString("pdfUri", null);
        if (pdfUriString != null) {
            Uri pdfUri = Uri.parse(pdfUriString);
            showPdf(pdfUri);
        } else {
            // Handle case where PDF URI is not found in shared preferences
            Toast.makeText(this, "PDF not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 &&
//                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, re-display the PDF
            displayPdfFromPreferences();
//        } else {
//            // Permission denied, show a toast or handle it appropriately
//            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//        }
    }
    private void showPdf(Uri pdfUri) {
        // Check for READ_EXTERNAL_STORAGE permission
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission not granted, request it
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    PERMISSION_REQUEST_CODE);
//        } else {
            // Permission granted, proceed to display the PDF
            // Create an Intent to open the PDF file with ACTION_VIEW
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle if there's no PDF viewer app installed
                e.printStackTrace();
                Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
            }
        }
//    }
}