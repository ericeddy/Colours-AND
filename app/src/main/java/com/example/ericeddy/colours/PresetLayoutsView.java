package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class PresetLayoutsView extends RelativeLayout {

    private Context mContext;
    private RelativeLayout rootView;
    public ColourPanel panel;

    private View button1;
    private View button2;
    private View button3;
    private View button4;
    private View button5;


    public PresetLayoutsView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public PresetLayoutsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_panel_presets, this, true);

        rootView = findViewById(R.id.root_view);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSettings();
            }
        });
        RelativeLayout mainViewBG = findViewById(R.id.view_background);
        mainViewBG.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
                if(xMod >= 28){
                    xMod = 0;
                }
            }
        }
        panel.setCells(cells);
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
            if(yMod >= 28){
                yMod = 0;
            }
        }
        panel.setCells(cells);

    }
    public void preset3() {

    }
    public void preset4() {

    }
    public void preset5() {

    }

    public void displaySettings() {
        // Do animate in //
        rootView.setVisibility(View.VISIBLE);
    }

    public void closeSettings() {
        // Do Animate Out //
        rootView.setVisibility(View.GONE);
    }
}
