package com.bird.motiondetector.plugin.devicemotion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.bird.motiondetector.R;

/**
 * Service used to perform foreground task to detect motion events
 */
public class MotionDetectorService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private String INTENT_EXTRA_TAB = "TAB";
    private String CHANNEL_ID = "monitor.motion.detector";
    private int NOTIFY_ID = 123456;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        new Thread() {

            @Override
            public void run() {
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);

                mSensorManager.registerListener(MotionDetectorService.this, mSensor,
                        SensorManager.SENSOR_DELAY_UI);
                setupNotification();
                super.run();
            }
        }.start();

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        for (MONITOR_MOTIONS motion : MONITOR_MOTIONS.values()) {
            if (motion.getMotion().trackMotion(this, event)) {
                break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * To show the notification along with foreground task run
     */
    private void setupNotification() {
        // Instantiate a Notification
        int icon = R.drawable.sample;
        CharSequence tickerText = getString(R.string.notif_server_starting);
        long when = System.currentTimeMillis();

        // Define Notification's message
        CharSequence contentTitle = getString(R.string.notif_title);
        CharSequence contentText = "";

        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, MotionDetectorService.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(channel);
            }
            builder.setChannelId(CHANNEL_ID);
        }

        Notification notification = builder.setSmallIcon(icon)
                .setTicker(tickerText)
                .setWhen(when)
                .setOngoing(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(contentIntent).build();

        notificationManager.notify(NOTIFY_ID, notification);

        startForeground(NOTIFY_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // in case if at all, service is getting killed then restarting the same.
        startService(new Intent(this, MotionDetectorService.class));
    }
}
