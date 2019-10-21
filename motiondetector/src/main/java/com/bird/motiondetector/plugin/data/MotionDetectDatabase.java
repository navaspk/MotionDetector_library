package com.bird.motiondetector.plugin.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bird.motiondetector.plugin.data.doa.MotionEventDao;
import com.bird.motiondetector.plugin.data.entity.MotionDetectorEntity;

@Database(entities = {MotionDetectorEntity.class}, version = 1)
abstract  class MotionDetectDatabase extends RoomDatabase {

    abstract MotionEventDao eventDao();

    private static volatile MotionDetectDatabase INSTANCE;

    static MotionDetectDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MotionDetectDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MotionDetectDatabase.class, "motion_detect_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
