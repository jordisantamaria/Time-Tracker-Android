package timetracker.com.timetracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import timetracker.com.timetracker.mypackage.Interval;

public class ProjectSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB_TimeTracker";

    String sqlP = "CREATE TABLE Projects (Name TEXT, Description TEXT, InitDate DATE, FinalDate DATE, Duration TIME, FatherProject TEXT, Active BOOLEAN)";
    String sqlT = "CREATE TABLE Tasks (Name TEXT, Description TEXT, InitDate DATE, FinalDate DATE, Duration TIME, FatherProject TEXT, Active BOOLEAN)";
    String sqlI = "CREATE TABLE Intervals (InitDate DATE, FinalDate DATE, Duration TIME, Task TEXT)";

    public ProjectSQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(sqlP);
        db.execSQL(sqlT);
        db.execSQL(sqlI);
        //addExample(db);
    }
    public void addExample(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("Name", "P0");
        values.put("Description", "Project for installing APPs");
        values.put("FatherProject", "PRaiz");
        values.put("InitDate", "20/12/2016");
        values.put("FinalDate", "20/12/2016");
        values.put("Duration", "00:35:10");
        values.put("Active", false);
        db.insert("Projects", null, values);
        values = new ContentValues();
        values.put("Name", "T0");
        values.put("Description", "Project for search results");
        values.put("FatherProject", "PRaiz");
        values.put("InitDate", "01/01/2017");
        values.put("FinalDate", "01/01/2017");
        values.put("Duration", "00:30:00");
        values.put("Active", false);
        db.insert("Tasks", null, values);
        values = new ContentValues();
        values.put("Name", "T0.1");
        values.put("Description", "Project for search results");
        values.put("FatherProject", "P0");
        values.put("InitDate", "12/12/2017");
        values.put("FinalDate", "12/12/2017");
        values.put("Duration", "00:42:10");
        values.put("Active", false);
        db.insert("Tasks", null, values);
        values = new ContentValues();
        values.put("Name", "P0.1");
        values.put("Description", "Project for installing APPs");
        values.put("FatherProject", "P0");
        values.put("InitDate", "20/12/2016");
        values.put("FinalDate", "20/12/2016");
        values.put("Duration", "00:25:00");
        values.put("Name", "P0.2");
        values.put("Description", "Project for installing APPs");
        values.put("FatherProject", "P0");
        values.put("InitDate", "20/12/2016");
        values.put("FinalDate", "20/12/2016");
        values.put("Duration", "00:10:00");
        values.put("Active", false);
        db.insert("Projects", null, values);
        values = new ContentValues();
        values.put("Name", "P1");
        values.put("Description", "Project for search results");
        values.put("FatherProject", "PRaiz");
        values.put("InitDate", "05/01/2017");
        values.put("FinalDate", "06/01/2017");
        values.put("Duration", "02:20:15");
        values.put("Active", false);
        db.insert("Projects", null, values);
        values = new ContentValues();
        values.put("Name", "T1.1");
        values.put("Description", "Project for search results");
        values.put("FatherProject", "P1");
        values.put("InitDate", "03/01/2017");
        values.put("FinalDate", "03/01/2017");
        values.put("Duration", "00:05:36");
        values.put("Active", false);
        db.insert("Tasks", null, values);
        values = new ContentValues();
        values.put("Name", "P1.1");
        values.put("Description", "Project for search results");
        values.put("FatherProject", "P1");
        values.put("InitDate", "03/01/2017");
        values.put("FinalDate", "03/01/2017");
        values.put("Duration", "01:30:15");
        values.put("Active", false);
        db.insert("Projects", null, values);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Projects");
        db.execSQL(sqlP);
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        db.execSQL(sqlT);
        db.execSQL("DROP TABLE IF EXISTS Intervals");
        db.execSQL(sqlI);
    }

    public void addProject(String name, String description, String father){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", description);
        values.put("FatherProject", father);
        values.put("Active", false);
        db.insert("Projects", null, values);
        db.close();
    }
    public Cursor readProjects(String father){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name, Duration FROM Projects WHERE FatherProject='" + father + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readAbstractProjects(String projectName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Description, Duration, Active FROM Projects WHERE Name='" + projectName + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readActiveProjects(String projectName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name, Duration FROM Projects WHERE FatherProject='" + projectName + "' AND Active ='1'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readTasks(String father){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name, Duration FROM Tasks WHERE FatherProject='" + father + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor readDescriptionTask(String task){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Description FROM Tasks WHERE Name='" + task + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor readActiveTasks(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name, Duration FROM Tasks WHERE FatherProject='" + taskName + "' AND Active ='1'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public void addTask(String name, String description, String father) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", description);
        values.put("FatherProject", father);
        values.put("Active", false);
        db.insert("Tasks", null, values);
        db.close();
    }

    public Cursor readIntervals(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT InitDate, FinalDate , Duration FROM Intervals WHERE Task='" + taskName + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public void activeTask(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Active","1");
        db.update("Tasks", valores, "Name = '"+ taskName + "'", null);
        db.close();
    }
    public void addInitDate(String d, String task) {
        System.out.println("Vamos a actualizar la tarea: "+ task +"y cambiamos por: " + d);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT InitDate FROM Tasks WHERE Name='" + task + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(cursor.moveToFirst() != false){
            int columna = cursor.getColumnIndex("InitDate");
            String date = cursor.getString(columna);
            System.out.println("Al buscar en la db vale: " + date);
            if(date == null){
                System.out.println("Como es null entra aqui");
                ContentValues valores = new ContentValues();
                valores.put("InitDate",""+ d +"");
                db.update("Tasks", valores, "Name = '"+ task + "'", null);
            }
        }
        db.close();
    }
    public void desactiveTask(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Active","0");
        db.update("Tasks", valores, "Name = '"+ taskName + "'", null);
        db.close();
    }
    public void addInterval(String init, String finalD, Time duration, String task) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("InitDate", init);
        values.put("FinalDate", finalD);
        values.put("Duration", ""+ duration+ "");
        values.put("Task", task);
        db.insert("Intervals", null, values);
        db.close();
    }
    public void addFinalDate(String d, String task) {
        System.out.println("Vamos a actualizar la tarea: "+ task +"y cambiamos por: " + d);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FinalDate FROM Tasks WHERE Name='" + task + "'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(cursor.moveToFirst() != false){
            int columna = cursor.getColumnIndex("FinalDate");
            String date = cursor.getString(columna);
            ContentValues valores = new ContentValues();
            valores.put("FinalDate",""+ d +"");
            db.update("Tasks", valores, "Name = '"+ task + "'", null);
        }
        db.close();
    }


    public void updateTaskName(String oldName, String newName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Name",""+ newName +"");
        db.update("Tasks", valores, "Name = '"+ oldName + "'", null);
        db.close();
    }
    public void updateTaskDescription(String oldName, String newName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Description",""+ newName +"");
        db.update("Tasks", valores, "Name = '"+ oldName + "'", null);
        db.close();
    }
    public void deleteTask(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("Tasks", "Name = '"+ name + "'", null);
        db.delete("Intervals", "Task = '"+ name + "'", null);
        db.close();
    }
    public void updateProjectName(String oldName, String newName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Name",""+ newName +"");
        db.update("Projects", valores, "Name = '"+ oldName + "'", null);
        db.close();
    }
    public void updateProjectDescription(String oldName, String newName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Description",""+ newName +"");
        db.update("Projects", valores, "Name = '"+ oldName + "'", null);
        db.close();
    }
    public void deleteProject(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("Projects", "Name = '"+ name + "'", null);
        db.delete("Tasks", "FatherProject = '"+ name + "'", null);
        db.close();
    }
    public Cursor areActiveTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name FROM Tasks WHERE Active ='1'", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public void pauseALLActivities(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("Active","0");
        db.update("Tasks", valores, "Active = '1'", null);
        db.close();
    }
}
