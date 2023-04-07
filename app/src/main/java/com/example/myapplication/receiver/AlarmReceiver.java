package com.example.myapplication.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    String ADMIN_CHANNEL_ID="Admin channel";
    NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())){
            // reset all alarms
            Toast.makeText(context, "reset all alarms", Toast.LENGTH_SHORT).show();


        }
        else{
            showNotification(context, intent.getExtras().get("message").toString(), intent.getExtras().get("title").toString());
            // perform your scheduled task here (eg. send alarm notification)
        }

    }

    private void showNotification(Context context,String message,String title){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri rawPathUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);
        Ringtone r = RingtoneManager.getRingtone(context, rawPathUri);
        r.play();
        //Setting up Notification channels for android O and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels(context);
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        Log.d("stest",title);
        Log.d("stest",message);
        PendingIntent pendingIntent = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, 999,intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getActivity(context, 999,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        int notificationId = new Random().nextInt(60000);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)  //a resource for your custom small icon
                .setContentTitle("Diet Reminder") //the "title" value you sent in your notification
                .setContentText(message) //ditto
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(Context context){
        CharSequence adminChannelName = context.getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = context.getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.enableVibration(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }


}