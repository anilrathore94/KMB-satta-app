package com.kmbbooking.starline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;


public class SelectGameActivity extends AppCompatActivity {

    LinearLayout img1, img2, img3, img4, img5, img6, img7;

    String gameName, startTime, closeTime;
    String timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game_recycle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Select Game");
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameName = extras.getString("gameName");
            startTime = extras.getString("stTimeN");
            closeTime = extras.getString("clTimeN");
            // and get whatever type user account id is
            timeValue = Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), startTime);

        }
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);


        if (timeValue.equalsIgnoreCase("yes")) {

            img5.setVisibility(View.VISIBLE);
            img6.setVisibility(View.VISIBLE);
            img7.setVisibility(View.VISIBLE);

        } else {

            img5.setVisibility(View.GONE);
            img6.setVisibility(View.GONE);
            img7.setVisibility(View.GONE);


        }


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, PlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Single Digit");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Digit");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, PlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Single Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Pana");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, PlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Double Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Pana");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, PlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Triple Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Pana");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, HalfSangamPlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Half Sangam");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Pana");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameActivity.this, FullSangamPlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Full Sangam");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Pana");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(SelectGameActivity.this, PlayGameActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Jodi Digit");
                intent.putExtra("stTime", startTime);
                intent.putExtra("clTime", closeTime);
                //naaaa
                intent.putExtra("game", "Digit");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);


            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }
}