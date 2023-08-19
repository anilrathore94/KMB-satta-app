package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.WalletAdapter;
import com.kmbbooking.starline.model.WalletModel;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class Wallet extends AppCompatActivity {

    
    private static List<WalletModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static WalletAdapter mAdapter;
    LinearLayout llTra, llAdd, llWith, llMore;
    TextView tvWallet;
    String StartDate, EndDate;
    private RecyclerView recyclerView;
    TextView tvAll,tvCredit,tvDebit;
    String type ="All";
    final int sdk = android.os.Build.VERSION.SDK_INT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        tvAll = findViewById(R.id.tvAll);
        tvCredit = findViewById(R.id.tvCredit);
        tvDebit = findViewById(R.id.tvDebit);


        type ="All";
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tvAll.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a) );
            tvCredit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
            tvDebit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
        } else {
            tvAll.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a));
            tvCredit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
            tvDebit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
        }


        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type ="All";
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    tvAll.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a) );
                    tvCredit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                    tvDebit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                } else {
                    tvAll.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a));
                    tvCredit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                    tvDebit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                }
                getAllBidHistory();
            }
        });


        tvCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type ="Credit";
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    tvAll.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                    tvCredit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a) );
                    tvDebit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                } else {
                    tvAll.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                    tvCredit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a));
                    tvDebit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                }
                getAllBidHistory();
            }
        });


        tvDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type ="Debit";
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    tvAll.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                    tvCredit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b) );
                    tvDebit.setBackgroundDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a) );
                } else {
                    tvAll.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                    tvCredit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_b));
                    tvDebit.setBackground(ContextCompat.getDrawable(Wallet.this, R.drawable.reg_bg_log_a));
                }
                getAllBidHistory();
            }
        });


        tvWallet = findViewById(R.id.tvWallet);
        llTra = findViewById(R.id.llTra);
        llAdd = findViewById(R.id.llAdd);
        llWith = findViewById(R.id.llWith);
        llMore = findViewById(R.id.llMore);

        tvWallet.setText(Saurya.ReadStringPreferences(SharedPrefData.PREF_WALLET) + " Points");


        llTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, FundTransferActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        llWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, WithdrawActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, Deposit.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, WalletHistory.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });




        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WalletAdapter(GetDownloadCategoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        String date = new SimpleDateFormat("yyyy-MM-", Locale.getDefault()).format(new Date());
        StartDate = date + "01";
        EndDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        getAllBidHistory();
    }

    private void getAllBidHistory() {

        GetDownloadCategoryModelArrayList.clear();
        mAdapter.notifyDataSetChanged();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getWalletHist(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE), StartDate, EndDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();

                Log.d("Response", String.valueOf(response1.body()));
                try {
                    JSONObject response = new JSONObject(String.valueOf(response1.body()));
                    GetDownloadCategoryModelArrayList.clear();
                    String Message = response.getString("result");
                    if (Message.equalsIgnoreCase("[]")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Wallet.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage("No Data Found.");
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
                            WalletModel updateFragmentModel = new WalletModel();
                            updateFragmentModel.setAmount(jsonObjectFeeList.getString("amount"));
                            updateFragmentModel.setUpdated_amount(jsonObjectFeeList.getString("updated_amount"));
                            updateFragmentModel.setRemark(jsonObjectFeeList.getString("remark"));
                            updateFragmentModel.setDate(jsonObjectFeeList.getString("date"));


                            updateFragmentModel.setTime(jsonObjectFeeList.getString("time"));





                            if(type.equalsIgnoreCase("All"))
                            {
                                GetDownloadCategoryModelArrayList.add(updateFragmentModel);
                            }
                            else if(type.equalsIgnoreCase("Credit"))
                            {
                                if (Double.parseDouble(jsonObjectFeeList.getString("amount")) >= 0.0) {
                                    GetDownloadCategoryModelArrayList.add(updateFragmentModel);
                                }

                            }
                            else if(type.equalsIgnoreCase("Debit"))
                            {
                                if (Double.parseDouble(jsonObjectFeeList.getString("amount")) >= 0.0) {

                                }
                                else
                                {
                                    GetDownloadCategoryModelArrayList.add(updateFragmentModel);
                                }

                            }
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



    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }


}