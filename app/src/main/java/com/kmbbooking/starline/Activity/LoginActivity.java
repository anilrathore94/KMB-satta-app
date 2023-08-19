package com.kmbbooking.starline.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {
    
    EditText LoginPhoneNumber, LoginPassword;
    TextView Login;
    TextView Register;
    TextView ForgotPassword;
    String LoginPhone, Login_Password, phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Saurya.writeStringPreference(SharedPrefData.PREF_FCMTOKENID, "deviceToken");

        LoginPhoneNumber = findViewById(R.id.LoginPhoneNumber);
        LoginPassword = findViewById(R.id.LoginPassword);
        Login = findViewById(R.id.Login);
        Register = findViewById(R.id.SignUp);
        ForgotPassword = findViewById(R.id.Password);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(LoginPhoneNumber.getText().toString().trim()) || LoginPhoneNumber.getText().toString().trim().length() > 10) {
                    LoginPhoneNumber.setError("Enter Valid Phone Number!!");
                    return;
                } else {
                    LoginPhone = LoginPhoneNumber.getText().toString();
                }

                if (TextUtils.isEmpty(LoginPassword.getText().toString().trim())
                        || LoginPassword.getText().toString().trim().length() < 6) {
                    LoginPassword.setError("Enter Your Password Again!!");
                    return;
                } else {
                    Login_Password = LoginPassword.getText().toString();
                }


                Regist();

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
                dialog.setContentView(R.layout.layout_progress);
                dialog.setCancelable(false);

                RetrofitInterface mAPIService;
                mAPIService = ApiUtils.getAPIService();

                mAPIService.getWallet(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                        dialog.dismiss();


                        Log.d("Response", String.valueOf(response1));
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response1.body()));

                            JSONObject json = jsonObject.getJSONObject("data");

                            Saurya.writeStringPreference(SharedPrefData.PREF_WP, json.optString("admin_wp"));

                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + json.optString("admin_wp")));
                            startActivity(callIntent);





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

        mAPIService.login(LoginPhone, Login_Password, "PrivateEducation").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    if (jsonObject.optString("success").equalsIgnoreCase("0")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage("User Not Found.");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alertDialog.show();

                    } else {
                        JSONObject json = jsonObject.getJSONObject("data");
                        phone_number = json.optString("phone_number");
                        Saurya.writeIntPreference(SharedPrefData.PREF_ISLOGGEDIN, 1);

                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINNAME, json.optString("name"));
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPHONE, json.optString("phone_number"));
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPASSWORD, LoginPassword.getText().toString());
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINEMAIL, json.optString("email"));


                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
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


   

}