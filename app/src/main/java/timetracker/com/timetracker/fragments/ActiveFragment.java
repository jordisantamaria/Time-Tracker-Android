package timetracker.com.timetracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import timetracker.com.timetracker.ItemAdapter;
import timetracker.com.timetracker.ItemAdapter_Project;
import timetracker.com.timetracker.ItemList;
import timetracker.com.timetracker.ItemList_Project;
import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.MainTask;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;


public class ActiveFragment extends Fragment {

    public ActiveFragment() {
        // Required empty public constructor
    }

    private ListView projects;
    private ArrayAdapter adapter;
    private ArrayList arrayProjects;
    private ArrayList arrayTasks;
    private ArrayList arrayProjectsNames;
    private ArrayList arrayTasksNames;
    private ProjectSQLite p;
    private ItemList item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String projectName = this.getArguments().getString("projectName");
        System.out.println("Entra en ACTIVE de " + projectName);
        arrayProjects = new ArrayList();
        arrayTasks = new ArrayList();
        arrayProjectsNames = new ArrayList();
        arrayTasksNames = new ArrayList();
        p = new ProjectSQLite(getContext());

        View view = inflater.inflate(R.layout.fragment_active, container, false);

        Cursor cursor = p.readActiveProjects(projectName);
        if(cursor.moveToFirst() != false){
            int columna = cursor.getColumnIndex("Name");
            String name = cursor.getString(columna);
            columna = cursor.getColumnIndex("Duration");
            String duration = cursor.getString(columna);
            if(duration == null){
                duration = "00:00:00";
            }
            arrayProjectsNames.add(name);
            arrayProjects.add(new ItemList_Project(R.mipmap.icon_project, name, duration));
            while (cursor.moveToNext()) {
                columna = cursor.getColumnIndex("Name");
                name = cursor.getString(columna);
                columna = cursor.getColumnIndex("Duration");
                duration = cursor.getString(columna);
                if(duration == null){
                    duration = "00:00:00";
                }
                arrayProjectsNames.add(name);
                arrayProjects.add(new ItemList_Project(R.mipmap.icon_project, name, duration));
            }

            System.out.println("Proyectos ALL de" + projectName + ": " + arrayProjectsNames);
            final ListView listV = (ListView) view.findViewById(R.id.subprojectsList);
            listV.setClickable(true);
            listV.setAdapter(new ItemAdapter_Project(getContext(), arrayProjects));
            listV.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Object projectNameO = new Object();
                    intent.putExtra("projectName",arrayProjectsNames.get(i).toString());
                    startActivity(intent);
                }
            });

        }

        cursor = p.readActiveTasks(projectName);
        if(cursor.moveToFirst() != false){
            int columna = cursor.getColumnIndex("Name");
            String name = cursor.getString(columna);
            columna = cursor.getColumnIndex("Duration");
            String duration = cursor.getString(columna);
            if(duration == null){
                duration = "00:00:00";
            }
            arrayTasksNames.add(name);
            arrayTasks.add(new ItemList(R.mipmap.icon_task, name, duration));
            while (cursor.moveToNext()) {
                columna = cursor.getColumnIndex("Name");
                name = cursor.getString(columna);
                columna = cursor.getColumnIndex("Duration");
                duration = cursor.getString(columna);
                if(duration == null){
                    duration = "00:00:00";
                }
                arrayTasksNames.add(name);
                arrayTasks.add(new ItemList(R.mipmap.icon_task, name, duration));
            }
            System.out.println("Tareas ALL de" + projectName + ": " + arrayProjectsNames);

            final ListView listV = (ListView) view.findViewById(R.id.tasksList);
            listV.setClickable(true);
            listV.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), MainTask.class);
                    intent.putExtra("taskName",  arrayTasksNames.get(i).toString());
                    startActivity(intent);
                }
            });
            listV.setAdapter(new ItemAdapter(getContext(),arrayTasks));
        }
        // Inflate the layout for this fragment
        return view;
    }

}