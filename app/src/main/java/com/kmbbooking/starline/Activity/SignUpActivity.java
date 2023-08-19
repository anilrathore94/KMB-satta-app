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

public class SignUpActivity extends AppCompatActivity {
    
    static Context context;
    static String UserName, UserPassword, UserPhone, UserEmail;
    static EditText SignUpFullName;
    static EditText SignUpPassword;
    static EditText SignUp_mobileNumber;
    static EditText SignUpEmail;
    TextView Login1;
    TextView CreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        context = this;

        Saurya.writeStringPreference(SharedPrefData.PREF_FCMTOKENID, "deviceToken");
        Login1 = findViewById(R.id.Login1);
        CreateAccount = findViewById(R.id.SignUp1);
        SignUpPassword = findViewById(R.id.SignUpPassword);
        SignUpFullName = findViewById(R.id.SignUpFullName);
        SignUpEmail = findViewById(R.id.signUpEmail);
        SignUp_mobileNumber = findViewById(R.id.LoginPhoneNumber);


        Login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);

            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(SignUpFullName.getText().toString().trim()) || SignUpFullName.getText().toString().trim().length() > 50) {
                    SignUpFullName.setError("Enter Your Name Again!!");
                } else {
                    UserName = SignUpFullName.getText().toString();
                }

                if (TextUtils.isEmpty(SignUp_mobileNumber.getText().toString().trim()) || SignUp_mobileNumber.getText().toString().trim().length() > 10) {
                    SignUp_mobileNumber.setError("Enter Valid Phone Number!!");
                } else {
                    UserPhone = SignUp_mobileNumber.getText().toString();
                }

                if (TextUtils.isEmpty(SignUpPassword.getText().toString().trim())
                        || SignUpPassword.getText().toString().trim().length() < 6) {
                    SignUpPassword.setError("Enter Your Password Again!!");
                } else {
                    UserPassword = SignUpPassword.getText().toString();
                }


                UserEmail = SignUpEmail.getText().toString();

                sendOTP();


            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }

    private void sendOTP() {

        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPHONE, UserPhone);


        Intent mainIntent = new Intent(SignUpActivity.this, OTPRegisterActivity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }



    public static void Regist() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.signUp(UserName, UserPhone, UserPassword, UserEmail, "1111", Saurya.ReadStringPreferences(SharedPrefData.PREF_FCMTOKENID)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    if (jsonObject.optString("success").equals("1")) {

                        Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();

                        Saurya.writeIntPreference(SharedPrefData.PREF_ISLOGGEDIN, 1);

                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINNAME, SignUpFullName.getText().toString());
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPHONE, SignUp_mobileNumber.getText().toString());
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINPASSWORD, SignUpPassword.getText().toString());
                        Saurya.writeStringPreference(SharedPrefData.PREF_LOGINEMAIL, SignUpEmail.getText().toString());


                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
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