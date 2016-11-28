package net.zerentia.refridgedate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private fridgeDBHandler fDB;



    private RecyclerAdapter adapter;
    private RecyclerView recView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = (RecyclerView)findViewById(R.id.recList);

        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(LocalDataHandler.getItems(), this);
        recView.setAdapter(adapter);

        fDB = new fridgeDBHandler(this);

        fDB.insertUpdate("a", 1);
    }
}
