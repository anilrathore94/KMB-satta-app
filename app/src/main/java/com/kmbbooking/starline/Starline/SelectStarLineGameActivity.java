package com.kmbbooking.starline.Starline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;


public class SelectStarLineGameActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4;
    String gameName, startTime;
    String timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game_star);


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

                Intent mainIntent = new Intent(getApplicationContext(), com.kmbbooking.starline.Starline.HomeStarLineActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameName = extras.getString("gameName");
            startTime = extras.getString("stTimeN");
            timeValue = Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), startTime);

        }

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.kmbbooking.starline.Starline.SelectStarLineGameActivity.this, PlayGameStarActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Single Digit");
                intent.putExtra("stTime", startTime);
                intent.putExtra("game", "Digit");
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.kmbbooking.starline.Starline.SelectStarLineGameActivity.this, PlayGameStarActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Single Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("game", "Pana");
                startActivity(intent);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.kmbbooking.starline.Starline.SelectStarLineGameActivity.this, PlayGameStarActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Double Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("game", "Pana");
                startActivity(intent);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.kmbbooking.starline.Starline.SelectStarLineGameActivity.this, PlayGameStarActivity.class);
                intent.putExtra("gameNm", gameName);
                intent.putExtra("gameType", "Triple Pana");
                intent.putExtra("stTime", startTime);
                intent.putExtra("game", "Pana");
                startActivity(intent);
            }
        });

    }
}