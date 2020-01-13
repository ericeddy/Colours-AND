package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class SliderSelectDialog extends DialogView {

    protected int thumbPadding = Helper.convertDpToPixels(12);
    protected int yPosition = 0;
    protected float getCurrentValue() {
        return 0;
    }
    protected float getMaxValue() {
        return 1;
    }

    protected RelativeLayout mainViewBG;
    protected View thumb;

    public SliderSelectDialog(Context context) {
        super(context);
        init();
    }

    public SliderSelectDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SliderSelectDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SliderSelectDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slider_select_dialog, this, true);

        super.init();

        thumb = findViewById(R.id.thumb);

        mainViewBG = findViewById(R.id.view_background);
        mainViewBG.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                } else if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP){
                    float value = 0;
                    float height = v.getMeasuredHeight();
                    if(event.getY() < (float)( thumbPadding)) {
                        value = 1;
                    } else if(event.getY() > (float)( height )){
                        value = 0;
                    } else {
                        value = (height - event.getY()) / (height - (thumbPadding * 2));
                        if(value > 1){
                            value = 1;
                        } else if( value < 0 ){
                            value = 0;
                        }
                    }
                    Log.v("touchValue", value+"");
                    onValueChanged(value);
                }
                return true;
            }
        });
    }

    protected void setInitPosition() {
        // Do In Subclass //
        float thumbPercent = getCurrentValue() / getMaxValue();
        int height = Helper.convertDpToPixels(400) - (thumbPadding * 2);
        int thumbPos = (int)((height - (thumbPercent * (height - thumbPadding * 2)) - (Helper.convertDpToPixels(40) * 0.5)));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)thumb.getLayoutParams();
        lp.topMargin = thumbPos;
        thumb.setLayoutParams(lp);
    }
    protected void onValueChanged(float value) {
//        float topPos = (float)(thumbPadding + (thumb.getMeasuredHeight() * 0.5));
//        float bottomPos = (float)(thumbPadding - mainViewBG.getMeasuredHeight() - (thumb.getMeasuredHeight() * 0.5));
        int height = mainViewBG.getMeasuredHeight() - (thumbPadding * 2);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)thumb.getLayoutParams();
        lp.topMargin = (int)((height - (value * (height - thumbPadding * 2))) - (thumb.getMeasuredHeight() * 0.5));
        thumb.setLayoutParams(lp);

    }

    public void displayDialog(int position) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mainViewBG.getLayoutParams();
        int trackWidth = Helper.convertDpToPixels(60);
        int newPos = position - (int)(trackWidth * 0.5);
        int width = MainActivity.getScreenWidth();
        if(newPos < thumbPadding) {
            newPos = thumbPadding;
        } else if(newPos + trackWidth > width - thumbPadding) {
            newPos =  width - trackWidth - thumbPadding;
        }
        lp.setMarginStart(newPos);
        mainViewBG.setLayoutParams(lp);
        setInitPosition();
        displayDialog();
    }

    public void setDialogBottom(int bottom) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mainViewBG.getLayoutParams();
        lp.bottomMargin = bottom;
        mainViewBG.setLayoutParams(lp);
    }
}
