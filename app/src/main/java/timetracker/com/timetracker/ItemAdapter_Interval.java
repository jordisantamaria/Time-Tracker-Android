package timetracker.com.timetracker;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import timetracker.com.timetracker.mypackage.Interval;

public class ItemAdapter_Interval extends BaseAdapter {

    private Context context;
    private List<ItemList_Interval> items;


    public ItemAdapter_Interval(Context context, List<ItemList_Interval> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.interval_list_item, parent, false);
        }

        // Set data into the view.

        TextView initDate = (TextView) rowView.findViewById(R.id.initDateItemList);
        TextView finalDate = (TextView) rowView.findViewById(R.id.finalDateItemList);
        TextView duration = (TextView) rowView.findViewById(R.id.durationIntervalItemList);


        ItemList_Interval item = this.items.get(position);
        initDate.setText(item.getInitDate());
        finalDate.setText(item.getFinalDate());
        duration.setText(item.getDuration());

        return rowView;
    }

}