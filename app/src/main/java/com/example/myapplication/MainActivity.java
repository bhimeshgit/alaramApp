package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarm();
    }

    private void setAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("any_data",123);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 15);  // set hour
        cal.set(Calendar.MINUTE, 49);          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        }else{

            String tag = "TAG";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), tag, new AlarmManager.OnAlarmListener() {
                    @Override
                    public void onAlarm() {
                        Toast.makeText(MainActivity.this, "AlarmManager.OnAlarmListener", Toast.LENGTH_SHORT).show();
                    }
                },null);
            }
        }
    }
    private void method3(){
        
    }
}