package com.kmbbooking.starline.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class WalletHistory extends AppCompatActivity {
    
    private static List<WalletModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static WalletAdapter mAdapter;
    EditText edtStartDate, edtEndDate;
    TextView btnSearch;
    DatePickerDialog datepicker;
    String StartDate, EndDate;
    ImageView ivComingSoon;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        ivComingSoon = findViewById(R.id.ivComingSoon);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Wallet History");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
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


    


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WalletAdapter(GetDownloadCategoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        btnSearch = findViewById(R.id.btnSearch);


        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(WalletHistory.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calander2 = Calendar.getInstance();
                                calander2.setTimeInMillis(0);
                                calander2.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                Date SelectedDate = calander2.getTime();
                                SimpleDateFormat dateformat_US = new SimpleDateFormat("yyyy-MM-dd");
                                edtStartDate.setText(dateformat_US.format(SelectedDate));
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(WalletHistory.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calander2 = Calendar.getInstance();
                                calander2.setTimeInMillis(0);
                                calander2.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                Date SelectedDate = calander2.getTime();
                                SimpleDateFormat dateformat_US = new SimpleDateFormat("yyyy-MM-dd");
                                edtEndDate.setText(dateformat_US.format(SelectedDate));
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtStartDate.getText().toString().trim())) {
                    edtStartDate.setError("Enter Valid Date!!");
                    return;
                } else {
                    StartDate = edtStartDate.getText().toString();
                }

                if (TextUtils.isEmpty(edtEndDate.getText().toString().trim())) {
                    edtEndDate.setError("Enter Valid Date!!");
                    return;
                } else {
                    EndDate = edtEndDate.getText().toString();
                }

                getAllBidHistory();
            }
        });

        recyclerView.setVisibility(View.GONE);
        ivComingSoon.setVisibility(View.VISIBLE);


        String date = new SimpleDateFormat("yyyy-MM-", Locale.getDefault()).format(new Date());
        StartDate = date + "01";
        edtStartDate.setText(StartDate);
        EndDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edtEndDate.setText(EndDate);
        getAllBidHistory();


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                String date = new SimpleDateFormat("yyyy-MM-", Locale.getDefault()).format(new Date());
                StartDate = date + "01";
                edtStartDate.setText(StartDate);
                EndDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                edtEndDate.setText(EndDate);
                getAllBidHistory();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
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
                swipeContainer.setRefreshing(false);
                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject response = new JSONObject(String.valueOf(response1.body()));
                    GetDownloadCategoryModelArrayList.clear();
                    String Message = response.getString("result");
                    if (Message.equalsIgnoreCase("[]")) {

                        recyclerView.setVisibility(View.GONE);
                        ivComingSoon.setVisibility(View.VISIBLE);

                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        ivComingSoon.setVisibility(View.GONE);
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


   
}