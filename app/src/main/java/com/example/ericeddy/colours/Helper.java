package com.example.ericeddy.colours;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;

import java.util.ArrayList;

public class Helper {

    public static int getContextColor(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return MainActivity.getInstance().getColor(colorRes);
        }

        return ContextCompat.getColor(MainActivity.getInstance(), colorRes);
    }

    public static int convertDpToPixels(float dp) {
        return convertDpToPixels(dp, MainActivity.getAppContext());
    }

    public static int convertDpToPixels(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private static ArrayList<int[]> _themesList;
    public static ArrayList<int[]> getThemesList() {
        if(_themesList == null){
            _themesList = new ArrayList<>();
            _themesList.add( getDefaultColors() );
            _themesList.add( getLightColors() );
            _themesList.add( getDarkColors() );
            _themesList.add( getGreenColors() );
        }
        return _themesList;
    }

    public static int[] getSelectedColors() {
        ArrayList<int[]> themes = getThemesList();
        int theme = PreferenceManager.getTheme();
        return themes.get(theme);
    }
    public static int[] getDefaultColors() {
        int[] defaultRainbowRes = {R.color.default_rainbow_11, R.color.default_rainbow_12, R.color.default_rainbow_13, R.color.default_rainbow_14,
                R.color.default_rainbow_21, R.color.default_rainbow_22, R.color.default_rainbow_23, R.color.default_rainbow_24,
                R.color.default_rainbow_31, R.color.default_rainbow_32, R.color.default_rainbow_33, R.color.default_rainbow_34,
                /*R.color.default_rainbow_41, R.color.default_rainbow_42, R.color.default_rainbow_43, R.color.default_rainbow_44,*/
                R.color.default_rainbow_51, R.color.default_rainbow_52, R.color.default_rainbow_53, R.color.default_rainbow_54,
                R.color.default_rainbow_61, R.color.default_rainbow_62, R.color.default_rainbow_63, R.color.default_rainbow_64,
                R.color.default_rainbow_71, R.color.default_rainbow_72, R.color.default_rainbow_73, R.color.default_rainbow_74,
                R.color.default_rainbow_81, R.color.default_rainbow_82, R.color.default_rainbow_83, R.color.default_rainbow_84 };
        return defaultRainbowRes;
    }

    public static int[] getLightColors() {
        int[] lightRainbowRes = {R.color.light_rainbow_11, R.color.light_rainbow_12, R.color.light_rainbow_13, R.color.light_rainbow_14,
                R.color.light_rainbow_21, R.color.light_rainbow_22, R.color.light_rainbow_23, R.color.light_rainbow_24,
                R.color.light_rainbow_31, R.color.light_rainbow_32, R.color.light_rainbow_33, R.color.light_rainbow_34,
                /*R.color.default_rainbow_41, R.color.default_rainbow_42, R.color.default_rainbow_43, R.color.default_rainbow_44,*/
                R.color.light_rainbow_51, R.color.light_rainbow_52, R.color.light_rainbow_53, R.color.light_rainbow_54,
                R.color.light_rainbow_61, R.color.light_rainbow_62, R.color.light_rainbow_63, R.color.light_rainbow_64,
                R.color.light_rainbow_71, R.color.light_rainbow_72, R.color.light_rainbow_73, R.color.light_rainbow_74,
                R.color.light_rainbow_81, R.color.light_rainbow_82, R.color.light_rainbow_83, R.color.light_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getDarkColors() {
        int[] lightRainbowRes = {R.color.dark_rainbow_11, R.color.dark_rainbow_12, R.color.dark_rainbow_13, R.color.dark_rainbow_14,
                R.color.dark_rainbow_21, R.color.dark_rainbow_22, R.color.dark_rainbow_23, R.color.dark_rainbow_24,
                R.color.dark_rainbow_31, R.color.dark_rainbow_32, R.color.dark_rainbow_33, R.color.dark_rainbow_34,
                R.color.dark_rainbow_51, R.color.dark_rainbow_52, R.color.dark_rainbow_53, R.color.dark_rainbow_54,
                R.color.dark_rainbow_61, R.color.dark_rainbow_62, R.color.dark_rainbow_63, R.color.dark_rainbow_64,
                R.color.dark_rainbow_71, R.color.dark_rainbow_72, R.color.dark_rainbow_73, R.color.dark_rainbow_74,
                R.color.dark_rainbow_81, R.color.dark_rainbow_82, R.color.dark_rainbow_83, R.color.dark_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getGreenColors() {
        int[] lightRainbowRes = {R.color.green_rainbow_11, R.color.green_rainbow_12, R.color.green_rainbow_13, R.color.green_rainbow_14,
                R.color.green_rainbow_21, R.color.green_rainbow_22, R.color.green_rainbow_23, R.color.green_rainbow_24,
                R.color.green_rainbow_31, R.color.green_rainbow_32, R.color.green_rainbow_33, R.color.green_rainbow_34,
                R.color.green_rainbow_51, R.color.green_rainbow_52, R.color.green_rainbow_53, R.color.green_rainbow_54,
                R.color.green_rainbow_61, R.color.green_rainbow_62, R.color.green_rainbow_63, R.color.green_rainbow_64,
                R.color.green_rainbow_71, R.color.green_rainbow_72, R.color.green_rainbow_73, R.color.green_rainbow_74,
                R.color.green_rainbow_81, R.color.green_rainbow_82, R.color.green_rainbow_83, R.color.green_rainbow_84 };
        return lightRainbowRes;
    }

    public static void printGreenColors() {
        int sr = 0; int sg = 245; int sb = 0;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int g1 = (int)((float)255 * 0.6);
        int g2 = (int)((float)255 * 0.25);
        int g3 = (int)((float)255 * 0.7);
        int g4 = (245);

        int r1 = 0;
        int r2 = (int)((float)255 * 0.06);
        int r3 = (int)((float)255 * 0.28);
        int r4 = 0;

        float dg1 = (float)(255 - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr2 = (float)r2 / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fg = sg - (dg1 * i);
                g = (int)fg;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = g1 + (dg2 * i2);
                g = (int)fg;

                fb = r1 + (dr2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = g2 + (dg3 * i3);
                g = (int)fg;

                fb = r2 + (dr3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = g3 + (dg4 * i4);
                g = (int)fg;

                fb = r3 + (dr4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("GREEN", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }
    public static void printGreenColors() {
        int sr = 0; int sg = 245; int sb = 0;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int g1 = (int)((float)255 * 0.6);
        int g2 = (int)((float)255 * 0.25);
        int g3 = (int)((float)255 * 0.7);
        int g4 = (245);

        int r1 = 0;
        int r2 = (int)((float)255 * 0.06);
        int r3 = (int)((float)255 * 0.28);
        int r4 = 0;

        float dg1 = (float)(255 - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr2 = (float)r2 / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fg = sg - (dg1 * i);
                g = (int)fg;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = g1 + (dg2 * i2);
                g = (int)fg;

                fb = r1 + (dr2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = g2 + (dg3 * i3);
                g = (int)fg;

                fb = r2 + (dr3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = g3 + (dg4 * i4);
                g = (int)fg;

                fb = r3 + (dr4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("GREEN", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }
    private static String getHexString(int value) {
        String s = Integer.toString(value, 16);
        if(s.length() == 1){
            s = "0" + s;
        }
        return s;
    }

}
