package com.kmbbooking.starline.Activity;

import android.app.Dialog;
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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotUpdatePasswordActivity extends AppCompatActivity {

    
    EditText NewPassword1;
    TextView btnSave1;
    String NewPassword, phoneNumber, success, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password_new);
    
        NewPassword1 = findViewById(R.id.NewPassword1);
        btnSave1 = findViewById(R.id.Login);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");


        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(NewPassword1.getText().toString().trim())
                        || NewPassword1.getText().toString().trim().length() < 6) {
                    NewPassword1.setError("Enter Your New Password Again!!");
                } else {
                    NewPassword = NewPassword1.getText().toString();
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

        mAPIService.forgetUpdatePass(phoneNumber, NewPassword).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject json = new JSONObject(String.valueOf(response.body()));

                    success = json.optString("success");
                    msg = json.optString("msg");

                    if (success.equals("1")) {
                        Toast.makeText(com.kmbbooking.starline.Activity.ForgotUpdatePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.kmbbooking.starline.Activity.ForgotUpdatePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                    } else {
                        Toast.makeText(com.kmbbooking.starline.Activity.ForgotUpdatePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.kmbbooking.starline.Activity.ForgotUpdatePasswordActivity.this, com.kmbbooking.starline.Activity.ForgotPassword.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
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