package com.hunterdavis.lifesim.UI;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by hunter on 7/23/14.
 */
public class MicrobeSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    EngineThread thread;
    Context ctx;

    SurfaceHolder ourHolder;


    public MicrobeSurfaceView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        // Constructor as before, with additional lines:
        ctx = context;

        ourHolder = getHolder();
        ourHolder.addCallback(this);

        setFocusable(true); // make sure we get key events

    }

    public EngineThread getThread() {
        return thread;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        ourHolder = holder;
        thread = new EngineThread(ourHolder, ctx, new Handler());
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) 	  {
        thread.setSurfaceSize(width, height);
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}