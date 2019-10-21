package com.bird.motiondetector.plugin.data.doa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.bird.motiondetector.plugin.data.entity.MotionDetectorEntity;

import java.util.List;

@Dao
public interface MotionEventDao {

    @Insert()
    long insert(MotionDetectorEntity entity);

    //if required
    @Query("DELETE FROM motion_detector_table")
    void deleteAll();

    @Query("SELECT * from motion_detector_table ORDER BY event ASC")
    List<MotionDetectorEntity> getAllEventsFromDb();

    @RawQuery(observedEntities = MotionDetectorEntity.class)
    MotionDetectorEntity getParticularEntry(SupportSQLiteQuery  query);
}
