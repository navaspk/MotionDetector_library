package com.bird.motiondetector.plugin.devicemotion;

import android.content.Context;
import android.hardware.SensorEvent;

/**
 * To update db or ui when rotation event happen - future purpose
 */
class RotateMotion implements TypesOfMotion {

    // This is for future implementation
    @Override
    public boolean trackMotion(Context context, SensorEvent event) {
        return false;
    }
}
