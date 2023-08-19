package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class PhonePayActivity extends AppCompatActivity {
    
    TextView type, typeNo;
    EditText tvMobile;

    TextView btnUpdate;

    String phonePe = "", gpay = "", paytm = "", name = "", email = "", phone_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_no);

        btnUpdate = findViewById(R.id.btnUpdate);

        type = findViewById(R.id.type);
        typeNo = findViewById(R.id.typeNo);
        tvMobile = findViewById(R.id.tvMobile);

        type.setText("Update PhonePe No");
        typeNo.setText("PhonePe Mobile No");
        tvMobile.setHint("Enter PhonePe Mobile No");


    

        getProfile();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(tvMobile.getText().toString().trim()) || tvMobile.getText().toString().trim().length() > 10) {
                    tvMobile.setError("Enter Valid Phone Number!!");
                    return;
                }

                Regist();

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }

    private void Regist() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.updateProfile(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE), tvMobile.getText().toString(), gpay, paytm).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));


                    Toast.makeText(getApplicationContext(), jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                    getProfile();


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

    private void getProfile() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getProfile(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();


                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject response = new JSONObject(String.valueOf(response1.body()));
                    JSONObject json = response.getJSONObject("data");


                    tvMobile.setText(json.optString("phonepay"));

                    name = json.optString("name");
                    email = json.optString("email");
                    phone_number = json.optString("phone_number");

                    paytm = json.optString("paytm");
                    phonePe = json.optString("phonepay");
                    gpay = json.optString("googlepay");

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