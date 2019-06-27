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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import timetracker.com.timetracker.mypackage.Interval;

public class ItemAdapter_menuPrincipal extends BaseAdapter {

    private Context context;
    private List<ItemList_menuPrincipal> items;
    private ImageView iconP;
    private TextView nameP;


    public ItemAdapter_menuPrincipal(Context context, List<ItemList_menuPrincipal> items) {
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
            rowView = inflater.inflate(R.layout.menu_list_item, parent, false);
        }

        // Set data into the view.
        ImageView iconP = (ImageView) rowView.findViewById(R.id.iconItem);
        final TextView nameP = (TextView) rowView.findViewById(R.id.nameItemList);


        final ItemList_menuPrincipal item = this.items.get(position);
        nameP.setText(item.getName());
        switch(item.getType()){
            case 0:
                iconP.setImageResource(R.drawable.ic_home);
                break;
            case 1:
                iconP.setImageResource(R.mipmap.icon_project);
                break;
            case 2:
                iconP.setImageResource(R.mipmap.icon_task);
                break;
            default:
                iconP.setImageResource(R.drawable.ic_home);
                break;


        }


        return rowView;
    }

}