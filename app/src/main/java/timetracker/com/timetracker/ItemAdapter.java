package timetracker.com.timetracker;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import timetracker.com.timetracker.mypackage.Interval;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<ItemList> items;
    private ImageView iconP;
    private TextView nameP;
    private ImageView iconPlay, iconPause;
    private ProjectSQLite p;
    private Interval interval;

    public ItemAdapter(Context context, List<ItemList> items) {
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

    public String getTextMiliseconds(long miliseconds) {
        float seconds =  ((float)miliseconds /(float) 1000) * -1 ;
        String time = "";
        time += String.valueOf(seconds);

        return time;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.task_list_item, parent, false);
        }

        // Set data into the view.
        ImageView iconP = (ImageView) rowView.findViewById(R.id.iconProject);
        final TextView nameP = (TextView) rowView.findViewById(R.id.nameItemList);
        final ImageView iconPlay = (ImageView) rowView.findViewById(R.id.playbutton);
        final Chronometer crono = (Chronometer) rowView.findViewById(R.id.chronometer2);

        final ItemList item = this.items.get(position);
        nameP.setText(item.getName());
        iconP.setImageResource(item.getIcon());
        //iconPlay.setImageResource(item.getplayIcon());

        iconPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = new ProjectSQLite(v.getContext());

                if(item.getplayIcon() == R.mipmap.ic_play){
                    iconPlay.setImageResource(R.mipmap.ic_pause);
                    item.setPlayIcon(R.mipmap.ic_pause);
                    p.activeTask(nameP.getText().toString());
                    crono.setBase(SystemClock.elapsedRealtime());
                    crono.start();
                    interval = new Interval();
                    Date initDate = interval.getInitDate();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String d = dateFormat.format(initDate);
                    p.addInitDate(d, nameP.getText().toString());
                }else{
                    iconPlay.setImageResource(R.mipmap.ic_play);
                    item.setPlayIcon(R.mipmap.ic_play);
                    p.desactiveTask(nameP.getText().toString());
                    crono.stop();
                    long duration = crono.getBase();
                    Date newDate = new Date(duration*1000);
                    interval.setDuration(newDate);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date initDate = interval.getInitDate();
                    String initD = dateFormat.format(initDate);
                    Date finalDate = interval.getFinalDate();
                    String finalD = dateFormat.format(finalDate);
                    Time durationD = interval.getDuration();
                    p.addInterval(initD, finalD, durationD, nameP.getText().toString());
                    p.addFinalDate(finalD, nameP.getText().toString());
                }
            }
        });
        return rowView;
    }

}