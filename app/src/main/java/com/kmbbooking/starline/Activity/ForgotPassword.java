package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPassword extends AppCompatActivity {
    
    static String phoneNumber;
    static Context context;
    TextView Login;
    EditText LoginPhoneNumber;
    String success;
    String msg;
    String otp;

    public static void goUpdate() {

        Intent intent = new Intent(context, ForgotUpdatePasswordActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        context.startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        context = this;
    
        LoginPhoneNumber = findViewById(R.id.LoginPhoneNumber);
        Login = findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(LoginPhoneNumber.getText().toString().trim()) || LoginPhoneNumber.getText().toString().trim().length() > 10) {
                    LoginPhoneNumber.setError("Enter Valid Phone Number!!");
                } else {
                    phoneNumber = LoginPhoneNumber.getText().toString();
                }

                Regist();
            }
        });
    }

    private void Regist() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);
        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.forgetPass(phoneNumber).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject json = new JSONObject(String.valueOf(response.body()));

                    success = json.optString("success");
                    msg = json.optString("msg");
                    otp = json.optString("otp");

                    if (success.equals("1") & (otp.length() > 0)) {

                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPHONE, LoginPhoneNumber.getText().toString());
                        Intent mainIntent = new Intent(ForgotPassword.this, OTPForgetActivity.class);
                        startActivity(mainIntent);

                        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                    } else {
                        Toast.makeText(ForgotPassword.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }


        });

        dialog.show();

    }

       

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }

}