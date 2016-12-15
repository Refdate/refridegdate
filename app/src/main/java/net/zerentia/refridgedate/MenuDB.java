package net.zerentia.refridgedate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by elev on 2016-12-15.
 */

public class MenuDB {

    public static void displayToast(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }

    /*
   <————————————————- DATABASE——————————————>
    */
    public static final String DATABASE_NAME = "menu_db.db";
    public static final String MENU_MONDAY_TBL = "monday";
    public static final String ITEM_NAME_FIELD = "_item_name";
    public static final String ITEM_ID_FIELD = "_id";
    public static final int DB_VER = 1;
    public static final String[] ALL_FIELDS = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_FIELD};
    public static final String CREATE_TBL = "CREATE table "+MENU_MONDAY_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_FIELD+" TEXT NOT NULL)";
    // open helper
    public class ShoppingDBOpenHelper extends SQLiteOpenHelper {
        public ShoppingDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VER);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TBL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion){
                db.execSQL("DROP table "+MENU_MONDAY_TBL);
                onCreate(db);
            }
        }
    }
    private ShoppingDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public MenuDB(Context context){
        dbOpenHelper = new ShoppingDBOpenHelper(context);
    }
    public void openDB(){
        db = dbOpenHelper.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
        db = null;
    }
    //SELECT * FROM shopping;
    public Cursor getAllItems(){
        Cursor cursor = db.query(MENU_MONDAY_TBL, ALL_FIELDS,
                null, null, null, null, null);
        return cursor;
    }
    //SELECT * FROM shopping WHERE _id = ?;
    public Cursor getItemByID(long itemId){
        //Cursor cursor = db.rawQuery("SELECT * FROM "+SHOPPING_TBL);
        Cursor cursor = db.query(MENU_MONDAY_TBL,
                ALL_FIELDS,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    //SELECT * FROM shopping WHERE _id = ? AND item_name = ‘Apple’;
    public Cursor getItemByIDName(long itemId, String itemName){
        Cursor cursor = db.query(MENU_MONDAY_TBL,
                ALL_FIELDS,ITEM_ID_FIELD+ " = ? AND "+ITEM_NAME_FIELD+" = ?",
                new String[]{Long.toString(itemId),itemName},
                null,null,null);
        return cursor;
    }
    // INSERT INTO shopping (_item_name) VALUES (‘Apple’,22);
    public long addItem(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FIELD,itemName);
        return db.insert(MENU_MONDAY_TBL,null,contentValues);
    }
    // UPDATE shopping SET _item_name = ‘Apple’ WHERE _id = ?;
    public int updateItemById(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FIELD,itemName);
        return db.update(MENU_MONDAY_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    // DELETE FROM shopping WHERE _id = ?
    public void removeItemById(long itemId){
        db.delete(MENU_MONDAY_TBL,ITEM_ID_FIELD+" = ?",new String[]{Long.toString(itemId)});
    }
}
