package com.example.maryann.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Mary Ann on 19/04/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Broadcast Received", Toast.LENGTH_LONG).show();
        eventMethod();

    }

    protected void eventMethod() {



    }
}