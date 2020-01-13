package com.example.ericeddy.colours;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;

public class SettingsBar extends RelativeLayout {

    public static int MAX_TOUCH_SIZE = 12;

    protected Context mContext;

    protected RelativeLayout rootView;
    protected HorizontalScrollView scroll;

    protected LinearLayout brushButtonsSection;
    protected RelativeLayout brushSectionBtn;
    protected RelativeLayout brushSelectColorBtn;
    protected RelativeLayout brushInvertBtn;
    protected RelativeLayout brushDefaultBtn;
    protected View brushSelectColorSelected;
    protected View brushInvertSelected;
    protected View brushDefaultSelected;
    protected ImageView brushSizeDecreaseBtn;
    protected ImageView brushSizeIncreaseBtn;
    protected TextView brushSizeDisplay;
    protected LinearLayout brushSizeLayout;

    protected LinearLayout controlsButtonsSection;
    protected RelativeLayout controlsSectionBtn;
    protected TextView controlsButtonTitle;
    private ImageView controlsPauseImage;
    private ImageView controlsPlayImage;
    protected RelativeLayout controlsRewindBtn;
    protected RelativeLayout controlsForwardBtn;
    protected View controlsRewindSelected;
    protected View controlsForwardSelected;
    protected ImageView controlsSpeedIncreaseBtn;
    protected ImageView controlsSpeedDecreaseBtn;
    protected TextView controlsSpeedDisplay;
    private LinearLayout controlsSpeedLayout;

    protected LinearLayout themeButtonsSection;
//    protected RelativeLayout themeSectionBtn;
    protected RelativeLayout themeColorBtn;
    protected RelativeLayout themePresetsBtn;
    protected RelativeLayout themeSaveBtn;
    protected RelativeLayout themeLoadBtn;
    protected RelativeLayout imageLoadBtn;
    protected RelativeLayout themeClearBtn;


    // 0 = Brushes, 1 = Controls, 2 = Themes //
    private int mode = 0;
    private boolean modeChanged = true;

    // 0 = Default, 1 = Invert, 2 = Select //
    private int brushType = 0;
    private int brushColorType = 0;
    private int touchSize = 0;
    public boolean isPlaying = false;
    private boolean isPlayingForwards = true;
    private float speed = 1;

    public SettingsBar(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SettingsBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SettingsBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public SettingsBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.settings_bar_layout, this, true);

        rootView = findViewById(R.id.root_view);
        scroll = findViewById(R.id.scroll);

        brushButtonsSection = findViewById(R.id.brushes_buttons);
        brushSectionBtn = findViewById(R.id.brushes_main_btn);

        brushSelectColorBtn = findViewById(R.id.brushes_select_color_btn);
        brushInvertBtn = findViewById(R.id.brushes_inverse_btn);
        brushDefaultBtn = findViewById(R.id.brushes_default_btn);
        brushSizeDecreaseBtn = findViewById(R.id.size_small_icon);
        brushSizeIncreaseBtn = findViewById(R.id.size_large_icon);
        brushSizeDisplay = findViewById(R.id.size_content);
        brushSizeLayout = findViewById(R.id.brush_size_layout);

        brushSelectColorSelected = findViewById(R.id.brushes_select_color_btn_selected);
        brushInvertSelected = findViewById(R.id.brushes_inverse_btn_selected);
        brushDefaultSelected = findViewById(R.id.brushes_default_btn_selected);

        controlsButtonsSection = findViewById(R.id.controls_buttons);
        controlsSectionBtn = findViewById(R.id.controls_main_btn);
        controlsButtonTitle = findViewById(R.id.controls_main_btn_title);

        controlsRewindBtn = findViewById(R.id.controls_rewind_btn);
        controlsSpeedDecreaseBtn = findViewById(R.id.speed_small_icon);
        controlsSpeedIncreaseBtn = findViewById(R.id.speed_large_icon);
        controlsSpeedDisplay = findViewById(R.id.speed_content);
        controlsForwardBtn = findViewById(R.id.controls_forwards_btn);
        controlsPauseImage = findViewById(R.id.pause_button_image);
        controlsPlayImage = findViewById(R.id.play_button_image);
        controlsSpeedLayout = findViewById(R.id.play_speed_layout);

