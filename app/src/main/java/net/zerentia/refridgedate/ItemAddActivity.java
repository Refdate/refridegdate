package net.zerentia.refridgedate;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Elev on 12/15/2016.
 */

public class ItemAddActivity extends AppCompatActivity{

    private sendUpdate uTask= null;


    public class sendUpdate extends AsyncTask<Void, Void, Boolean>
    {
        private String item;
        private String date;
        private int amount;

        sendUpdate(String item, String date)
        {
            this.item = item;
            this.date = date;
            this.amount = 1;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Boolean result = false;
            String url;
            JSONObject js;

            url = "http://zerentia.net/workspace/refdate/requests/update.php?email=1&key=1&i=" + item + "&d=" + date + "&a=" + amount;

            try {
                js = readJsonFromUrl(url);
                result = js.getBoolean("result");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return result;
        }

        protected void onPostExecute(final Boolean success) {
            uTask = null;


            if (success) {
            } else {
            }
        }
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });
    }

    public void attemptUpdate()
    {
        String item;
        String date;

        EditText itemField = (EditText) findViewById(R.id.itemInput);
        EditText dateField = (EditText) findViewById(R.id.dateInput);

        item = itemField.getText().toString();

        date = dateField.getText().toString();

        uTask = new sendUpdate(item, date);
        uTask.execute();

    }

}
