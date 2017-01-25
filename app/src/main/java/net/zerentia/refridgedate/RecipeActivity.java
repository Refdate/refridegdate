package net.zerentia.refridgedate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the class that handles the recipe list and calls the recipe database.
 *
 *
 * Created by elev on 2017-01-17.
 * @author Tim Ekenberg
 * @version 1.0
 * @since   2017-01-18
 */

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
    private ListView recipeListView;
    private CheckBox haveItemsCheckBox;
    private SearchView recipeSearch;
    private RecipeDB recipeDB;
    private Button gotoRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeDB = new RecipeDB(this);
        recipeDB.openDB();


        recipeListView = (ListView) findViewById(R.id.recipeList);
        recipeListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        gotoRecipe = (Button) findViewById(R.id.recipeNewButton);
        gotoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecipeNewActivity.class));
            }
        });

        //testing reload
        reloadAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipeDB.closeDB();
    }

    //Checks what items exists in fridgeDB and in recipeDB
    public void haveItems(View v){



        boolean checked = ((CheckBox) v).isChecked();

        if (checked = true){
        //convert recipe items to array.

            Cursor cursor = recipeDB.getItemByItems();

            String[] recipeItems = new String[cursor.getCount()];
            int i = 0;
            while(cursor.moveToNext()){
                String str = cursor.getString(cursor.getColumnIndex("_items_name"));
                recipeItems[i] = str;
                i++;
            }

        }

        else{
        }
    }

    private void reloadAdapter() {
        Cursor cursor = recipeDB.getAllItems();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.recipe_item, cursor, new String[]{recipeDB.ITEM_RECIPE_NAME_FIELD},
                new int[]{R.id.recipeNameText}, 1);
        recipeListView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}