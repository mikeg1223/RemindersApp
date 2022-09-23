package edu.qc.seclass.glm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlertReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Llegue a AlertReceiver");

        String title = intent.getStringExtra("title");
        String msg = intent.getStringExtra("message");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        int currentNotificationID = notificationHelper.getSelector();
        NotificationCompat.Builder builder = notificationHelper.getChannelNotification(title, msg, currentNotificationID);
        notificationHelper.getManager().notify(currentNotificationID, builder.build());
    }
}