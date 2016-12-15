package net.zerentia.refridgedate;

import android.content.Context;
import android.database.Cursor;
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

    private static final String F_NAME = "::fridge";
    private static final String F_C_ID = "id";
    private static final String F_C_ITEM = "item";
    private static final String F_C_DATE = "date";
    private static final String F_C_AMOUNT = "amount";

    private static final String F_C_TEMP_NAME = "temp::fridge";

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
        db.execSQL("create table [" + FRIDGE + F_NAME +"] (" + F_C_ID + " INTEGER," + F_C_ITEM + " TEXT, " + F_C_DATE + " INTEGER," + F_C_AMOUNT + " INTEGER)");
        db.execSQL("create table [" + U_NAME +"] (" + U_C_ID + " INTEGER," + U_C_ITEM + " TEXT, " + U_C_TIMESTAMP + " INTEGER," + U_C_AMOUNT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS [" + FRIDGE + F_NAME + "]");
        db.execSQL("DROP TABLE IF EXISTS ["+U_NAME + "]");
    }

    public void insertUpdate(String item, int amount)
    {

        Calendar c = Calendar.getInstance();

        long time = c.getTimeInMillis()/1000;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into [" + U_NAME + "]('item', 'timestamp', 'amount') VALUES('" + item + "', '" + time + "', '" + amount + "')");
    }

    public void insertItem(int id, String item, int date, int amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into [" + FRIDGE + F_NAME + "]('item', 'date', 'amount') VALUES('" + item + "', '" + date + "', '" + amount + "')");
    }

    public void startDummy()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS ["+ F_C_TEMP_NAME + "]");
        db.execSQL("create table [" + F_C_TEMP_NAME +"] (" + F_C_ID + " INTEGER," + F_C_ITEM + " TEXT, " + F_C_DATE + " INTEGER," + F_C_AMOUNT + " INTEGER)");
    }
    public void insertDummy(int id, int date, String item, int amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into [" + F_C_TEMP_NAME + "]('item', 'date', 'amount') VALUES('" + item + "', '" + date + "', '" + amount + "')");
    }
    public void applyDummy(int uid)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS [" + FRIDGE + F_NAME + "]");
        db.execSQL("ALTER TABLE [" + F_C_TEMP_NAME + "] RENAME TO [" + FRIDGE + F_NAME + "]");
    }

    public void updateList()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from [" + FRIDGE + F_NAME + "] ORDER BY DATE DESC", null);

        processUpdates(res);
    }

    public void processUpdates(Cursor res)
    {
        String item;
        int date;
        int amount;


        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext())
        {
            item = res.getString(1);
            date = res.getInt(2);
            amount = res.getInt(3);

            LocalDataHandler.insertIntoList(item, date, amount);
        }
    }

}
