package net.zerentia.refridgedate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Elev on 12/12/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemHolder> {

    private static int currentElements;
    private List<DateItem> listData;
    private LayoutInflater inflater;

    private sendUpdate uTask = null;

    public RecyclerAdapter(List<DateItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.date_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        final DateItem item = listData.get(position);
        holder.title.setText(item.getTitle());

        Calendar c = Calendar.getInstance();

        long time = (c.getTimeInMillis()/1000)/86400;

        holder.dateText.setText((item.getDate() - time) + " days left");
       // holder.icon.setImageResource(item.getImageResId());
        holder.container.setBackgroundResource(pickColor(item.getDate()));
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String itemName;
                String date;


                TextView dt = holder.dateText;
                TextView tv = holder.title;

                itemName = tv.getText().toString();
                date = dt.getText().toString();
                itemName = itemName.replaceAll("-?[0-9]*\\s:\\s", "");

                date = date.replaceAll("\\sdays\\sleft", "");

                Calendar c = Calendar.getInstance();
                int d = (int) ((c.getTimeInMillis())/1000)/86400;

                int da = Integer.parseInt(date) + d;

                date = String.valueOf(da);

                uTask = new sendUpdate(itemName, date);
                uTask.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView dateText;
        private ImageView icon;
        private View container;
        private ImageButton delButton;


        public ItemHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lblItemText);
            icon = (ImageView) itemView.findViewById(R.id.imItemIcon);
            container = itemView.findViewById(R.id.contItemRoot);
            dateText = (TextView) itemView.findViewById(R.id.daysLeftText);
            delButton = (ImageButton) itemView.findViewById(R.id.delButton);
        }
    }

    public int pickColor(int Date)
    {
        Calendar c = Calendar.getInstance();

        long time = (c.getTimeInMillis()/1000)/86400;

        Log.d("DEBUG", Date + " - " + time);

        if(Date - time <= 0)
        {
            return R.color.colorDateOut;
        }
        else if(Date - time <= 2)
        {
            return R.color.colorDateRealyShort;
        }
        else if(Date - time <= 5)
        {
            return R.color.colorDateShort;
        }
        else if(Date - time >10)
        {
            return R.color.colorDateLong;
        }
        else
        {
            return R.color.colorDateMedium;
        }
    }

    public class sendUpdate extends AsyncTask<Void, Void, Boolean>
    {

        private String item;
        private String date;

        sendUpdate(String item, String date)
        {
            this.item = item;
            this.date = date;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {

            Boolean result = false;
            String url;
            JSONObject js;
            Log.d("uTask", "started background");
            url = "http://zerentia.net/workspace/refdate/requests/update.php?email=1&key=1";

            try
            {
                url += "&i=" + URLEncoder.encode(item, "UTF-8");
                url += "&da=" + URLEncoder.encode(date, "UTF-8");
                url += "&a=-1";
                url += "&d=1";
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            Log.d("url", url);
            try
            {
                js = ExternalDataHandler.readJsonFromUrl(url);
                result = js.getBoolean("result");
            } catch (IOException e)
            {
                // e.printStackTrace();
            } catch (JSONException e)
            {
                //e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(final Boolean success) {
            try
            {
                MainActivity ma = (MainActivity) getActivity();
                ma.updateRecView();
            } catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            } catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

            uTask = null;
        }

    }

    public static Activity getActivity() throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException
    {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);

        Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
        if(activities == null)
            return null;

        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                return activity;
            }
        }

        return null;
    }
}
