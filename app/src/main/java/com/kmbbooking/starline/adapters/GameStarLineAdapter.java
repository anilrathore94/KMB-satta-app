package com.kmbbooking.starline.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.Starline.GameStarChartHistory;
import com.kmbbooking.starline.Starline.SelectStarLineGameActivity;
import com.kmbbooking.starline.model.GameData;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;

import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class GameStarLineAdapter extends RecyclerView.Adapter<GameStarLineAdapter.ViewHolder> {

    List<GameData> gameData;
    Context context;
    Activity mActivity;

    public GameStarLineAdapter(List<GameData> gameData, Activity activity) {
        this.gameData = gameData;
        this.context = activity;
        this.context = activity;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_recyclerview_star, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GameData myGameData = gameData.get(position);
        holder.gameName.setText(myGameData.getOpenTime());
        String result = myGameData.getOpenDigit() + "-" + myGameData.getOpenPana();
        holder.digit.setText(result);


        holder.gameName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/compass.ttf"));
        holder.digit.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/compass.ttf"));
        holder.marketStatus.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/compass.ttf"));

        if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getOpenTime()).equalsIgnoreCase("yes")) {
            holder.marketStatus.setText("Starline is Open. Enjoy !");
            holder.marketStatus.setTextColor(Color.parseColor("#000000"));
            holder.llPlay.setBackgroundResource(R.drawable.play);

        } else {
            holder.marketStatus.setText("Starline Game Closed For Toaday !");
            holder.marketStatus.setTextColor(Color.parseColor("#FF0000"));
            holder.llPlay.setBackgroundResource(R.drawable.close);
        }


        holder.llChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {
                    //Toast.makeText(context,"Not Activated",5000).show();
                    return;
                }

                Intent intent = new Intent(context, GameStarChartHistory.class);
                intent.putExtra("gameName", myGameData.getGameName());
                context.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);

            }
        });

        holder.llPlay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getOpenTime()).equalsIgnoreCase("yes")) {
                    Intent intent = new Intent(context, SelectStarLineGameActivity.class);
                    intent.putExtra("gameName", myGameData.getGameName());
                    intent.putExtra("stTimeN", myGameData.getOpenTime());
                    context.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                } else {

                    Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(200);
                    }
                    Toast.makeText(context, "Market Closed..", 5000).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return gameData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameName;
        TextView digit;
        TextView marketStatus;
        LinearLayout llPlay, llChart, llBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBack = itemView.findViewById(R.id.llBack);
            gameName = itemView.findViewById(R.id.gameName);
            digit = itemView.findViewById(R.id.digit);
            marketStatus = itemView.findViewById(R.id.marketStatus);
            llPlay = itemView.findViewById(R.id.llPlay);
            llChart = itemView.findViewById(R.id.llChart);
        }
    }
}