package com.campusrecruitmentsystem.student.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;


import com.campusrecruitmentsystem.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseSQlite {
    private String folderName;
    private static String folder;
    private String strInputDBFileName;
    private String strInputDBFileName1;
    private String strInputDBFileName2;
    private String ac_name;
    private String dbName = "/uu.db";
    private String dbName1 = "/TrainingManual.pdf";
    private String dbName2 = "/noc_manual.pdf";
    private Context context;

    public DataBaseSQlite(String folderName, Context context) {
        this.strInputDBFileName = "/" + folderName + dbName;
        this.strInputDBFileName1 = "/" + folderName + dbName1;
        this.strInputDBFileName2 = "/" + folderName + dbName2;
        DataBaseSQlite.folder = folderName;
        this.folderName = "/" + folderName;
        this.context = context;
        dataBase();
    }

    public static SQLiteDatabase connectToDb(Context context) {
        String folderName = context.getString(R.string.db_name);

//        String dbPath = Environment.getExternalStorageDirectory() + "/" + folder + "/uu.db";//rwp_citysurvey_dp/uu.db";
        String dbPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + folder + "/uu.db";

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        return db;
    }

    public boolean dataBase() {
        boolean b = false;
            copyFromAssetsToSDCard("uu.db");

            ReadWriteDatabase();
            b = true;
        return b;
    }//end of dataBase

    private void copyFromAssetsToSDCard(String strDBFile) {
        // TODO Auto-generated method stub
        try {
            //Open your local db as the input stream
            InputStream myInput = context.getAssets().open(strDBFile);

            //Open the empty db as the output stream
            String fPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + folderName;

//            String fPath = Environment.getExternalStorageDirectory() + folderName;
            File f = new File(fPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            String dbFilePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + strInputDBFileName;

//            String dbFilePath = Environment.getExternalStorageDirectory() + strInputDBFileName;
            File dbFile = new File(dbFilePath);
            OutputStream myOutput = new FileOutputStream(dbFile);
            //transfer bytes from the input-file to the output-file
            byte[] buffer = new byte[2048];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Toast.makeText(context, "Error Copying Database file = " + ex.getMessage() + "", Toast.LENGTH_LONG).show();
        }
    }//end of copyFromAssetsToSDCard

    private String ReadWriteDatabase() {
        // TODO Auto-generated method stub
        try {


        } catch (Exception ex) {
            Toast.makeText(context, "Error Reading Database file = " + ex.getMessage() + "", Toast.LENGTH_LONG).show();
        }//end of try-catch
        return ac_name;
    }//end of ReadWriteDatabase

}