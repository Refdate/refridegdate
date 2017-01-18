package net.zerentia.refridgedate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import static net.zerentia.refridgedate.RecyclerAdapter.getActivity;

/**
 * Created by elev on 2017-01-13.
 */

public class OptionsActivity extends AppCompatActivity{

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);

        spinner = (Spinner) findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this,R.array.action_choose_lang_options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i) {
                    case 0: //English
                        Toast.makeText(getApplicationContext(),"Change the language i your phone settings", Toast.LENGTH_LONG).show();
                        return;
                    case 1: //Swedish
                        Toast.makeText(getApplicationContext(),"Change the language i your phone settings", Toast.LENGTH_LONG).show();
                        return;
                    default: //By default set to english
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
