package timetracker.com.timetracker.DialegActivitys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;

public class ActionsTaskActivity extends Activity {

    String taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_task);

        Intent i = getIntent();
        taskName= i.getExtras().get("taskName").toString();
    }

    public void onChangeName(View v)
    {
        EditText newName = (EditText)findViewById(R.id.newName);
        System.out.println(newName.getText());
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.updateTaskName(taskName, newName.getText().toString());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void onChangeDescription(View v)
    {
        EditText newName = (EditText)findViewById(R.id.newDescription);
        System.out.println(newName.getText());
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.updateTaskDescription(taskName, newName.getText().toString());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void onDelete(View v)
    {
        System.out.println("delete");
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.deleteTask(taskName);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
