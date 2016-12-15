package net.zerentia.refridgedate;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
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
 * Created by Elev on 12/12/2016.
 */

public class ExternalDataHandler {

    runJsonUpdate jTask = null;

    public static void updates()
    {
        while(true)
        {

        }
    }

    public void pullData(fridgeDBHandler db)
    {
        String url = "http://zerentia.net/workspace/refdate/requests/retrive.php?email=1&key=1";
        jTask = new runJsonUpdate(db);
        jTask.execute();
    }

    private class runJsonUpdate extends AsyncTask {


        JSONArray jsonArray;
        JSONObject jsonObject;

        int id;
        String item;
        int date;
        int amount;
        fridgeDBHandler db;

        runJsonUpdate(fridgeDBHandler db)
        {
            this.db = db;
        }

        @Override
        protected void onPreExecute()
        {
            Log.d("DEBUG", "onPreExecute()");
        }


        @Override
        protected Object doInBackground(Object[] params) {

            Log.d("DEBUG", "is running in ASYNC");

            try {
                jsonObject = readJsonFromUrl("http://zerentia.net/workspace/refdate/requests/retrive.php?email=1&key=1");

                jsonArray = jsonObject.getJSONArray("data");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            db.startDummy();

            for(int i = 0; i < jsonArray.length(); i++)
            {
                try {
                    jsonObject = jsonArray.getJSONObject(i);

                    id = jsonObject.getInt("id");
                    item = jsonObject.getString("item");
                    date = jsonObject.getInt("date");
                    amount = jsonObject.getInt("amount");

                    db.insertDummy(id, date, item, amount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            db.applyDummy(1);
            return null;
        }
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

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

    public int toDays(String date)
    {
        int days = 0;

        String[] sDate = date.split("-");

        int year = Integer.parseInt(sDate[0]);
        int month = Integer.parseInt(sDate[1]);
        int day = Integer.parseInt(sDate[2]);


        Log.d("year", String.valueOf(year));
        Log.d("month", String.valueOf(month));
        Log.d("day", String.valueOf(day));

        days += day;
        int[] mdays = {31, 28, 31, 30,31, 30 , 31, 31, 30, 31, 30, 31};

        for(int m = 0; m < month; m++)
        {
            days += mdays[m];
            if(m == 1 && ((year-1972) % 4 == 0))
            {
                days += 1;
            }
        }

        days += 365*year;

        days += (year - 1972) / 4;

        return days;
    }
}

