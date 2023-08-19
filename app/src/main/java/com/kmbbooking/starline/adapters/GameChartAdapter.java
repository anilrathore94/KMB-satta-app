package com.kmbbooking.starline.adapters;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.model.GameChartModel;
import com.kmbbooking.starline.utils.Saurya;

import java.util.List;

/**
 * Created by Rahul on 12/22/2020.
 */

public class GameChartAdapter extends RecyclerView.Adapter<GameChartAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<GameChartModel> moviesList;


    public GameChartAdapter(List<GameChartModel> moviesList) {
        this.moviesList = moviesList;
    }

    public static String insertPeriodically(String textValue, int n) {
        StringBuilder str = new StringBuilder(textValue);
        int idx = str.length() - n;
        while (idx > 0) {
            str.insert(idx, "\n");
            idx = idx - n;
        }
        return str.toString();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chart_report_new, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new MyViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final GameChartModel model = moviesList.get(position);

//        holder.tvTopic.setText("Game Name  : " + model.getGame_name());
//        holder.tvSubTopic.setText("Game Date  : " + model.getResult_date());


        String[] open = model.getOpen_number().split("-", 2);
        String[] close = model.getClose_number().split("-", 2);


        holder.tvTopic.setText(insertPeriodically(open[0], 1));
        holder.tvTopicLast.setText(insertPeriodically(close[0], 1));

        holder.tvSubTopic.setText(open[1] + close[1]);
        holder.tvDate.setText(Saurya.getDate(model.getResult_date()));

        if (open[1].equalsIgnoreCase(close[1])) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else if ((open[1].equalsIgnoreCase("0") || open[1].equalsIgnoreCase("5")) && (close[1].equalsIgnoreCase("0") || close[1].equalsIgnoreCase("5"))) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else if ((open[1].equalsIgnoreCase("1") || open[1].equalsIgnoreCase("6")) && (close[1].equalsIgnoreCase("1") || close[1].equalsIgnoreCase("6"))) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else if ((open[1].equalsIgnoreCase("2") || open[1].equalsIgnoreCase("7")) && (close[1].equalsIgnoreCase("2") || close[1].equalsIgnoreCase("7"))) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else if ((open[1].equalsIgnoreCase("3") || open[1].equalsIgnoreCase("8")) && (close[1].equalsIgnoreCase("3") || close[1].equalsIgnoreCase("8"))) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else if ((open[1].equalsIgnoreCase("4") || open[1].equalsIgnoreCase("9")) && (close[1].equalsIgnoreCase("4") || close[1].equalsIgnoreCase("9"))) {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#FF0000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#FF0000"));

        } else {
            holder.tvTopic.setTextColor(Color.parseColor("#000000"));
            holder.tvTopicLast.setTextColor(Color.parseColor("#000000"));
            holder.tvSubTopic.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return moviesList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopicLast, tvTopic, tvSubTopic, tvDate;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvSubTopic = (TextView) convertView.findViewById(R.id.tvSubTopic);
            tvTopicLast = (TextView) convertView.findViewById(R.id.tvTopicLast);
            tvDate = (TextView) convertView.findViewById(R.id.tvDate);


        }
    }
}