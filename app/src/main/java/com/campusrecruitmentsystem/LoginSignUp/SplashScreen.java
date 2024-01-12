package com.campusrecruitmentsystem.LoginSignUp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import com.campusrecruitmentsystem.R;
import com.campusrecruitmentsystem.student.database.DataBaseSQlite;

public class SplashScreen extends AppCompatActivity {

    static Context context;


    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    private int currentApiVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        context = this;


        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            checkingPermission();
        } else {   // Pre Marshallow -
            CreateNewDataBase();
            playAnimation();
            startactivty();
        }
    }

    private void checkingPermission() {
        if (ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[6]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(SplashScreen.this, permissionsRequired[7]) != PackageManager.PERMISSION_GRANTED

        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[5])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[6])
                    || ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, permissionsRequired[7])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location,Storage and SMS permissions.Otherwise App won't Work!!!");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashScreen.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
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
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(SplashScreen.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

//            txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    public static void CreateNewDataBase() {
        DataBaseSQlite db = new DataBaseSQlite(context.getString(R.string.db_folder), context);
        db.dataBase();
    }
    private void playAnimation() {
        /*ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.dots_loading).into(imageViewTarget);*/
        FrameLayout frameStacktelevision = (FrameLayout) findViewById(R.id.frameStacktelevision);
        LinearLayout ll_texts = (LinearLayout) findViewById(R.id.ll_texts);
        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        frameStacktelevision.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        ll_texts.setAnimation(animation);
    }

    private void startactivty() {
        // TODO Auto-generated method stub
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent mInHome = new Intent(SplashScreen.this, LoginScreen.class);
                SplashScreen.this.startActivity(mInHome);
                SplashScreen.this.finish();

//                SharedPreferences pref = getApplicationContext().getSharedPreferences("voilation", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//
//                String  username ="";
//                username= pref.getString("username", null);
//
//
//                if(username !=null && !username.isEmpty())
//                {
//
//                    Intent mInHome = new Intent(SplashScreen.this, LoginScreen.class);
//                    SplashScreen.this.startActivity(mInHome);
//                    SplashScreen.this.finish();
//
//                }else
//                {
//                    Intent mInHome = new Intent(SplashScreen.this, SelectLanguage.class); /*LoginActivity*/
//                    SplashScreen.this.startActivity(mInHome);
//                    SplashScreen.this.finish();
//                }
            }
        }, 3000);
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