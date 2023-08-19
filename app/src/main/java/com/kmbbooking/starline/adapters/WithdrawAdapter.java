package com.kmbbooking.starline.adapters;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.kmbbooking.starline.R;
import com.kmbbooking.starline.model.WithdrawModel;

import java.util.List;


/**
 * Created by Rahul on 12/22/2020.
 */

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<WithdrawModel> moviesList;


    public WithdrawAdapter(List<WithdrawModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wallet_report, parent, false);
            return new MyViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new MyViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final WithdrawModel model = moviesList.get(position);
        holder.tvRemark.setText(model.getRemark());
        holder.tvAmount.setText(model.getPoints());
        holder.tvDate.setText(model.getDate());

        if(model.getStatus().equalsIgnoreCase("0"))
        {
            holder.tvRemark.setTextColor(Color.parseColor("#000000"));
            holder.tvAmount.setTextColor(Color.parseColor("#000000"));
            holder.llPlay.setBackgroundResource(R.drawable.pending);
        }
        else if(model.getStatus().equalsIgnoreCase("1"))
        {
            holder.tvRemark.setTextColor(Color.parseColor("#3A600D"));
            holder.tvAmount.setTextColor(Color.parseColor("#3A600D"));
            holder.llPlay.setBackgroundResource(R.drawable.succ);
        }
        else if(model.getStatus().equalsIgnoreCase("-1"))
        {
            holder.tvRemark.setTextColor(Color.parseColor("#E42020"));
            holder.tvAmount.setTextColor(Color.parseColor("#E42020"));
            holder.llPlay.setBackgroundResource(R.drawable.rej);
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
        TextView tvAmount, tvRemark,tvDate;
        LinearLayout llPlay;
        public MyViewHolder(View convertView) {
            super(convertView);

            tvAmount = (TextView) convertView.findViewById(R.id.tvTopic);
            tvRemark = (TextView) convertView.findViewById(R.id.tvtest_paper);
            tvDate = (TextView) convertView.findViewById(R.id.tvDivision);

            llPlay = (LinearLayout) convertView.findViewById(R.id.llPlay);


        }
    }
}