package timetracker.com.timetracker.DialegActivitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.Date;

import timetracker.com.timetracker.HomeActivity;
import timetracker.com.timetracker.MainActivity;
import timetracker.com.timetracker.R;
import timetracker.com.timetracker.fragments.DatePickerFragment;
import timetracker.com.timetracker.mypackage.Format;
import timetracker.com.timetracker.mypackage.Html;
import timetracker.com.timetracker.mypackage.Project;
import timetracker.com.timetracker.mypackage.Report;
import timetracker.com.timetracker.mypackage.Serial;
import timetracker.com.timetracker.mypackage.Txt;


public class CreateReportActivity extends Activity implements DatePickerFragment.datePickerListener {
    private static final int  YEAR = 2016;
    private static final int MONTH = 11;
    private static final int DAY = 23;
    private static final int H1 = 2;
    private static final int M1 = 0;
    private static final int H2 = 22;
    private static final int M2 = 30;
    private static final int YEAR_FORMAT_DATECLASS = 1900;
    private static final int MONTH_FORMAT_DATECLASS = 1;
    private static Serial serial = new Serial();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    Button initDateButton;
    Button finalDateButton;
    Button initHourButton;
    Button finalHourButton;
    int day, month, year;
    int hour;
    int minute;
    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        initDateButton = (Button) findViewById(R.id.initDate);
        finalDateButton = (Button) findViewById(R.id.finalDate);
        initHourButton = (Button) findViewById(R.id.initHour);
        finalHourButton = (Button) findViewById(R.id.finalHour);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
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

    public void onTypeClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.detailed:
                if (checked)
                    // detailed checked
                    break;
            case R.id.summarized:
                if (checked)
                    // summarized checked
                    break;
        }
    }

    public void onFormatClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.document:
                if (checked)
                    // detailed checked
                    break;
            case R.id.web:
                if (checked)
                    // summarized checked
                    break;
        }
    }

    public void onCreateReport(View v) throws IOException {
       // Project project = serial.read();
        Date initD = new Date(YEAR - YEAR_FORMAT_DATECLASS,
                MONTH - MONTH_FORMAT_DATECLASS,
                DAY, H1, M1, 0);
        Date finalD = new Date(YEAR - YEAR_FORMAT_DATECLASS,
                MONTH - MONTH_FORMAT_DATECLASS,
                DAY, H2, M2, 0);
        Format form = new Txt();
        /*project.createSmallReport(initD,
                finalD, form);
        project.createDetailedReport(initD,
                finalD, form);
        form = new Html();
        project.createSmallReport(initD,
                finalD, form);
        project.createDetailedReport(initD,
                finalD, form);*/
        finish();
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

    public void onInitHour(View v) {
        showDialog(0);
    }
    public void onFinalHour(View v) {
        showDialog(1);
    }
    public Dialog onCreateDialog(int i){
        switch (i){
            case 0:
                return new TimePickerDialog(this, mTimeSetListener, hour, minute, false);
            case 1:
                return new TimePickerDialog(this, mTimeSetListener2, hour, minute, false);
            default:
                return null;
        }
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourO, int minO){
            hour = hourO;
            minute = minO;
            String hourS;
            String minS;
            Button init = (Button) findViewById(R.id.initHour);
            if(hour <10){
                hourS = "0" + hour + "";
            }else{
                hourS = "" + hour + "";
            }
            if(minute <10){
                minS = "0" + minute + "";
            }else{
                minS = "" + minute + "";
            }
            init.setText(hourS + " : " + minS);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourO, int minO){
            hour = hourO;
            minute = minO;
            String hourS;
            String minS;
            Button init = (Button) findViewById(R.id.finalHour);
            if(hour <10){
                hourS = "0" + hour + "";
            }else{
                hourS = "" + hour + "";
            }
            if(minute <10){
                minS = "0" + minute + "";
            }else{
                minS = "" + minute + "";
            }
            init.setText(hourS + " : " + minS);
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CreateReport Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

