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
import com.kmbbooking.starline.model.WalletModel;

import java.util.List;


/**
 * Created by Rahul on 12/22/2020.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<WalletModel> moviesList;


    public WalletAdapter(List<WalletModel> moviesList) {
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

        final WalletModel model = moviesList.get(position);

        holder.tvTopic.setText(model.getAmount());
        holder.tvtest_paper.setText(model.getRemark());

        holder.tvDivision.setText(model.getDate());

        if (Double.parseDouble(model.getAmount()) >= 0.0) {
            holder.tvTopic.setTextColor(Color.parseColor("#417800"));
            holder.llPlay.setBackgroundResource(R.drawable.w_add);
        } else {
            holder.tvTopic.setTextColor(Color.parseColor("#FF0000"));
            holder.llPlay.setBackgroundResource(R.drawable.w_minus);
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
        TextView tvDivision, tvTopic, tvtest_paper;
        LinearLayout llPlay;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvtest_paper = (TextView) convertView.findViewById(R.id.tvtest_paper);
            llPlay = (LinearLayout) convertView.findViewById(R.id.llPlay);
            tvDivision = (TextView) convertView.findViewById(R.id.tvDivision);


        }
    }
}