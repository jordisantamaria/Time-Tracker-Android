package timetracker.com.timetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import timetracker.com.timetracker.DialegActivitys.ActionsTaskActivity;
import timetracker.com.timetracker.DialegActivitys.CreateProjectActivity;
import timetracker.com.timetracker.DialegActivitys.CreateReportActivity;
import timetracker.com.timetracker.DialegActivitys.CreateTaskActivity;

/**
 * Created by Solde on 06/01/2017.
 */

public class MainTask extends AppCompatActivity {

    private String taskName = null;
    private String taskDescription = null;
    private TextView descriptionText;
    private ProjectSQLite p;
    private TextView title;
    private ArrayList arrayIntervals;
    boolean menuStatus = false;
    private ArrayList arrayMenuNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_main);
        taskName = getIntent().getExtras().getString("taskName");
        arrayIntervals = new ArrayList();
        System.out.println("Entra en el MainTask de " + taskName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.title_mainTask);
        title.setText(taskName);
        this.setTitle("");

        p = new ProjectSQLite(this);
        Cursor cursor = p.readDescriptionTask(taskName);
        if (cursor.moveToFirst() != false) {
            int columna = cursor.getColumnIndex("Description");
            taskDescription = cursor.getString(columna);
        }
        descriptionText = (TextView) findViewById(R.id.descriptionText_Task);
        descriptionText.setText(taskDescription);
        cursor = p.readIntervals(taskName);
        if (cursor.moveToFirst() != false) {
            int columna = cursor.getColumnIndex("InitDate");
            String initDate = cursor.getString(columna);
            columna = cursor.getColumnIndex("FinalDate");
            String finalDate = cursor.getString(columna);
            columna = cursor.getColumnIndex("Duration");
            String duration = cursor.getString(columna);
            arrayIntervals.add(new ItemList_Interval(initDate, finalDate, duration));
            while (cursor.moveToNext()) {
                columna = cursor.getColumnIndex("InitDate");
                initDate = cursor.getString(columna);
                columna = cursor.getColumnIndex("FinalDate");
                finalDate = cursor.getString(columna);
                columna = cursor.getColumnIndex("Duration");
                duration = cursor.getString(columna);
                arrayIntervals.add(new ItemList_Interval(initDate, finalDate, duration));
            }

            final ListView listV = (ListView) findViewById(R.id.intervalList);
            listV.setAdapter(new ItemAdapter_Interval(this, arrayIntervals));
        }
        menuStatus = false;

        final ViewPager viewPager2 = (ViewPager) findViewById(R.id.pager2);
        final PageAdapterMenu adapter2 = new PageAdapterMenu
                (getSupportFragmentManager(), 1);
        viewPager2.setAdapter(null);
        viewPager2.setCurrentItem(0);
        Button menu = (Button) findViewById(R.id.botonMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ClickMenu");

                if(menuStatus == false){
                    viewPager2.setAdapter(adapter2);
                    menuStatus = true;
                }else{
                    viewPager2.setAdapter(null);
                    menuStatus = false;
                }

            }
        });
    }


}
