package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BrushSizeDialog extends SliderSelectDialog {

    int currentTouchSize = 0;

    @Override
    protected float getCurrentValue() {
        return currentTouchSize;
    }

    @Override
    protected float getMaxValue() {
        return SettingsBar.MAX_TOUCH_SIZE;
    }

    public BrushSizeDialog(Context context) {
        super(context);
        init();
    }

    public BrushSizeDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrushSizeDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BrushSizeDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        super.init();
        setInitPosition();
    }

    @Override
    protected void setInitPosition() {
        currentTouchSize = PreferenceManager.getTouchSize();
        super.setInitPosition();
    }

    @Override
    protected void onValueChanged(float value) {
        super.onValueChanged(value);
        int size = (int)(value * (float)getMaxValue());
        if(size != PreferenceManager.getTouchSize()){
            currentTouchSize = size;
            PreferenceManager.setTouchSize(size);
            MainActivity.touchSizeChanged();
        }
    }
}
