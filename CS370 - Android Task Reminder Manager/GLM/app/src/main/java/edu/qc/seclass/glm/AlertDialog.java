package edu.qc.seclass.glm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class AlertDialog extends AppCompatActivity {

    DatePicker date;
    TimePicker time;
    Button add;
    LayoutInflater inflater;
    Boolean bday = false;
    Boolean bweek= false;
    Boolean bmonth= false;
    Boolean byear= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aleret_dialog);

        add = (Button) findViewById(R.id.btnSaveAlert);
        date = (DatePicker) findViewById(R.id.datePicker);
        time = (TimePicker) findViewById(R.id.timePicker);
        Button repeat = (Button) findViewById(R.id.btnRepeat);
        inflater = this.getLayoutInflater();


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setRepeatDialog();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getCurrentHour(), time.getCurrentMinute());

                Intent data = new Intent();
                data.putExtra("date", calendar.getTimeInMillis());
                data.putExtra("day", bday);
                data.putExtra("week", bweek);
                data.putExtra("month", bmonth);
                data.putExtra("year", byear);
                setResult(1, data);
                finish();
            }
        });
    }

    void setRepeatDialog(){
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setTitle("Make it a repeating Alert");
        final View mView = inflater.inflate(R.layout.repeat_dialog, null);
        alert.setView(mView);

        alert.setPositiveButton("Add Repeat", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CheckBox day = mView.findViewById(R.id.rptDay);
                CheckBox week = mView.findViewById(R.id.rptWeek);
                CheckBox month = mView.findViewById(R.id.rptMonth);
                CheckBox year = mView.findViewById(R.id.rptYear);

                if(day.isChecked()) {
                    bday = true;
                }
                if(week.isChecked()) {
                    bweek = true;
                }
                if(month.isChecked()) {
                    bmonth = true;
                }
                if(year.isChecked()) {
                    byear = true;
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        alert.show();
    }

}
