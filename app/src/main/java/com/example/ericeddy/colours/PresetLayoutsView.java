package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class PresetLayoutsView extends DialogView {

    public ColourPanel panel;

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

        colorLength = Helper.getLightColors().length;

        button1 = findViewById(R.id.preset_1);
        button2 = findViewById(R.id.preset_2);
        button3 = findViewById(R.id.preset_3);
        button4 = findViewById(R.id.preset_4);
        button5 = findViewById(R.id.preset_5);

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preset1();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preset2();
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preset3();
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preset4();
            }
        });
        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preset5();
            }
        });
    }

    public void preset1() {
        // Vertical bars
        int[][] cells = new int[panel.yNumCells][panel.xNumCells];
        int x; int y; int xMod = 0; int yMod = 0;
        for (y = 0; y < panel.yNumCells; y++) {
            xMod = 0;
            for (x = 0; x < panel.xNumCells; x++) {
                cells[y][x] = xMod;
                xMod = xMod + 1;
                if(xMod >= colorLength){
                    xMod = 0;
                }
            }
        }
        setCells(cells);
    }

    public void preset2() {
        // Horizontal bars
        int[][] cells = new int[panel.yNumCells][panel.xNumCells];
        int x; int y; int xMod = 0; int yMod = 0;
        for (y = 0; y < panel.yNumCells; y++) {
            for (x = 0; x < panel.xNumCells; x++) {
                cells[y][x] = yMod;
            }
            yMod = yMod + 1;
            if(yMod >= colorLength){
                yMod = 0;
            }
        }
        setCells(cells);

    }
    public void preset3() {
        int[][] cells = new int[panel.yNumCells][panel.xNumCells];
        int x; int y; int xMod = 0; int yMod = 0; int zMod = 0;
        for (y = 0; y < panel.yNumCells; y++) {
            xMod = 0;
            for (x = 0; x < panel.xNumCells; x++) {
                xMod = xMod + 1;
                if(xMod >= colorLength){
                    xMod = 0;
                }
                zMod = xMod + yMod;
                if(zMod >= colorLength){
                    zMod = zMod % colorLength;
                }
                cells[y][x] = zMod;
            }
            yMod = yMod + 1;
            if(yMod >= colorLength){
                yMod = 0;
            }
        }
        setCells(cells);
    }
    public void preset4() {
        int[][] cells = new int[panel.yNumCells][panel.xNumCells];
        int x; int y; int colorVal; int diffX; int diffY;
        //bars // middle square // bars
        int startYSquare = ( panel.yNumCells - panel.xNumCells ) / 2;
        int bottomBarsStart = panel.yNumCells - startYSquare;
        boolean isOverHalfY = false; boolean isOverHalfX = false;
        for (y = 0; y < panel.yNumCells; y++) {
            diffY = panel.yNumCells - y;
            isOverHalfY = y >= ( panel.yNumCells / 2 );
            for (x = 0; x < panel.xNumCells; x++) {
                diffX = panel.xNumCells - x - 1;
                isOverHalfX = x >= ( panel.xNumCells / 2 );
                if(y < startYSquare){
                    colorVal = y;
                } else if (y > bottomBarsStart) {
                    colorVal = diffY;
                } else {
                    int dX = ( isOverHalfX ? diffX : x);
                    int dY = (( isOverHalfY ? diffY : y ) );
                    colorVal = dY - startYSquare < dX ? dY : dX + startYSquare;
                }
                if(colorVal >= colorLength) {
                    colorVal = colorVal % colorLength;
                }
                cells[y][x] = colorVal;
            }
        }
        setCells(cells);
    }
    public void preset5() {
        int[][] cells = new int[panel.yNumCells][panel.xNumCells];
        int x; int y; int colorVal; int diffX; int diffY;
        //bars // middle square // bars
//        int startYSquare = ( panel.yNumCells - panel.xNumCells ) / 2;
//        int bottomBarsStart = panel.yNumCells - startYSquare;
        boolean isOverHalfY = false; boolean isOverHalfX = false;
        for (y = 0; y < panel.yNumCells; y++) {
            diffY = panel.yNumCells - y;
            isOverHalfY = y > ( panel.yNumCells / 2 );
            for (x = 0; x < panel.xNumCells; x++) {
                diffX = panel.xNumCells - x;
                isOverHalfX = x >= ( panel.xNumCells / 2 );

                int dX = ( isOverHalfX ? diffX : x);
                int dY = (( isOverHalfY ? diffY : y ) );
                int dZ = (dY < dX ? dY : dX);
                colorVal = dZ > 4 ? colorLength - 2 : dZ;
                colorVal = colorLength - colorVal;

                if(colorVal >= colorLength) {
                    colorVal = colorVal % colorLength;
                }
                cells[y][x] = colorVal;
            }
        }
        setCells(cells);
    }

    private void setCells(int[][] cells) {
        panel.addMemoryState();
        panel.setCells(cells);
    }
}
