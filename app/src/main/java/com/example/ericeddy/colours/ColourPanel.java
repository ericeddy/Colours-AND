package com.example.ericeddy.colours;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class ColourPanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = ColourPanel.class.getSimpleName();

    private LoopThread thread;
    private int[] colours;
    private int[][] cells;
    private int cellSize = 0;
    private int xNumCells = 24;
    private int yNumCells = 0;

    private int currentCellX = -1;
    private int currentCellY = -1;

    private int touchSize = 1;

    private boolean needsDraw = false;
    private boolean isPlaying = false;

    private Canvas mCanvas;
    private Bitmap screenBMP;
    private Matrix matrix;


    SurfaceHolder surfaceHolder;

    int myCanvas_w, myCanvas_h;


    public ColourPanel(Context context) {
        super(context);
        init();
    }
    public ColourPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColourPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public void init() {
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        setFocusable(true);

        generateDefaultBitmaps();
        post(new Runnable() {
            @Override
            public void run() {
                generateDefaultCells();
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myCanvas_w = getMeasuredWidth();
        myCanvas_h = getMeasuredHeight();

        int maxWidth = getMeasuredWidth();
        int maxHeight = getMeasuredHeight();

        mCanvas = new Canvas();
        screenBMP = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(screenBMP);
        matrix = new Matrix();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        boolean retry = true;
//        while (retry) {
//            try {
//                thread.join();
//                retry = false;
//            } catch (InterruptedException e) {
//                // try again shutting down the thread
//            }
//        }
    }

    public void MyGameSurfaceView_OnResume(){

        surfaceHolder = getHolder();
        getHolder().addCallback(this);

//Create and start background Thread
        thread = new LoopThread(this, 20);
        thread.setRunning(true);
        thread.start();

    }
    public void MyGameSurfaceView_OnPause(){
//Kill the background Thread
        boolean retry = true;
        thread.setRunning(false);

        while(retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int cellX = (int)Math.floor(event.getX() / cellSize);
        int cellY = (int)Math.floor(event.getY() / cellSize);

        if(cellY > yNumCells || cellX > xNumCells) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentCellX = cellX;
            currentCellY = cellY;

            // Increase this cell by 1 //
            cellTouched(cellY, cellX);
            needsDraw = true;
            return true;
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(currentCellX != cellX || currentCellY != cellY) {
                // Increase this cell by 1 //
                cellTouched(cellY, cellX);
                currentCellX = cellX;
                currentCellY = cellY;
                needsDraw = true;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void cellTouched(int cellY, int cellX){
        if( touchSize == 0 ){
            increaseCellValue(cellY, cellX);
        } else {

        }
    }
    private void increaseCellValue(int cellY, int cellX) {
        if(cellY > yNumCells || cellX > xNumCells) {
            return;
        }
        int newValue = cells[cellY][cellX] + 1;
        if(newValue >= colours.length){
            newValue = 0;
        }
        cells[cellY][cellX] = newValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if( canvas == null/*!needsDraw || screenBMP == null || mCanvas == null*/){
            return;
        }

        for(int yCell = 0; yCell < yNumCells; yCell++){
            for(int xCell = 0; xCell < xNumCells; xCell++){
                Paint p = new Paint();
                int cellValue = cells[yCell][xCell];
                p.setColor(Helper.getContextColor( colours[cellValue] ));
                Rect currentRect = new Rect(xCell * cellSize, yCell * cellSize, (xCell * cellSize) + cellSize, (yCell * cellSize) + cellSize);
                mCanvas.drawRect(currentRect, p);
            }
        }

        canvas.drawBitmap(screenBMP, matrix, null);
        needsDraw = false;
    }

    public void updateSurfaceView(){
//The function run in background thread, not ui thread.

        Canvas canvas = null;
        if(isPlaying){
            increaseAllCells();
        }

        try{
            canvas = surfaceHolder.lockCanvas();

            synchronized (surfaceHolder) {
                onDraw(canvas);
            }
        }finally{
            if(canvas != null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void generateDefaultBitmaps() {
        // Depending on value we'll make 21 (maybe 28) bitmaps for sequencing //
        int[] defaultRainbowRes = {R.color.default_rainbow_11, R.color.default_rainbow_12, R.color.default_rainbow_13, R.color.default_rainbow_14,
                R.color.default_rainbow_21, R.color.default_rainbow_22, R.color.default_rainbow_23, R.color.default_rainbow_24,
                R.color.default_rainbow_31, R.color.default_rainbow_32, R.color.default_rainbow_33, R.color.default_rainbow_34,
                R.color.default_rainbow_41, R.color.default_rainbow_42, R.color.default_rainbow_43, R.color.default_rainbow_44,
                R.color.default_rainbow_51, R.color.default_rainbow_52, R.color.default_rainbow_53, R.color.default_rainbow_54,
                R.color.default_rainbow_61, R.color.default_rainbow_62, R.color.default_rainbow_63, R.color.default_rainbow_64,
                R.color.default_rainbow_71, R.color.default_rainbow_72, R.color.default_rainbow_73, R.color.default_rainbow_74 };
        colours = defaultRainbowRes;
    }

    public void generateDefaultCells() {
        int maxWidth = getMeasuredWidth();
        int maxHeight = getMeasuredHeight();

        cellSize = maxWidth /xNumCells;
        yNumCells = maxHeight / cellSize;

        cells = new int[yNumCells][xNumCells];
        needsDraw = true;
    }
    public void resetCells() {
        generateDefaultCells();
    }
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    public void increaseAllCells() {
        for(int yCell = 0; yCell < yNumCells; yCell++) {
            for (int xCell = 0; xCell < xNumCells; xCell++) {
                increaseCellValue(yCell, xCell);
            }
        }
    }
}
