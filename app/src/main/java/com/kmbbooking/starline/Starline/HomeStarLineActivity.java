package com.kmbbooking.starline.Starline;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kmbbooking.starline.Activity.HomeActivity;
import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.GameStarLineAdapter;
import com.kmbbooking.starline.model.GameData;
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

public class HomeStarLineActivity extends AppCompatActivity {
    public static ProgressDialog pDialog;
    ViewFlipper flipper;
    RecyclerView recyclerView;
    LinearLayout llStarLineSelect, llWinReport, llBid;
    List<GameData> list = new ArrayList<>();
    TextView ba, bb, bc, bd;
    TextView tvWallet, llStarLine;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_star);

        tvWallet = findViewById(R.id.tvWallet);
        if (!checkIfAlreadyhavePermission()) {
            requestForSpecificPermission();
        }
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llStarLineSelect = findViewById(R.id.llStarLineSelect);
        flipper = findViewById(R.id.flipper);

        ba = findViewById(R.id.ba);
        bb = findViewById(R.id.bb);
        bc = findViewById(R.id.bc);
        bd = findViewById(R.id.bd);

        llWinReport = findViewById(R.id.llWinReport);
        llBid = findViewById(R.id.llBid);

        llWinReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                Intent intent3 = new Intent(com.kmbbooking.starline.Starline.HomeStarLineActivity.this, WinHistoryStarActivity.class);
                startActivity(intent3);
            }
        });
        llBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                Intent intent2 = new Intent(com.kmbbooking.starline.Starline.HomeStarLineActivity.this, BidHistoryStarLineActivity.class);
                startActivity(intent2);
            }
        });

        llStarLine = findViewById(R.id.llStarLine);


        llStarLine.setText("Play Main Market");
        llStarLineSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {

                    return;
                }

                Intent intent8 = new Intent(com.kmbbooking.starline.Starline.HomeStarLineActivity.this, HomeActivity.class);
                startActivity(intent8);
            }
        });


        recyclerView = findViewById(R.id.recyclerview);


        getWallet();
        Regist();

        flipper.setAutoStart(true);
        flipper.setFlipInterval(5000);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getWallet();
                Regist();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getGameRateStar();

    }

    private void getGameRateStar() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getStarRates().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> responsea) {
                dialog.dismiss();
                try {
                    JSONObject response = new JSONObject(String.valueOf(responsea.body()));
                    JSONArray jsonArrayFeeList = new JSONArray(response.getString("result"));

                    Log.d("Response", "" + jsonArrayFeeList.length());
                    for (int i = 0; i < jsonArrayFeeList.length(); i++) {
                        JSONObject jsonObjectFeeList = jsonArrayFeeList.getJSONObject(i);
                        if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Single Digit")) {
                            ba.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Single Pana")) {
                            bb.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Double Pana")) {
                            bc.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Triple Pana")) {
                            bd.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        }


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


    private void Regist() {
        list.clear();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.GET_GAME_STAR().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                swipeContainer.setRefreshing(false);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));


                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        GameData gameData = new GameData();
                        gameData.setGameName(jsonObject1.optString("games_name"));
                        gameData.setOpenTime(jsonObject1.optString("open_time"));
                        gameData.setMarketStatus(jsonObject1.optString("market_status"));
                        gameData.setGameStatus(jsonObject1.optString("game_status"));

                        gameData.setOpenDigit(jsonObject1.optString("digit"));
                        gameData.setOpenPana(jsonObject1.optString("pana"));


                        list.add(gameData);
                    }

                    GameStarLineAdapter gameDataAdapter = new GameStarLineAdapter(list, com.kmbbooking.starline.Starline.HomeStarLineActivity.this);
                    recyclerView.setAdapter(gameDataAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(com.kmbbooking.starline.Starline.HomeStarLineActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);


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


                    Saurya.writeStringPreference(SharedPrefData.PREF_STATUS, json.optString("status"));
                    Saurya.writeStringPreference(SharedPrefData.PREF_WALLET, json.optString("wallet"));

                    Saurya.writeStringPreference(SharedPrefData.PREF_START_TIME, json.optString("start_time"));


                    tvWallet.setText(json.optString("wallet"));

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


    

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_TASKS, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}