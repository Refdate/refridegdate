package net.zerentia.refridgedate;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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


    public class ShoppingList extends AppCompatActivity implements View.OnClickListener{
        private ArrayList<HashMap<String,String>> items = new ArrayList<HashMap<String, String>>();
        public static final String ITEM_NAME_KEY = "_item_name";
        public static final String ITEM_QTY_KEY = "_item_qty";
        private EditText itemQty;
        private EditText itemName;
        private ArrayAdapter<String> adapter;
        private ListView itemListView;
        private Button addItem;
        private int selectedItem = -1;
        private ShoppingListDB shoppingDB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping_list);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Shopping List");
            shoppingDB = new ShoppingListDB(this);
            shoppingDB.openDB();
            itemName = (EditText)findViewById(R.id.item_name);
            itemQty = (EditText)findViewById(R.id.item_qty);
            itemListView = (ListView)findViewById(R.id.item_list);
            itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor cursor = shoppingDB.getItemByID(id);
                    cursor.moveToFirst();
                    itemName.setText(cursor.getString(cursor.getColumnIndex(ShoppingListDB.ITEM_NAME_FIELD)));
                    itemQty.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(ShoppingListDB.ITEM_QTY_FIELD))));
                    addItem.setText("Edit");
                    cursor.close();
                    //selectedItem = id;
                }
            });
            itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    displayDialog(id);
                    return true;
                }
            });
            addItem = (Button)findViewById(R.id.add_button);
            addItem.setOnClickListener(this);
            Bundle extras = getIntent().getExtras();

            reloadAdapter();
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            shoppingDB.closeDB();
        }
        private void displayDialog(final long selected){
            AlertDialog.Builder  builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("Do you really want to delete this entry?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // proceed delete
                    shoppingDB.removeItemById(selected);
                    reloadAdapter();
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
            Cursor cursor = shoppingDB.getAllItems();
            cursor.moveToFirst(); // index 0

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.activity_shopping_list_itembox,cursor,new String[]{ShoppingListDB.ITEM_NAME_FIELD,ShoppingListDB.ITEM_QTY_FIELD},
                    new int[]{R.id.item_name_list,R.id.item_qty_list},1);
            itemListView.setAdapter(simpleCursorAdapter);
            itemName.setText("");
            itemQty.setText("");
        }
        @Override
        public void onClick(View v) {
            if (!itemName.getText().toString().equals("") && !itemQty.getText().toString().equals("")){
                if (selectedItem == -1){
                    shoppingDB.addItem(itemName.getText().toString(), Integer.parseInt(itemQty.getText().toString()));
                }else{
                    shoppingDB.updateItemById(selectedItem,itemName.getText().toString(),Integer.parseInt(itemQty.getText().toString()));
                    selectedItem = -1;
                    addItem.setText("Add");
                }
                reloadAdapter();
            }else{
            }
        }
    }


