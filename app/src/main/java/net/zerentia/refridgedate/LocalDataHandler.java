package net.zerentia.refridgedate;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Elev on 12/12/2016.
 */

public class LocalDataHandler {

    private static List<DateItem> data;

    public static List<DateItem> getItems()
    {
        return data;
    }



    public static void insertIntoList(DateItem d) {
        data.add(d);
    }

    public static void insertIntoList(String item, int date, int amount)
    {
        Log.d("LDH", item);

        DateItem d = new DateItem();

        d.setDate(date);
        d.setTitle(amount + " : " + item);

        data.add(d);
    }

    public static void cleanList()
    {
        data = new ArrayList<>();
    }

}
