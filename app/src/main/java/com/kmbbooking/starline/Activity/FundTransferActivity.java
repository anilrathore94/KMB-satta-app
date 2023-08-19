package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class FundTransferActivity extends AppCompatActivity {
    
    TextView btnSignUp;
    EditText amount, userId;
    TextView tvUserName;
    String amountValue = "", reciverId = "", active = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Transfer Point");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

    
        amount = findViewById(R.id.amount);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setVisibility(View.GONE);

        active = "false";

        btnSignUp = findViewById(R.id.btnSignUp);
        userId = findViewById(R.id.userId);


        userId.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                getProfile(String.valueOf(s));
                // TODO Auto-generated method stub
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amountValue = amount.getText().toString().trim();
                reciverId = userId.getText().toString().trim();

                if (TextUtils.isEmpty(reciverId)) {
                    userId.setError("Reciver UserName is Required.");
                    return;
                }

                if (TextUtils.isEmpty(amountValue)) {
                    amount.setError("Amount is Required.");
                    return;
                }


                if (active.equalsIgnoreCase("true")) {
                    getWallet();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FundTransferActivity.this);
                    alertDialog.setTitle("Info !");
                    alertDialog.setMessage("Enter Correct Reciver UserName.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }


            }
        });


    }


    private void getProfile(final String uName) {

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getProfile(uName).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {


                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject response = new JSONObject(String.valueOf(response1.body()));
                    if (response.optString("success").equalsIgnoreCase("1")) {
                        JSONObject json = response.getJSONObject("data");
                        tvUserName.setVisibility(View.VISIBLE);
                        active = "true";
                        tvUserName.setText(json.optString("name"));

                    } else {
                        active = "false";
                        tvUserName.setVisibility(View.VISIBLE);
                        tvUserName.setText("Wrong User ID");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }


        });


    }

    private void getWallet() {
        final Dialog dialog = new Dialog(this);
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
                    Saurya.writeStringPreference(SharedPrefData.PREF_WALLET, json.optString("wallet"));

                    if (Integer.parseInt(json.optString("wallet")) >= Integer.parseInt(amountValue)) {
                        uploadBitmap();
                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FundTransferActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage(jsonObject.optString("msg"));
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                        alertDialog.show();
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

    private void uploadBitmap() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.setTransfer(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE), amount.getText().toString().trim(), userId.getText().toString().trim()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    if (jsonObject.optString("success").equals("1")) {

                        Toast.makeText(FundTransferActivity.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FundTransferActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage(jsonObject.optString("msg"));
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                        alertDialog.show();
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