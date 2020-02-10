package com.example.ericeddy.colours.UI.Dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ericeddy.colours.System.Helper;
import com.example.ericeddy.colours.R;
import com.example.ericeddy.colours.UI.ColourPanel;

public class PresetLayoutsView extends DialogView {

    public ColourPanel panel;
    private Matrix matrix = new Matrix();
    private int colorLength;

    private View button1;
    private View button2;
    private View button3;
    private View button4;
    private View button5;


    public PresetLayoutsView(Context context) {
        super(context);
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_panel_presets, this, true);

        super.init();

        colorLength = Helper.getSelectedColors().length;

        button1 = findViewById(R.id.preset_1);
        button2 = findViewById(R.id.preset_2);
        button3 = findViewById(R.id.preset_3);
        button4 = findViewById(R.id.preset_4);
        button5 = findViewById(R.id.preset_5);

        button1.setBackground(getButtonBitmap(0));
        button2.setBackground(getButtonBitmap(1));
        button3.setBackground(getButtonBitmap(2));
        button4.setBackground(getButtonBitmap(3));
        button5.setBackground(getButtonBitmap(4));

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreset(0);
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreset(1);
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreset(2);
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreset(3);
            }
        });
        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreset(4);
            }
        });
    }

    public Drawable getButtonBitmap(int button) {
        int bitmapSize = Helper.convertDpToPixels(60);
        Bitmap bmp = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        int[] colours = Helper.getBtnColors();
        int[][] cells = null;
        if(button == 0){
            cells = preset1(20, 20, 12);
        } else if(button == 1){
            cells = preset2(20, 20, 12);
        } else if(button == 2){
            cells = preset3(20, 20, 12);
        } else if(button == 3){
            cells = preset4(20, 20, 12);
        } else if(button == 4){
            cells = preset5(20, 20, 12);
        }
        int gridSize = bitmapSize / 20;
        for(int y = 0; y < cells.length; y++) {
            for(int x = 0; x < cells.length; x++){
                Paint p = new Paint();
                int cellValue = cells[y][x];
                p.setColor(Helper.getContextColor( colours[cellValue] ));
                Rect currentRect = new Rect(x * gridSize, y * gridSize, (x * gridSize) + gridSize, (y * gridSize) + gridSize);
                canvas.drawRect(currentRect, p);
            }
        }
//        canvas.drawBitmap(bmp, matrix, null);
        Drawable d = new BitmapDrawable(getResources(), bmp);
        return d;
    }

    public void selectPreset(int preset) {
        int[][] cells = null;
        if(preset == 0){
            cells = preset1(panel.yNumCells, panel.xNumCells, colorLength);
        } else if(preset == 1){
            cells = preset2(panel.yNumCells, panel.xNumCells, colorLength);
        } else if(preset == 2){
            cells = preset3(panel.yNumCells, panel.xNumCells, colorLength);
        } else if(preset == 3){
            cells = preset4(panel.yNumCells, panel.xNumCells, colorLength);
        } else if(preset == 4){
            cells = preset5(panel.yNumCells, panel.xNumCells, colorLength);
        }
        if(cells != null){
            setCells(cells);
        }
    }
    public int[][] preset1(int yCell, int xCell, int length) {
        // Vertical bars
//        int yCell = panel.yNumCells;
//        int xCell = panel.xNumCells;
//        int length = colorLength;
        int[][] cells = new int[yCell][xCell];
        int x; int y; int xMod = 0; int yMod = 0;
        for (y = 0; y < yCell; y++) {
            xMod = 0;
            for (x = 0; x < xCell; x++) {
                cells[y][x] = xMod;
                xMod = xMod + 1;
                if(xMod >= length){
                    xMod = 0;
                }
            }
        }
        return cells;
    }

    public int[][] preset2(int yCell, int xCell, int length) {
        // Horizontal bars
        int[][] cells = new int[yCell][xCell];
        int x; int y; int xMod = 0; int yMod = 0;
        for (y = 0; y < yCell; y++) {
            for (x = 0; x < xCell; x++) {
                cells[y][x] = yMod;
            }
            yMod = yMod + 1;
            if(yMod >= length){
                yMod = 0;
            }
        }
        return cells;// setCells(cells);

    }
    public int[][] preset3(int yCell, int xCell, int length) {
        int[][] cells = new int[yCell][xCell];
        int x; int y; int xMod = 0; int yMod = 0; int zMod = 0;
        for (y = 0; y < yCell; y++) {
            xMod = 0;
            for (x = 0; x < xCell; x++) {
                xMod = xMod + 1;
                if(xMod >= length){
                    xMod = 0;
                }
                zMod = xMod + yMod;
                if(zMod >= length){
                    zMod = zMod % length;
                }
                cells[y][x] = zMod;
            }
            yMod = yMod + 1;
            if(yMod >= length){
                yMod = 0;
            }
        }
        return cells;
    }
    public int[][] preset4(int yCell, int xCell, int length) {
        int[][] cells = new int[yCell][xCell];
        int x; int y; int colorVal; int diffX; int diffY;
        //bars // middle square // bars
        int startYSquare = ( yCell - xCell ) / 2;
        int bottomBarsStart = yCell - startYSquare;
        boolean isOverHalfY = false; boolean isOverHalfX = false;
        for (y = 0; y < yCell; y++) {
            diffY = yCell - y - 1;
            isOverHalfY = y >= ( yCell / 2 );
            for (x = 0; x < xCell; x++) {
                diffX = xCell - x - 1;
                isOverHalfX = x >= ( xCell / 2 );
                if(y < startYSquare){
                    colorVal = y;
                } else if (y > bottomBarsStart) {
                    colorVal = diffY;
                } else {
                    int dX = ( isOverHalfX ? diffX : x);
                    int dY = (( isOverHalfY ? diffY : y ) );
                    colorVal = dY - startYSquare < dX ? dY : dX + startYSquare;
                }
                if(colorVal >= length) {
                    colorVal = colorVal % length;
                }
                cells[y][x] = colorVal;
            }
        }
        return cells;
    }
    public int[][] preset5(int yCell, int xCell, int length) {
        int[][] cells = new int[yCell][xCell];
        int x; int y; int colorVal; int diffX; int diffY;
        //bars // middle square // bars
//        int startYSquare = ( panel.yNumCells - panel.xNumCells ) / 2;
//        int bottomBarsStart = panel.yNumCells - startYSquare;
        boolean isOverHalfY = false; boolean isOverHalfX = false;
        for (y = 0; y < yCell; y++) {
            diffY = yCell - y - 1;
            isOverHalfY = y > ( yCell / 2 );
            for (x = 0; x < xCell; x++) {
                diffX = xCell - x - 1;
                isOverHalfX = x >= ( xCell / 2 );

                int dX = ( isOverHalfX ? diffX : x);
                int dY = (( isOverHalfY ? diffY : y ) );
                int dZ = (dY < dX ? dY : dX);
                colorVal = dZ > 4 ? length - 16 : dZ - 6;
                colorVal = length - colorVal;

                if(colorVal >= length) {
                    colorVal = colorVal % length;
                }
                cells[y][x] = colorVal;
            }
        }
        return cells;
    }



    private void setCells(int[][] cells) {
        panel.addMemoryState();
        panel.setCells(cells);
    }
}
