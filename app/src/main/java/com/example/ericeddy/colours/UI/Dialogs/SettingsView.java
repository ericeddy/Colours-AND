package com.example.ericeddy.colours.UI.Dialogs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericeddy.colours.System.PreferenceManager;
import com.example.ericeddy.colours.R;
import com.example.ericeddy.colours.UI.MainActivity;

public class SettingsView extends DialogView {

    private LinearLayout standardButton;
    private ImageView standardImage;
    private TextView standardText;

    private LinearLayout colourButton;
    private ImageView colourImage;
    private TextView colourText;

    private ImageView smallImage;
    private ImageView largeImage;
    private TextView sizeContent;

    private int MAX_TOUCH_SIZE = 12;

    private int touchType = 0;
    private int touchColour = 0;
    private int touchSize = 0;


    public SettingsView(Context context) {
        super(context);
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings, this, true);

        super.init();

        standardButton = findViewById(R.id.setting_touch_type_default);
        standardImage = findViewById(R.id.setting_touch_type_default_image);
        standardText = findViewById(R.id.setting_touch_type_default_text);
        standardButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTouchType(0);
            }
        });

        colourButton = findViewById(R.id.setting_touch_type_color);
        colourImage = findViewById(R.id.setting_touch_type_color_image);
        colourText = findViewById(R.id.setting_touch_type_color_text);
        colourButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTouchType(1);
            }
        });

        smallImage = findViewById(R.id.size_small_icon);
        largeImage = findViewById(R.id.size_large_icon);
        sizeContent = findViewById(R.id.size_content);

        smallImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(touchSize != 0){
                    changeTouchSize(false);
                }
            }
        });
        largeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(touchSize < MAX_TOUCH_SIZE) {
                    changeTouchSize(true);
                }

            }
        });
    }



    private void selectTouchType(int type) {
        PreferenceManager.setTouchType(type);
        touchType = type;
        handleChanges();
    }

    @Override
    public void displayDialog(RelativeLayout relativeLayout) {
        super.displayDialog(relativeLayout);
        // Do animate in //
        touchType = PreferenceManager.getTouchType();
        touchColour = PreferenceManager.getTouchColour(); // Sort out what to do with that //
        touchSize = PreferenceManager.getTouchSize();

        handleChanges();
    }


    public void changeTouchSize(boolean increase) {
        touchSize = touchSize + ( increase ? 1 : -1 );
        PreferenceManager.setTouchSize(touchSize);
        MainActivity.touchSizeChanged();
        handleChanges();
    }
    private void handleChanges() {
        if( touchType == 0 ) {
            standardButton.setAlpha(1f);
            colourButton.setAlpha(0.25f);
        } else if (touchType == 1){
            standardButton.setAlpha(0.25f);
            colourButton.setAlpha(1f);
        }
        if(touchSize == 0){
            largeImage.setAlpha(1f);
            smallImage.setAlpha(0.25f);
        } else if (touchSize == MAX_TOUCH_SIZE) {
            smallImage.setAlpha(1f);
            largeImage.setAlpha(0.25f);
        } else {
            largeImage.setAlpha(1f);
            smallImage.setAlpha(1f);
        }
        sizeContent.setText( touchSize + "" );
    }
}
