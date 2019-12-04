package com.example.ericeddy.colours;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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

    private SettingsBar settingsBar;
    private SettingsView settingsDialog;
    private PresetLayoutsView presetLayouts;

    private ColourPanel panel;
    private int[][] pausedCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.sInstance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        panel = findViewById(R.id.panel);

        settingsBar = findViewById(R.id.bottom_bar);
        settingsDialog = findViewById(R.id.settings);
        presetLayouts = findViewById(R.id.presets_panel);
        presetLayouts.panel = panel;

        setPlaying(false);
        panel.brushTypeChanged();
        panel.touchSizeChanged();
        panel.isPlayingForwardsChanged();
    }

    @Override
    protected void onResume() {
// TODO Auto-generated method stub
        super.onResume();
        panel.MyGameSurfaceView_OnResume();
        if(pausedCells != null)panel.setCells(pausedCells);
    }
    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        pausedCells = panel.getCellsCopy();
        if( settingsBar.isPlaying ) setPlaying(false);
        panel.MyGameSurfaceView_OnPause();
    }

    @Override
    public void onBackPressed() {
        if( settingsBar.isPlaying ) setPlaying(false);
        panel.undoLastAction();
    }

    public void displayPresetsDialog() {
        if( settingsBar.isPlaying ) setPlaying(false);
        presetLayouts.displayDialog();
    }


    public static void displayPresets() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayPresetsDialog();
        }

    }
    public static void playingForwardsChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.isPlayingForwardsChanged();
        }
    }
    public static void brushTypeChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.brushTypeChanged();
        }
    }
    public static void touchSizeChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.touchSizeChanged();
        }
    }


    public static void clearTouched() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.resetCells();
        }

    }

    public static void setPlaying(boolean playing) {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            if(playing){
                mainActivity.panel.addMemoryState();
            }
            mainActivity.panel.setIsPlaying(playing);

        }
    }
}
