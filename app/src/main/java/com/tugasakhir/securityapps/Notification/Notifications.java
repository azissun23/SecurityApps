package com.tugasakhir.securityapps.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.tugasakhir.securityapps.R;

public class Notifications {
    private int notifID = 1001;
    private String chID = "ch_id";
    private String chName = "notif";

    public void sendNotif(Context context,String User, String Status, String Waktu, int id){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, chID)
                .setSmallIcon(R.drawable.ic_priority_high_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_priority_high_black_24dp))
                .setContentTitle(User)
                .setContentTitle(Status)
                .setContentTitle(Waktu)
                .setVibrate(new long[]{1000, 1000, 1000});

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chID, chName,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chName);
            builder.setChannelId(chID);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(notifID,notification);
        }
    }
}
