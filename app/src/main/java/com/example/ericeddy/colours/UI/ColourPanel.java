package com.example.ericeddy.colours.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ericeddy.colours.System.Helper;
import com.example.ericeddy.colours.System.LoopThread;
import com.example.ericeddy.colours.System.PreferenceManager;

import java.util.ArrayList;

public class ColourPanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = ColourPanel.class.getSimpleName();

    private LoopThread thread;
    private int[] colours;
    private int[][] cells;
    public int cellSize = 0;
    public int xNumCells = 36;
    public int yNumCells = 0;

    private int currentCellX = -1;
    private int currentCellY = -1;

    private int touchSize = 0;
    private int brushType = 0;
    private int brushColorType = -1;
    private int theme = 0;

    private boolean needsDraw = false;
    private boolean isPlaying = false;
    private float playingCounter = 0;
    private float playingSpeed = 1;
    private boolean isPlayingForwards = true;

    private Canvas mCanvas;
    private Bitmap screenBMP;
    private Matrix matrix = new Matrix();


    SurfaceHolder surfaceHolder;

    int myCanvas_w, myCanvas_h;
    ArrayList<int[][]> memory = new ArrayList<>();


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
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v("COLORPANEL", "surface created");
        myCanvas_w = getMeasuredWidth();
        myCanvas_h = getMeasuredHeight();

        int maxWidth = getMeasuredWidth();
        int maxHeight = getMeasuredHeight();

        mCanvas = new Canvas();
        screenBMP = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(screenBMP);
        matrix = new Matrix();

        post(new Runnable() {
            @Override
            public void run() {
                if(cellSize == 0){
                    generateDefaultCells();
                } else {
                    needsDraw = true;
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v("COLORPANEL", "surface destroyed");
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
        thread = new LoopThread(this, 5);
        thread.setRunning(true);
        speedChanged();
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentCellX = cellX;
            currentCellY = cellY;

            addMemoryState();

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

    public void addMemoryState() {
        memory.add( getCellsCopy() );
        Log.v("Color Panel", "Memory Size:" + memory.size());
    }

    private void cellTouched(int cellY, int cellX){
        if( touchSize == 0 ){
            affectCellValue(cellY, cellX);
        } else {

            int length = 1 + (touchSize * 2); // touch = 1; length = 3;
            int skip = 0;
            for(int j = 0; j <= touchSize; j++ ){ // x
                int offsetY = -touchSize;
                for(int i = 0; i < length; i++ ){ // y
                    int c_cellY = cellY + offsetY;
                    if(j > 0) {
                        int c_cellX1 = cellX + skip;
                        int c_cellX2 = cellX - skip;
                        if(i >= skip && i < length - skip){
                            affectCellValue(c_cellY, c_cellX1);
                            affectCellValue(c_cellY, c_cellX2);
                        }
                    } else {
                        affectCellValue(c_cellY, cellX);

                    }
                    offsetY++;
                }
                skip++;
            }

//            for( int j = 0; j < length; j++ ){
//                int offsetX = -touchSize;
//                int c_cellY = offsetY + cellY;
//                for(int i = 0; i < length; i++ ){
//                    int c_cellX = offsetX + cellX;
//
//
//
//                    offsetX++;
//                }
//                offsetY++;
//            }
        }
    }
    private void affectCellValue(int cellY, int cellX) {
        if(cellY < 0 || cellY >= yNumCells || cellX < 0 || cellX >= xNumCells) {
            return;
        }
        int newValue = 0;
        if(brushType == 0) {
            newValue = cells[cellY][cellX] + 1;
        } else if (brushType == 1) {
            newValue = cells[cellY][cellX] - 1;
        } else if(brushType == 2) {
            newValue = brushColorType;
        }
        if(newValue >= colours.length){
            newValue = 0;
        } else if( newValue < 0 ) {
            newValue = colours.length - 1;
        }
        cells[cellY][cellX] = newValue;
    }

    private void affectCellPlayingValue(int cellY, int cellX) {
        if(cellY < 0 || cellY >= yNumCells || cellX < 0 || cellX >= xNumCells) {
            return;
        }
        int newValue;
        if(isPlayingForwards) {
            newValue = cells[cellY][cellX] + 1;
        } else {
            newValue = cells[cellY][cellX] - 1;
        }
        if(newValue >= colours.length){
            newValue = 0;
        } else if( newValue < 0 ) {
            newValue = colours.length - 1;
        }
        cells[cellY][cellX] = newValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if( canvas == null || matrix == null || screenBMP == null/*!needsDraw || screenBMP == null || mCanvas == null*/){
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

    @SuppressLint("WrongCall")
    public void updateSurfaceView(){
//The function run in background thread, not ui thread.

        Canvas canvas = null;
        if(isPlaying){
            playingCounter = playingCounter - playingSpeed;
            if(playingCounter <= 0){
                increaseAllCells();
                playingCounter = 1 + playingCounter;
            }
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
        int[] defaultRainbowRes = Helper.getSelectedColors();
        colours = defaultRainbowRes;
    }

    public void generateDefaultCells() {
        int maxWidth = getMeasuredWidth();
        int maxHeight = getMeasuredHeight();

        cellSize = maxWidth /xNumCells;
        yNumCells = maxHeight / cellSize;

        cells = new int[yNumCells][xNumCells];
        needsDraw = true;

        MainActivity.colorPanelSizeUpdated();
    }
    public void resetCells() {
        memory.clear();
        memory = new ArrayList<>();
        generateDefaultCells();
    }
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    public void increaseAllCells() {
        for(int yCell = 0; yCell < yNumCells; yCell++) {
            for (int xCell = 0; xCell < xNumCells; xCell++) {
                affectCellPlayingValue(yCell, xCell);
            }
        }
    }

    public int[][] getCells() {
        return cells;
    }
    public void setCells(int[][] newValue) {
        if(newValue.length == cells.length && newValue[0].length == cells[0].length){
            cells = newValue;
        } else {
            int[][] fixedData = new int[cells.length][cells[0].length];
            for( int yCell = 0; yCell < yNumCells; yCell++ ){
                if(yCell < newValue.length) {
                    for(int xCell = 0; xCell < xNumCells; xCell++){
                        if(xCell < newValue[0].length) {
                            fixedData[yCell][xCell] = newValue[yCell][xCell];
                        }
                    }
                }
            }
            cells = fixedData;
        }
    }

    public int[][] getCellsCopy() {
        if (cells == null || cells.length == 0) return null;
        int length = cells.length;
        int[][] target = new int[length][cells[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(cells[i], 0, target[i], 0, cells[i].length);
        }
        return target;
    }

    public void undoLastAction() {
        if(memory.size() == 0) return;
        int index = memory.size()-1;
        setCells(memory.get(index));
        memory.remove(index);
        needsDraw = true;
        Log.v("Color Panel", "Memory Size:" + memory.size());
    }

    public void isPlayingForwardsChanged(){
        isPlayingForwards = PreferenceManager.getPlayingForwards();
    }
    public void touchSizeChanged(){
        touchSize = PreferenceManager.getTouchSize();
    }
    public void brushTypeChanged() {
        brushType = PreferenceManager.getTouchType();
        brushColorType = PreferenceManager.getTouchColour();
    }
    public void speedChanged() {
        playingSpeed = PreferenceManager.getPlayingSpeed();
        if(playingSpeed == 1){
            playingCounter = 0;
        }
//        thread.SPEED_MODIFIER = PreferenceManager.getPlayingSpeed();
    }

    public void themeChanged() {
        generateDefaultBitmaps();
    }
}
