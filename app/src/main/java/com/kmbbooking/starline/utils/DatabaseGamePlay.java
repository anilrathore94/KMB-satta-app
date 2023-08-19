package com.kmbbooking.starline.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rahul on 11/05/2019.
 */

public class DatabaseGamePlay extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Cofig.DB_NAME + "_game_db_play";
    private static final String TABLE_PUSH_NOTIFICATIONSALERTS = "play_game";

    // TABLE_LOGIN_DETAILS column names
    private static final String KEY_IDPUSH = "id";

    private static final String KEY_DATE = "push_date";
    private static final String KEY_SESSION = "push_session";

    private static final String KEY_OPEN_PANA = "push_open_pana";
    private static final String KEY_CLOSE_PANA = "push_close_pana";

    private static final String KEY_OPEN_DIGIT = "push_open_digit";
    private static final String KEY_CLOSE_DIGIT = "push_close_digit";

    private static final String KEY_POINTS = "push_points";
    private static final String KEY_TYPE = "push_type";


    // Todo table create blocked
    private static final String DATABASE_CREATE_PUSHNOTIFICATIONS = "CREATE TABLE "
            + TABLE_PUSH_NOTIFICATIONSALERTS + "(" + KEY_IDPUSH + " INTEGER PRIMARY KEY," + KEY_DATE
            + " TEXT," + KEY_SESSION
            + " TEXT," + KEY_OPEN_PANA
            + " TEXT," + KEY_CLOSE_PANA
            + " TEXT," + KEY_OPEN_DIGIT
            + " TEXT," + KEY_CLOSE_DIGIT
            + " TEXT," + KEY_POINTS
            + " TEXT," + KEY_TYPE + " TEXT"
            + ")";

    public DatabaseGamePlay(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_PUSHNOTIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_PUSHNOTIFICATIONS);
        onCreate(db);

    }
}
