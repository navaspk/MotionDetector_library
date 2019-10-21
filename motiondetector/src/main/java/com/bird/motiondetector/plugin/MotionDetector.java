package com.bird.motiondetector.plugin;

import android.content.Context;
import android.content.Intent;

import com.bird.motiondetector.plugin.data.MotionDetectRepository;
import com.bird.motiondetector.plugin.devicemotion.MotionDetectorService;
import com.bird.motiondetector.plugin.utilities.ToUpdate;
import com.bird.motiondetector.plugin.utilities.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * This class is accessing from provider for initializing
 */
public class MotionDetector {

    private static WeakReference<MotionDetectRepository> weakReference;
    private MotionDetectRepository mRepository;
    private static MotionDetector MONITOR_INSTANCE;

    public static MotionDetector getInstance() {
        if (MONITOR_INSTANCE == null) {
            synchronized (MotionDetector.class) {
                if (MONITOR_INSTANCE == null) {
                    MONITOR_INSTANCE = new MotionDetector();
                }
            }
        }
        return MONITOR_INSTANCE;
    }

    private MotionDetector() {
    }

    /**
     * Library initialization
     * @param context
     */
    void initialize(Context context) {
        if (mRepository == null) {
            mRepository = new MotionDetectRepository(context.getApplicationContext(), true);
            weakReference = new WeakReference<>(mRepository);
        }

        // need to start service, need to check whether service is
        // running, if not running then start
        if (!Utils.isServiceRunning(context.getApplicationContext()))
            context.startService(new Intent(context, MotionDetectorService.class));
    }

    public void getAllEvents(boolean forceFetch, ToUpdate updateObj) {
        if (weakReference.get() == null)
            throw new IllegalStateException("No access to db");
        else
            weakReference.get().getAllEvent(forceFetch, updateObj);
    }
}
