package com.example.ericeddy.colours.System;

import android.os.SystemClock;

import com.example.ericeddy.colours.UI.ColourPanel;

public class LoopThread extends Thread {

    private static final String TAG = LoopThread.class.getSimpleName();
/*
    private SurfaceHolder surfaceHolder;
    private ColourPanel panel;

    // desired fps
    private final static int 	TARGET_FPS = 29;

    // maximum number of frames to be skipped
    private final static int	MAX_FRAME_SKIPS = 3;
    // the frame period
    private final static int	FRAME_PERIOD = 1000 / TARGET_FPS;




    public LoopThread(SurfaceHolder surfaceHolder, ColourPanel panel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }

    // flag to hold game state
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;

        Log.d(TAG, "Starting game loop");

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped

        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing on the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;	// resetting the frames skipped
                    // update game state
//                    this.panel.update();

                    // render state to the screen
                    // draws the canvas on the panel
                    this.panel.onDraw(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
//                        this.panel.update();

                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            } // end finally
        }
    } */


    private final static int TARGET_FPS = 24;

    // maximum number of frames to be skipped
    private final static int	MAX_FRAME_SKIPS = 2;
    // the frame period
    private final static int	FRAME_PERIOD = 1000 / TARGET_FPS;

    volatile boolean running = false;

    ColourPanel parent;
    long sleepTime;

    LoopThread(ColourPanel sv, long st){
        super();
        parent = sv;
        sleepTime = st;
    }

    public void setRunning(boolean r){
        running = r;
    }

    @Override
    public void run() {

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime = 0;		// ms to sleep (<0 if we're behind)
//        int framesSkipped;	// number of frames being skipped

        while(running){

            try {
                beginTime = SystemClock.uptimeMillis();

                parent.updateSurfaceView();

//                framesSkipped = 0;	// resetting the frames skipped
                // calculate how long did the cycle take

                timeDiff = SystemClock.uptimeMillis() - beginTime;
                // calculate sleep time
                sleepTime = (int)(TARGET_FPS - timeDiff);

                if (sleepTime < 2) {
                    sleepTime = 2;
                }
                sleep(sleepTime);
//                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
//                    // we need to catch up
//                    // update without rendering
////                        this.panel.update();
//
//                    // add frame period to check if in next frame
//                    sleepTime += FRAME_PERIOD;
//                    framesSkipped++;
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}