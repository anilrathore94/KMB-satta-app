package com.kmbbooking.starline.adapters;

import android.app.ProgressDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.model.WinHistoryModel;

import java.util.List;


/**
 * Created by Rahul on 12/22/2020.
 */

public class WinHistoryStarAdapter extends RecyclerView.Adapter<WinHistoryStarAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<WinHistoryModel> moviesList;


    public WinHistoryStarAdapter(List<WinHistoryModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_win_report_star, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new MyViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final WinHistoryModel model = moviesList.get(position);

        holder.tvTopic.setText("Game Name : " + model.getGame_name());
        holder.tvSubTopic.setText("Game Type : " + model.getGame_type());

        holder.tvtest_paper.setText("Session : OPEN");

        holder.tvDivision.setText("Open Digit : " + model.getOpen_digit());
        holder.tvBatch.setText("Open Panna : " + model.getOpen_pana());
        holder.tvDate.setText("Date : " + model.getDate());

        holder.tvAmt.setText("Winning Points : " + model.getWinning_points());
        holder.tvAct.setText("Points Action : " + model.getPoints_action());
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
        TextView tvDivision, tvBatch, tvDate, tvTopic, tvSubTopic, tvtest_paper,
                tvAmt, tvAct;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvSubTopic = (TextView) convertView.findViewById(R.id.tvSubTopic);
            tvtest_paper = (TextView) convertView.findViewById(R.id.tvtest_paper);

            tvDivision = (TextView) convertView.findViewById(R.id.tvDivision);
            tvBatch = (TextView) convertView.findViewById(R.id.tvBatch);
            tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            tvAmt = (TextView) convertView.findViewById(R.id.tvAmt);
            tvAct = (TextView) convertView.findViewById(R.id.tvAct);


        }
    }
}