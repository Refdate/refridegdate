package net.zerentia.refridgedate;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * This is the class that handles the recipe list and calls the recipe database.
 *
 * Created by elev on 2017-01-17.
 * @author Tim Ekenberg
 * @version 1.0
 * @since   2017-01-18
 */

public class RecipeActivity {

    private ListView recipeListView;
    private CheckBox haveItemsCheckBox;
    private SearchView recipeSearch;
    private RecipeDB recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        RecipeDB = new RecipeDB(this);
        RecipeDB.openDB();
    }

    recipeListView = (ListView) findViewById(R.id.recipeListView);
    recipeListView.setOnTouchListener(new View.OnTouchListener() {
        // Setting on Touch Listener for handling the touch inside ScrollView
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // Disallow the touch request for parent scroll on touch of child view
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    });

    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipeDB.closeDB();
    }
}