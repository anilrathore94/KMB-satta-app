package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.WithdrawAdapter;
import com.kmbbooking.starline.model.WithdrawModel;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WithdrawActivity extends AppCompatActivity {
    
    TextView btnSignUp;

    public static ProgressDialog pDialog;
    private RecyclerView recyclerView;
    EditText amount;
    Spinner remark;
    String amountValue = "", remarkValue = "";
    ArrayList<String> user_spinner_Class;
    TextView withdrawTime;
    private static List<WithdrawModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static WithdrawAdapter mAdapter;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);



        amount = findViewById(R.id.amount);
        withdrawTime= findViewById(R.id.withdrawTime);
        btnSignUp = findViewById(R.id.btnSignUp);
        remark = findViewById(R.id.remark);

        user_spinner_Class = new ArrayList<String>();


        String openTime = Saurya.getTime(Saurya.ReadStringPreferences(SharedPrefData.WITH_OPEN));
        String closeTime = Saurya.getTime(Saurya.ReadStringPreferences(SharedPrefData.WITH_CLOSE));
        withdrawTime.setText("Withdraw Time ("+openTime+" to "+closeTime+")");



        remark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                //cat_id_value = ;

                if (String.valueOf(user_spinner_Class.get(position)).equalsIgnoreCase("Select Payment Methods")) {
                    remarkValue = "";


                } else {
                    remarkValue = String.valueOf(user_spinner_Class.get(position));
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amountValue = amount.getText().toString().trim();

                if (TextUtils.isEmpty(amountValue)) {
                    amount.setError("Amount is Required.");
                    return;
                }


                if (TextUtils.isEmpty(remarkValue)) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
                    alertDialog.setTitle("Info !");
                    alertDialog.setMessage("Select Remark First.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                    return;
                }


                if(Saurya.check(openTime,closeTime).equalsIgnoreCase("no"))
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
                    alertDialog.setTitle("Info !");
                    alertDialog.setMessage("Please check App Withdraw Time.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                    return;
                }

                getWallet();


            }
        });




        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                getAllBidHistory();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getAllClass();


        pDialog = new ProgressDialog(WithdrawActivity.this);
        pDialog.setCancelable(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WithdrawAdapter(GetDownloadCategoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllBidHistory();
    }


    private void getAllClass() {

        remarkValue = "";
        user_spinner_Class.clear();

        user_spinner_Class.add("Select Payment Methods");
        user_spinner_Class.add("PayTM");
        user_spinner_Class.add("GooglePay");
        user_spinner_Class.add("PhonePe");
        user_spinner_Class.add("Others");

        ArrayAdapter adapter = new ArrayAdapter(WithdrawActivity.this, R.layout.simple_spinner_item11, user_spinner_Class);
        remark.setAdapter(adapter);
    }



    private void getAllBidHistory() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.withReqHustory(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();


                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject response = new JSONObject(String.valueOf(response1.body()));
                    swipeContainer.setRefreshing(false);
                    GetDownloadCategoryModelArrayList.clear();
                    String Message = response.getString("result");
                    if (Message.equalsIgnoreCase("[]")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage("No Withdraw Record Found.");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                        alertDialog.show();
                    } else {

                        JSONArray jsonArrayFeeList = new JSONArray(response.getString("result"));

                        Log.d("Response", "" + jsonArrayFeeList.length());
                        for (int i = 0; i < jsonArrayFeeList.length(); i++) {
                            JSONObject jsonObjectFeeList = jsonArrayFeeList.getJSONObject(i);
                            WithdrawModel updateFragmentModel = new WithdrawModel();

                            updateFragmentModel.setPoints(jsonObjectFeeList.getString("points"));
                            updateFragmentModel.setStatus(jsonObjectFeeList.getString("status"));

                            updateFragmentModel.setRemark(jsonObjectFeeList.getString("remark"));
                            updateFragmentModel.setDate(jsonObjectFeeList.getString("date"));

                            GetDownloadCategoryModelArrayList.add(updateFragmentModel);


                        }
                        mAdapter.notifyDataSetChanged();
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage("in sufficient Fund.");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(WithdrawActivity.this, Deposit.class);
                                startActivity(intent);
                                dialog.cancel();
                                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
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

    private void uploadBitmap() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.Withdraw(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE), amount.getText().toString(), remarkValue).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                    if (jsonObject.optString("success").equals("1")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
                        alertDialog.setTitle("Success !");
                        alertDialog.setMessage(jsonObject.optString("msg"));
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                dialog.cancel();
                            }
                        });

                        alertDialog.show();


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WithdrawActivity.this);
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
}