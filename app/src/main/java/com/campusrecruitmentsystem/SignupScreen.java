package com.campusrecruitmentsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.campusrecruitmentsystem.GetterSetter.RegisterGetterSetter;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.database.Querries;
import com.campusrecruitmentsystem.helperClases.Helper;
import com.campusrecruitmentsystem.helperClases.ViewDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

import me.echodev.resizer.Resizer;

public class SignupScreen extends AppCompatActivity {
    private TextView tvOption1, tvOption2,txtLogin;
    private ImageView imageArrowleft,imageProfile;
    private EditText etusername,etEmail,etPassword,etCompanyName,etnoOfEmployees,etProfession;
    private Spinner spn_location;
    String SignupType = "";
    private Helper helper;
    Context context;
    Boolean empty = false;
    Button btnRegister;

    boolean emailAvailable = false;
    String imageName;
    String _path = "";

    int SELECT_PICTURE = 100;
    Boolean imageok = false;
    int camera1 = 0;
    private String Document_img1="";
    private int THUMBNAIL_SIZE = 60;
    Bitmap bitmapOrg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        findViews();
        context = this;
        helper = new Helper(context);
        tvOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTextViewClick(tvOption1,"student");
            }
        });

        tvOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTextViewClick(tvOption2,"company");
            }
        });
        imageArrowleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingDataFunction();
            }
        });

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera1= 1;

                PickImageFromGalley(SELECT_PICTURE);
            }
        });
    }

    private void findViews() {
        tvOption1 = findViewById(R.id.tvOption1);
        tvOption2 = findViewById(R.id.tvOption2);
        imageArrowleft = findViewById(R.id.imageArrowleft);
        imageProfile = findViewById(R.id.imageProfile);
        txtLogin = findViewById(R.id.txtLogin);

        etusername = findViewById(R.id.etusername);
        etEmail = findViewById(R.id.etEmail);
        etProfession = findViewById(R.id.etProfession);
        etPassword = findViewById(R.id.etPassword);
        etCompanyName = findViewById(R.id.etCompanyName);
        etnoOfEmployees = findViewById(R.id.etnoOfEmployees);
        spn_location = findViewById(R.id.spn_location);
        btnRegister = findViewById(R.id.btnRegister);

    }

    private void handleTextViewClick(TextView textView,String value) {

        // Reset background for all TextViews
        resetBackgroundForAllTextViews();
        textView.setSelected(true);

        // Check which TextView is selected
        if (value.equalsIgnoreCase("student")) {
            // Option 1 is selected
            // Handle accordingly
            SignupType = "student";
            etCompanyName.setVisibility(View.GONE);
            etnoOfEmployees.setVisibility(View.GONE);
            etProfession.setVisibility(View.VISIBLE);
            etusername.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etCompanyName.setText("");
            etnoOfEmployees.setText("");
            etProfession.setText("");
            spn_location.setSelection(0);
        } else {
            // Option 2 is selected
            // Handle accordingly
            SignupType = "company";
            etCompanyName.setVisibility(View.VISIBLE);
            etnoOfEmployees.setVisibility(View.VISIBLE);
            etProfession.setVisibility(View.GONE);
            etusername.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etCompanyName.setText("");
            etnoOfEmployees.setText("");
            etProfession.setText("");
            spn_location.setSelection(0);

        }
        // Highlight the selected TextView
    }

    private void resetBackgroundForAllTextViews() {
        tvOption1.setSelected(false);
        tvOption2.setSelected(false);
        // Add more TextViews if needed
    }

    private void sendingDataFunction() {
        try {
            if (!SignupType.equalsIgnoreCase("")){
                getData();

            }else {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(SignupScreen.this, "Please Select Student or Company.!!!");//
//                helper.dialog(context,"Please Select Student or Company","Alert");

            }

        } catch (Exception e) {
            System.out.println("Error= "+e.getMessage());
            Toast.makeText(SignupScreen.this, "Error is= " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAllfields() {
        etusername.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etCompanyName.setError(null);
        etnoOfEmployees.setError(null);
        etProfession.setError(null);
        spn_location.setSelection(0);

        etusername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etCompanyName.setText("");
        etnoOfEmployees.setText("");
        etProfession.setText("");
        imageProfile.setImageResource(R.drawable.img_ellipse);


    }

    private void getData()  {
        String first_name= etusername.getText().toString();
        String emailAddress= etEmail.getText().toString();
        String password= etPassword.getText().toString();
        String location= spn_location.getSelectedItem().toString();
        String company_name="";
        String no_of_employees="";
        String profession="";
        if (SignupType.equalsIgnoreCase("student")){
             company_name= "empty";
             no_of_employees = "empty";
            profession = etProfession.getText().toString();

        } else if (SignupType.equalsIgnoreCase("company")) {
             company_name= etCompanyName.getText().toString();
             no_of_employees = etnoOfEmployees.getText().toString();
            profession = "empty";


        }

        if (imageok){
            if (!first_name.equalsIgnoreCase("")) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    if (!profession.equalsIgnoreCase("")){

                        if (!password.equalsIgnoreCase("")) {
                            if (!company_name.equalsIgnoreCase("")) {
                                if (!no_of_employees.equalsIgnoreCase("")) {
                                    if (!location.equalsIgnoreCase("Select Location")) {
                                        RegisterGetterSetter.setSignUp_Type(SignupType);
                                        RegisterGetterSetter.setFirst_name(first_name);
                                        RegisterGetterSetter.setEmail_address(emailAddress);
                                        RegisterGetterSetter.setLocation(location);
                                        RegisterGetterSetter.setCompany_name(company_name);
                                        RegisterGetterSetter.setNo_of_employees(no_of_employees);
                                        RegisterGetterSetter.setPassword(password);
                                        RegisterGetterSetter.setProfession(profession);


                                        System.out.println("first_name= " + first_name);
                                        System.out.println("emailAddress= " + emailAddress);
                                        System.out.println("password= " + password);
                                        System.out.println("company_name= " + company_name);
                                        System.out.println("no_of_employees= " + no_of_employees);
                                        System.out.println("location= " + location);
                                        System.out.println("profession= " + profession);
                                        UserAlreadyRegister(emailAddress);
                                        if (!emailAvailable){
                                            long id = Querries.insertIntoSignUp(context);
                                            if (id > 0) {
                                                ViewDialog alert = new ViewDialog();
                                                alert.showDialog(SignupScreen.this, "User Registered Successfully.!!!");//
                                                clearAllfields();
                                            }else {
                                                ViewDialog alert = new ViewDialog();
                                                alert.showDialog(SignupScreen.this, "Database Connection Problem.!!!");//
                                            }
                                        }else {
                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(SignupScreen.this, "User Already Registered Please Login.!!!");//
                                        }

//                        sendData();

                                    } else {
                                        spn_location.requestFocus();
                                        ViewDialog alert = new ViewDialog();
                                        alert.showDialog(SignupScreen.this, "Please Select Location.!!!");//
//                                helper.dialog(context,"Please Select Location","Alert");
                                    }

                                } else {
                                    etnoOfEmployees.setError("Enter Number of Employes");
                                    etnoOfEmployees.requestFocus();
                                }

                            } else {
                                etCompanyName.setError("Enter Company Name");
                                etCompanyName.requestFocus();
                            }

                        } else {
                            etPassword.setError("invalid Password");
                            etPassword.requestFocus();
                        }
                    }else {
                        etProfession.setError("Please Enter Professions");
                        etProfession.requestFocus();
                    }

                } else {
                    etEmail.setError("invalid Email address");
                    etEmail.requestFocus();
                }


            } else {
                etusername.setError("Please Enter Full Name");
                etusername.requestFocus();
            }
        }else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(SignupScreen.this, "Please Select Picture.!!!");//
        }

    }
    private void UserAlreadyRegister(String email) {
        try {



            int counted = 0;
            SQLiteDatabase db = DataBaseSQlite.connectToDb(context);
            String query = "select distinct email from tbl_signup Where email = '"+email+"'  ";
            Cursor cur = db.rawQuery(query, null);
            counted = cur.getCount();
            if (counted > 0) {

                emailAvailable = true;
//                while (cur.moveToNext()) {
//
//                    arrBlock.add(cur.getString(cur.getColumnIndexOrThrow("block")));
//                }

                cur.close();
                db.close();

            } else {
                emailAvailable = false;
            }
        } catch (Exception e) {
          emailAvailable = false;
        }

    }

    public void BackToLogin(View view) {
        finish();
    }
    private String getPictureName(String prefix) {
        String uniqueID = (UUID.randomUUID().toString()).substring(0, 4);
        String strDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

            strDate = dateFormat.format(new Date()).toString() + "_" + uniqueID + ".jpg";

        return prefix + "_" + strDate;
    }
    private void PickImageFromGalley(int Code) {

        imageName = getPictureName("image");
        String strFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/";

        _path = strFolder + imageName;
        File folder = new File(strFolder);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                File file = new File(_path);
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                Uri outputFileUri;
                if (Build.VERSION.SDK_INT >= 24) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    outputFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                } else {
                    outputFileUri = Uri.fromFile(file);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent , Code);
            } else {
                helper.dialog(context, "Cannot create directory.", "Alert!");
            }
        } else {
            File file = new File(_path);
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Uri outputFileUri;
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                outputFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            } else {
                outputFileUri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, Code);
        }
    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("touch", "resultCode: " + resultCode);
        Log.d("touch", "resultOk: " + RESULT_OK);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    saveImageToDirectory(selectedImageUri);
                    imageok = true;
                    Uri selectedImage = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    thumbnail=getResizedBitmap(thumbnail, 400);
