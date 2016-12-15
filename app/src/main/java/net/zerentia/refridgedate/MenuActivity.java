package net.zerentia.refridgedate;

/**
 * Created by elev on 2016-12-15.
 */

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import java.util.ArrayList;
import java.util.HashMap;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<HashMap<String,String>> items = new ArrayList<HashMap<String, String>>();
    public static final String ITEM_NAME_KEY = "_item_name";
    private EditText mondayItem;
    private ArrayAdapter<String> adapter;
    private ListView itemListView;
    private Button addItem;
    private long selectedItem = -1;
    private MenuDB menuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuDB = new MenuDB(this);
        menuDB.openDB();
        mondayItem = (EditText)findViewById(R.id.mondayText);
        itemListView = (ListView)findViewById(R.id.mondayMenu);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByID(id);
                cursor.moveToFirst();
                mondayItem.setText(cursor.getString(cursor.getColumnIndex(ShoppingListDB.ITEM_NAME_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                displayDialog(id);
                return true;
            }
        });

        addItem = (Button)findViewById(R.id.mondayButton);
        addItem.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        reloadAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuDB.closeDB();
    }

    private void displayDialog(final long selected){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you really want to delete this entry?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // proceed delete
                menuDB.removeItemById(selected);
                reloadAdapter();
                MenuDB.displayToast(MenuActivity.this, "Successfully deleted");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void reloadAdapter(){
        Cursor cursor = menuDB.getAllItems();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.activity_menu_itembox,cursor,new String[]{MenuDB.ITEM_NAME_FIELD},
                new int[]{R.id.menu_item_name_list},1);
        itemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
    }
    @Override
    public void onClick(View v) {
        if (!mondayItem.getText().toString().equals("")){
            if (selectedItem == -1){
                menuDB.addItem(mondayItem.getText().toString());
            }else{
                menuDB.updateItemById(selectedItem,mondayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapter();
        }else{
        }
    }
}
