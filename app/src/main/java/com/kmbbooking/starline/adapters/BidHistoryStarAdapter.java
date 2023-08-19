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
import com.kmbbooking.starline.model.BidHistoryData;

import java.util.List;


/**
 * Created by Rahul on 12/22/2020.
 */

public class BidHistoryStarAdapter extends RecyclerView.Adapter<BidHistoryStarAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<BidHistoryData> moviesList;


    public BidHistoryStarAdapter(List<BidHistoryData> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bid_report, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new MyViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final BidHistoryData model = moviesList.get(position);

        holder.tvTopic.setText(model.getGame_name() + " (" + model.getGame_type() + ")");

        holder.tvSession.setText("Session : OPEN");

        if (model.getOpen_digit().equalsIgnoreCase("")) {
            holder.tvDivision.setVisibility(View.GONE);
        } else {
            holder.tvDivision.setVisibility(View.VISIBLE);
        }


        if (model.getOpen_pana().equalsIgnoreCase("")) {
            holder.tvDivision1.setVisibility(View.GONE);
        } else {
            holder.tvDivision1.setVisibility(View.VISIBLE);
        }


        holder.tvDivision.setText("Digit : " + model.getOpen_digit());
        holder.tvDivision1.setText("Pana : " + model.getOpen_pana());


        holder.tvPoint.setText(model.getPoints() + " PTS.");

        holder.tvDate.setText("Date : " + model.getDate());

        holder.tvDivision11.setVisibility(View.GONE);
        holder.tvDivision21.setVisibility(View.GONE);

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
        TextView tvDivision, tvDivision1, tvDivision11, tvDivision21, tvTopic, tvDate, tvSession, tvPoint;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvSession = (TextView) convertView.findViewById(R.id.tvSession);
            tvPoint = (TextView) convertView.findViewById(R.id.tvPoint);
            tvDivision1 = (TextView) convertView.findViewById(R.id.tvDivision1);
            tvDivision = (TextView) convertView.findViewById(R.id.tvDivision);
            tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            tvDivision11 = (TextView) convertView.findViewById(R.id.tvDivision11);
            tvDivision21 = (TextView) convertView.findViewById(R.id.tvDivision21);

        }
    }
}