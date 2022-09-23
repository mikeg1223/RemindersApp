package edu.qc.seclass.glm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    private static final int NUMBER_OF_CHANNELS = 3;
    private static final Pair<String, String>[]  channelParams = new Pair[NUMBER_OF_CHANNELS];
    private NotificationChannel channels[] = new NotificationChannel[NUMBER_OF_CHANNELS];
    private int selector = 0;

    private NotificationManager manager;

    public NotificationHelper(Context base){
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        for(int i=0; i<3; i++){
            channelParams[i] = new Pair<>("RemindersApp"+(i+1), "Channel "+(i+1));
        }

        for(int i=0; i < NUMBER_OF_CHANNELS; i++){
            String channelID = channelParams[i].first;
            String channelName = channelParams[i].second;
            channels[i] = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channels[i].enableLights(true);
            channels[i].enableVibration(true);
            channels[i].setLightColor(R.color.design_default_color_primary);
            channels[i].setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channels[i].setDescription("Channel for Reminders from the ReminderApp (GLM)");

            getManager().createNotificationChannel(channels[i]);
        }
    }

    public int getSelector(){
        return this.selector;
    }

    public NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationCompat.Builder getChannelNotification(String title, String msg, int selector){
        this.selector = selector;
        NotificationChannel currentChannel = channels[this.selector++];
        if(this.selector == NUMBER_OF_CHANNELS){this.selector=0;}

        currentChannel.enableLights(true);
        currentChannel.enableVibration(true);
        currentChannel.setLightColor(R.color.design_default_color_primary);
        currentChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(currentChannel);

        return new NotificationCompat.Builder(getApplicationContext(), channelParams[this.selector].first)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }
}
