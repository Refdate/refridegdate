package net.zerentia.refridgedate;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by Elev on 12/15/2016.
 */

public class ItemAddActivity extends AppCompatActivity{

    private sendUpdate uTask= null;
    private getProduct gTask=null;

    private ImageButton barcodeButton;
    private Button addButton;
    private String checkBarcode = "123123123123";

    private Activity self = this;

    public class getProduct extends AsyncTask<Void, Void, Boolean>
    {
        private String barcode;

        getProduct(String barcode)
        {
            this.barcode = barcode;

        }



        @Override
        protected Boolean doInBackground(Void... params) {

            String url = "http://zerentia.net/workspace/refdate/requests/productName.php?barcode=" + barcode;

            try {
                JSONObject jsonObject = readJsonFromUrl(url);
                barcode = jsonObject.getString("Item");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Boolean res)
        {
            EditText text = (EditText) findViewById(R.id.itemInput);
            text.setText(barcode);
            gTask = null;
        }

    }

    public void setCheckBarcode(String barcode)
    {
        this.checkBarcode = barcode;
    }

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
            Log.d("uTask", "started background");
            url = "http://zerentia.net/workspace/refdate/requests/update.php?email=1&key=1";
//            url.replaceAll("\\s", "%20");

            //{"&i=", "&d=", "&a="};

            try {
                url += "&i=" + URLEncoder.encode(item, "UTF-8");
                url += "&d=" + URLEncoder.encode(date, "UTF-8");
                url += "&a=" + amount;
                if(checkBarcode != null)
                {
                    url += "&b=" + URLEncoder.encode(checkBarcode, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("url", url);
            try {
                js = readJsonFromUrl(url);
                result = js.getBoolean("result");
            } catch (IOException e) {
               // e.printStackTrace();
            } catch (JSONException e)
            {
                //e.printStackTrace();
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
        Log.d("onCreate", "Ran");
        //i = getIntent();

        addButton = (Button) findViewById(R.id.add_button);
        barcodeButton = (ImageButton) findViewById(R.id.camera_barcode);

        Log.d("onCreate", "Buttons found");
        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(self);
                scanIntegrator.initiateScan();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
                Log.d("Click", "Add");
            }
        });
        Log.d("onCreate", "added on clicks");
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            setCheckBarcode(scanContent);

            gTask = new getProduct(scanContent);
            gTask.execute();

        }
    }

}
