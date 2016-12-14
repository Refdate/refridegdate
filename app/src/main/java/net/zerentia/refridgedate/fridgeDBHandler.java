package net.zerentia.refridgedate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Elev on 12/13/2016.
 */

public class fridgeDBHandler extends SQLiteOpenHelper{


    //to be set to depend on logged in user
    private static final String FRIDGE = "1";

    private static final String DATABASE_NAME = "fridges.db";

    private static final String F_NAME = FRIDGE + "::fridge";
    private static final String F_C_ID = "id";
    private static final String F_C_ITEM = "item";
    private static final String F_C_DATE = "date";
    private static final String F_C_AMOUNT = "amount";


    private static final String U_NAME = FRIDGE + "::Updates";
    private static final String U_C_ID = "id";
    private static final String U_C_ITEM = "item";
    private static final String U_C_AMOUNT = "amount";
    private static final String U_C_TIMESTAMP = "timestamp";

    public fridgeDBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table [" + F_NAME +"] (" + F_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + F_C_ITEM + " TEXT, " + F_C_DATE + " INTEGER," + F_C_AMOUNT + " INTEGER)");
        db.execSQL("create table [" + U_NAME +"] (" + U_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + U_C_ITEM + " TEXT, " + U_C_TIMESTAMP + " INTEGER," + U_C_AMOUNT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS ["+F_NAME + "]");
        db.execSQL("DROP TABLE IF EXISTS ["+U_NAME + "]");
    }

    public void insertUpdate(String item, int amount)
    {

        Calendar c = Calendar.getInstance();

        long time = c.getTimeInMillis()/1000;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into [" + U_NAME + "]('item', 'timestamp', 'amount') VALUES('" + item + "', '" + time + "', '" + amount + "')");
    }

    public void insertItem(String item, int date, int amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into [" + F_NAME + "]('item', 'timestamp', 'amount') VALUES('" + item + "', '" + date + "', '" + amount + "')");
    }
}
