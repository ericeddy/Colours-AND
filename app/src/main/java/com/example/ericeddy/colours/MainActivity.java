package com.example.ericeddy.colours;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static MainActivity sInstance;
    public static MainActivity getInstance() {
        if(sInstance != null){
            return sInstance;
        }
        return null;
    }
    public static Context getAppContext() {
        if(sInstance != null){
            return sInstance;
        }
        return null;
    }

    private SettingsView settings;

    private ColourPanel panel;
    private int[][] cells;

    private RelativeLayout settingsButton;
    private RelativeLayout pausePlayButton;
    private RelativeLayout refreshButton;

    private ImageView pauseImage;
    private ImageView playImage;

    private boolean isPlaying = false;
    private void setPlaying(boolean playing) {
        isPlaying = playing;
        if(isPlaying){
            pauseImage.setVisibility(View.VISIBLE);
            playImage.setVisibility(View.GONE);
        } else {
            pauseImage.setVisibility(View.GONE);
            playImage.setVisibility(View.VISIBLE);
        }
        panel.setIsPlaying(isPlaying);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        panel = findViewById(R.id.panel);

        settingsButton = findViewById(R.id.color_button);
        pausePlayButton = findViewById(R.id.play_button);
        refreshButton = findViewById(R.id.refresh_button);

        settings = findViewById(R.id.settings);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.resetCells();
            }
        });
        pausePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlaying(!isPlaying);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.displaySettings();
            }
        });

        pauseImage = findViewById(R.id.pause_button_image);
        playImage = findViewById(R.id.play_button_image);
        setPlaying(false);
        MainActivity.sInstance = this;
        panel.touchSizeChanged();
    }

    @Override
    protected void onResume() {
// TODO Auto-generated method stub
        super.onResume();
        panel.MyGameSurfaceView_OnResume();
        if(cells != null)panel.setCells(cells);
    }
    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        cells = panel.getCells();
        if( isPlaying ) setPlaying(false);
        panel.MyGameSurfaceView_OnPause();
    }
    public static void touchSizeChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        mainActivity.panel.touchSizeChanged();
    }
}
