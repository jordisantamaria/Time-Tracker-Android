package timetracker.com.timetracker.DialegActivitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.ProjectSQLite;
import timetracker.com.timetracker.R;

public class ActionsProjectActivity extends Activity {

    String projectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_task);

        Intent i = getIntent();
        projectName= i.getExtras().get("projectName").toString();
    }

    public void onChangeName(View v)
    {
        EditText newName = (EditText)findViewById(R.id.newName);
        System.out.println(newName.getText());
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.updateProjectName(projectName, newName.getText().toString());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void onChangeDescription(View v)
    {
        EditText newName = (EditText)findViewById(R.id.newDescription);
        System.out.println(newName.getText());
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.updateProjectDescription(projectName, newName.getText().toString());
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void onDelete(View v)
    {
        System.out.println("delete");
        ProjectSQLite sql = new ProjectSQLite(this);
        sql.deleteProject(projectName);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
