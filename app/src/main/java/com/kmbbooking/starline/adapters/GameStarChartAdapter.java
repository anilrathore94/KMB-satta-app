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

public class GameStarChartAdapter extends RecyclerView.Adapter<GameStarChartAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<GameChartModel> moviesList;


    public GameStarChartAdapter(List<GameChartModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chart_report_star, parent, false);
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


        String[] open = model.getOpen_number().split("-", 2);


        holder.tvTopic.setText(open[0]);

        holder.tvSubTopic.setText(open[1]);
        holder.tvDate.setText(Saurya.getDate(model.getResult_date()));

        holder.tvTopic.setTextColor(Color.parseColor("#000000"));
        holder.tvSubTopic.setTextColor(Color.parseColor("#000000"));
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
        TextView tvTopic, tvSubTopic, tvDate;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvSubTopic = (TextView) convertView.findViewById(R.id.tvSubTopic);
            tvDate = (TextView) convertView.findViewById(R.id.tvDate);


        }
    }
}