package com.kmbbooking.starline.Activity;

import android.app.Dialog;
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

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.NoticeAdapter;
import com.kmbbooking.starline.model.NoticeModel;
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

public class NoticeActivity extends AppCompatActivity {

    
    private static List<NoticeModel> GetDownloadCategoryModelArrayList = new ArrayList<>();
    private static NoticeAdapter mAdapter;
    ImageView ivComingSoon;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ivComingSoon = findViewById(R.id.ivComingSoon);
    

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Notice");
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
        mAdapter = new NoticeAdapter(GetDownloadCategoryModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.setVisibility(View.GONE);
        ivComingSoon.setVisibility(View.VISIBLE);

        getAllWinHistory();

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
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

        mAPIService.getNotice().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> responsea) {
                dialog.dismiss();
                try {
                    JSONObject response = new JSONObject(String.valueOf(responsea.body()));

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
                            NoticeModel updateFragmentModel = new NoticeModel();
                            updateFragmentModel.setContent(jsonObjectFeeList.getString("content"));
                            updateFragmentModel.setDate(jsonObjectFeeList.getString("date"));
                            updateFragmentModel.setTitle(jsonObjectFeeList.getString("title"));

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