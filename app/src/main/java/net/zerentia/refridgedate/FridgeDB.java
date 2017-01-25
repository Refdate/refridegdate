package net.zerentia.refridgedate;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This is the class that handles and creates the fridge database.
 *
 * Created by elev on 2017-01-23.
 * @author Tim Ekenberg
 * @version 1.0
 * @since   2017-01-23
 */

public class FridgeDB {


    /*
   <————————————————- DATABASE——————————————>
    */
    public static final String DATABASE_NAME = "fridge_db.db";
    public static final String FRIDGE_TBL = "fridge";

    public static final String ITEM_ID_FIELD = "_id";
    public static final String ITEM_NAME_FIELD = "_item_name";
    public static final String ITEM_DATE_FIELD = "_item_date";
    public static final int DB_VER = 1;

    public static final String[] ALL_FIELDS = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_FIELD,ITEM_DATE_FIELD};

    public static final String CREATE_FRIDGE_TBL = "CREATE table "+FRIDGE_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_FIELD+" TEXT NOT NULL,"+
            ITEM_DATE_FIELD+" TEXT NOT NULL)";

    // open helper
    public class FridgeDBOpenHelper extends SQLiteOpenHelper {
        public FridgeDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_FRIDGE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion){
                db.execSQL(CREATE_FRIDGE_TBL);
                onCreate(db);
            }
        }
    }
    private FridgeDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public FridgeDB(Context context){
        dbOpenHelper = new FridgeDBOpenHelper(context);
    }
    public void openDB(){
        db = dbOpenHelper.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
        db = null;
    }

    //---------------------GetAllItems----------------------------------
    //SELECT * FROM menu;
    public Cursor getFridgeItems(){
        Cursor cursor = db.query(FRIDGE_TBL, ALL_FIELDS,
                null, null, null, null, null);
        return cursor;
    }
}
