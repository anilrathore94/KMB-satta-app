package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;


public class GameRates extends AppCompatActivity {


    
    TextView aa, ab, ac, ad, ae, af, ag;
    TextView ba, bb, bc, bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rates);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Game Rates");
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

    
        aa = findViewById(R.id.aa);
        ab = findViewById(R.id.ab);
        ac = findViewById(R.id.ac);
        ad = findViewById(R.id.ad);
        ae = findViewById(R.id.ae);
        af = findViewById(R.id.af);
        ag = findViewById(R.id.ag);

        ba = findViewById(R.id.ba);
        bb = findViewById(R.id.bb);
        bc = findViewById(R.id.bc);
        bd = findViewById(R.id.bd);

        getGameRate();

    }

    private void getGameRate() {



        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);

        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getRates().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> responsea) {
                dialog.dismiss();
                try {
                    JSONObject response = new JSONObject(String.valueOf(responsea.body()));
                    JSONArray jsonArrayFeeList = new JSONArray(response.getString("result"));
                    for (int i = 0; i < jsonArrayFeeList.length(); i++) {
                        JSONObject jsonObjectFeeList = jsonArrayFeeList.getJSONObject(i);
                        if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Single Digit")) {
                            aa.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Jodi Digit")) {
                            ab.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Single Pana")) {
                            ac.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Double Pana")) {
                            ad.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Triple Pana")) {
                            ae.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Half Sangam")) {
                            af.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
                        } else if (jsonObjectFeeList.getString("type").equalsIgnoreCase("Full Sangam")) {
                            ag.setText(jsonObjectFeeList.getString("min_value") + " - " + jsonObjectFeeList.getString("max_value"));
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


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }
}