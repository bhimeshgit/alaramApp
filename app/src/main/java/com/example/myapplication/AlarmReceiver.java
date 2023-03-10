package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())){
            // reset all alarms
            Toast.makeText(context, "reset all alarms", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "perform your scheduled task here ", Toast.LENGTH_SHORT).show();
            // perform your scheduled task here (eg. send alarm notification)
        }

    }
}