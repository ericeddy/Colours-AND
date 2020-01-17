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

    public ColourPanel panel;
    ImageView imageView;
    Bitmap originalBitmap;
    Bitmap renderedBitmap;
    int colorPanelWidth;
    int colorPanelHeight;
    float scale = 0.75f;
    private RelativeLayout origButton;
    private RelativeLayout hueButton;
    private RelativeLayout toneButton;
    private RelativeLayout doneButton;

    int[] colors;
    int[] previewColors;
    float[] colors_hues;
    float[] colors_values;
    float[] colors_saturation;
    float[] colors_values_minmax;
    float[] colors_saturation_minmax;

    int[][] output;

    int lastChange = 0; // 0 == none, 1 == hue, 2 == tone

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
        origButton = findViewById(R.id.original_btn);
        hueButton = findViewById(R.id.hue_btn);
        toneButton = findViewById(R.id.tone_btn);
        doneButton = findViewById(R.id.apply_btn);

        origButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processBy(0);
            }
        });
        hueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processBy(1);
            }
        });
        toneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processBy(2);
            }
        });
        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doneButton.getAlpha() == 1){
                    doneTapped();
                }
            }
        });

        doneButton.setAlpha(0.2f);
    }

    public void displayDialog(RelativeLayout root, int colorPanelW, int colorPanelH, Bitmap bitmap) {
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
        lp.height = h + Helper.convertDpToPixels(16) + Helper.convertDpToPixels(32) + Helper.convertDpToPixels(32) + Helper.convertDpToPixels(16);// padding + button
        mainViewBG.setLayoutParams(lp);

        displayDialog(root);
    }



