package timetracker.com.timetracker.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.Calendar;

/**
 * Created by User on 05/01/2017.
 */
public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static interface datePickerListener {
        void itemClicked(int year, int month, int day);
    };
    private datePickerListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        month = month +1;
        if (listener != null) {
            listener.itemClicked(year, month, day);
        }
        view.updateDate(year,month,day);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (datePickerListener)activity;
    }

}