package com.example.ericeddy.colours;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsView extends RelativeLayout {

    private Context mContext;

    private RelativeLayout rootView;
    private RelativeLayout background;

    private LinearLayout standardButton;
    private ImageView standardImage;
    private TextView standardText;

    private LinearLayout colourButton;
    private ImageView colourImage;
    private TextView colourText;

    private ImageView smallImage;
    private ImageView largeImage;
    private TextView sizeContent;


    public SettingsView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings, this, true);

        rootView = findViewById(R.id.root_view);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSettings();
            }
        });

        standardButton = findViewById(R.id.setting_touch_type_default);
        standardImage = findViewById(R.id.setting_touch_type_default_image);
        standardText = findViewById(R.id.setting_touch_type_default_text);

        colourButton = findViewById(R.id.setting_touch_type_color);
        colourImage = findViewById(R.id.setting_touch_type_color_image);
        colourText = findViewById(R.id.setting_touch_type_color_text);

        smallImage = findViewById(R.id.size_small_icon);
        largeImage = findViewById(R.id.size_large_icon);
        sizeContent = findViewById(R.id.size_content);
    }



    private void selectTouchType(int type) {
        if( type == 0 ) {
            standardButton.setAlpha(1f);
            colourButton.setAlpha(0.25f);
        } else if (type == 1){
            standardButton.setAlpha(0.25f);
            colourButton.setAlpha(1f);
        }
    }

    public void displaySettings() {
        // Do animate in //
        int touchType = PreferenceManager.getTouchType();
        int touchColour = PreferenceManager.getTouchColour(); // Sort out what to do with that //
        int touchSize =PreferenceManager.getTouchSize();

        selectTouchType(touchType);
        sizeContent.setText( touchSize + "" );

        rootView.setVisibility(View.VISIBLE);
    }

    public void closeSettings() {
        // Do Animate Out //
        rootView.setVisibility(View.GONE);
    }
}
