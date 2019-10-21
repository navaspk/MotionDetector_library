package com.bird.motiondetector.plugin.devicemotion;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.bird.motiondetector.plugin.data.eventlogger.LogEvent;

import java.text.DecimalFormat;

/**
 * class used to detect Free fall and respective operation
 */
class FreeFallMotion implements TypesOfMotion {

    private double mPreviousAccValue = 9;
    private long mFreeFallStartedTime = 0;

    @Override
    public boolean trackMotion(Context context, SensorEvent event) {
        // event.values[0] - Acceleration force along the x axis, so on.

        // taking event only when accuracy is high or medium at least.
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            double loX = event.values[0];
            double loY = event.values[1];
            double loZ = event.values[2];

            double accelerationReader = Math.sqrt(Math.abs(Math.pow(loX, 2))
                    + Math.abs(Math.pow(loY, 2))
                    + Math.abs(Math.pow(loZ, 2)));

            DecimalFormat precision = new DecimalFormat("0.00");
            double accRound = Double.parseDouble(precision.format(accelerationReader));
            if (mPreviousAccValue > accRound) {
                // if acceleration is decresing order
                mPreviousAccValue = accRound;
                if (mFreeFallStartedTime == 0)
                    mFreeFallStartedTime = System.currentTimeMillis();
            }

            //depending on device value intervals are changing
            if ((accRound > 0.3d && accRound < 0.5d) || (accRound > 0.66  && accRound < .99)) {
                // free fall detected
                long time = System.currentTimeMillis() - mFreeFallStartedTime;
                mPreviousAccValue = 9;
                mFreeFallStartedTime = 0;
                LogEvent.builder().duration(time+" ms").time(System.currentTimeMillis())
                        .save(context.getApplicationContext());

                return true;
            }
        }
        return false;
    }
}