//                    Log.w("path of image from gallery......******************.........", picturePath+"");
                    System.out.println("path of image from gallery......******************........."+ picturePath+"");
                    imageProfile.setImageBitmap(thumbnail);
                    BitMapToString(thumbnail);

                    String[] separated = picturePath.split("/");

                    int last = separated.length-1;
                    String last_value= separated[last];

                    System.out.println("last_value"+last_value);
                    RegisterGetterSetter.setStrImagePath(picturePath);
//                    RegisterGetterSetter.setStrFileName(last_value);
                    RegisterGetterSetter.setBitMapCameraImage(thumbnail);


                }
            }

            else{
                switch (resultCode) {
                    case 0:
                        if (camera1 == 1) { // Property
                            imageProfile.setImageResource(R.drawable.img_ellipse);
                            imageok = false;


                            RegisterGetterSetter.setStrImagePath("");
                            RegisterGetterSetter.setStrFileName("");
                            RegisterGetterSetter.setStrImageName("");


                        }
                        break;

                    case -1:
                        if (camera1 == 1) { // Property
                            onPhotoTaken();
                            if (resizeImage2()) {
                                imageok = true;
                            }
                        }

                        break;
                }

            }
        }
    }
    private void saveImageToDirectory(Uri imageUri) {
        try {

            String[] separated = _path.split("/");

            int last = separated.length-1;
            String last_value= separated[last];

            System.out.println("last_value= "+last_value);
            RegisterGetterSetter.setStrFileName(last_value);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            File file = new File(_path);
            System.out.println("_path= "+_path);
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }
    protected void onPhotoTaken() { // Property
        try {
            //TempData.setImageName(imageName);
            Log.i("MakeMachine", "onPhotoTaken");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 6;

            if (camera1 ==1) {
                Bitmap bitmap = BitmapFactory.decodeFile(_path, options);
                Bitmap b1 = Bitmap.createScaledBitmap(bitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
                Bitmap bitmapRotated = rotateImage(b1, _path);
                imageProfile.setImageBitmap(bitmapRotated);


                RegisterGetterSetter.setStrImagePath(_path);
                RegisterGetterSetter.setStrFileName(imageName);
                RegisterGetterSetter.setBitMapCameraImage(bitmap);


                System.out.println("This is Running786");

            }


            setResult(RESULT_OK);
        } catch (Exception e) {
            Toast.makeText(context, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Error is OnPhotoTaken"+ e.getMessage());
        }
    }
    public static Bitmap rotateImage(Bitmap bitmap, String path) throws IOException {
        int rotate = 0;
        ExifInterface exif;
        exif = new ExifInterface(path);

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public boolean resizeImage2()  { // Property
        Boolean abc = false;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;

        String path1 = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "/" + imageName;
        String folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator  + context.getString(R.string.db_folder_images) + "";


        File f = new File(folder);
        if (!f.exists()) {
            f.mkdir();
        }
        File imgFile = new File(path1);
        System.out.println("7866"+path1);
        if (imgFile.exists()) {
            bitmapOrg = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
        } else {
            Toast.makeText(context, "No Image Is Present", Toast.LENGTH_SHORT).show();
        }
        try {
            StringTokenizer tokens = new StringTokenizer(imageName, ".");
            String first = tokens.nextToken();// this will contain name of picture without extension(.jepg)
            File ff = new File(path1);
            File resizedImage = new Resizer(this)
                    .setTargetLength(1440)
                    .setQuality(80)
                    .setOutputFormat("JPEG")
                    .setOutputFilename(first)
                    .setOutputDirPath(folder)
                    .setSourceImage(ff)
                    .getResizedFile();

            if(camera1 == 1)
                RegisterGetterSetter.setStrImagePath(path1);


            abc = true;

        } catch (Exception e) {
            Toast.makeText(context, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Error is786"+e.getMessage());
        }
        return abc;
    }
}