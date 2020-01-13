package com.example.ericeddy.colours;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Set;
import java.util.TreeSet;

public class ImageLoadDialog extends DialogView {

    ImageView imageView;
    Bitmap originalBitmap;
    Bitmap renderedBitmap;
    int colorPanelWidth;
    int colorPanelHeight;
    float scale = 0.75f;
    private RelativeLayout hueButton;
    private RelativeLayout toneButton;

    int[] colors;
    float[] colors_hues;
    float[] colors_values;
    float[] colors_saturation;
    float[] colors_values_minmax;
    float[] colors_saturation_minmax;

    public ImageLoadDialog(Context context) {
        super(context);
        init();
    }

    public ImageLoadDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageLoadDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ImageLoadDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_load_dialog, this, true);

        super.init();

        imageView = findViewById(R.id.image_preview);
        hueButton = findViewById(R.id.hue_btn);
        toneButton = findViewById(R.id.tone_btn);

        hueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processBy(true);
            }
        });
        toneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processBy(false);
            }
        });
    }

    public void displayDialog(int colorPanelW, int colorPanelH, Bitmap bitmap) {
        originalBitmap = bitmap;
        colorPanelWidth = colorPanelW;
        colorPanelHeight = colorPanelH;

        int w = (int)(colorPanelW * scale);
        int h = (int)(colorPanelH * scale);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(w, h));

        colors = Helper.getSelectedColors();
        colors_hues = new float[colors.length];
        colors_values = new float[colors.length];
        colors_saturation = new float[colors.length];
        colors_values_minmax = new float[]{1, 0};
        colors_saturation_minmax = new float[]{1, 0};

        int imageW = originalBitmap.getWidth();
        int imageH = originalBitmap.getHeight();
        int imageTop = (colorPanelHeight - imageH)/2 ;
        int imageLeft = (colorPanelWidth - imageW)/2;
        Rect destR = new Rect( imageLeft, imageTop,  imageW + imageLeft, imageH + imageTop);
        if (imageW > colorPanelWidth || imageH > colorPanelHeight) {
            final float widthRatio = (float) colorPanelWidth / (float) imageW;
            final float heightRatio = (float) colorPanelHeight / (float) imageH;
            float scale = heightRatio < widthRatio ? heightRatio : widthRatio;
            int scaleW = (int)(imageW * scale);
            int scaleH =  (int)(imageH * scale);
            imageTop = (colorPanelHeight - scaleH)/2;
            imageLeft = (colorPanelWidth - scaleW)/2;

            destR = new Rect(imageLeft, imageTop,  scaleW + imageLeft, scaleH + imageTop);
        }
        Rect mainR = new Rect(0, 0, colorPanelWidth, colorPanelHeight);

        renderedBitmap = Bitmap.createBitmap(colorPanelWidth, colorPanelHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(renderedBitmap);
        Paint p = new Paint();
        p.setColor(Helper.getContextColor(colors[0]));
        canvas.drawRect(mainR, p);
        canvas.drawBitmap(originalBitmap, null, destR, null);

        imageView.setImageBitmap(renderedBitmap);


        RelativeLayout mainViewBG = findViewById(R.id.view_background);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mainViewBG.getLayoutParams();
        lp.width = w + Helper.convertDpToPixels(16);
        lp.height = h + Helper.convertDpToPixels(16) + Helper.convertDpToPixels(32);// padding + button
        mainViewBG.setLayoutParams(lp);

        displayDialog();
    }



