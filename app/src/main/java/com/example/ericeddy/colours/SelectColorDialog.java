package com.example.ericeddy.colours;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SelectColorDialog extends DialogView {

    public LinearLayout listView;
    private int[] colors;
    private int initTouchType = 0;
    private int currentTouchType = 0;
    private int selectedId = -1;
    private int listHeight;
    private int cellSize;

    public SelectColorDialog(Context context) {
        super(context);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.select_color_dialog, this, true);

        super.init();

        listView = findViewById(R.id.list_view);
        listView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int yIndex = (int)(event.getY() / cellSize);
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(event.getX() > 0 && event.getX() < v.getMeasuredWidth()){
                        if(yIndex != selectedId) {
                            PreferenceManager.setTouchColour(yIndex);
                            PreferenceManager.setTouchType(2);
                            MainActivity.selectColorChanged();
                            selectedId = yIndex;
                            updateCells();
                        }
                    } else {
                        PreferenceManager.setTouchColour(-1);
                        PreferenceManager.setTouchType(initTouchType == 2 ? 0 : initTouchType);
                        MainActivity.selectColorChanged();
                        selectedId = -1;
                        updateCells();
                    }
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getX() > 0 && event.getX() < v.getMeasuredWidth()){
                        if(yIndex != selectedId) {
                            PreferenceManager.setTouchColour(yIndex);
                            PreferenceManager.setTouchType(2);
                            MainActivity.selectColorChanged();
                            selectedId = yIndex;
                            updateCells();
                        }
                    } else {
                        PreferenceManager.setTouchColour(-1);
                        PreferenceManager.setTouchType(initTouchType == 2 ? 0 : initTouchType);
                        MainActivity.selectColorChanged();
                        selectedId = -1;
                        updateCells();
                    }
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public void displayDialog() {
        super.displayDialog();

        Helper.printBlueColors();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)(mContext)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        listHeight = (height - Helper.convertDpToPixels(16 + 32) );// internal padding + bottom padding
        colors = Helper.getSelectedColors();
        cellSize = listHeight / colors.length;
        selectedId = PreferenceManager.getTouchColour();
        initTouchType = PreferenceManager.getTouchType();
        updateCells();
    }

    private void updateCells() {
        currentTouchType = PreferenceManager.getTouchType();
        for(int i = 0; i < colors.length; i++) {
            SelectColorHolder v = (SelectColorHolder) listView.getChildAt(i);
            if (v == null) {
                SelectColorHolder view = new SelectColorHolder(mContext);
                view.setup(i, (selectedId == i && currentTouchType == 2), cellSize, colors);
                listView.addView(view);
            } else {
                v.setup(i, (selectedId == i && currentTouchType == 2), cellSize, colors);
            }
        }

        ViewGroup.LayoutParams ll = listView.getLayoutParams();
        ll.height = (colors.length * cellSize);
        ll.width = cellSize;
        listView.setLayoutParams(ll);
    }

    static class SelectColorHolder extends RelativeLayout {

        private Context mContext;
        private RelativeLayout background;
        private View selectedView;
        private int id;

        public SelectColorHolder(Context context) {
            super(context);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            mContext = context;
            init();
        }

        public void init() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.select_color_holder, this, true);

            background = findViewById(R.id.select_color_holder);
            selectedView = findViewById(R.id.selected);
        }



        public void setup( int colorId, boolean selected, int cellSize, int[] colors) {
            id = colorId;
            selectedView.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
            background.setBackgroundColor( mContext.getColor(colors[colorId]) );
            background.getLayoutParams().width = cellSize;
            background.getLayoutParams().height = cellSize;
        }
    }
}
