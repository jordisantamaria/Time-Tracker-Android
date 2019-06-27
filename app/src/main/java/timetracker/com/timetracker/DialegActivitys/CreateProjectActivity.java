package timetracker.com.timetracker.DialegActivitys;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;

//actividad que se ve como un dialogo debido a que en manifest esta definido un theme dialog.
public class CreateProjectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
    }
    //recogemos los valores y los enviamos a la actividad principal al darle al boton create
    public void onCreateProject(View v)
    {
        String father = getIntent().getExtras().getString("Father");
        System.out.println("Crear proyecto para " + father);
        EditText nameEdit = (EditText)findViewById(R.id.name);
        String nameText =nameEdit.getText().toString();

        EditText descriptionEdit = (EditText)findViewById(R.id.description);
        String descriptionText =descriptionEdit.getText().toString();

        ProjectSQLite activity = new ProjectSQLite(this);
        activity.addProject(nameText, descriptionText, father);

        Intent intent = null;
        if(father.equals("PRaiz")){
            intent = new Intent(this, HomeActivity.class);
        } else{
            intent = new Intent(this, MainActivity.class);
        }
        intent.putExtra("projectName",father);
        startActivity(intent);
        //acabamos la actividad para que no se pueda acceder con el boton atras.
        finish();
    }
}
