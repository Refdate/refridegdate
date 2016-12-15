package net.zerentia.refridgedate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Elev on 12/12/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DerpHolder> {

    private List<DateItem> listData;
    private LayoutInflater inflater;

    public RecyclerAdapter(List<DateItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.date_item, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        DateItem item = listData.get(position);
        holder.title.setText(item.getTitle());

        Calendar c = Calendar.getInstance();

        long time = (c.getTimeInMillis()/1000)/86400;

        holder.dateText.setText((item.getDate() - time) + " days left");
       // holder.icon.setImageResource(item.getImageResId());
        holder.container.setBackgroundResource(pickColor(item.getDate()));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView dateText;
        private ImageView icon;
        private View container;


        public DerpHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lblItemText);
            icon = (ImageView) itemView.findViewById(R.id.imItemIcon);
            container = itemView.findViewById(R.id.contItemRoot);
            dateText = (TextView) itemView.findViewById(R.id.daysLeftText);
        }
    }

    public int pickColor(int Date)
    {
        Calendar c = Calendar.getInstance();

        long time = (c.getTimeInMillis()/1000)/86400;

        Log.d("DEBUG", Date + " - " + time);

        if(Date - time <= 2)
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

}
