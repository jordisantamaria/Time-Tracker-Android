package timetracker.com.timetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter_Project extends BaseAdapter {

    private Context context;
    private List<ItemList_Project> items;

    public ItemAdapter_Project(Context context, List<ItemList_Project> items) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.project_list_item, parent, false);
        }

        // Set data into the view.
        ImageView iconP = (ImageView) rowView.findViewById(R.id.iconProject);
        TextView nameP = (TextView) rowView.findViewById(R.id.nameItemList);
        TextView durationP = (TextView) rowView.findViewById(R.id.durationItemList);

        ItemList_Project item = this.items.get(position);
        nameP.setText(item.getName());
        durationP.setText(item.getDuration());
        iconP.setImageResource(item.getIcon());

        return rowView;
    }

}