        controlsRewindSelected = findViewById(R.id.controls_rewind_btn_selected);
        controlsForwardSelected = findViewById(R.id.controls_forwards_btn_selected);

        themeButtonsSection = findViewById(R.id.themes_buttons);
//        themeSectionBtn = findViewById(R.id.themes_main_btn);

        themeColorBtn = findViewById(R.id.themes_color_btn);
        themePresetsBtn = findViewById(R.id.themes_presets_btn);
        themeSaveBtn = findViewById(R.id.themes_save_btn);
        themeLoadBtn = findViewById(R.id.themes_load_btn);
        imageLoadBtn = findViewById(R.id.themes_load_image_btn);
        themeClearBtn = findViewById(R.id.themes_clear_btn);

        brushSectionBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushSectionButtonAction(); } });
        controlsSectionBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { controlPlayPauseAction();
            }
        });
        controlsSectionBtn.setOnLongClickListener(new OnLongClickListener() {
                                                      @Override
                                                      public boolean onLongClick(View v) { controlSectionButtonAction(); return true; }
                                                  });
//        themeSectionBtn.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) { themeSectionButtonAction();
//                    }
//                });
//        themeSectionBtn.setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) { themePresetsAction(); return true; }
//        });

        brushSelectColorBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushSelectAction(); } });
        brushInvertBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushInvertAction(); } });
        brushDefaultBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushDefaultAction(); } });
        brushSizeDecreaseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushDecreaseSizeAction(); } });
        brushSizeIncreaseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushIncreaseSizeAction(); } });
        brushSizeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { brushSizeLayoutAction(); } });
        controlsSpeedLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { playSpeedLayoutAction(); } });

        brushSizeDecreaseBtn.setOnTouchListener(new OnLongTouchRepeater(new Runnable() {
            @Override
            public void run() { brushDecreaseSizeAction(); } }, 150));
        brushSizeIncreaseBtn.setOnTouchListener(new OnLongTouchRepeater(new Runnable() {
            @Override
            public void run() { brushIncreaseSizeAction(); } }, 150));

        controlsRewindBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { controlRewindAction(); } });
        controlsForwardBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { controlForwardAction(); } });

        controlsSpeedIncreaseBtn.setOnTouchListener(new OnLongTouchRepeater(new Runnable() {
            @Override
            public void run() { controlIncreaseSpeedAction(); } }, 100));
        controlsSpeedDecreaseBtn.setOnTouchListener(new OnLongTouchRepeater(new Runnable() {
            @Override
            public void run() { controlDecreaseSpeedAction(); } }, 100));

        themeColorBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { themeColorAction(); } });
        themePresetsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { themePresetsAction(); } });
        themeSaveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { themeSaveAction(); } });
        themeLoadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { themeLoadAction(); } });
        imageLoadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { imageLoadAction(); } });
        themeClearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { themeClearAction(); } });

        touchSize = PreferenceManager.getTouchSize();
        brushType = PreferenceManager.getTouchType();
        isPlayingForwards = PreferenceManager.getPlayingForwards();
        speed = PreferenceManager.getPlayingSpeed();

        updateUI();
    }

    private void updateUI() {

        String displaySizeText = "" + touchSize;
        brushSizeDisplay.setText(displaySizeText);

        String displaySpeedText = String.format("%.2f", speed) + "x";
        controlsSpeedDisplay.setText(displaySpeedText);

        String playPause = !isPlaying ? "Play" : "Pause";
        controlsButtonTitle.setText(playPause);

        brushDefaultSelected.setVisibility( brushType == 0 ? View.VISIBLE : View.GONE );
        brushInvertSelected.setVisibility( brushType == 1 ?  View.VISIBLE : View.GONE );
        brushSelectColorSelected.setVisibility( brushType == 2 ? View.VISIBLE : View.GONE );

        controlsPauseImage.setVisibility( isPlaying ? View.VISIBLE : View.GONE);
        controlsPlayImage.setVisibility( isPlaying ? View.GONE : View.VISIBLE);

        controlsForwardSelected.setVisibility( isPlayingForwards ? View.VISIBLE : View.GONE);
        controlsRewindSelected.setVisibility( isPlayingForwards ? View.GONE : View.VISIBLE);

//        if(modeChanged){
//            if( mode == 0 ){
//                goneIfNeeded(controlsButtonsSection);
//                goneIfNeeded(themeButtonsSection);
//
//                visibleIfNeeded(brushButtonsSection);
//                goneIfNeeded(brushSectionBtn);
//                visibleIfNeeded(themeSectionBtn);
//
//            } else if( mode == 1 ) {
//                goneIfNeeded(brushButtonsSection);
//                goneIfNeeded(themeButtonsSection);
//
//                visibleIfNeeded(controlsButtonsSection);
//
//                visibleIfNeeded(brushSectionBtn);
//                visibleIfNeeded(themeSectionBtn);
//
//            } else if( mode == 2 ) {
//                goneIfNeeded(brushButtonsSection);
//                goneIfNeeded(controlsButtonsSection);
//
//                visibleIfNeeded(themeButtonsSection);
//                goneIfNeeded(themeSectionBtn);
//
//                visibleIfNeeded(brushSectionBtn);
//            }
//            modeChanged = false;
//        }
    }

    private void brushSectionButtonAction() {
        mode = 0;
        modeChanged = true;
        updateUI();
    }

    private void controlSectionButtonAction() {
        mode = 1;
        modeChanged = true;
        updateUI();
    }

    private void themeSectionButtonAction() {
        mode = 2;
        modeChanged = true;
        updateUI();
    }

    private void brushSelectAction() {
        MainActivity.displayColorSelectDialog();
    }

    private void brushInvertAction() {
        brushType = 1;
        PreferenceManager.setTouchType(brushType);
        MainActivity.brushTypeChanged();
        updateUI();
    }

    private void brushDefaultAction() {
        brushType = 0;
        PreferenceManager.setTouchType(brushType);
        MainActivity.brushTypeChanged();
        updateUI();
    }
    public void brushColorSelected() {
        brushType = PreferenceManager.getTouchType();
        updateUI();
    }

    private void brushDecreaseSizeAction() {
        if(touchSize > 0){
            changeTouchSize(false);
        }
    }

    private void brushIncreaseSizeAction() {
        if(touchSize < MAX_TOUCH_SIZE) {
            changeTouchSize(true);
        }
    }

    private void brushSizeLayoutAction() {
        MainActivity.displayBrushSizeDialog();
    }

    private void playSpeedLayoutAction() {
        MainActivity.displayPlaySpeedDialog();
    }

    public void changeTouchSize(boolean increase) {
        touchSize = touchSize + ( increase ? 1 : -1 );
        PreferenceManager.setTouchSize(touchSize);
        MainActivity.touchSizeChanged();
        updateUI();
    }


    private void controlRewindAction() {
        isPlayingForwards = false;
        PreferenceManager.setPlayingForwards(isPlayingForwards);
        MainActivity.playingForwardsChanged();
        updateUI();
    }

    private void controlForwardAction() {
        isPlayingForwards = true;
        PreferenceManager.setPlayingForwards(isPlayingForwards);
        MainActivity.playingForwardsChanged();
        updateUI();
    }

    private void controlDecreaseSpeedAction() {
        if(speed >= 0.02) {
            speed = speed - 0.01f;
        } else {
            speed = 0.01f;
        }
        PreferenceManager.setPlayingSpeed(speed);
        MainActivity.speedChanged();
    }

    private void controlIncreaseSpeedAction() {
        if(speed < 1.0f) {
            speed = speed + 0.01f;
        } else {
            speed = 1.0f;
        }
        PreferenceManager.setPlayingSpeed(speed);
        MainActivity.speedChanged();
    }

    public void speedChanged() {
        speed = PreferenceManager.getPlayingSpeed();
        updateUI();
    }

    private void controlPlayPauseAction() {
//        setPlaying(!isPlaying);
        MainActivity.setPlaying(!isPlaying);
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        updateUI();
    }

    private void themeColorAction() {
        MainActivity.displayThemeDialog();
    }

    private void themePresetsAction() {
        MainActivity.displayPresets();
    }

    private void themeSaveAction() {
        MainActivity.displaySaveDialog();
    }

    private void themeLoadAction() {
        MainActivity.displayLoadDialog();
    }

    private void imageLoadAction() {
        MainActivity.loadImageAction();
    }

    private void themeClearAction() {
        MainActivity.clearTouched();
    }










    private void visibleIfNeeded(View view){
        int v = view.getVisibility();
        if(v != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }
    private void goneIfNeeded(View view){
        int v = view.getVisibility();
        if(v != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public void updateBackgroundGradient() {

        final int[] colors = new int[] {
                Helper.getContextColor(R.color.setting_bar_1),
                Helper.getContextColor(R.color.setting_bar_2),
                Helper.getContextColor(R.color.setting_bar_3)
        };

        final float[] positions = new float[] {
                0, 0.10f, 1f
        };
//        LinearGradient lg = new LinearGradient(0, 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight(), colors, positions, Shader.TileMode.REPEAT);
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                float angleInRadians = (float)Math.toRadians(90);

                float endX = (float)Math.cos(angleInRadians) * width;
                float endY = (float)Math.sin(angleInRadians) * height;

                LinearGradient linearGradient = new LinearGradient(0, 0, endX, endY,
                        colors,
                        positions,
                        Shader.TileMode.REPEAT);
                return linearGradient;
            }
        };

        PaintDrawable paint = new PaintDrawable();
        paint.setShape(new RectShape());
        paint.setShaderFactory(shaderFactory);

        rootView.setBackground(paint);
    }

    public int getPositionForBrushSize() {
        return (int)(brushSizeLayout.getX() + Helper.convertDpToPixels(8)) - getScrollPosition() + (int)(brushSizeLayout.getMeasuredWidth() * 0.5);
    }

    public int getPositionForPlaySpeed() {
        return (int)(controlsSpeedLayout.getX() + controlsButtonsSection.getX()) - getScrollPosition() + (int)(controlsSpeedLayout.getMeasuredWidth() * 0.5);
    }
    public int getScrollPosition(){
        return scroll.getScrollX();
    }

    // FOR USE BY EXTERNAL SETTING OF SIZE
    public void touchSizeChanged() {
        touchSize = PreferenceManager.getTouchSize();
        updateUI();
    }

    class OnLongTouchRepeater implements OnTouchListener {

        Handler timerHandler = new Handler();
        Runnable repeatMethod;
        boolean finished = false;
        int loopNum = 0; int loopSpeed;

        public OnLongTouchRepeater(final Runnable method, int loop) {
            super();
            loopSpeed = loop;
            repeatMethod = new Runnable() {
                @Override
                public void run() {
                    if(!finished){
                        loopNum++;
                        timerHandler.post(method);
                        int offset = (loopNum * 2);
                        int delay = loopSpeed - (offset > loopSpeed/2 ? loopSpeed/2 : offset);
                        timerHandler.postDelayed(repeatMethod, delay);
                    }
                }
            };
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                finished = false;
                loopNum = 0;
                timerHandler.postDelayed(repeatMethod, 0);
            } else if(event.getAction() == MotionEvent.ACTION_MOVE){
//                if(event.getX() < 0 || event.getX() > v.getMeasuredWidth() || event.getY() < 0 || event.getY() > v.getMeasuredHeight() ){
//                    finished = true;
//                }
            } else if(event.getAction() == MotionEvent.ACTION_UP) {
                finished = true;
            } else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                finished = true;
            }
            return true;
        }
    }
}

