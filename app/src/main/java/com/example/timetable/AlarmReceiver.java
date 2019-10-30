package com.example.timetable;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("RING".equals(intent.getAction())){
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context)
                    .setContentText("There will be one course recently!")
                    .setContentTitle("Module")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setSmallIcon(R.drawable.watch_out)
                    .setDefaults(Notification.DEFAULT_ALL);
            manager.notify(Notification.FLAG_AUTO_CANCEL,notificationBuilder.build());
        }
    }
}
