package com.kmbbooking.starline.Starline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.Activity.HomeActivity;
import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.GameStarChartAdapter;
import com.kmbbooking.starline.model.GameChartModel;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class GameStarChartHistory extends AppCompatActivity {
    public static ProgressDialog pDialog;
    private static List<GameChartModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static GameStarChartAdapter mAdapter;
    String gameName;
    ImageView ivComingSoon;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_history);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameName = extras.getString("gameName");
            // and get whatever type user account id is
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText(gameName);
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
        mAdapter = new GameStarChartAdapter(GetDownloadCategoryModelArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.setAdapter(mAdapter);
        ivComingSoon = findViewById(R.id.ivComingSoon);

        recyclerView.setVisibility(View.VISIBLE);
        ivComingSoon.setVisibility(View.GONE);
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
        mAPIService.GET_CHART_STAR().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();
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
                            GameChartModel updateFragmentModel = new GameChartModel();
                            updateFragmentModel.setGame_name(jsonObjectFeeList.getString("game_name"));
                            updateFragmentModel.setResult_date(jsonObjectFeeList.getString("result_date"));
                            updateFragmentModel.setOpen_number(jsonObjectFeeList.getString("open_number"));


                            if (gameName.equalsIgnoreCase(jsonObjectFeeList.getString("game_name"))) {
                                GetDownloadCategoryModelArrayList.add(updateFragmentModel);
                            }

                        }

                        if (GetDownloadCategoryModelArrayList.size() > 0) {
                            mAdapter.notifyDataSetChanged();

                        } else {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(com.kmbbooking.starline.Starline.GameStarChartHistory.this);
                            alertDialog.setTitle("Info !");
                            alertDialog.setMessage("No Data Found.");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });

                            alertDialog.show();

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


    
}