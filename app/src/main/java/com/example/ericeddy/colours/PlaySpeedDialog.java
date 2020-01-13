package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class PlaySpeedDialog extends SliderSelectDialog {

    float currentPlaySpeed = 0;

    @Override
    protected float getCurrentValue() {
        return currentPlaySpeed;
    }

    @Override
    protected float getMaxValue() {
        return 1;
    }

    public PlaySpeedDialog(Context context) {
        super(context);
        init();
    }

    public PlaySpeedDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaySpeedDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlaySpeedDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        currentPlaySpeed = PreferenceManager.getPlayingSpeed();
        super.setInitPosition();
    }

    @Override
    protected void onValueChanged(float value) {
        super.onValueChanged(value);
        float size = (value * getMaxValue());
        if(size != PreferenceManager.getTouchSize()){
            currentPlaySpeed = size;
            PreferenceManager.setPlayingSpeed(size);
            MainActivity.speedChanged();
        }
    }
}
