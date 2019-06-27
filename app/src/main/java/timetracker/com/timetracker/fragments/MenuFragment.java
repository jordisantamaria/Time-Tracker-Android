package timetracker.com.timetracker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import timetracker.com.timetracker.DialegActivitys.ActionsProjectActivity;
import timetracker.com.timetracker.DialegActivitys.ActionsTaskActivity;
import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.ItemAdapter;
import timetracker.com.timetracker.ItemAdapter_Project;
import timetracker.com.timetracker.ItemAdapter_menuPrincipal;
import timetracker.com.timetracker.ItemList;
import timetracker.com.timetracker.ItemList_Project;
import timetracker.com.timetracker.ItemList_menuPrincipal;
import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.MainTask;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;


public class MenuFragment extends Fragment {
    public MenuFragment() {
        // Required empty public constructor
    }

    private ListView listV;
    private ArrayAdapter adapter;
    private ArrayList arrayMenuNames;
    private ArrayList arrayNames;
    private ProjectSQLite p;
    private ItemList item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arrayMenuNames = new ArrayList();
        arrayNames = new ArrayList();
        arrayMenuNames.add(new ItemList_menuPrincipal(0, "Home",0));
        arrayNames.add("PRaiz");
         p = new ProjectSQLite(getContext());

        View view = inflater.inflate(R.layout.menu_principal, container, false);
        Cursor cursor = p.readProjects("PRaiz");
        if(cursor.moveToFirst() != false){
            int columna = cursor.getColumnIndex("Name");
            String name = cursor.getString(columna);
            arrayNames.add(name);
            arrayMenuNames.add(new ItemList_menuPrincipal(1, name,1));
            while (cursor.moveToNext()) {
                columna = cursor.getColumnIndex("Name");
                name = cursor.getString(columna);
                arrayNames.add(name);
                arrayMenuNames.add(new ItemList_menuPrincipal(1, name,1));
            }
        }

        // Inflate the layout for this fragment
        final ListView listV = (ListView)view.findViewById(R.id.menu);
        listV.setAdapter(new ItemAdapter_menuPrincipal(getContext(),arrayMenuNames));
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayNames.get(i).toString() == "PRaiz"){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("projectName",arrayNames.get(i).toString());
                    intent.putExtra("taskName",arrayNames.get(i).toString());
                    startActivity(intent);
                }

            }
        });
        return view;
    }

}