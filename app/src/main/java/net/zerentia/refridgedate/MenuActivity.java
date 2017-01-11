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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
    public static final String ITEM_NAME_KEY = "_item_name";
    private EditText mondayItem;
    private EditText tuesdayItem;
    private EditText wednesdayItem;
    private EditText thursdayItem;
    private EditText fridayItem;
    private EditText saturdayItem;
    private EditText sundayItem;
    private ArrayAdapter<String> adapter;
    private ListView mondayItemListView;
    private ListView tuesdayItemListView;
    private ListView wednesdayItemListView;
    private ListView thursdayItemListView;
    private ListView fridayItemListView;
    private ListView saturdayItemListView;
    private ListView sundayItemListView;
    private Button mondayAddItem;
    private Button tuesdayAddItem;
    private Button wednesdayAddItem;
    private Button thursdayAddItem;
    private Button fridayAddItem;
    private Button saturdayAddItem;
    private Button sundayAddItem;
    private long selectedItem = -1;
    private int selectedItemDelete = 0;
    private MenuDB menuDB;

    //TODO Optimise the code.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuDB = new MenuDB(this);
        menuDB.openDB();

        mondayItem = (EditText) findViewById(R.id.mondayText);
        tuesdayItem = (EditText) findViewById(R.id.tuesdayText);
        wednesdayItem = (EditText) findViewById(R.id.wednesdayText);
        thursdayItem = (EditText) findViewById(R.id.thursdayText);
        fridayItem = (EditText) findViewById(R.id.fridayText);
        saturdayItem = (EditText) findViewById(R.id.saturdayText);
        sundayItem = (EditText) findViewById(R.id.sundayText);

        mondayItemListView = (ListView) findViewById(R.id.mondayMenu);
        mondayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        tuesdayItemListView = (ListView) findViewById(R.id.tuesdayMenu);
        tuesdayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        wednesdayItemListView =(ListView) findViewById(R.id.wednesdayMenu);
        wednesdayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        thursdayItemListView = (ListView) findViewById(R.id.thursdayMenu);
        thursdayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        fridayItemListView = (ListView) findViewById(R.id.fridayMenu);
        fridayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        saturdayItemListView = (ListView) findViewById(R.id.saturdayMenu);
        saturdayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        sundayItemListView = (ListView) findViewById(R.id.sundayMenu);
        sundayItemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mondayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdMon(id);
                cursor.moveToFirst();
                mondayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_MON_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        tuesdayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdTue(id);
                cursor.moveToFirst();
                tuesdayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_TUE_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        wednesdayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdWed(id);
                cursor.moveToFirst();
                wednesdayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_WED_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        thursdayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdThu(id);
                cursor.moveToFirst();
                thursdayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_THU_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        fridayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdFri(id);
                cursor.moveToFirst();
                fridayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_FRI_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        saturdayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdSat(id);
                cursor.moveToFirst();
                saturdayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_SAT_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        sundayItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = menuDB.getItemByIdSun(id);
                cursor.moveToFirst();
                sundayItem.setText(cursor.getString(cursor.getColumnIndex(MenuDB.ITEM_NAME_SUN_FIELD)));
                cursor.close();
                selectedItem = id;
            }
        });

        mondayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 1;
                displayDialog(id);
                return true;
            }
        });

        tuesdayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 2;
                displayDialog(id);
                return true;
            }
        });

        wednesdayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 3;
                displayDialog(id);
                return true;
            }
        });

        thursdayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 4;
                displayDialog(id);
                return true;
            }
        });

        fridayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 5;
                displayDialog(id);
                return true;
            }
        });

        saturdayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 6;
                displayDialog(id);
                return true;
            }
        });

        sundayItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemDelete = 7;
                displayDialog(id);
                return true;
            }
        });


        mondayAddItem = (Button) findViewById(R.id.mondayButton);
        mondayAddItem.setOnClickListener(this);

        tuesdayAddItem = (Button) findViewById(R.id.tuesdayButton);
        tuesdayAddItem.setOnClickListener(this);

        wednesdayAddItem = (Button) findViewById(R.id.wednesdayButton);
        wednesdayAddItem.setOnClickListener(this);

        thursdayAddItem = (Button) findViewById(R.id.thursdayButton);
        thursdayAddItem.setOnClickListener(this);

        fridayAddItem = (Button) findViewById(R.id.fridayButton);
        fridayAddItem.setOnClickListener(this);

        saturdayAddItem = (Button) findViewById(R.id.saturdayButton);
        saturdayAddItem.setOnClickListener(this);

        sundayAddItem = (Button) findViewById(R.id.sundayButton);
        sundayAddItem.setOnClickListener(this);

        reloadAdapterMon();
        reloadAdapterTue();
        reloadAdapterWed();
        reloadAdapterThu();
        reloadAdapterFri();
        reloadAdapterSat();
        reloadAdapterSun();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuDB.closeDB();
    }


    private void displayDialog(final long selected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you really want to delete this entry?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // proceed delete
                switch (selectedItemDelete) {

                    case 1:
                        menuDB.removeItemByIdMon(selected);
                        reloadAdapterMon();
                        break;
                    case 2:
                        menuDB.removeItemByIdTue(selected);
                        reloadAdapterTue();
                        break;
                    case 3:
                        menuDB.removeItemByIdWed(selected);
                        reloadAdapterWed();
                        break;
                    case 4:
                        menuDB.removeItemByIdThu(selected);
                        reloadAdapterThu();
                        break;
                    case 5:
                        menuDB.removeItemByIdFri(selected);
                        reloadAdapterFri();
                        break;
                    case 6:
                        menuDB.removeItemByIdSat(selected);
                        reloadAdapterSat();
                        break;
                    case 7:
                        menuDB.removeItemByIdSun(selected);
                        reloadAdapterSun();
                        break;
                }
                selectedItemDelete = 0;
                MenuDB.displayToast(MenuActivity.this, "Successfully deleted");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void reloadAdapterMon() {
        Cursor cursor = menuDB.getAllItemsMon();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_MON_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        mondayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");
    }

    private void reloadAdapterTue() {
        Cursor cursor = menuDB.getAllItemsTue();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_TUE_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        tuesdayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    private void reloadAdapterWed() {
        Cursor cursor = menuDB.getAllItemsWed();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_WED_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        wednesdayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    private void reloadAdapterThu() {
        Cursor cursor = menuDB.getAllItemsThu();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_THU_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        thursdayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    private void reloadAdapterFri() {
        Cursor cursor = menuDB.getAllItemsFri();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_FRI_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        fridayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    private void reloadAdapterSat() {
        Cursor cursor = menuDB.getAllItemsSat();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_SAT_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        saturdayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    private void reloadAdapterSun() {
        Cursor cursor = menuDB.getAllItemsSun();
        cursor.moveToFirst(); // index 0

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_menu_itembox, cursor, new String[]{MenuDB.ITEM_NAME_SUN_FIELD},
                new int[]{R.id.menu_item_name_list}, 1);
        sundayItemListView.setAdapter(simpleCursorAdapter);
        mondayItem.setText("");
        tuesdayItem.setText("");
        wednesdayItem.setText("");
        thursdayItem.setText("");
        fridayItem.setText("");
        saturdayItem.setText("");
        sundayItem.setText("");

    }

    @Override
    public void onClick(View v) {
        if (!mondayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemMon(mondayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdMon(selectedItem, mondayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterMon();
        } else {
        }
        if (!tuesdayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemTue(tuesdayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdTue(selectedItem, tuesdayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterTue();
        } else {
        }
        if (!wednesdayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemWed(wednesdayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdWed(selectedItem, wednesdayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterWed();
        } else {
        }
        if (!thursdayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemThu(thursdayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdThu(selectedItem, thursdayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterThu();
        } else {
        }
        if (!fridayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemFri(fridayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdFri(selectedItem, fridayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterFri();
        } else {
        }
        if (!saturdayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemSat(saturdayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdSat(selectedItem, saturdayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterSat();
        } else {
        }
        if (!sundayItem.getText().toString().equals("")) {
            if (selectedItem == -1) {
                menuDB.addItemSun(sundayItem.getText().toString());

                MenuDB.displayToast(this, "Successfully Added");
            } else {
                menuDB.updateItemByIdSun(selectedItem, sundayItem.getText().toString());
                selectedItem = -1;
            }
            reloadAdapterSun();
        } else {
        }
    }
}
