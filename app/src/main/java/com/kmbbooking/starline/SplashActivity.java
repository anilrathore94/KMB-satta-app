package com.kmbbooking.starline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.kmbbooking.starline.Activity.HomeActivity;
import com.kmbbooking.starline.Activity.SignUpActivity;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;


public class SplashActivity extends AppCompatActivity {
    public static ProgressDialog pDialog;
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("1")) {

                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                }

                else
                {
                    Intent mainIntent = new Intent(SplashActivity.this, SignUpActivity.class);
                    startActivity(mainIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                }

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


    
}