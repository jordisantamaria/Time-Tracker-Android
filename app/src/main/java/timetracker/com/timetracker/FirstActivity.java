package timetracker.com.timetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import timetracker.com.timetracker.DialegActivitys.CreateProjectActivity;
import timetracker.com.timetracker.DialegActivitys.CreateTaskActivity;
import timetracker.com.timetracker.mypackage.*;

/*Create by Albert, Carlos and Jordi*/
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity_main);

        //comprobamos si es la primera vez ejecutamos la aplicación, si no lo es mostramos la mainActivity
        Boolean isFirstRun= true;
        isFirstRun =  getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", isFirstRun);

        if (!isFirstRun) {
            //show start activity

            startActivity(new Intent(this, HomeActivity.class));
           // Toast.makeText(this, "First Run="+isFirstRun, Toast.LENGTH_LONG)
             //       .show();
        }
    }

    public void onCreateNewTask(View view)
    {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra("Father", "PRaiz");
        startActivity(intent);
    }
    public void onCreateNewProject(View view)
    {
        Intent intent = new Intent(this, CreateProjectActivity.class);
        intent.putExtra("Father", "PRaiz");
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //al destruirla definimos que ya no se vuelva  amostrar esta actividad
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
        finish();
    }

}
