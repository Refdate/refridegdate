package net.zerentia.refridgedate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

/**
 * This is the main class.
 * This class handles the fridgeDB database.
 *
 * Created by Elev on 2016-11-23.
 * @author Rasmus and Tim Ekenberg
 * @version 1.0
 * @since   2016-11-23.
 */

public class MainActivity extends AppCompatActivity {

    private fridgeDBHandler fDB;

    private Button gotoShoppingList;
    private Button gotoMenu;
    private Button gotoOptions;
    private Button gotoRecipe;

    private RecyclerAdapter adapter;
    private RecyclerView recView;
    private ExternalDataHandler DH;
    private Button gotoAddItem;

    //currently an update button
    private Button gotoSocial;

    private String loginKey;

    protected void onResume()
    {
        super.onResume();
        updateRecView();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = (RecyclerView)findViewById(R.id.recList);

        recView.setLayoutManager(new LinearLayoutManager(this));
        fDB = new fridgeDBHandler(this);

        DH = new ExternalDataHandler();
        LocalDataHandler.cleanList();
        DH.pullData(fDB);
        fDB.updateList();

        loginKey = getIntent().getExtras().getString("loginKey");

        adapter = new RecyclerAdapter(LocalDataHandler.getItems(), this);
        recView.setAdapter(adapter);

        gotoShoppingList = (Button) findViewById(R.id.goto_shoppinglist);
        gotoShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingListActivity.class));
            }
        });

        gotoMenu = (Button) findViewById(R.id.goto_menu);
        gotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        gotoAddItem = (Button) findViewById(R.id.menu_add_goto);
        gotoAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ItemAddActivity.class);
                startActivity(i);

            }
        });

        gotoOptions = (Button) findViewById(R.id.goto_options);
        gotoOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsActivity.class));
            }
        });

        gotoRecipe = (Button) findViewById(R.id.goto_recipe);
        gotoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
            }
        });

        gotoSocial = (Button) findViewById(R.id.goto_social);
        gotoSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecView();
            }
        });

    }

    public void updateRecView()
    {
        DH.pullData(fDB);

    }

    public void onDoneUpdate()
    {


        LocalDataHandler.cleanList();
        fDB.updateList();

        RecyclerView rView = (RecyclerView) findViewById(R.id.recList);
        rView.removeAllViews();
//        rView.removeViewInLayout(rView);
        adapter = new RecyclerAdapter(LocalDataHandler.getItems(), this);
        recView.setAdapter(adapter);
    }
}
