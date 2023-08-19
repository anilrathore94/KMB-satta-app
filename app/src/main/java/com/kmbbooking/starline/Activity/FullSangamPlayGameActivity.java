package com.kmbbooking.starline.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kmbbooking.starline.R;
import com.kmbbooking.starline.adapters.GamePlayFullAdapter;
import com.kmbbooking.starline.alerts.BottomSheetMaterialDialog;
import com.kmbbooking.starline.model.GamePlayModel;
import com.kmbbooking.starline.retro.ApiUtils;
import com.kmbbooking.starline.retro.RetrofitInterface;
import com.kmbbooking.starline.utils.DatabaseGamePlay;
import com.kmbbooking.starline.utils.Saurya;
import com.kmbbooking.starline.utils.NonScrolGridView;
import com.kmbbooking.starline.utils.SharedPrefData;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class FullSangamPlayGameActivity extends AppCompatActivity {
    
    static TextView btSubmitFinal;
    static ArrayList<GamePlayModel> notificationsModelArrayList;
    static DatabaseGamePlay database;
    static TextView totalPt;
    private static GamePlayFullAdapter updateFragmentAdapters;
    private static int totPoints = 0;
    String gameName, gameType, startTime, closeTime;
    TextView tvDate, typeVal;
    EditText etValue;
    AutoCompleteTextView myAutoComplete, etPoints;
    RadioButton rbOpen;
    String game;
    TextView btSubmit;
    String sessionType = "";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterPana;
    String gameList2[] = {
            "128", "129", "120", "130", "140", "123", "124", "125", "126", "127",
            "137", "138", "139", "149", "159", "150", "160", "134", "135", "136",
            "146", "147", "148", "158", "168", "169", "179", "170", "180", "145",
            "236", "156", "157", "167", "230", "178", "250", "189", "234", "190",
            "245", "237", "238", "239", "249", "240", "269", "260", "270", "235",
            "290", "246", "247", "248", "258", "259", "278", "279", "289", "280",
            "380", "345", "256", "257", "267", "268", "340", "350", "360", "370",
            "470", "390", "346", "347", "348", "349", "359", "369", "379", "389",
            "489", "480", "490", "356", "357", "358", "368", "378", "450", "460",
            "560", "570", "580", "590", "456", "367", "458", "459", "469", "479",
            "579", "589", "670", "680", "690", "457", "467", "468", "478", "569",
            "678", "679", "689", "789", "780", "790", "890", "567", "568", "578",
            "100", "110", "166", "112", "113", "114", "115", "116", "117", "118",
            "119", "200", "229", "220", "122", "277", "133", "224", "144", "226",
            "155", "228", "300", "266", "177", "330", "188", "233", "199", "244",
            "227", "255", "337", "338", "339", "448", "223", "288", "225", "299",
            "335", "336", "355", "400", "366", "466", "377", "440", "388", "334",
            "344", "499", "445", "446", "447", "556", "449", "477", "559", "488",
            "399", "660", "599", "455", "500", "600", "557", "558", "577", "550",
            "588", "688", "779", "699", "799", "880", "566", "800", "667", "668",
            "669", "778", "788", "770", "889", "899", "700", "990", "900", "677",
            "777", "444", "111", "888", "555", "222", "999", "666", "333", "000"
    };
    NonScrolGridView mHelpTextView;
    SQLiteDatabase sqLiteDatabase = null;

    private static void getAllNotifications() {

        totPoints = 0;

        Cursor cursor = null;
        notificationsModelArrayList.clear();
        SQLiteDatabase sqLiteDatabase = null;

        try {
            sqLiteDatabase = database.getWritableDatabase();

            String query = "select * from play_game ORDER BY id DESC";

            cursor = sqLiteDatabase.rawQuery(query, null);

            int ii = 0;
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {

                        if (cursor.moveToFirst()) {
                            do {
                                String id = cursor.getString(cursor.getColumnIndex("id"));

                                String push_date = cursor.getString(cursor.getColumnIndex("push_date"));
                                String push_session = cursor.getString(cursor.getColumnIndex("push_session"));
                                String push_open_pana = cursor.getString(cursor.getColumnIndex("push_open_pana"));

                                String push_close_pana = cursor.getString(cursor.getColumnIndex("push_close_pana"));
                                String push_open_digit = cursor.getString(cursor.getColumnIndex("push_open_digit"));
                                String push_close_digit = cursor.getString(cursor.getColumnIndex("push_close_digit"));
                                String push_points = cursor.getString(cursor.getColumnIndex("push_points"));
                                String push_type = cursor.getString(cursor.getColumnIndex("push_type"));

                                GamePlayModel notifcationDataModel = new GamePlayModel();

                                notifcationDataModel.setId(id);
                                notifcationDataModel.setDate(push_date);
                                notifcationDataModel.setSession(push_session);
                                notifcationDataModel.setOpen_pana(push_open_pana);


                                notifcationDataModel.setClose_pana(push_close_pana);
                                notifcationDataModel.setOpen_digit(push_open_digit);
                                notifcationDataModel.setClose_digit(push_close_digit);
                                notifcationDataModel.setPoints(push_points);
                                notifcationDataModel.setPush_type(push_type);

                                notificationsModelArrayList.add(notifcationDataModel);
                                totPoints = totPoints + Integer.parseInt(push_points);
                                ii++;

                            } while (cursor.moveToNext());
                        }


                    } while (cursor.moveToNext());
                }


            }


        } catch (SQLiteException e) {

            e.printStackTrace();
        } finally {
            if (sqLiteDatabase.isOpen()) {

                sqLiteDatabase.close();
            }
        }
        Log.d("ccount", String.valueOf(cursor.getCount()));

        updateFragmentAdapters.notifyDataSetChanged();

        if (cursor.getCount() > 0) {
            btSubmitFinal.setVisibility(View.VISIBLE);
            totalPt.setText("" + totPoints);
        } else {
            btSubmitFinal.setVisibility(View.GONE);
        }
        //


    }

    public static void deleteRowNew(String value) {

        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = database.getWritableDatabase();


        sqLiteDatabase.execSQL("DELETE FROM play_game WHERE id='" + value + "'");
        sqLiteDatabase.close();


        getAllNotifications();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_open);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvS = toolbar.findViewById(R.id.titleToolbar);
        tvS.setText("Full Sangam");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        btSubmitFinal = findViewById(R.id.btSubmitFinal);
        totalPt = findViewById(R.id.totalPt);


    
        typeVal = findViewById(R.id.typeVal);
        rbOpen = findViewById(R.id.rbOpen);
        etValue = findViewById(R.id.etValue);
        gameType = "";
        myAutoComplete = findViewById(R.id.autoCompleteTextView);
        etPoints = findViewById(R.id.etPoints);
        btSubmit = findViewById(R.id.btSubmit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameName = extras.getString("gameNm");
            gameType = extras.getString("gameType");
            startTime = extras.getString("stTime");
            closeTime = extras.getString("clTime");
            game = extras.getString("game");

            if (Saurya.check(Saurya.ReadStringPreferences(SharedPrefData.PREF_START_TIME), startTime)
                    .equalsIgnoreCase("yes")) {
                rbOpen.setChecked(true);
                rbOpen.setClickable(true);
                sessionType = "Open";


            } else {

                rbOpen.setChecked(false);
                rbOpen.setClickable(false);
                sessionType = "Close";

            }


            myAutoComplete.setHint("Open Pana");
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, gameList2);

            etPoints.setHint("close Pana");
            adapterPana = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, gameList2);


        }

        // and get whatever type user account id is
        tvDate = findViewById(R.id.tvDate);
        String date = new SimpleDateFormat("MMM,dd\nyyyy", Locale.getDefault()).format(new Date());
        tvDate.setText(date);


        //Getting the instance of AutoCompleteTextView
        myAutoComplete.setThreshold(1);//will start working from first character
        myAutoComplete.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


        etPoints.setThreshold(1);//will start working from first character
        etPoints.setAdapter(adapterPana);//setting the adapter data into the AutoCompleteTextView


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                    tvDate.setError("Enter Valid Date!!");
                    return;
                }


                if (TextUtils.isEmpty(myAutoComplete.getText().toString().trim())) {
                    myAutoComplete.setError("Select One!!");
                    return;
                }

                if (game(myAutoComplete.getText().toString().trim(), gameList2).equalsIgnoreCase("false")) {
                    myAutoComplete.setError("Wrong Value!!");
                    return;
                }


                if (TextUtils.isEmpty(etPoints.getText().toString().trim())) {
                    etPoints.setError("Select One!!");
                    return;
                }


                if (game(etPoints.getText().toString().trim(), gameList2).equalsIgnoreCase("false")) {
                    etPoints.setError("Wrong Value!!");
                    return;
                }

                if (TextUtils.isEmpty(etValue.getText().toString().trim())) {
                    etValue.setError("Enter Points!!");
                    return;
                }

                if (Integer.parseInt(etValue.getText().toString().trim()) < Integer.parseInt(Saurya.ReadStringPreferences(SharedPrefData.MIN_BID)) ||
                        Integer.parseInt(etValue.getText().toString().trim()) > Integer.parseInt(Saurya.ReadStringPreferences(SharedPrefData.MAX_BID))) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FullSangamPlayGameActivity.this);
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Minimum bid points " + Saurya.ReadStringPreferences(SharedPrefData.MIN_BID) +
                            " Maximum bid points " + Saurya.ReadStringPreferences(SharedPrefData.MAX_BID));
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();

                        }
                    });

                    alertDialog.show();
                    return;
                }

                String open_pana = "";
                String open_digit = "";
                String close_pana = "";
                String close_digit = "";


                open_pana = myAutoComplete.getText().toString();
                close_pana = etPoints.getText().toString();

                open_digit = "NA";
                close_digit = "NA";

                saveEntry(tvDate.getText().toString(), sessionType,
                        open_pana, close_pana,
                        open_digit, close_digit, String.valueOf(Integer.parseInt(etValue.getText().toString())));


            }
        });

        database = new DatabaseGamePlay(FullSangamPlayGameActivity.this);
        sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM play_game");
        sqLiteDatabase.close();

        mHelpTextView = findViewById(R.id.list);

        notificationsModelArrayList = new ArrayList<GamePlayModel>();

        updateFragmentAdapters = new GamePlayFullAdapter(FullSangamPlayGameActivity.this, notificationsModelArrayList);

        mHelpTextView.setAdapter(updateFragmentAdapters);

        getAllNotifications();


        btSubmitFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase sqLiteDatabase = null;

                try {
                    sqLiteDatabase = database.getWritableDatabase();
                    String query = "select * from play_game ORDER BY id DESC";
                    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                    if (cursor != null) {

                        if (cursor.getCount() > 0) {
                            JSONArray jsa = new JSONArray();
                            if (cursor.moveToFirst()) {
                                do {
                                    String id = cursor.getString(cursor.getColumnIndex("id"));

                                    String push_date = cursor.getString(cursor.getColumnIndex("push_date"));
                                    String push_session = cursor.getString(cursor.getColumnIndex("push_session"));
                                    String push_open_pana = cursor.getString(cursor.getColumnIndex("push_open_pana"));

                                    String push_close_pana = cursor.getString(cursor.getColumnIndex("push_close_pana"));
                                    String push_open_digit = cursor.getString(cursor.getColumnIndex("push_open_digit"));
                                    String push_close_digit = cursor.getString(cursor.getColumnIndex("push_close_digit"));
                                    String push_points = cursor.getString(cursor.getColumnIndex("push_points"));


                                    JSONObject cust = new JSONObject();
                                    cust.put("phone_number", Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE));
                                    cust.put("game_name", gameName);
                                    cust.put("game_type", gameType);
                                    cust.put("session", push_session);

                                    cust.put("open_pana", push_open_pana);
                                    cust.put("open_digit", push_open_digit);
                                    cust.put("close_pana", push_close_pana);
                                    cust.put("close_digit", push_close_digit);
                                    cust.put("points_action", push_points);


                                    jsa.put(cust);

                                } while (cursor.moveToNext());
                            }
                            Log.d("test11", String.valueOf(jsa));
                            getWallet(String.valueOf(jsa));


                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(FullSangamPlayGameActivity.this);
                            alertDialog.setTitle("Info");
                            alertDialog.setMessage("Please Add Class here...");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();

                                }
                            });

                            alertDialog.show();
                        }


                    }


                } catch (SQLiteException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (sqLiteDatabase.isOpen()) {

                        sqLiteDatabase.close();
                    }
                }


            }
        });

    }

    private void saveEntry(String push_Date, String push_session, String push_open_pana, String push_close_pana,
                           String push_open_digit, String push_close_digit, String KEY_POINTS) {
        long data = -1;
        SQLiteDatabase sqLiteDatabase = null;

        try {

            sqLiteDatabase = database.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("push_Date", push_Date);
            contentValues.put("push_session", push_session);
            contentValues.put("push_open_pana", push_open_pana);
            contentValues.put("push_close_pana", push_close_pana);

            contentValues.put("push_open_digit", push_open_digit);
            contentValues.put("push_close_digit", push_close_digit);
            contentValues.put("push_points", KEY_POINTS);
            contentValues.put("push_type", "Open Pana");
            Log.e("contentValues", "" + contentValues);

            data = sqLiteDatabase.insert("play_game", null, contentValues);
            Log.e("data", "" + data);

        } catch (SQLiteException e) {

            e.printStackTrace();
        } finally {
            if (sqLiteDatabase.isOpen()) {


                sqLiteDatabase.close();
            }
        }


        etPoints.setText("");
        myAutoComplete.setText("");


        getAllNotifications();

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        sessionType = "";
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbOpen:
                if (checked)
                    sessionType = "Open";
                break;

        }
    }

    private void getWallet(final String bidData) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);


        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();

        mAPIService.getWallet(Saurya.ReadStringPreferences(SharedPrefData.PREF_LOGINPHONE)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response1) {
                dialog.dismiss();


                Log.d("Response", String.valueOf(response1));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response1.body()));


                    JSONObject json = jsonObject.getJSONObject("data");
                    Saurya.writeStringPreference(SharedPrefData.PREF_WALLET, json.optString("wallet"));

                    if (Integer.parseInt(json.optString("wallet")) >= totPoints) {

                        Regist(bidData);

                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FullSangamPlayGameActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage(jsonObject.optString("msg"));
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                        alertDialog.show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    private void Regist(final String bidData) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);
        RetrofitInterface mAPIService;
        mAPIService = ApiUtils.getAPIService();
        mAPIService.newStarBidSANGAM(bidData).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));

                    if (jsonObject.optString("success").equalsIgnoreCase("1")) {

                        totalPt.setText("0.0");
                        sqLiteDatabase = database.getWritableDatabase();
                        sqLiteDatabase.execSQL("DELETE FROM play_game");
                        sqLiteDatabase.close();
                        getAllNotifications();


                        BottomSheetMaterialDialog mAnimatedBottomSheetDialoga = new BottomSheetMaterialDialog.Builder(FullSangamPlayGameActivity.this)
                                .setTitle("Success?")
                                .setMessage(jsonObject.optString("msg"))
                                .setCancelable(false)
                                .setPositiveButton("Play Again", R.drawable.sss, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(final com.kmbbooking.starline.alerts.interfaces.DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                    }
                                })
                                .setNegativeButton("Cancel", R.drawable.ss, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(com.kmbbooking.starline.alerts.interfaces.DialogInterface dialogInterface, int which) {
                                        Intent mainIntent = new Intent(FullSangamPlayGameActivity.this, HomeActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setAnimation("delete_anim.json")
                                .build();
                        mAnimatedBottomSheetDialoga.show();

                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FullSangamPlayGameActivity.this);
                        alertDialog.setTitle("Info !");
                        alertDialog.setMessage(jsonObject.optString("msg"));
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
            }


        });
        dialog.show();

    }

   

    public String game(String test, String store[]) {
        String value = "false";


        for (int i = 0; i < store.length; i++) {
            if (store[i].equalsIgnoreCase(test)) {
                value = "true";
            }

        }

        return value;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }
}