package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.kmbbooking.starline.R;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Cofig;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class OTPForgetActivity extends AppCompatActivity {
    
    static Context context;
    MyCounter timer;
    EditText et_otp;
    TextView tvTimer;
    TextView resumeExaButton;
    TextView phNo;
    ImageView ivEdit;
    String ttValue = "";

    public static String getRandomNumberString() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_otp);
        phNo = findViewById(R.id.phNo);
        ivEdit = findViewById(R.id.ivEdit);
        et_otp = findViewById(R.id.et_otp);
        tvTimer = findViewById(R.id.tvTimer);
        resumeExaButton = findViewById(R.id.resumeExaButton);

    


        phNo.setText(firstTwo(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)) + "-XXX-XXX-" + lastTwo(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)));


        context = this;


        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ForgotPassword.class);
                context.startActivity(intent);

            }
        });


        resumeExaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ttValue.equalsIgnoreCase("Resend OTP")) {


                        sendOTP();

                } else {
                    String stringmyOTP = et_otp.getText().toString();

                    if (TextUtils.isEmpty(stringmyOTP)) {
                        Saurya.showPromptDialog(OTPForgetActivity.this, "Error", "Sorry! Please Enter Verification Code !", "Ok", 2);

                        return;
                    }


                    if (!stringmyOTP.equalsIgnoreCase(Saurya.ReadStringPreferences(SharedPrefData.PREF_OTP))) {
                        Saurya.showPromptDialog(OTPForgetActivity.this, "Error", "Please Enter Correct Verification Code !", "Ok", 2);

                        return;
                    }

                    ForgotPassword.goUpdate();
                }


            }
        });



            sendOTP();


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }

    public void sendOTP() {
        String otp = getRandomNumberString();
        Saurya.writeStringPreference(SharedPrefData.PREF_OTP, otp);

//        Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_LONG).show();
//        timer = new MyCounter(30 * 1000, 1000);
//        timer.start();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        Log.d("otp_url", Cofig.SEND_OTP +
                Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE) + "/" + Saurya.ReadStringPreferences(SharedPrefData.PREF_OTP));


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.sendOTP(Cofig.SEND_OTP +
                Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE) + "/" + Saurya.ReadStringPreferences(SharedPrefData.PREF_OTP)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> responsea) {

                dialog.dismiss();
                timer = new MyCounter(30 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }


        });

        dialog.show();
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }


    public String lastTwo(String str) {
        return str.substring(str.length() - 2);
    }


   


    public class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

            ttValue = "Resend OTP";


        }

        @Override
        public void onTick(long millisUntilFinished) {
            ttValue = "Verify";

            tvTimer.setText(String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + " Sec");

        }
    }
}