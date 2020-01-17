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
            _themesList.add( getDefaultColors() );  // 0
            _themesList.add( getLightColors() );    // 1
            _themesList.add( getDarkColors() );     // 2
            _themesList.add( getBWColors() );       // 3
            _themesList.add( getRedColors() );      // 4
            _themesList.add( getOrangeColors() );   // 5
            _themesList.add( getYellowColors() );   // 6
            _themesList.add( getGreenColors() );    // 7
            _themesList.add( getBlueColors() );     // 8
            _themesList.add( getPurpleColors() );   // 9
        }
        return _themesList;
    }
    public static boolean isSelectedColorsRainbow(){
        int theme = PreferenceManager.getTheme();
        return theme <= 2;
    }
    public static int getDefaultRainbow() { return 0; }
    public static int getDefaultMono() { return 3; }
    public static void setDefaultRainbow(){
        PreferenceManager.setTheme( getDefaultRainbow() );
        MainActivity.themeChanged();
    }
    public static void setNextRainbow(){
        int theme = PreferenceManager.getTheme();
        if(theme == 2){
            theme = 0;
        } else {
            theme = theme + 1;
        }
        PreferenceManager.setTheme( theme );
        MainActivity.themeChanged();
    }
    public static void setDefaultMono(){
        PreferenceManager.setTheme( getDefaultMono() );
        MainActivity.themeChanged();
    }
    public static void setNextMono(){
        int theme = PreferenceManager.getTheme();
        if(theme == 9){
            theme = 3;
        } else {
            theme = theme + 1;
        }
        PreferenceManager.setTheme( theme );
        MainActivity.themeChanged();
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

    public static int[] getOrangeColors() {
        int[] lightRainbowRes = {
                R.color.orange_rainbow_11, R.color.orange_rainbow_12, R.color.orange_rainbow_13, R.color.orange_rainbow_14,
                R.color.orange_rainbow_21, R.color.orange_rainbow_22, R.color.orange_rainbow_23, R.color.orange_rainbow_24,
                R.color.orange_rainbow_31, R.color.orange_rainbow_32, R.color.orange_rainbow_33, R.color.orange_rainbow_34,
                R.color.orange_rainbow_51, R.color.orange_rainbow_52, R.color.orange_rainbow_53, R.color.orange_rainbow_54,
                R.color.orange_rainbow_61, R.color.orange_rainbow_62, R.color.orange_rainbow_63, R.color.orange_rainbow_64,
                R.color.orange_rainbow_71, R.color.orange_rainbow_72, R.color.orange_rainbow_73, R.color.orange_rainbow_74,
                R.color.orange_rainbow_81, R.color.orange_rainbow_82, R.color.orange_rainbow_83, R.color.orange_rainbow_84 };
        return lightRainbowRes;
    }

    public static int[] getPurpleColors() {
        int[] lightRainbowRes = {
                R.color.purple_rainbow_11, R.color.purple_rainbow_12, R.color.purple_rainbow_13, R.color.purple_rainbow_14,
                R.color.purple_rainbow_21, R.color.purple_rainbow_22, R.color.purple_rainbow_23, R.color.purple_rainbow_24,
                R.color.purple_rainbow_31, R.color.purple_rainbow_32, R.color.purple_rainbow_33, R.color.purple_rainbow_34,
                R.color.purple_rainbow_51, R.color.purple_rainbow_52, R.color.purple_rainbow_53, R.color.purple_rainbow_54,
                R.color.purple_rainbow_61, R.color.purple_rainbow_62, R.color.purple_rainbow_63, R.color.purple_rainbow_64,
                R.color.purple_rainbow_71, R.color.purple_rainbow_72, R.color.purple_rainbow_73, R.color.purple_rainbow_74,
                R.color.purple_rainbow_81, R.color.purple_rainbow_82, R.color.purple_rainbow_83, R.color.purple_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getBWColors() {
        int[] lightRainbowRes = {
                R.color.bw_rainbow_11, R.color.bw_rainbow_12, R.color.bw_rainbow_13, R.color.bw_rainbow_14,
                R.color.bw_rainbow_21, R.color.bw_rainbow_22, R.color.bw_rainbow_23, R.color.bw_rainbow_24,
                R.color.bw_rainbow_31, R.color.bw_rainbow_32, R.color.bw_rainbow_33, R.color.bw_rainbow_34,
                R.color.bw_rainbow_51, R.color.bw_rainbow_52, R.color.bw_rainbow_53, R.color.bw_rainbow_54,
                R.color.bw_rainbow_61, R.color.bw_rainbow_62, R.color.bw_rainbow_63, R.color.bw_rainbow_64,
                R.color.bw_rainbow_71, R.color.bw_rainbow_72, R.color.bw_rainbow_73, R.color.bw_rainbow_74,
                R.color.bw_rainbow_81, R.color.bw_rainbow_82, R.color.bw_rainbow_83, R.color.bw_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getRedColors() {
        int[] lightRainbowRes = {
                R.color.red_rainbow_11, R.color.red_rainbow_12, R.color.red_rainbow_13, R.color.red_rainbow_14,
                R.color.red_rainbow_21, R.color.red_rainbow_22, R.color.red_rainbow_23, R.color.red_rainbow_24,
                R.color.red_rainbow_31, R.color.red_rainbow_32, R.color.red_rainbow_33, R.color.red_rainbow_34,
                R.color.red_rainbow_51, R.color.red_rainbow_52, R.color.red_rainbow_53, R.color.red_rainbow_54,
                R.color.red_rainbow_61, R.color.red_rainbow_62, R.color.red_rainbow_63, R.color.red_rainbow_64,
                R.color.red_rainbow_71, R.color.red_rainbow_72, R.color.red_rainbow_73, R.color.red_rainbow_74,
                R.color.red_rainbow_81, R.color.red_rainbow_82, R.color.red_rainbow_83, R.color.red_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getYellowColors() {
        int[] lightRainbowRes = {
                R.color.yellow_rainbow_11, R.color.yellow_rainbow_12, R.color.yellow_rainbow_13, R.color.yellow_rainbow_14,
                R.color.yellow_rainbow_21, R.color.yellow_rainbow_22, R.color.yellow_rainbow_23, R.color.yellow_rainbow_24,
                R.color.yellow_rainbow_31, R.color.yellow_rainbow_32, R.color.yellow_rainbow_33, R.color.yellow_rainbow_34,
                R.color.yellow_rainbow_51, R.color.yellow_rainbow_52, R.color.yellow_rainbow_53, R.color.yellow_rainbow_54,
                R.color.yellow_rainbow_61, R.color.yellow_rainbow_62, R.color.yellow_rainbow_63, R.color.yellow_rainbow_64,
                R.color.yellow_rainbow_71, R.color.yellow_rainbow_72, R.color.yellow_rainbow_73, R.color.yellow_rainbow_74,
                R.color.yellow_rainbow_81, R.color.yellow_rainbow_82, R.color.yellow_rainbow_83, R.color.yellow_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getBlueColors() {
        int[] lightRainbowRes = {
                R.color.blue_rainbow_11, R.color.blue_rainbow_12, R.color.blue_rainbow_13, R.color.blue_rainbow_14,
                R.color.blue_rainbow_21, R.color.blue_rainbow_22, R.color.blue_rainbow_23, R.color.blue_rainbow_24,
                R.color.blue_rainbow_31, R.color.blue_rainbow_32, R.color.blue_rainbow_33, R.color.blue_rainbow_34,
                R.color.blue_rainbow_51, R.color.blue_rainbow_52, R.color.blue_rainbow_53, R.color.blue_rainbow_54,
                R.color.blue_rainbow_61, R.color.blue_rainbow_62, R.color.blue_rainbow_63, R.color.blue_rainbow_64,
                R.color.blue_rainbow_71, R.color.blue_rainbow_72, R.color.blue_rainbow_73, R.color.blue_rainbow_74,
                R.color.blue_rainbow_81, R.color.blue_rainbow_82, R.color.blue_rainbow_83, R.color.blue_rainbow_84 };
        return lightRainbowRes;
    }
    public static int[] getBtnColors() {
        int[] res = {
                R.color.grey_2,R.color.grey_4,R.color.grey_6,R.color.grey_8,
                R.color.grey_a,R.color.grey_c,R.color.grey_d,R.color.grey_b,
                R.color.grey_9,R.color.grey_7,R.color.grey_5,R.color.grey_3
        };
        return res;
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
    public static void printOrangeColors() {
        int sr = 255; int sg = (int)((float)sr * 0.5); int sb = 0;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int r1 = (int)((float)255 * 0.6);
        int r2 = (int)((float)255 * 0.25);
        int r3 = (int)((float)255 * 0.7);
        int r4 = 255;

        int b1 = 0;
        int b2 = 31;
        int b3 = 71;
        int b4 = 0;

        int g1 = (int)(((float)r1 * 0.5) + ((float)b1 * 0.5));
        int g2 = (int)(((float)r2 * 0.5) + ((float)b2 * 0.5));
        int g3 = (int)(((float)r3 * 0.5) + ((float)b3 * 0.5));
        int g4 = (int)(((float)r4 * 0.5) + ((float)b4 * 0.5));

        float dg1 = (float)(sg - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr1 = (float)(sr-r1) / (float)l1;
        float dr2 = (float)(r2-r1) / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;

        float db1 = (float)(sb-b1) / (float)l1;
        float db2 = (float)(b2-b1) / (float)l2;
        float db3 = (float)(b3-b2) / (float)l3;
        float db4 = (float)(b4-b3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fr = sr - (dr1 * i);
                r = (int)fr;

                fg = sg - (dg1 * i);
                g = (int)fg;

                fb = sb - (db1 * i);
                b = (int)fb;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = g1 + (dg2 * i2);
                g = (int)fg;

                fb = b1 + (db2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = g2 + (dg3 * i3);
                g = (int)fg;

                fb = b2 + (db3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = g3 + (dg4 * i4);
                g = (int)fg;

                fb = b3 + (db4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("ORANGE", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }
    public static void printPurpleColors() {
        int sr = (int)((float)255 * 0.66); int sg = 0; int sb = 255;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int b1 = (int)((float)255 * 0.6);
        int b2 = (int)((float)255 * 0.25);
        int b3 = (int)((float)255 * 0.6);
        int b4 = 255;

        int g1 = 0;
        int g2 = 26;
        int g3 = 61;
        int g4 = 0;

        int r1 = (int)(((float)b1 * 0.66) + ((float)g1 * 0.33));
        int r2 = (int)(((float)b2 * 0.66) + ((float)g2 * 0.33));
        int r3 = (int)(((float)b3 * 0.66) + ((float)g3 * 0.33));
        int r4 = (int)(((float)b4 * 0.66) + ((float)g4 * 0.33));

        float dg1 = (float)(sg - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr1 = (float)(sr-r1) / (float)l1;
        float dr2 = (float)(r2-r1) / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;

        float db1 = (float)(sb-b1) / (float)l1;
        float db2 = (float)(b2-b1) / (float)l2;
        float db3 = (float)(b3-b2) / (float)l3;
        float db4 = (float)(b4-b3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fr = sr - (dr1 * i);
                r = (int)fr;

                fg = sg - (dg1 * i);
                g = (int)fg;

                fb = sb - (db1 * i);
                b = (int)fb;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = g1 + (dg2 * i2);
                g = (int)fg;

                fb = b1 + (db2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = g2 + (dg3 * i3);
                g = (int)fg;

                fb = b2 + (db3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = g3 + (dg4 * i4);
                g = (int)fg;

                fb = b3 + (db4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("PURPLE", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }


    public static void printRedColors() {
        int sr = 255; int sg = 0; int sb = 0;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int r1 = (int)((float)255 * 0.6);
        int r2 = (int)((float)255 * 0.25);
        int r3 = (int)((float)255 * 0.6);
        int r4 = 255;

        int g1 = 0;
        int g2 = 26;
        int g3 = 71;
        int g4 = 0;

        int b1 = g1;
        int b2 = g2;
        int b3 = g3;
        int b4 = g4;

        float dg1 = (float)(sg - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr1 = (float)(sr-r1) / (float)l1;
        float dr2 = (float)(r2-r1) / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;

        float db1 = (float)(sb-b1) / (float)l1;
        float db2 = (float)(b2-b1) / (float)l2;
        float db3 = (float)(b3-b2) / (float)l3;
        float db4 = (float)(b4-b3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fr = sr - (dr1 * i);
                r = (int)fr;

                fg = sg - (dg1 * i);
                g = (int)fg;

                fb = sb - (db1 * i);
                b = (int)fb;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = g1 + (dg2 * i2);
                g = (int)fg;

                fb = b1 + (db2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = g2 + (dg3 * i3);
                g = (int)fg;

                fb = b2 + (db3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = g3 + (dg4 * i4);
                g = (int)fg;

                fb = b3 + (db4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("PURPLE", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }
    public static void printYellowColors() {
        int sr = 230; int sg = 230; int sb = 0;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int r1 = (int)((float)255 * 0.6);
        int r2 = (int)((float)255 * 0.25);
        int r3 = (int)((float)255 * 0.6);
        int r4 = 230;

        int g1 = r1;
        int g2 = r2;
        int g3 = r3;
        int g4 = r4;

        int b1 = 0;
        int b2 = 41;
        int b3 = 71;
        int b4 = 0;

        float dg1 = (float)(sg - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr1 = (float)(sr-r1) / (float)l1;
        float dr2 = (float)(r2-r1) / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;

        float db1 = (float)(sb-b1) / (float)l1;
        float db2 = (float)(b2-b1) / (float)l2;
        float db3 = (float)(b3-b2) / (float)l3;
        float db4 = (float)(b4-b3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fr = sr - (dr1 * i);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = sb - (db1 * i);
                b = (int)fb;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b1 + (db2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b2 + (db3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b3 + (db4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("YELLOW", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
        }

    }
    public static void printBlueColors() {
        int sr = 0; int sg = 0; int sb = 255;
        float fr = sr; float fg = sg; float fb = sb;
        int r = sr; int g = sg; int b = sb;

        int l = 28;
        int l1 = 8; int l2 = 6; int l3 = 8; int l4 = 6;

        int b1 = (int)((float)255 * 0.6);
        int b2 = (int)((float)255 * 0.25);
        int b3 = (int)((float)255 * 0.7);
        int b4 = 255;

        int g1 = 0;
        int g2 = 13;
        int g3 = 26;
        int g4 = 0;

        int r1 = g1;
        int r2 = g2;
        int r3 = g3;
        int r4 = g4;

        float dg1 = (float)(sg - g1) / (float)l1;
        float dg2 = (float)(g2 - g1) / (float)l2;
        float dg3 = (float)(g3 - g2) / (float)l3;
        float dg4 = (float)(g4 - g3) / (float)l3;

        float dr1 = (float)(sr-r1) / (float)l1;
        float dr2 = (float)(r2-r1) / (float)l2;
        float dr3 = (float)(r3-r2) / (float)l3;
        float dr4 = (float)(r4-r3) / (float)l4;

        float db1 = (float)(sb-b1) / (float)l1;
        float db2 = (float)(b2-b1) / (float)l2;
        float db3 = (float)(b3-b2) / (float)l3;
        float db4 = (float)(b4-b3) / (float)l4;


        for(int i = 0; i < l; i++){
            if(i < l1){
                fr = sr - (dr1 * i);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = sb - (db1 * i);
                b = (int)fb;
            } else if(i < l1 + l2){
                int i2 = i - l1;
                fr = r1 + (dr2 * i2);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b1 + (db2 * i2);
                b = (int)fb;

            } else if(i < l1 + l2 + l3){
                int i3 = i - l1 - l2;
                fr = r2 + (dr3 * i3);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b2 + (db3 * i3);
                b = (int)fb;
            } else if(i < l1 + l2 + l3 + l4){
                int i4 = i - l1 - l2 - l3;
                fr = r3 + (dr4 * i4);
                r = (int)fr;

                fg = fr;
                g = (int)fg;

                fb = b3 + (db4 * i4);
                b = (int)fb;
            }
//            Log.v("GREEN", i + " - " + r + " " + g + " " + b);
            Log.v("BLUE", " - " + getHexString(r) + "" + getHexString(g) + "" + getHexString(b));
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
