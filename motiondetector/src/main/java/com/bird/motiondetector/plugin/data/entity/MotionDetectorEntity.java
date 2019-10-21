package com.bird.motiondetector.plugin.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "motion_detector_table")
public class MotionDetectorEntity {

    @NonNull
    @ColumnInfo(name = "event")
    private String mEvent;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "event_time")
    private String mEventTime;

    public void setEvent(String mEvent) {
        this.mEvent = mEvent;
    }

    public void setEventTime(String currentMilliSec) {
        this.mEventTime = currentMilliSec;
    }

    public String getEvent(){
        return this.mEvent;
    }

    public String getEventTime(){
        return this.mEventTime;
    }
}
