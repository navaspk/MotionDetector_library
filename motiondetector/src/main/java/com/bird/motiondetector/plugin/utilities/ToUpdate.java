package com.bird.motiondetector.plugin.utilities;

import java.util.List;

/**
 * Interface used to update the client UI based on request
 */
public interface ToUpdate {
    void onUpdate(List<String> data);
}
