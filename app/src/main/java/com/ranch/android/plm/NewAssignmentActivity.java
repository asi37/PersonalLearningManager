package com.ranch.android.plm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Asitha on 4/9/2017.
 */

public class NewAssignmentActivity extends AppCompatActivity implements View.OnClickListener {

        private int month, year, day, daytime, mintime;
    private EditText textdate, texttime;
    private Button btn_date, btn_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assignment);
        Log.d(MainActivity.LOGTAG, "Inside the onCreate Method");

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

//        textdate = (EditText) findViewById(R.id.editText_date);
//        texttime = (EditText) findViewById(R.id.editText_time);
//        btn_date = (Button) findViewById(R.id.date_btn);
//        btn_time = (Button) findViewById(R.id.time_btn);
//
//        btn_date.setOnClickListener(this);
//        btn_time.setOnClickListener(this);
     }


    @Override
    public void onClick(View v) {

//        if (v == btn_date) {
//            final Calendar c = Calendar.getInstance();
//            day = c.get(Calendar.DAY_OF_MONTH);
//            month = c.get(Calendar.MONTH);
//            year = c.get(Calendar.YEAR);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    textdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//
//                }
//            }
//                    , day, month, year);
//            datePickerDialog.show();
//
//
//        } else if (v == btn_time) {
//            final Calendar c = Calendar.getInstance();
//            daytime = c.get(Calendar.HOUR_OF_DAY);
//            mintime = c.get(Calendar.MINUTE);
//
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    texttime.setText(hourOfDay + ":" + minute);
//
//                }
//            }, daytime, mintime, false);
//            timePickerDialog.show();

//        }

    }
}
