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

public class WinHistoryAdapter extends RecyclerView.Adapter<WinHistoryAdapter.MyViewHolder> {
    public static ProgressDialog pDialog;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<WinHistoryModel> moviesList;


    public WinHistoryAdapter(List<WinHistoryModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_win_report, parent, false);
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

        holder.tvTopic.setText("Game Name  : " + model.getGame_name());
        holder.tvSubTopic.setText("Game Type  : " + model.getGame_type());

        if (model.getSession().equalsIgnoreCase("0")) {
            holder.tvtest_paper.setText("Session : CLOSE");
        } else {
            holder.tvtest_paper.setText("Session : OPEN");
        }


        holder.tvDivision.setText("Open Digit  : " + model.getOpen_digit());
        holder.tvBatch.setText("Open Panna  : " + model.getOpen_pana());
        holder.tvClass.setText("Close Digit  : " + model.getClose_digit());
        holder.tvSubject.setText("Close Panna  : " + model.getClose_pana());

        holder.tvAmt.setText("Winning Points  : " + model.getWinning_points());
        holder.tvAct.setText("Points Action  : " + model.getPoints_action());

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
        TextView tvDivision, tvBatch, tvClass, tvSubject, tvTopic, tvSubTopic, tvtest_paper,
                tvAmt, tvAct;

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            tvSubTopic = (TextView) convertView.findViewById(R.id.tvSubTopic);
            tvtest_paper = (TextView) convertView.findViewById(R.id.tvtest_paper);

            tvDivision = (TextView) convertView.findViewById(R.id.tvDivision);
            tvBatch = (TextView) convertView.findViewById(R.id.tvBatch);
            tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);

            tvAmt = (TextView) convertView.findViewById(R.id.tvAmt);
            tvAct = (TextView) convertView.findViewById(R.id.tvAct);


        }
    }
}