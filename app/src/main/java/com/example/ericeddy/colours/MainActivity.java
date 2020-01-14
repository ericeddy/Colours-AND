package com.example.ericeddy.colours;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

    private RelativeLayout root;

    private SettingsBar settingsBar;
    private SettingsView settingsDialog;
    private PresetLayoutsView presetLayouts;
    private SaveDialog saveDialog;
    private AreYouSureDialog areYouSureDialog;
    private LoadDialog loadDialog;
    private SelectColorDialog selectColorDialog;
    private SelectThemeDialog selectThemeDialog;
    private BrushSizeDialog brushSizeDialog;
    private PlaySpeedDialog playSpeedDialog;
    private ImageLoadDialog imageLoadDialog;

    public ColourPanel panel;
    private int[][] pausedCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.sInstance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);

        dbManager = new DBManager(this);
        dbManager.open();

        panel = findViewById(R.id.panel);

        settingsBar = findViewById(R.id.bottom_bar);
//        settingsDialog = findViewById(R.id.settings);
        presetLayouts = new PresetLayoutsView(this);// findViewById(R.id.presets_panel);
        presetLayouts.panel = panel;

        saveDialog = new SaveDialog(this);// findViewById(R.id.save_dialog);
        saveDialog.panel = panel;
        saveDialog.dbManager = dbManager;

        loadDialog = new LoadDialog(this);//  findViewById(R.id.load_dialog);
        loadDialog.panel = panel;
        loadDialog.dbManager = dbManager;

        selectColorDialog = new SelectColorDialog(this);//  findViewById(R.id.select_color_dialog);
        selectThemeDialog = new SelectThemeDialog(this);//  findViewById(R.id.theme_dialog);
        brushSizeDialog = new BrushSizeDialog(this);//  findViewById(R.id.brush_size_dialog);
        playSpeedDialog = new PlaySpeedDialog(this);//  findViewById(R.id.play_speed_dialog);
        imageLoadDialog = new ImageLoadDialog(this);//  findViewById(R.id.image_load_dialog);
        imageLoadDialog.panel = panel;

        areYouSureDialog = new AreYouSureDialog(this);//  findViewById(R.id.are_you_sure_dialog);

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
        presetLayouts.displayDialog(root);
    }

    public void displaySave() {
        if( settingsBar.isPlaying ) setPlaying(false);
        saveDialog.displayDialog(root);
    }


    public static void displaySaveDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displaySave();
        }

    }

    public void displayLoad() {
        if( settingsBar.isPlaying ) setPlaying(false);
        loadDialog.displayDialog(root);
    }


    public static void displayLoadDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayLoad();
        }

    }

    public static void loadImageAction() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.openGallery();
        }

    }

    public void displayTheme() {
        selectThemeDialog.displayDialog(root);
    }


    public static void displayThemeDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayTheme();
        }

    }

    public void displayColorSelect() {
        selectColorDialog.displayDialog(root);
    }

    public static void displayColorSelectDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayColorSelect();
        }
    }

    public void displayBrushSizeSlider() {
        brushSizeDialog.displayDialog(root,  settingsBar.getPositionForBrushSize());
    }

    public static void displayBrushSizeDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayBrushSizeSlider();
        }
    }

    public void displayPlaySpeedSlider() {
        playSpeedDialog.displayDialog(root,  settingsBar.getPositionForPlaySpeed());
    }

    public static void displayPlaySpeedDialog() {
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            mainActivity.displayPlaySpeedSlider();
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
        areYouSureDialog.displayDialog(root);
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
            mainActivity.settingsBar.touchSizeChanged();
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
    public static int getScreenWidth(){
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            return mainActivity.settingsBar.getMeasuredWidth();
        }
        return 0;
    }

    public static int getScreenHeight(){
        MainActivity mainActivity = MainActivity.getInstance();
        if (mainActivity != null) {
            return mainActivity.findViewById(R.id.root).getMeasuredHeight();
        }
        return 0;
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

        brushSizeDialog.setDialogBottom(lp.height);
        playSpeedDialog.setDialogBottom(lp.height);
    }


    private static final int PICK_FROM_GALLERY = 1;
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 9;

    public void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // The permission is NOT already granted.
                    // Check if the user has been asked about this permission already and denied
                    // it. If so, we want to give more explanation about why the permission is needed.
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show our own UI to explain to the user why we need to read the contacts
                        // before actually requesting the permission and showing the default UI
                    }

                    // Fire off an async request to actually get the permission
                    // This will show the standard permission request dialog UI
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST);
                }

            } else {
//                panel.MyGameSurfaceView_OnPause();

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FROM_GALLERY){
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();

                //yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                try {
//                    Bitmap bmp = getBitmapFromUri(selectedImage);
                    Bitmap bmp = handleSamplingAndRotationBitmap(this, selectedImage);
                    imageLoadDialog.displayDialog(root, panel.myCanvas_w, panel.myCanvas_h, bmp);
                    bmp.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
                //    imgViewProfilePic.setImageBitmap(yourSelectedImage);
            }
//            panel.MyGameSurfaceView_OnResume();
        }
    }

    /**
     * This method is responsible for solving the rotation issue if exist. Also scale the images to
     * 1024x1024 resolution
     *
     * @param context       The current context
     * @param selectedImage The Image URI
     * @return Bitmap image results
     * @throws IOException
     */
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_WIDTH = MainActivity.getScreenWidth();
        int MAX_HEIGHT = MainActivity.getInstance().panel.getMeasuredHeight();

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     *                  method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getBitmapFromUri(Uri imageUri) {
        getContentResolver().notifyChange(imageUri, null);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;

        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
            return bitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
