package net.zerentia.refridgedate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the class that handles the recipe create/edit.
 *
 * Created by elev on 2017-01-24.
 *
 * @author Tim Ekenberg
 * @version 1.0
 * @since   2017-01-24
 */

public class RecipeNewActivity extends AppCompatActivity {

    private RecipeDB recipeDB;
    private EditText recipeName;
    private EditText recipeItems;
    private EditText recipeText;
    private Button recipeSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);
        recipeDB = new RecipeDB(this);
        recipeDB.openDB();

        recipeName = (EditText)findViewById(R.id.nameEditText);
        recipeItems = (EditText)findViewById(R.id.IngredientsEditText);
        recipeText = (EditText)findViewById(R.id.recipeEditText);

        recipeSave = (Button) findViewById(R.id.recipeSaveButton);
        recipeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recipeDB.addItem(recipeName.getText().toString(), recipeItems.getText().toString(), recipeText.getText().toString());
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipeDB.closeDB();
    }

}
