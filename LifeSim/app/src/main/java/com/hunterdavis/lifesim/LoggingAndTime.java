package com.hunterdavis.lifesim;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by hunter on 7/20/14.
 */
public class LoggingAndTime {

    private static long lastLogTimeOverall = 0;
    private static HashMap<String, Long> lastLogTimePerTag = new HashMap<String, Long>();

    public static final boolean loggingEnabled = true;

    public static void logWithTiming(String tag, String message) {
        if(!loggingEnabled) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        Log.d(tag, message);
        Log.d(tag, "---Current Time is " + currentTime);
        Log.d(tag, "---Total Time Since Last Log Is " + (currentTime - lastLogTimeOverall));
        Log.d(tag, "---Last Log Time Overall is " + lastLogTimeOverall);

        if (lastLogTimePerTag.containsKey(tag)) {
            Log.d(tag, "---Total Time Since This Tag is: " + (currentTime - lastLogTimePerTag.get(tag)));
            Log.d(tag, "---Last Log Time For This Tag is: " + lastLogTimePerTag.get(tag));
        }

        lastLogTimeOverall = currentTime;
        lastLogTimePerTag.put(tag, currentTime);
    }
}
