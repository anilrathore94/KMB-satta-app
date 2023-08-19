package com.kmbbooking.starline.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.Activity.GameChartHistory;
import com.kmbbooking.starline.Activity.SelectGameActivity;
import com.kmbbooking.starline.R;
import com.kmbbooking.starline.model.GameData;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.SharedPrefData;

import java.util.List;

public class GameDataAdapter extends RecyclerView.Adapter<GameDataAdapter.ViewHolder> {

    List<GameData> gameData;
    Context context;
    Activity mActivity;

    public GameDataAdapter(List<GameData> gameData, Activity activity) {
        this.gameData = gameData;
        this.context = activity;
        this.context = activity;
        this.mActivity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_recyclerview_samples, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GameData myGameData = gameData.get(position);
        holder.gameName.setText(myGameData.getGameName());
        String result = myGameData.getOpenPana() + "-" + myGameData.getOpenDigit() + "" + myGameData.getCloseDigit() + "-" + myGameData.getClosePana();
        holder.digit.setText(result);
        holder.openTime.setText("Opne Bid Close Time : "+myGameData.getOpenTime() +"  Close Bid Close Time : "+myGameData.getCloseTime());
        holder.openTime.setSelected(true);
        if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getCloseTime()).equalsIgnoreCase("yes")) {


            if (myGameData.getGameStatus().equalsIgnoreCase("0")) {
                holder.marketStatus.setText("Market Closed For Toaday !");
                holder.llStarLineSelect.setBackgroundColor(Color.parseColor("#FF0000"));
                holder.llPlay.setBackgroundResource(R.drawable.control);
            } else {
                holder.marketStatus.setText("Market is Open. Enjoy !");
                holder.llStarLineSelect.setBackgroundColor(Color.parseColor("#58C569"));
                holder.llPlay.setBackgroundResource(R.drawable.control);
            }

        } else {
            holder.marketStatus.setText("Market Closed For Toaday !");
            holder.llStarLineSelect.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.llPlay.setBackgroundResource(R.drawable.control);

        }




        if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("1")) {

            holder.tvPlay.setText("  Play Game");
            holder.llPlay.setVisibility(View.VISIBLE);
            holder.llStarLineSelect.setVisibility(View.VISIBLE);

            if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getCloseTime()).equalsIgnoreCase("yes")) {
                holder.llPlay.setBackgroundResource(R.drawable.control);

            } else {

                holder.llPlay.setBackgroundResource(R.drawable.control);
            }

        } else {
            holder.tvPlay.setText("  Chart");
            holder.llPlay.setVisibility(View.VISIBLE);
            holder.llStarLineSelect.setVisibility(View.INVISIBLE);

            holder.llPlay.setBackgroundResource(R.drawable.chart);
        }


        holder.llChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, GameChartHistory.class);
                intent.putExtra("gameName", myGameData.getGameName());
                context.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
            }
        });

        holder.llPlayNew.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {


                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("1")) {

                    if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getCloseTime()).equalsIgnoreCase("yes")) {


                        if (myGameData.getGameStatus().equalsIgnoreCase("0")) {


                            Toast.makeText(context, "Market Closed for Today", 5000).show();
                        } else {
                            Intent intent = new Intent(context, SelectGameActivity.class);
                            intent.putExtra("gameName", myGameData.getGameName());
                            intent.putExtra("stTimeN", myGameData.getOpenTime());
                            intent.putExtra("clTimeN", myGameData.getCloseTime());
                            context.startActivity(intent);
                            mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                        }


                    } else {


                        Toast.makeText(context, "Market Closed..", 5000).show();
                    }


                } else {

                    Intent intent = new Intent(context, GameChartHistory.class);
                    intent.putExtra("gameName", myGameData.getGameName());
                    context.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                }


                if (Saurya.ReadStringPreferences(SharedPrefData.PREF_STATUS).equalsIgnoreCase("0")) {
                    //Toast.makeText(context,"Not Activated",5000).show();
                    return;
                }


                if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), myGameData.getCloseTime()).equalsIgnoreCase("yes")) {


                    if (myGameData.getGameStatus().equalsIgnoreCase("0")) {


                        Toast.makeText(context, "Market Closed for Today", 5000).show();
                    } else {
                        Intent intent = new Intent(context, SelectGameActivity.class);
                        intent.putExtra("gameName", myGameData.getGameName());
                        intent.putExtra("stTimeN", myGameData.getOpenTime());
                        intent.putExtra("clTimeN", myGameData.getCloseTime());
                        context.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                    }


                } else {


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
        TextView digit, gameName, openTime;
        TextView tvPlay;
        LinearLayout llPlay, llChart, llStarLineSelect,llPlayNew;
        TextView marketStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlay= itemView.findViewById(R.id.tvPlay);
            llPlayNew = itemView.findViewById(R.id.llPlayNew);
            llStarLineSelect = itemView.findViewById(R.id.llStarLineSelect);
            gameName = itemView.findViewById(R.id.gameName);
            digit = itemView.findViewById(R.id.digit);
            openTime = itemView.findViewById(R.id.openTime);
            marketStatus = itemView.findViewById(R.id.marketStatus);
            llPlay = itemView.findViewById(R.id.llPlay);
            llChart = itemView.findViewById(R.id.llChart);

        }
    }


}