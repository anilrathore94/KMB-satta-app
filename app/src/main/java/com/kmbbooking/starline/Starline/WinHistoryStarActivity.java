package com.kmbbooking.starline.Starline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.kmbbooking.starline.adapters.WinHistoryStarAdapter;
import com.kmbbooking.starline.model.WinHistoryModel;
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

public class WinHistoryStarActivity extends AppCompatActivity {
    public static ProgressDialog pDialog;
    private static List<WinHistoryModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static WinHistoryStarAdapter mAdapter;
    ImageView ivComingSoon;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_history);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Win History");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(getApplicationContext(), com.kmbbooking.starline.Starline.HomeStarLineActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WinHistoryStarAdapter(GetDownloadCategoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        ivComingSoon = findViewById(R.id.ivComingSoon);
        recyclerView.setVisibility(View.GONE);
        ivComingSoon.setVisibility(View.VISIBLE);

        getAllWinHistory();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAllWinHistory();

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void getAllWinHistory() {

        GetDownloadCategoryModelArrayList.clear();
        mAdapter.notifyDataSetChanged();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getWinStarReport(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();


                Log.d("Response", String.valueOf(response1));
                swipeContainer.setRefreshing(false);
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
                            WinHistoryModel updateFragmentModel = new WinHistoryModel();
                            updateFragmentModel.setGame_name(jsonObjectFeeList.getString("game_name"));
                            updateFragmentModel.setGame_type(jsonObjectFeeList.getString("game_type"));


                            updateFragmentModel.setOpen_pana(jsonObjectFeeList.getString("open_pana"));
                            updateFragmentModel.setOpen_digit(jsonObjectFeeList.getString("open_digit"));

                            updateFragmentModel.setWinning_points(jsonObjectFeeList.getString("winning_points"));
                            updateFragmentModel.setPoints_action(jsonObjectFeeList.getString("points_action"));
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


   
}