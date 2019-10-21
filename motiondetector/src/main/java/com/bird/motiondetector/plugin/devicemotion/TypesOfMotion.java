package com.bird.motiondetector.plugin.devicemotion;

import android.content.Context;
import android.hardware.SensorEvent;

/**
 * To perform respective task based on sensor event
 */
interface TypesOfMotion {
    boolean trackMotion(Context context, SensorEvent event);
}