//    private int[][] processPixels(int[] allPixels, int xCells, int yCells) {
//        int[][] avgs = new int[yCells][xCells];
//        for( int y = 0; y < yCells; y++){
//            for( int x = 0; x < xCells; x++){
//
//            }
//        }
//    }

    // Mode; 0 == Original; 1 == Hue; 2 == Tone;
    private void processBy(int mode) {
        if(MainActivity.getInstance() == null) { return; }
        if(mode == 1){
            if(!Helper.isSelectedColorsRainbow()){
                Helper.setDefaultRainbow();
            } else if( lastChange == 1 ){
                Helper.setNextRainbow();
            }

            colors = Helper.getSelectedColors();
            getHueValues();
        } else if(mode == 2) {
            if(Helper.isSelectedColorsRainbow()){
                Helper.setDefaultMono();
            } else if( lastChange == 2 ){
                Helper.setNextMono();
            }

            colors = Helper.getBWColors();
            getColorValues();
            getSaturationValues();
        }
        previewColors = Helper.getSelectedColors();

        int xCells = MainActivity.getInstance().panel.xNumCells;
        int yCells = MainActivity.getInstance().panel.yNumCells;
        int renderWidth = renderedBitmap.getWidth();
        int renderHeight = renderedBitmap.getHeight();
        int[] allPixels = new int[ renderWidth * renderHeight ];
        renderedBitmap.getPixels(allPixels, 0, renderWidth, 0, 0, renderWidth, renderHeight);

        int size = (int)((float)colorPanelWidth / (float)xCells);
        int[][] avgs = new int[yCells][xCells];
        int[][] most = new int[yCells][xCells];
        int[][] tone = new int[yCells][xCells];
        output = new int[yCells][xCells];
        Set<Integer> uniquePixels = new TreeSet<>();
        SparseIntArray valueMapper = new SparseIntArray();
//        float[] s_minmax = new float[]{1, 0}; float[] v_minmax = new float[]{1, 0};
        for( int y = 0; y < yCells; y++){
            for( int x = 0; x < xCells; x++){
                int[] pixels = getBitmapPixels(allPixels, renderWidth, x, y, size);
                long redBucket = 0;
                long greenBucket = 0;
                long blueBucket = 0;
                long pixelCount = pixels.length;

                SparseIntArray duplicateCount = new SparseIntArray();
                int largestCount = 0; int largestCountC = 0;
                for(int p : pixels){
                    redBucket += Color.red(p);
                    greenBucket += Color.green(p);
                    blueBucket += Color.blue(p);

                    int count = duplicateCount.get(p)+1;
                    duplicateCount.append(p, count);
                    if(count > largestCount) {
                        largestCount = count;
                        largestCountC = p;
                    }
                }
                int rA = (int)((redBucket / pixelCount)); int gA = (int)((greenBucket / pixelCount)); int bA = (int)((blueBucket / pixelCount));
                int c = Color.rgb( rA, gA, bA);
                int pAVG = (int)((float)(rA + gA + bA) / (float) 3); // BW AVG
                int pC = Color.rgb(pAVG,pAVG,pAVG); // BW AVG
                avgs[y][x] = c;
                most[y][x] = largestCountC;
                tone[y][x] = pC;
//                if(y == 8){
//                    Log.v("PIXEL", "y: " + y + ", x: " + x + " = " + Integer.toHexString(c) + " - " + rA + " - " + gA + " - " + bA + " - " + pAVG + " = " + Integer.toHexString(pC));
//                }
                uniquePixels.add( (mode == 1) ? largestCountC : pC );
//                float[] hsv = new float[3];
//                Color.colorToHSV(c, hsv);
//                if(hsv[1] < s_minmax[0]){
//                    s_minmax[0] = hsv[1];
//                }
//                if(hsv[1] > s_minmax[1]){
//                    s_minmax[1] = hsv[1];
//                }
//                if(hsv[2] < v_minmax[0]){
//                    v_minmax[0] = hsv[2];
//                }
//                if(hsv[2] > v_minmax[1]){
//                    v_minmax[1] = hsv[2];
//                }
            }
        }

//        Log.v("MINMAX", "color - s: " + colors_saturation_minmax[0] + " - " + colors_saturation_minmax[1] + "; v:" + colors_values_minmax[0] + " - " + colors_values_minmax[1] );
//        Log.v("MINMAX", "image - s: " + s_minmax[0] + " - " + s_minmax[1] + "; v:" + v_minmax[0] + " - " + v_minmax[1] );
//        float[] sRanges = new float[] {colors_saturation_minmax[1] - colors_saturation_minmax[0], s_minmax[1] - s_minmax[0]};
//        float[] vRanges = new float[] {colors_values_minmax[1] - colors_values_minmax[0], v_minmax[1] - v_minmax[0]};
//        Log.v("RANGES", "range - s: " + sRanges[0] + " - " + sRanges[1] + "; v:" + vRanges[0] + " - " + vRanges[1] );
//        float s_scale = sRanges[0] / sRanges[1]; float v_scale = vRanges[0] / vRanges[1];

        for(int c : uniquePixels) {
            float[] hsv = new float[3];
            Color.colorToHSV(c, hsv);
            int i = 0;
            float h = hsv[0];
//            float s = hsv[1];
//            float v = hsv[2];
//            float scaled_s = (s * s_scale) + colors_saturation_minmax[0];
//            float scaled_v = (v * v_scale) + colors_values_minmax[0];
            if( mode == 1 ){
                while( h > colors_hues[i] ){
                    i++;
                    if( i == colors_hues.length ){
                        i = colors_hues.length - 1;
                        break;
                    }
                }
                if(i == colors_hues.length - 1){
                    valueMapper.append(c, i);
//                    Log.v("Mapper", i + " - " + h + " - " + c);
                } else {
                    int ind = ( colors_hues[i+1] - h < h - colors_hues[i]) ? i+1: i;
                    valueMapper.append(c, ind);
//                    Log.v("Mapper", ind + " - " + h + " - " + c);
                }
            } else if( mode == 2 ) {
                int grey = Color.red(c);
                float step = 9.1875f;
                int full = 27 - (int)(Math.floor((float)grey / step));

                boolean firstHalf = full % 2 == 0;
                int index = full/2;//firstHalf ? full / 2 : 14 + ( 13 - (full / 2) ) ;

//                Log.v("MAPPER", "index" + index + " = " + Integer.toHexString(c));
                valueMapper.append(c, index);

//                int max = 0;
//                if( scaled_s > 0.9 && scaled_s > scaled_v && scaled_v/scaled_s > 0.6){
//                    // 0-7 index
//                    i = 0;
//                    max = 8;
//                } else if(scaled_s > scaled_v && scaled_v/scaled_s > 0.4){
//                    // 8-13 index
//                    i = 8;
//                    max = 14;
//                } else if( scaled_s > scaled_v ? scaled_v / scaled_s > 0.9 : scaled_s / scaled_v > 0.9 ){
//                    // 22-27 index
//                    i = 22;
//                    max = 28;
//                } else {
//                    // 14-21 index
//                    i = 14;
//                    max = 22;
//                }


//                int index = 0;
//                int diffIndexS = 0;
//                int diffIndexV = 0;
//                float minDiffS = 1;
//                float minDiffV = 1;
//                while(i < max){
//                    float cs = colors_saturation[i];
//                    float cv = colors_values[i];
//
//                    float fs = scaled_s < cs ? cs - scaled_s : scaled_s - cs;
//                    float fv = scaled_v < cv ? cv - scaled_v : scaled_v - cv;
//
//                    if(fs < minDiffS){
//                        minDiffS = fs;
//                        diffIndexS = index;
//                    }
//                    if(fv < minDiffV){
//                        minDiffV = fv;
//                        diffIndexV = index;
//                    }
//
//                    i++;
//                    index++;
//                }
//
//                int colorID = (minDiffS < minDiffV ? diffIndexS : diffIndexV);

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

                int cellValue = mode == 2 ? tone[y][x] : most[y][x];
                if(mode == 0){
                    p.setColor( cellValue );
                } else {
                    int[] cs = mode == 2 ? previewColors : colors;
                    p.setColor( Helper.getContextColor(cs[valueMapper.get(cellValue)]) );
                    output[y][x] = valueMapper.get(cellValue);
                }
                Rect currentRect = new Rect(x * size, y * size, (x * size) + size, (y * size) + size);
                canvas.drawRect(currentRect, p);
            }
        }
        imageView.setImageBitmap(newRender);

        lastChange = mode;
        if(mode > 0){
            doneButton.setAlpha(1f);
        }
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
//            Log.v("INIT", hsv[1] + " - " + hsv[2]);
            i++;
        }
//        Log.v("MINMAX", colors_saturation_minmax[0] + " - " + colors_saturation_minmax[1] + " - " + colors_values_minmax[0]+ " - " + colors_values_minmax[1]);
    }

    public int[] getBitmapPixels(int[] allPixels, int rowX, int x, int y, int size) {
        final int[] subsetPixels = new int[size * size];
        for (int row = 0; row < size; row++) {
            System.arraycopy(allPixels, ((x * size) + (y * rowX * size) + (row * rowX)), subsetPixels, row * size, size);
        }
        return subsetPixels;
    }



    private void doneTapped() {
        panel.addMemoryState();
        panel.setCells(output);

        closeDialog();
    }

    private void resetMemory() {
        originalBitmap.recycle();
        renderedBitmap.recycle();

        colors = Helper.getSelectedColors();
        colors_hues = new float[colors.length];
        colors_values = new float[colors.length];
        colors_saturation = new float[colors.length];
        colors_values_minmax = new float[]{1, 0};
        colors_saturation_minmax = new float[]{1, 0};

        output = null;

        imageView.setImageDrawable(null);
    }

    @Override
    public void closeDialog() {
        resetMemory();

        super.closeDialog();
    }
}
