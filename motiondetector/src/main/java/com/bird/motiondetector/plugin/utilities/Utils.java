package com.bird.motiondetector.plugin.utilities;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.bird.motiondetector.R;
import com.bird.motiondetector.plugin.data.entity.MotionDetectorEntity;
import com.bird.motiondetector.plugin.devicemotion.MotionDetectorService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Utils {

    private static String CHANNEL_ID = "monitor.motion.detector";
    /**
     * To show the notification
     * @param context
     * @param received
     */
    public static void showNotification(Context context, String received) {
       /* Notification.Builder mBuilder =   new Notification.Builder(context)
                .setSmallIcon(R.drawable.sample)
                .setContentTitle("Notification!")
                .setContentText(received)
                .setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());*/


        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MotionDetectorService.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID,
                        context.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(channel);
            }
            builder.setChannelId(CHANNEL_ID);
        }

        Notification notification = builder.setSmallIcon(R.drawable.sample)
                .setSmallIcon(R.drawable.sample)
                .setContentTitle("Notification!")
                .setContentText(received)
                .setAutoCancel(true).build();

        notificationManager.notify(012, notification);
    }

    /**
     * get the current time
     *
     * @return
     */
    public static String getCurrentTime(long eventTime) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);

        String time = sdf.format(new Date(eventTime));
        return time;
    }

    /**
     * get the content from entity class and make list
     *
     * @param context
     * @param allEvent
     * @return
     */
    public static List<String> getDbList(Context context, List<MotionDetectorEntity> allEvent) {
        if (context == null || allEvent.size() == 0)
            return new ArrayList<>();
        ArrayList<String> dbItem = new ArrayList<>();
        for (MotionDetectorEntity entity : allEvent) {
            String duration = entity.getEvent();
            String time = entity.getEventTime();
            String content = context.getString(R.string.duration);
            content = content+"="+duration;
            String timeString = context.getString(R.string.time);
            content = content+", "+timeString+"="+time;

            dbItem.add(content);
        }
        return dbItem;
    }

    /**
     * check whether service is running or not
     *
     * @return
     */
    public static boolean isServiceRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rsInfos = am.getRunningServices(500);
        if (rsInfos != null && rsInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo rsInfo : rsInfos) {
                if (rsInfo.service.getPackageName().equals(context.getPackageName())
                        && rsInfo.service.getClassName().equals(MotionDetectorService.class.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
