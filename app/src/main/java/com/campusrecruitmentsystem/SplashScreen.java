package com.campusrecruitmentsystem;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.campusrecruitmentsystem.company.HomeActivity;
import com.campusrecruitmentsystem.company.TestActivity;
import com.campusrecruitmentsystem.database.DataBaseSQlite;
import com.campusrecruitmentsystem.student.DashboardStudent;


public class SplashScreen extends AppCompatActivity {
    static Context context;
    public static final String PREFS_NAME = "MyPrefsFile";


    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    private int currentApiVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        context = this;

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32) {
            // Marshmallow+
            checkingPermission();
        }else if(Build.VERSION.SDK_INT > 32){
            checkingPermission2();
            System.out.println("SDK > 32= ");
        }else {   // Pre Marshallow -
            CreateNewDataBase();
            playAnimation();
            startactivty();
        }

    }

    private void playAnimation() {
        /*ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.dots_loading).into(imageViewTarget);*/
        FrameLayout ivAnim = (FrameLayout) findViewById(R.id.frameStacktelevision);
        LinearLayout textView = (LinearLayout) findViewById(R.id.ll_texts);
        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        ivAnim.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        textView.setAnimation(animation);
    }

    private void startactivty() {
        // TODO Auto-generated method stub
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
                boolean hasLoggedInStudent = settings.getBoolean("hasLoggedInStudent", false);
                boolean hasLoggedInCompany = settings.getBoolean("hasLoggedInCompany", false);
                //  boolean hasLoggedOut = settings.getBoolean("hasLoggedOut", false);

                if(hasLoggedInStudent) {
                    Intent intent = new Intent(SplashScreen.this, DashboardStudent.class);
                    startActivity(intent);
                    finish();
                } else if (hasLoggedInCompany) {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Intent mInHome = new Intent(SplashScreen.this, LoginScreen.class);
                    SplashScreen.this.startActivity(mInHome);
                    SplashScreen.this.finish();
                }

            }
        }, 3000);
    }

    public static void  CreateNewDataBase()
    {
        DataBaseSQlite db =  new DataBaseSQlite(context.getString(R.string.db_name), context);
        db.dataBase();

    }

    private void checkingPermission()
    {
        if(ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[6]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[7]) != PackageManager.PERMISSION_GRANTED

        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[5])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[6])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[7]))
            {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location,Storage and SMS permissions.Otherwise App won't Work!!!");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location,Storage  permissions.Otherwise App won't Work!!!");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant SMS,Location and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

//            txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }


    private void checkingPermission2()
    {
        if(ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED

        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[5])
            )
            {
//                Toast.makeText(context,"One",Toast.LENGTH_SHORT).show();
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location,Storage and SMS permissions.Otherwise App won't Work!!!");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
//                Toast.makeText(context,"Two",Toast.LENGTH_SHORT).show();

                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location,Storage  permissions.Otherwise App won't Work!!!");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant SMS,Location and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


            }  else {
//                Toast.makeText(context,"Three",Toast.LENGTH_SHORT).show();

                //just request the permission
                ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

//            txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32){
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        allgranted = true;
                        System.out.println("Permission granted: " + permissionsRequired[i]);

                    } else {
                        allgranted = false;
                        System.out.println("Permission denied: " + permissionsRequired[i]);

                        break;
                    }
                }
            }else if(Build.VERSION.SDK_INT > 32){
                for(int i=0;i<grantResults.length-2;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        allgranted = true;
                        System.out.println("Permission granted: " + permissionsRequired[i]);

                    } else {
                        allgranted = false;
                        System.out.println("Permission denied: " + permissionsRequired[i]);

                        break;
                    }
                }
            }else {
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        allgranted = true;
                        System.out.println("Permission granted: " + permissionsRequired[i]);

                    } else {
                        allgranted = false;
                        System.out.println("Permission denied: " + permissionsRequired[i]);

                        break;
                    }
                }
            }



            if(allgranted){
                proceedAfterPermission();
            }else if(Build.VERSION.SDK_INT > 32)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[4])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[5]))
                {
//                txtPermissions.setText("Permissions Required");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs SMS,Storage and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission 1",Toast.LENGTH_LONG).show();
                }
            }
            else if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32){
                if(ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[2])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[3])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[4])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[5])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[6])
                        || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,permissionsRequired[7]))
                {
//                txtPermissions.setText("Permissions Required");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs SMS,Storage and Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(SplashScreen.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission 2",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
    private void proceedAfterPermission() {
//        txtPermissions.setText("We've got all permissions");
//        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        CreateNewDataBase();
        playAnimation();
        startactivty();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        if (sentToSettings)
        {
            if (ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}