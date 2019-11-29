package com.example.ericeddy.colours;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

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
}
