package timetracker.com.timetracker.DialegActivitys;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;
import timetracker.com.timetracker.fragments.DatePickerFragment;

//actividad que se ve como un dialogo debido a que en manifest esta definido un theme dialog.
public class CreateTaskActivity extends Activity implements DatePickerFragment.datePickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }
    @Override
    public void itemClicked(int year, int month, int day) {
        //comprobamos si tiene fragmentContainer, en mobil no lo tiene por lo que generara un intent, en cambio en tablet si tiene por lo que reemplazara el contenido del fragmentContainer

        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            initDateButton.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
        Fragment prevFinalDate = getFragmentManager().findFragmentByTag("finalDateTag");
        if (prevFinalDate != null) {
            finalDateButton.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }

    }
    //recogemos los valores y los enviamos a la actividad principal al darle al boton create
    public void onCreateTask(View v)
    {
        String father = getIntent().getExtras().getString("Father");
        System.out.println("Crear Tarea para " + father);
        EditText nameEdit = (EditText)findViewById(R.id.name);
        String nameText =nameEdit.getText().toString();

        EditText descriptionEdit = (EditText)findViewById(R.id.description);
        String descriptionText =descriptionEdit.getText().toString();

        ProjectSQLite activity = new ProjectSQLite(this);
        activity.addTask(nameText, descriptionText, father);

        Intent intent = null;
        if(father.equals("PRaiz")){
            intent = new Intent(this, HomeActivity.class);
        } else{
            intent = new Intent(this, MainActivity.class);
        }
        intent.putExtra("projectName",father);
        startActivity(intent);
        finish();
    }
    Button initDateButton;
    Button finalDateButton;
    @Override
    protected void onStart() {
        super.onStart();
        initDateButton = (Button)findViewById(R.id.initDate);
        finalDateButton= (Button)findViewById(R.id.finalDate);
    }

    public void onInitDate(View v) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(ft, "dialog");

    }

    public void onFinalDate(View v) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("finalDateTag");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(ft, "finalDateTag");

    }




}
