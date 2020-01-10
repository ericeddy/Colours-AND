package com.example.ericeddy.colours;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static MainActivity sInstance;
    private DBManager dbManager;

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
    private SaveDialog saveDialog;
    private AreYouSureDialog areYouSureDialog;
    private LoadDialog loadDialog;
    private SelectColorDialog selectColorDialog;
    private SelectThemeDialog selectThemeDialog;

    private ColourPanel panel;
    private int[][] pausedCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.sInstance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();

        panel = findViewById(R.id.panel);

        settingsBar = findViewById(R.id.bottom_bar);
        settingsDialog = findViewById(R.id.settings);
        presetLayouts = findViewById(R.id.presets_panel);
        presetLayouts.panel = panel;

        saveDialog = findViewById(R.id.save_dialog);
        saveDialog.panel = panel;
        saveDialog.dbManager = dbManager;

        loadDialog = findViewById(R.id.load_dialog);
        loadDialog.panel = panel;
        loadDialog.dbManager = dbManager;

        selectColorDialog = findViewById(R.id.select_color_dialog);
        selectThemeDialog = findViewById(R.id.theme_dialog);

        areYouSureDialog = findViewById(R.id.are_you_sure_dialog);

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
        panel.undoLastAction();
    }

    public void displayPresetsDialog() {
        if( settingsBar.isPlaying ) setPlaying(false);
        presetLayouts.displayDialog();
    }

    public void displaySave() {
        if( settingsBar.isPlaying ) setPlaying(false);
        saveDialog.displayDialog();
    }


    public static void displaySaveDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displaySave();
        }

    }

    public void displayLoad() {
        if( settingsBar.isPlaying ) setPlaying(false);
        loadDialog.displayDialog();
    }


    public static void displayLoadDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayLoad();
        }

    }

    public void displayTheme() {
        selectThemeDialog.displayDialog();
    }


    public static void displayThemeDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayTheme();
        }

    }

    public void displayColorSelect() {
        selectColorDialog.displayDialog();
    }


    public static void displayColorSelectDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayColorSelect();
        }

    }

    public void displayAreYouSureForDeleteDesign(final int id) {
        areYouSureDialog.setYesButtonAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDialog.deleteSavedDesign(id);
                areYouSureDialog.closeDialog();
            }
        });
        areYouSureDialog.setNoButtonAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureDialog.closeDialog();
            }
        });
        areYouSureDialog.displayDialog();
    }
    public static void displayAreYouSureDialogForDeleteDesign(int id) {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayAreYouSureForDeleteDesign(id);
        }

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

    public static void selectColorChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.settingsBar.brushColorSelected();

            mainActivity.panel.brushTypeChanged();
        }
    }

    public static void speedChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.settingsBar.speedChanged();
            mainActivity.panel.speedChanged();
        }
    }


    public static void themeChanged() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.themeChanged();
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
            mainActivity.settingsBar.setPlaying(playing);

        }
    }

    public static void selectDesign(int[][] data) {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.panel.addMemoryState();
            if(mainActivity.settingsBar.isPlaying){
                setPlaying(false);
            }
            mainActivity.panel.setCells(data);

        }
    }

    public static void colorPanelSizeUpdated() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.updateSettingsBarSize();
        }

    }

    private void updateSettingsBarSize() {
        RelativeLayout root = findViewById(R.id.root);
        int screenHeight = root.getMeasuredHeight();
        int colorPanelHeight = panel.cellSize * panel.yNumCells;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)settingsBar.getLayoutParams();
        lp.height = screenHeight - colorPanelHeight;
        settingsBar.setLayoutParams(lp);
        settingsBar.requestLayout();
        settingsBar.updateBackgroundGradient();
    }
}
