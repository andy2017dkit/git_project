package com.example.maryann.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class sMainActivity extends AppCompatActivity {

    Button setTime;
    Button scheduleEvent;
    static final int DATEPICKER_DIALOG_ID = 0;
    static final int TIMEPICKER_DIALOG_ID = 1;
    int dpYear, dpMonth, dpDay, tpHour, tpMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar calendar = Calendar.getInstance();
        dpYear = calendar.get(Calendar.YEAR);
        dpMonth = calendar.get(Calendar.MONTH);
        dpDay = calendar.get(Calendar.DAY_OF_MONTH);
        tpHour = calendar.get(Calendar.HOUR_OF_DAY);
        tpMinute = calendar.get(Calendar.MINUTE);

        setTime = (Button) findViewById(R.id.button1);
        scheduleEvent = (Button) findViewById(R.id.button2);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATEPICKER_DIALOG_ID);
            }
        });

        scheduleEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("com.ohs.example.myEvent");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmMgr = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, dpYear);
                cal.set(Calendar.MONTH, dpMonth);
                cal.set(Calendar.DAY_OF_MONTH, dpDay);
                cal.set(Calendar.HOUR_OF_DAY, tpHour);
                cal.set(Calendar.MINUTE, tpMinute);
                cal.set(Calendar.SECOND, 0);
                long mills = cal.getTimeInMillis();

                alarmMgr.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
                Toast.makeText(this, "Event scheduled at " + tpHour + ":" + tpMinute + " " + dpDay + "/" + dpMonth + "/" + dpYear, Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATEPICKER_DIALOG_ID) {
            return new DatePickerDialog(this, datePickerListener, dpYear, dpMonth, dpDay);
        } else if (id == TIMEPICKER_DIALOG_ID) {
            return new TimePickerDialog(this, timePickerListener, tpHour, tpMinute, false);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dpYear = i;
                    dpMonth = i1 + 1;
                    dpDay = i2;
                    showDialog(TIMEPICKER_DIALOG_ID);
                }
            };

    protected TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    tpHour = i;
                    tpMinute = i1;
                }
            };
}