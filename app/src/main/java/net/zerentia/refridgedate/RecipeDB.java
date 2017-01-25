package net.zerentia.refridgedate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is the class that handles and creates the recipe database.
 *
 * Created by elev on 2017-01-17.
 * @author Tim Ekenberg
 * @version 1.0
 * @since   2017-01-18
 */

public class RecipeDB {


    /*
   <————————————————- DATABASE——————————————>
    */
    public static final String DATABASE_NAME = "recipe_db.db";
    public static final String RECIPE_NAME_TBL = "recipe_name";
    public static final String RECIPE_TBL = "recipe";

    public static final String ITEM_ID_FIELD = "_id";
    public static final String ITEM_RECIPE_NAME_FIELD = "_recipe_name";
    public static final String ITEM_ITEMS_NAME_FIELD = "_items_name";
    public static final String ITEM_RECIPE_FIELD = "_recipe";
    public static final int DB_VER = 1;

    public static final String[] ALL_FIELDS_RECIPE = new String[]{
            ITEM_ID_FIELD,ITEM_ITEMS_NAME_FIELD,ITEM_RECIPE_NAME_FIELD,ITEM_RECIPE_FIELD};

    public static final String CREATE_RECIPE_NAME_TBL = "CREATE table "+RECIPE_NAME_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_RECIPE_NAME_FIELD+" TEXT,"+
            ITEM_ITEMS_NAME_FIELD+" TEXT,"+
            ITEM_RECIPE_FIELD+" TEXT)";
    public static final String CREATE_RECIPE_TBL = "CREATE table "+RECIPE_TBL+
            " ("+ITEM_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ITEM_RECIPE_NAME_FIELD+" TEXT,"+
            ITEM_RECIPE_FIELD+" TEXT)";

    // open helper
    public class RecipeDBOpenHelper extends SQLiteOpenHelper {
        public RecipeDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_RECIPE_NAME_TBL);
            db.execSQL(CREATE_RECIPE_TBL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion){
                db.execSQL(CREATE_RECIPE_NAME_TBL);
                db.execSQL(CREATE_RECIPE_TBL);
                onCreate(db);
            }
        }
    }
    private RecipeDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public RecipeDB(Context context){
        dbOpenHelper = new RecipeDBOpenHelper(context);
    }
    public void openDB(){
        db = dbOpenHelper.getWritableDatabase();
    }
    public void closeDB(){
        db.close();
        db = null;
    }

    //SELECT * FROM Recipe WHERE _items_name = ?

    //---------------------GetItemByItems----------------------------------
    //SELECT * FROM Recipe WHERE _items_name = ?
    public Cursor getItemByItems(){

        Cursor cursor = db.query(RECIPE_NAME_TBL,
                ALL_FIELDS_RECIPE,ITEM_ITEMS_NAME_FIELD+ " = ?",
                null, null, null, null, null);
        return cursor;
    }

    //---------------------GetItem--------------------------------------
    //SELECT * FROM Recipe WHERE _recipe_name = ?
    public Cursor getItemByName(long recipeName){

        Cursor cursor = db.query(RECIPE_NAME_TBL,
                ALL_FIELDS_RECIPE,ITEM_RECIPE_NAME_FIELD+ " = ?",
                new String[]{Long.toString(recipeName)},
                null,null,null);
        return cursor;
    }

    //---------------------GetAllItems----------------------------------
    //SELECT * FROM menu;
    public Cursor getAllItems(){
        Cursor cursor = db.query(RECIPE_NAME_TBL, ALL_FIELDS_RECIPE,
                null, null, null, null, null);
        return cursor;
    }
    //---------------------GetItemByID----------------------------------
    //SELECT * FROM menu WHERE _id = ?;
    public Cursor getItemById(long itemId){
        Cursor cursor = db.query(RECIPE_NAME_TBL,
                ALL_FIELDS_RECIPE,ITEM_ID_FIELD+ " = ?",
                new String[]{Long.toString(itemId)},
                null,null,null);
        return cursor;
    }
    //---------------------AddItem----------------------------------
    // INSERT INTO recipe (_item_name) VALUES (Apple);
    public long addItem(String recipeName, String recipeItems, String recipeText){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_RECIPE_NAME_FIELD,recipeName);
        contentValues.put(ITEM_ITEMS_NAME_FIELD,recipeItems);
        contentValues.put(ITEM_RECIPE_FIELD,recipeText);
        return db.insert(RECIPE_NAME_TBL,null,contentValues);
    }
    //---------------------UpdateItem----------------------------------
    // UPDATE menu SET _item_name = Apple WHERE _id = ?;
    //public int updateItemById(long itemId,String itemName){
    //    ContentValues contentValues = new ContentValues();
    //    contentValues.put(ITEM_ITEMS_NAME_FIELD,itemName);
    //    return db.update(RECIPE_NAME_TBL, contentValues, ITEM_ID_FIELD + " = ?", new String[]{Long.toString(itemId)});
    //}
    //---------------------DeleteItem----------------------------------
    // DELETE FROM menu WHERE _id = ?
    public void removeItemById(long itemId){
        db.delete(RECIPE_NAME_TBL,ITEM_ID_FIELD +" = ?",new String[]{Long.toString(itemId)});

    }
}
