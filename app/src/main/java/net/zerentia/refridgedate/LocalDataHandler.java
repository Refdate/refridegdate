package net.zerentia.refridgedate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Elev on 12/12/2016.
 */

public class LocalDataHandler {

    //REMOVE THESE WHEN DB IS IMPLEMENTED ONLY DUMMY DATA
    private static int dates[] = {1, 2 ,3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 6};
    private static String names[] = {"Milk", "Egg", "Chicken", "Beef", "Milk", "IceCream", "Pork", "Sausage", "Milk", "Soja", "Apple", "Orange", "Juice", "Milk", "Egg", "Beef", "Beef", "Chicken"};


    private static List<DateItem> data;

    public static List<DateItem> getItems()
    {
        return data;
    }

    public static void useDummyData()
    {
        //TODO get from DB
        for(int i = 0; i < dates.length && i < names.length; i++)
        {
            DateItem tempData = new DateItem();
            tempData.setTitle(names[i]);
            tempData.setDate(dates[i]);

            insertIntoList(tempData);
        }
    }


    public static void insertIntoList(DateItem d) {
        data.add(d);
    }

    public static void insertIntoList(String item, int date, int amount)
    {
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
