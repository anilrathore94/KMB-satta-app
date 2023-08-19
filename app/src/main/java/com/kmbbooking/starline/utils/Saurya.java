package com.kmbbooking.starline.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kmbbooking.starline.model.UpdateFragmentModel;
import com.krishna.securetimer.SecureTimer;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Rahul on 12/22/2020.
 */

//---------------------
//----------5%---------
//---------------------
//---------------------

public class Saurya extends Application {

    public static final String TAG = Application.class.getSimpleName();
    public static List<UpdateFragmentModel> updateFragmentModelList;
    public static Context MY_APP_CONTEXT = null;
    public static String MY_APP_SHARED_PREFERENCES = Cofig.DB_NAME;
    public static SharedPreferences mPreferences;
    private static Saurya mInstance;

    public static Context getMyappContext() {
        return MY_APP_CONTEXT;
    }

    public void setMyappContext(Context mContext) {
        MY_APP_CONTEXT = mContext;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MY_APP_SHARED_PREFERENCES, 0);

    }

    public static void writeIntPreference(String key, int value) {
        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }


    public static void writeStringPreference(String key, String value) {
        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();

    }

    public static String check(String timeOne, String timeTwo) {

        Log.d("asdfs", timeOne);
        Log.d("asdfs", timeTwo);

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        String inputPattern = "hh:mm a";


        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        long currentTimeInMillis = SecureTimer.with(MY_APP_CONTEXT).getCurrentTimeInMillis();
        Date resultdate = new Date(currentTimeInMillis);
        String currebtTime = inputFormat.format(resultdate);


        Date dateOne = null;
        Date dateTwo = null;
        Date dateCurrent = null;


        String str = "";

        try {
            Date _24HourDt = _24HourSDF.parse(timeOne);
            dateOne = inputFormat.parse(_12HourSDF.format(_24HourDt));
            dateTwo = inputFormat.parse(timeTwo);
            dateCurrent = inputFormat.parse(currebtTime);

            Log.d("asdf", _24HourSDF.format(dateOne));
            Log.d("asdf", _24HourSDF.format(dateTwo));
            Log.d("asdf", _24HourSDF.format(dateCurrent));


            if (dateCurrent.after(dateOne) && dateCurrent.before(dateTwo)) {
                str = "yes";
            } else {
                str = "no";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return str;
    }

    public static String check1(String timeOne, String timeTwo) {

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");

        String inputPattern = "hh:mm a";


        long currentTimeInMillis = SecureTimer.with(MY_APP_CONTEXT).getCurrentTimeInMillis();
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        Date dateOne = null;
        Date dateTwo = null;
        Date dateCurrent = null;

        String str = "";

        try {

            dateOne = inputFormat.parse(timeOne);
            dateTwo = inputFormat.parse(timeTwo);
            dateCurrent = new Date(currentTimeInMillis);

            Log.d("asdf", _24HourSDF.format(dateOne));
            Log.d("asdf", _24HourSDF.format(dateTwo));
            Log.d("asdf", _24HourSDF.format(dateCurrent));

            if (dateCurrent.after(dateOne) && dateCurrent.before(dateTwo)) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                str = "yes";
            } else {
                str = "no";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return str;
    }


    public static String getTime(String dateValue) {

        String inputPattern = "HH:mm";
        String outputPattern = "hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateValue);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str.toUpperCase();
    }

    public static String getDate(String dateValue) {

        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEEE (dd MMM yyyy)";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateValue);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void showPromptDialog(final Activity activity, String title, String message, String buttonString, int promptType) {

        if (!activity.isFinishing()) {


        }

    }


    public static String ReadStringPreferences(String key) {

        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
        return mPreferences.getString(key, "");

    }

    public static int ReadIntPreferences(String key) {

        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
        return mPreferences.getInt(key, 0);

    }

    public static synchronized Saurya getInstance() {
        return mInstance;
    }

    public static void ClearPriferences() {

        mPreferences = getSharedPreferences(MY_APP_CONTEXT);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }


    public static void logout_clean_data() {

        String FILE_PATH = "/sdcard/Android/data/starline.starline/files/Download/video/.data/";

        File dir = new File(FILE_PATH);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(newBase);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setMyappContext(getApplicationContext());
        updateFragmentModelList = new ArrayList<UpdateFragmentModel>();
        mInstance = this;
    }

}
