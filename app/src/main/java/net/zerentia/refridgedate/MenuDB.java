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
    public static final String MENU_MON_TBL = "menu_mon";
    public static final String MENU_TUE_TBL = "menu_tue";
    public static final String MENU_WED_TBL = "menu_wed";
    public static final String MENU_THU_TBL = "menu_thu";
    public static final String MENU_FRI_TBL = "menu_fri";
    public static final String MENU_SAT_TBL = "menu_sat";
    public static final String MENU_SUN_TBL = "menu_sun";
    public static final String ITEM_NAME_MON_FIELD = "_item_name_mon";
    public static final String ITEM_NAME_TUE_FIELD = "_item_name_tue";
    public static final String ITEM_NAME_WED_FIELD = "_item_name_wed";
    public static final String ITEM_NAME_THU_FIELD = "_item_name_thu";
    public static final String ITEM_NAME_FRI_FIELD = "_item_name_fri";
    public static final String ITEM_NAME_SAT_FIELD = "_item_name_sat";
    public static final String ITEM_NAME_SUN_FIELD = "_item_name_sun";
    public static final String ITEM_ID_FIELD = "_id";
    public static final int DB_VER = 1;

    public static final String[] ALL_FIELDS_MON = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_MON_FIELD};
    public static final String[] ALL_FIELDS_TUE = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_TUE_FIELD};
    public static final String[] ALL_FIELDS_WED = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_WED_FIELD};
    public static final String[] ALL_FIELDS_THU = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_THU_FIELD};
    public static final String[] ALL_FIELDS_FRI = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_FRI_FIELD};
    public static final String[] ALL_FIELDS_SAT = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_SAT_FIELD};
    public static final String[] ALL_FIELDS_SUN = new String[]{
            ITEM_ID_FIELD,ITEM_NAME_SUN_FIELD};

    public static final String CREATE_MON_TBL = "CREATE table "+MENU_MON_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_MON_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_TUE_TBL = "CREATE table "+MENU_TUE_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_TUE_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_WED_TBL = "CREATE table "+MENU_WED_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_WED_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_THU_TBL = "CREATE table "+MENU_THU_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_THU_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_FRI_TBL = "CREATE table "+MENU_FRI_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_FRI_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_SAT_TBL = "CREATE table "+MENU_SAT_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_SAT_FIELD+" TEXT NOT NULL)";
    public static final String CREATE_SUN_TBL = "CREATE table "+MENU_SUN_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_NAME_SUN_FIELD+" TEXT NOT NULL)";
    // open helper
    public class MenuDBOpenHelper extends SQLiteOpenHelper {
        public MenuDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VER);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_MON_TBL);
            db.execSQL(CREATE_TUE_TBL);
            db.execSQL(CREATE_WED_TBL);
            db.execSQL(CREATE_THU_TBL);
            db.execSQL(CREATE_FRI_TBL);
            db.execSQL(CREATE_SAT_TBL);
            db.execSQL(CREATE_SUN_TBL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion){
                db.execSQL("DROP table "+MENU_MON_TBL);
                db.execSQL("DROP table "+MENU_TUE_TBL);
                db.execSQL("DROP table "+MENU_WED_TBL);
                db.execSQL("DROP table "+MENU_THU_TBL);
                db.execSQL("DROP table "+MENU_FRI_TBL);
                db.execSQL("DROP table "+MENU_SAT_TBL);
                db.execSQL("DROP table "+MENU_SUN_TBL);
                onCreate(db);
            }
        }
    }
    private MenuDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public MenuDB(Context context){
        dbOpenHelper = new MenuDBOpenHelper(context);
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
    public Cursor getAllItemsMon(){
        Cursor cursor = db.query(MENU_MON_TBL, ALL_FIELDS_MON,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsTue(){
        Cursor cursor = db.query(MENU_TUE_TBL, ALL_FIELDS_TUE,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsWed(){
        Cursor cursor = db.query(MENU_WED_TBL, ALL_FIELDS_WED,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsThu(){
        Cursor cursor = db.query(MENU_THU_TBL, ALL_FIELDS_THU,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsFri(){
        Cursor cursor = db.query(MENU_FRI_TBL, ALL_FIELDS_FRI,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsSat(){
        Cursor cursor = db.query(MENU_SAT_TBL, ALL_FIELDS_SAT,
                null, null, null, null, null);
        return cursor;
    }
    public Cursor getAllItemsSun(){
        Cursor cursor = db.query(MENU_SUN_TBL, ALL_FIELDS_SUN,
                null, null, null, null, null);
        return cursor;
    }
    //---------------------GetItemByID----------------------------------
    //SELECT * FROM menu WHERE _id = ?;
    public Cursor getItemByIdMon(long itemId){
        Cursor cursor = db.query(MENU_MON_TBL,
                ALL_FIELDS_MON,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdTue(long itemId){
        Cursor cursor = db.query(MENU_TUE_TBL,
                ALL_FIELDS_TUE,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdWed(long itemId){
        Cursor cursor = db.query(MENU_WED_TBL,
                ALL_FIELDS_WED,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdThu(long itemId){
        Cursor cursor = db.query(MENU_THU_TBL,
                ALL_FIELDS_THU,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdFri(long itemId){
        Cursor cursor = db.query(MENU_FRI_TBL,
                ALL_FIELDS_FRI,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdSat(long itemId){
        Cursor cursor = db.query(MENU_SAT_TBL,
                ALL_FIELDS_SAT,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    public Cursor getItemByIdSun(long itemId){
        Cursor cursor = db.query(MENU_SUN_TBL,
                ALL_FIELDS_SUN,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    //---------------------AddItem----------------------------------
    // INSERT INTO menu (_item_name) VALUES (Apple);
    public long addItemMon(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_MON_FIELD,itemName);
        return db.insert(MENU_MON_TBL,null,contentValues);
    }
    public long addItemTue(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_TUE_FIELD,itemName);
        return db.insert(MENU_TUE_TBL,null,contentValues);
    }
    public long addItemWed(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_WED_FIELD,itemName);
        return db.insert(MENU_WED_TBL,null,contentValues);
    }
    public long addItemThu(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_THU_FIELD,itemName);
        return db.insert(MENU_THU_TBL,null,contentValues);
    }
    public long addItemFri(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FRI_FIELD,itemName);
        return db.insert(MENU_FRI_TBL,null,contentValues);
    }
    public long addItemSat(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_SAT_FIELD,itemName);
        return db.insert(MENU_SAT_TBL,null,contentValues);
    }
    public long addItemSun(String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_SUN_FIELD,itemName);
        return db.insert(MENU_SUN_TBL,null,contentValues);
    }
    //---------------------UpdateItem----------------------------------
    // UPDATE menu SET _item_name = Apple WHERE _id = ?;
    public int updateItemByIdMon(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_MON_FIELD,itemName);
        return db.update(MENU_MON_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdTue(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_TUE_FIELD,itemName);
        return db.update(MENU_TUE_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdWed(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_WED_FIELD,itemName);
        return db.update(MENU_WED_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdThu(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_THU_FIELD,itemName);
        return db.update(MENU_THU_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdFri(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_FRI_FIELD,itemName);
        return db.update(MENU_FRI_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdSat(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_SAT_FIELD,itemName);
        return db.update(MENU_SAT_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    public int updateItemByIdSun(long itemId,String itemName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_SUN_FIELD,itemName);
        return db.update(MENU_SUN_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    }
    //---------------------DeleteItem----------------------------------
    // DELETE FROM menu WHERE _id = ?
    public void removeItemByIdMon(long itemId){
        db.delete(MENU_MON_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdTue(long itemId){
        db.delete(MENU_TUE_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdWed(long itemId){
        db.delete(MENU_WED_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdThu(long itemId){
        db.delete(MENU_THU_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdFri(long itemId){
        db.delete(MENU_FRI_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdSat(long itemId){
        db.delete(MENU_SAT_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
    public void removeItemByIdSun(long itemId){
        db.delete(MENU_SUN_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
}
