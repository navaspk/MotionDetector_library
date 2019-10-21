package com.bird.motiondetector.plugin.data.eventlogger;

import android.content.Context;
import android.os.AsyncTask;

import com.bird.motiondetector.R;
import com.bird.motiondetector.plugin.data.MotionDetectRepository;
import com.bird.motiondetector.plugin.data.entity.MotionDetectorEntity;
import com.bird.motiondetector.plugin.utilities.Utils;

import java.lang.ref.WeakReference;

/**
 * To log the event into db when motion detected
 */
public class LogEvent {

    private String mDuration;
    // in future if required
    private long mTime;

    private LogEvent(Builder builder) {
        mDuration = builder.mDuration;
        mTime = builder.mTime;
    }

    private void save(Context context) {
        new InsertAsyncTask(context, mDuration, mTime).execute();
    }

    public static Builder builder() {
        return new LogEvent.Builder();
    }

    public static class Builder {
        private String mDuration;
        private long mTime;

        public Builder duration(String duration) {
            mDuration = duration;
            return this;
        }

        public Builder time(long time) {
            mTime = time;
            return this;
        }

        public void save(Context context) {
            new LogEvent(this).save(context);
        }
    }

    /**
     * To perform the insertion task in different thread
     */
    private static class InsertAsyncTask extends AsyncTask<Void, Void, Long> {

        private WeakReference<Context> contextWeakReference;
        private String duration;
        private long time;

        InsertAsyncTask(Context context, String duration, long time) {
            contextWeakReference = new WeakReference<>(context);
            this.duration = duration;
            this.time = time;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            if (contextWeakReference.get() == null)
                return null;
            String currentTime = Utils.getCurrentTime(time);
            MotionDetectRepository repository = new MotionDetectRepository
                    (contextWeakReference.get().getApplicationContext(), false);
            // checking whether any specific entry already there or not
            if (repository.getParticularEntry(currentTime) != null) {
                return null;
            }

            // inserting new entry
            MotionDetectorEntity entity = new MotionDetectorEntity();
            entity.setEvent(duration);
            entity.setEventTime(currentTime);
            return repository.insert(entity);
        }

        @Override
        protected void onPostExecute(Long success) {
            super.onPostExecute(success);
            // show the notification
            if (success != null && success.longValue() > 0)
                Utils.showNotification(contextWeakReference.get().getApplicationContext(),
                        contextWeakReference.get().getApplicationContext().getString(R.string.noti_received));

        }
    }
}
