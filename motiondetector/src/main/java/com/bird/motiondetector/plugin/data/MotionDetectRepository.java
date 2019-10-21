package com.bird.motiondetector.plugin.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.bird.motiondetector.plugin.data.doa.MotionEventDao;
import com.bird.motiondetector.plugin.data.entity.MotionDetectorEntity;
import com.bird.motiondetector.plugin.utilities.ToUpdate;
import com.bird.motiondetector.plugin.utilities.Utils;

import java.util.List;

/**
 * This is repository class used to decide which kind of data storage required.
 * As of db insertion and query are taken care, in future with this class itself
 * we can decide whether we need to query/push into cloud or not
 */
public class MotionDetectRepository {

    private static final String TAG = "MotionDetectRepository";
    private MotionEventDao mMotionEventDao;
    private Context mContext;
    private ToUpdate mUpdateObj;

    public MotionDetectRepository(Context context, boolean isGetContent) {
        mContext = context;
        MotionDetectDatabase db = MotionDetectDatabase.getDatabase(context.getApplicationContext());
        mMotionEventDao = db.eventDao();
        // during the time of inserting, we do not want to get the content
        if (isGetContent)
            new FetchAsyncTask(mMotionEventDao).execute();
    }

    public void getAllEvent(boolean forceFetch, ToUpdate updateObj) {
        mUpdateObj = updateObj;
        if (forceFetch) {
            new FetchAsyncTask(mMotionEventDao).execute();
        }
    }

    void updateDataEvent(List<MotionDetectorEntity> allEvent) {
        List<String> AllEvent = null;
        if (allEvent.size() > 0)
            AllEvent = Utils.getDbList(mContext.getApplicationContext(), allEvent);
        if (mUpdateObj != null)
            mUpdateObj.onUpdate(AllEvent);
    }


    public long insert (MotionDetectorEntity event) {
        try {
            return mMotionEventDao.insert(event);
        } catch (SQLiteConstraintException e) {
            Log.d(TAG, "Constraints are coming same");
        }
        return -1;
    }

    public MotionDetectorEntity getParticularEntry(String time) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM motion_detector_table WHERE event_time = ?",
                new Object[]{time});
        MotionDetectorEntity entity = mMotionEventDao.getParticularEntry(query);
        return entity;
    }

    private class FetchAsyncTask extends AsyncTask<MotionDetectorEntity, Void, List<MotionDetectorEntity>> {

        private MotionEventDao motionEventDao;

        FetchAsyncTask(MotionEventDao dao) {
            motionEventDao = dao;
        }

        @Override
        protected List<MotionDetectorEntity> doInBackground(final MotionDetectorEntity... params) {
            return motionEventDao.getAllEventsFromDb();
        }

        @Override
        protected void onPostExecute(List<MotionDetectorEntity> motionDetectorEntities) {
            super.onPostExecute(motionDetectorEntities);
            updateDataEvent(motionDetectorEntities);
        }
    }
}
