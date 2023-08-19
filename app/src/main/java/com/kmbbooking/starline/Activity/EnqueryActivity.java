package com.kmbbooking.starline.Activity;

import android.app.Dialog;
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


public class EnqueryActivity extends AppCompatActivity {
    
    TextView tvName, tvMobile;
    EditText etEnquiry;
    TextView btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquery);

        btnUpdate = findViewById(R.id.btnUpdate);

        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);
        etEnquiry = findViewById(R.id.etEnquiry);

        tvName.setText(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME));
        tvMobile.setText(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE));


    


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(etEnquiry.getText().toString().trim())) {
                    etEnquiry.setError("Enter Your Query Here");
                    return;
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
        mAPIService.setEnquiry(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE), Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINNAME), etEnquiry.getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    Toast.makeText(getApplicationContext(), jsonObject.optString("msg"), Toast.LENGTH_LONG).show();
                    finish();
                    overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);


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