package timetracker.com.timetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import timetracker.com.timetracker.DialegActivitys.ActionsProjectActivity;
import timetracker.com.timetracker.DialegActivitys.ActionsTaskActivity;
import timetracker.com.timetracker.DialegActivitys.CreateProjectActivity;
import timetracker.com.timetracker.DialegActivitys.CreateReportActivity;
import timetracker.com.timetracker.DialegActivitys.CreateTaskActivity;
import timetracker.com.timetracker.fragments.AllFragment;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {
    boolean menuStatus = false;
    private ArrayList arrayMenuNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupTabs();
        this.setTitle("");
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

    public void onBackPressed(){
        ProjectSQLite p = new ProjectSQLite(this);
        Cursor cursor = p.areActiveTasks();
        if(cursor.moveToFirst() != false) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing App")
                    .setMessage("Are you sure you want to close the app? There are active activities, if you leave it will stop")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            super.onBackPressed();
        }
    }

    private void setupTabs()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapterHome adapter = new PageAdapterHome
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    //elegir las opciones de la barra de acción
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_create_project:

                intent = new Intent(this, CreateProjectActivity.class);
                intent.putExtra("Father", "PRaiz");
                startActivity(intent);
                return true;
            case R.id.action_create_task:

                intent = new Intent(this, CreateTaskActivity.class);
                intent.putExtra("Father", "PRaiz");
                startActivity(intent);
                return true;

            case R.id.action_settings:
                ProjectSQLite p = new ProjectSQLite(this);
                p.pauseALLActivities();
                return true;
            case R.id.action_create_report:

                intent = new Intent(this, CreateReportActivity.class);
                intent.putExtra("Father", "PRaiz");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
