package net.zerentia.refridgedate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elev on 2016-12-13.
 */

public class ShoppingListDB {

    /*
   <————————————————- DATABASE——————————————>
    */
    public static final String DATABASE_NAME = "shopping_list_db.db";
    public static final String SHOPPING_TBL = "shopping";
    public static final String ITEM_NAME_FIELD = "_item_name";
    public static final String ITEM_QTY_FIELD = "_item_qty";
    public static final String ITEM_ID_FIELD = "_id";
    public static final int DB_VER = 1;
    public static final String[] ALL_FIELDS = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_FIELD,ITEM_QTY_FIELD};
    public static final String CREATE_TBL = "CREATE table "+SHOPPING_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_FIELD+" TEXT NOT NULL,"+
            ITEM_QTY_FIELD+" INTEGER NOT NULL)";
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
                db.execSQL("DROP table "+SHOPPING_TBL);
                onCreate(db);
            }
        }
    }
    //—————————
    private ShoppingDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public ShoppingListDB(Context context){
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
        Cursor cursor = db.query(SHOPPING_TBL, ALL_FIELDS,
                null, null, null, null, null);
        return cursor;
    }
    //SELECT * FROM shopping WHERE _id = ?;
    public Cursor getItemByID(long itemId){
        //Cursor cursor = db.rawQuery("SELECT * FROM "+SHOPPING_TBL);
        Cursor cursor = db.query(SHOPPING_TBL,
                ALL_FIELDS,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    //SELECT * FROM shopping WHERE _id = ? AND item_name = ‘Apple’;
    public Cursor getItemByIDName(long itemId, String itemName){
        Cursor cursor = db.query(SHOPPING_TBL,
                ALL_FIELDS,ITEM_ID_FIELD+ " = ? AND "+ITEM_NAME_FIELD+" = ?",
                new String[]{Long.toString(itemId),itemName},
                null,null,null);
        return cursor;
    }
    // INSERT INTO shopping (_item_name, _item_qty) VALUES (‘Apple’,22);
    public long addItem(String itemName, int itemQty){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FIELD,itemName);
        contentValues.put(ITEM_QTY_FIELD,itemQty);
        return db.insert(SHOPPING_TBL,null,contentValues);
    }
    // UPDATE shopping SET _item_name = ‘Apple’, item_qty = 22 WHERE _id = ?;
    public int updateItemById(long itemId,String itemName, int itemQty){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FIELD,itemName);
        contentValues.put(ITEM_QTY_FIELD,itemQty);
        return db.update(SHOPPING_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    // DELETE FROM shopping WHERE _id = ?
    public void removeItemById(long itemId){
        db.delete(SHOPPING_TBL,ITEM_ID_FIELD+" = ?",new String[]{Long.toString(itemId)});
    }
}

