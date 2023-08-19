package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
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


public class ChangePassword extends AppCompatActivity {

    
    EditText NewPassword, CurrentPassword;
    TextView btnSave;
    String strCurrentPassword, strNewPassword, msg, success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



        NewPassword = findViewById(R.id.NewPassword);
        CurrentPassword = findViewById(R.id.CurrentPassword);
        btnSave = findViewById(R.id.Login);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(CurrentPassword.getText().toString().trim())
                        || CurrentPassword.getText().toString().trim().length() < 6) {
                    CurrentPassword.setError("Enter Your Current Password Again!!");

                    return;
                }


                if (TextUtils.isEmpty(NewPassword.getText().toString().trim())
                        || NewPassword.getText().toString().trim().length() < 6) {
                    NewPassword.setError("Enter Your New Password Again!!");
                    return;
                }


                strNewPassword = NewPassword.getText().toString();
                strCurrentPassword = CurrentPassword.getText().toString();
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
        mAPIService.changePass(strCurrentPassword, strNewPassword, Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    success = jsonObject.optString("success");
                    msg = jsonObject.optString("msg");

                    if (success.equals("1")) {
                        Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();

                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPASSWORD, NewPassword.getText().toString());

                        Intent intent = new Intent(ChangePassword.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                    } else {
                        Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();
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