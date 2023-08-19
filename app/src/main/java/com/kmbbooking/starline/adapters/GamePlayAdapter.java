package com.kmbbooking.starline.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.kmbbooking.starline.Activity.PlayGameActivity;
import com.kmbbooking.starline.R;
import com.kmbbooking.starline.model.GamePlayModel;

import java.util.List;


/**
 * Created by rahul on 11/05/2019.
 */

public class GamePlayAdapter extends BaseAdapter {


    public static Context activity;
    private LayoutInflater inflater;
    private List<GamePlayModel> UpdateFragmentModel;

    public GamePlayAdapter(Activity updateffagmentDetailsActivity, List<GamePlayModel> UpdateFragmentModel1) {


        this.activity = updateffagmentDetailsActivity;
        UpdateFragmentModel = UpdateFragmentModel1;

    }

    @Override
    public int getCount() {
        return UpdateFragmentModel.size();
    }

    @Override
    public Object getItem(int location) {
        return UpdateFragmentModel.get(location);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = new ViewHolder();

        final GamePlayModel model = UpdateFragmentModel.get(position);


        if (convertView == null) {


            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_course_report_new, null);

            holder.tvDivision1 = (TextView) convertView.findViewById(R.id.tvS);
            holder.tvDivision = (TextView) convertView.findViewById(R.id.tvTicket);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tvDelete);
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are You Want to Delete Entry");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        PlayGameActivity.deleteRowNew(model.getId());
                        dialog.cancel();

                    }
                });

                alertDialog.show();

            }
        });


        String getOpen_digit = "", getOpen_pana = "", getClose_digit = "", getClose_pana = "";

        getOpen_digit = model.getOpen_digit();
        getOpen_pana = model.getOpen_pana();
        getClose_digit = model.getClose_digit();
        getClose_pana = model.getClose_pana();
        if (model.getOpen_digit().equalsIgnoreCase("NA") || model.getOpen_digit().equalsIgnoreCase("")) {
            getOpen_digit = "";
        }

        if (model.getOpen_pana().equalsIgnoreCase("NA") || model.getOpen_pana().equalsIgnoreCase("")) {
            getOpen_pana = "";
        }

        if (model.getClose_digit().equalsIgnoreCase("NA") || model.getClose_digit().equalsIgnoreCase("")) {
            getClose_digit = "";
        }

        if (model.getClose_pana().equalsIgnoreCase("NA") || model.getClose_pana().equalsIgnoreCase("")) {
            getClose_pana = "";
        }


        holder.tvDivision1.setText( getOpen_digit + getOpen_pana + getClose_digit + getClose_pana);
        holder.tvDivision.setText( model.getPoints());
        holder.tvDelete.setText(model.getSession());


        return convertView;
    }


    static class ViewHolder {

        ImageView ivDelete;
        TextView tvDivision, tvDivision1;
        TextView tvDelete;

    }


}