//    private int[][] processPixels(int[] allPixels, int xCells, int yCells) {
//        int[][] avgs = new int[yCells][xCells];
//        for( int y = 0; y < yCells; y++){
//            for( int x = 0; x < xCells; x++){
//
//            }
//        }
//    }

    private void processBy(boolean hue) {
        if(MainActivity.getInstance() == null) { return; }
        if(hue){
            getHueValues();
        } else {
            getColorValues();
            getSaturationValues();
        }

        int xCells = MainActivity.getInstance().panel.xNumCells;
        int yCells = MainActivity.getInstance().panel.yNumCells;
        int renderWidth = renderedBitmap.getWidth();
        int renderHeight = renderedBitmap.getHeight();
        int[] allPixels = new int[ renderWidth * renderHeight ];
        renderedBitmap.getPixels(allPixels, 0, renderWidth, 0, 0, renderWidth, renderHeight);

        int size = (int)((float)colorPanelWidth / (float)xCells);
        int[][] avgs = new int[yCells][xCells];
        Set<Integer> uniquePixels = new TreeSet<>();
        SparseIntArray valueMapper = new SparseIntArray();
        for( int y = 0; y < yCells; y++){
            for( int x = 0; x < xCells; x++){
                int[] pixels = getBitmapPixels(allPixels, renderWidth, x, y, size);
                long redBucket = 0;
                long greenBucket = 0;
                long blueBucket = 0;
                long pixelCount = pixels.length;

                for(int p : pixels){
                    redBucket += Color.red(p);
                    greenBucket += Color.green(p);
                    blueBucket += Color.blue(p);
                }
                int c = Color.rgb( (int)((redBucket / pixelCount)), (int)((greenBucket / pixelCount)), (int)((blueBucket / pixelCount)));
                avgs[y][x] = c;
                uniquePixels.add(c);
            }
        }

        for(int c : uniquePixels) {
            float[] hsv = new float[3];
            Color.colorToHSV(c, hsv);
            int i = 0;
            float h = hsv[0];
            float s = hsv[1];
            float v = hsv[2];
            if( hue ){
                while( h > colors_hues[i] ){
                    i++;
                    if( i == colors_hues.length ){
                        i = colors_hues.length - 1;
                        break;
                    }
                }
                if(i == colors_hues.length - 1){
                    valueMapper.append(c, i);
                    Log.v("Mapper", i + " - " + h + " - " + c);
                } else {
                    int ind = ( colors_hues[i+1] - h < h - colors_hues[i]) ? i+1: i;
                    valueMapper.append(c, ind);
                    Log.v("Mapper", ind + " - " + h + " - " + c);
                }
            } else {
                int max = 0;
                if( s > 0.9 && s > v && v/s > 0.6){
                    // 0-7 index
                    i = 0;
                    max = 8;
                } else if(s > v && v/s > 0.4){
                    // 8-13 index
                    i = 8;
                    max = 14;
                } else if( s > v ? v / s > 0.9 : s / v > 0.9 ){
                    // 22-27 index
                    i = 22;
                    max = 28;
                } else {
                    // 14-21 index
                    i = 14;
                    max = 22;
                }
                int index = 0;
                int diffIndexS = 0;
                int diffIndexV = 0;
                float minDiffS = 1;
                float minDiffV = 1;
                while(i < max){
                    float cs = colors_saturation[i];
                    float cv = colors_values[i];

                    float fs = s < cs ? cs - s : s - cs;
                    float fv = v < cv ? cv - v : v - cv;

                    if(fs < minDiffS){
                        minDiffS = fs;
                        diffIndexS = index;
                    }
                    if(fv < minDiffV){
                        minDiffV = fv;
                        diffIndexV = index;
                    }

                    i++;
                    index++;
                }

                int colorID = (minDiffS < minDiffV ? diffIndexS : diffIndexV);
                valueMapper.append(c, colorID);
                /*



                while( h > colors_values[i] ){
                    i++;
                    if( i == colors_values.length ){
                        i = colors_values.length - 1;
                        break;
                    }
                }
                if(i == colors_values.length - 1){
                    valueMapper.append(c, i);
                    Log.v("Mapper", i + " - " + h + " - " + c);
                } else {
                    int ind = ( colors_hues[i+1] - h < h - colors_hues[i]) ? i+1: i;
                    valueMapper.append(c, ind);
                    Log.v("Mapper", ind + " - " + h + " - " + c);
                }*/
            }
        }

        Bitmap newRender = Bitmap.createBitmap(colorPanelWidth, colorPanelHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newRender);
        Paint p = new Paint();
        for( int y = 0; y < yCells; y++) {
            for (int x = 0; x < xCells; x++) {

                int cellValue = avgs[y][x];
                p.setColor( Helper.getContextColor(colors[valueMapper.get(cellValue)]) );
                Rect currentRect = new Rect(x * size, y * size, (x * size) + size, (y * size) + size);
                canvas.drawRect(currentRect, p);
            }
        }
        imageView.setImageBitmap(newRender);
    }

    private void getHueValues() {
        int i = 0;
        for( int c : colors ) {
            float[] hsv = new float[3];
            Color.colorToHSV(Helper.getContextColor(c), hsv);
            colors_hues[i] = hsv[0];
            i++;
        }
    }

    private void getColorValues() {
        int i = 0;
        for( int c : colors ) {
            float[] hsv = new float[3];
            Color.colorToHSV(Helper.getContextColor(c), hsv);
            colors_values[i] = hsv[2];
            if(hsv[2] < colors_values_minmax[0]){
                colors_values_minmax[0] = hsv[2];
            }
            if(hsv[2] > colors_values_minmax[1]){
                colors_values_minmax[1] = hsv[2];
            }
            i++;
        }
    }

    private void getSaturationValues() {
        int i = 0;
        for( int c : colors ) {
            float[] hsv = new float[3];
            Color.colorToHSV(Helper.getContextColor(c), hsv);
            colors_saturation[i] = hsv[1];
            if(hsv[1] < colors_saturation_minmax[0]){
                colors_saturation_minmax[0] = hsv[1];
            }
            if(hsv[1] > colors_saturation_minmax[1]){
                colors_saturation_minmax[1] = hsv[1];
            }
            Log.v("INIT", hsv[1] + " - " + hsv[2]);
            i++;
        }
        Log.v("MINMAX", colors_saturation_minmax[0] + " - " + colors_saturation_minmax[1] + " - " + colors_values_minmax[0]+ " - " + colors_values_minmax[1]);
    }

    public int[] getBitmapPixels(int[] allPixels, int rowX, int x, int y, int size) {
        final int[] subsetPixels = new int[size * size];
        for (int row = 0; row < size; row++) {
            System.arraycopy(allPixels, ((x * size) + (y * rowX * size) + (row * rowX)), subsetPixels, row * size, size);
        }
        return subsetPixels;
    }

}
