package com.spitspce.space;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author Sumeet
 *
 */
public class EventItemAdapter extends ArrayAdapter<EventItem> {
    Context context;
    int layoutResourceId;
    ArrayList<EventItem> data = new ArrayList<EventItem>();

    public EventItemAdapter(Context context, int layoutResourceId,
                            ArrayList<EventItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.eventImage = (ImageView) row.findViewById(R.id.image_view_category_event);


            holder.eventTitle = (TextView) row.findViewById(R.id.category_event_item_text);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/Berlin Sans FB.ttf");

        EventItem item = data.get(position);
        holder.eventImage.setBackgroundResource(item.getEventImageID());
        holder.eventTitle.setTypeface(tf2);
        holder.eventTitle.setText(item.getEventName());

        return row;

    }

    static class RecordHolder {

        private ImageView eventImage;
        private TextView eventTitle;

    }
}
