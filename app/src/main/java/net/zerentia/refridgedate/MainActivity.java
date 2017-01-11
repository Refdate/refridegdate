package net.zerentia.refridgedate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private fridgeDBHandler fDB;

    private Button gotoShoppingList;
    private Button gotoMenu;

    private RecyclerAdapter adapter;
    private RecyclerView recView;

    private Button gotoAddItem;
    private ExternalDataHandler DH;
    //currently an update button
    private Button gotoSocial;

    private String loginKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        gotoAddItem = (Button) findViewById(R.id.menu_add_goto);

        gotoAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ItemAddActivity.class);
                i.putExtra("Key", loginKey);
                startActivity(i);

            }
        });

        gotoSocial = (Button) findViewById(R.id.goto_social);

        gotoSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Key", loginKey);
                startActivity(i);
                finish();
            }
        });


    }
}
