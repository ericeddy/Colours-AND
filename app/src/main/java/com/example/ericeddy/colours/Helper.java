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
}
