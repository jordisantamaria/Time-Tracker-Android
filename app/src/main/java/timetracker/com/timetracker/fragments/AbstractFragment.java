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
import android.widget.TextView;

import java.util.ArrayList;

import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AbstractFragment extends Fragment  {


    public AbstractFragment() {
        // Required empty public constructor
    }

    TextView descriptionText;
    TextView durationText;
    TextView activeText;
    private ProjectSQLite p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String projectName = this.getArguments().getString("projectName");
        System.out.println("Entra en abstract de " + projectName);
        p = new ProjectSQLite(getContext());
        Cursor cursor = p.readAbstractProjects(projectName);

        View viewD = inflater.inflate(R.layout.fragment_abstract, container, false);

        if(cursor.moveToFirst() != false) {
            int columna = cursor.getColumnIndex("Description");
            String desc = cursor.getString(columna);
            columna = cursor.getColumnIndex("Duration");
            String duration = cursor.getString(columna);
            columna = cursor.getColumnIndex("Active");
            String active = cursor.getString(columna);
            System.out.println("Ha recibido los parametros: " + desc + duration + active);

            descriptionText = (TextView) viewD.findViewById(R.id.description);
            descriptionText.setText(desc);

            durationText = (TextView) viewD.findViewById(R.id.duration_abstract);
            if (duration == null){
                durationText.setText("00:00:00");
            } else {
                durationText.setText(duration);
            }

            activeText = (TextView) viewD.findViewById(R.id.active_abstract);
            if (active.equals((String)"0")){
                activeText.setText("INACTIVE");
            } else {
                activeText.setText("ACTIVE");
            }


        }
        return viewD;
    }

}
