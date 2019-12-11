package com.example.ericeddy.colours;

import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PreferenceManager {
    private static final String KEY_PREFERENCES = "KEY_PREFERENCES";

    private static final String KEY_TOUCH_TYPE = "KEY_TOUCH_TYPE";
    private static final String KEY_TOUCH_TYPE_COLOUR = "KEY_TOUCH_TYPE_COLOUR";
    private static final String KEY_TOUCH_SIZE = "KEY_TOUCH_SIZE";
    private static final String KEY_PLAYING_FORWARDS = "KEY_PLAYING_FORWARDS";
    private static final String KEY_PLAYING_SPEED = "KEY_PLAYING_SPEED";
    private static final String KEY_THEME = "KEY_THEME";

    public static void setTouchType(int type) {
        setInteger(KEY_PREFERENCES, KEY_TOUCH_TYPE, type);
    }
    public static int getTouchType(){
        return getInteger(KEY_PREFERENCES, KEY_TOUCH_TYPE, 0);
    }

    public static void setTouchColour(int colour) {
        setInteger(KEY_PREFERENCES, KEY_TOUCH_TYPE_COLOUR, colour);
    }
    public static int getTouchColour(){
        return getInteger(KEY_PREFERENCES, KEY_TOUCH_TYPE_COLOUR, -1);
    }

    public static void setTouchSize(int size) {
        setInteger(KEY_PREFERENCES, KEY_TOUCH_SIZE, size);
    }
    public static int getTouchSize(){
        return getInteger(KEY_PREFERENCES, KEY_TOUCH_SIZE, 0);
    }

    public static void setPlayingForwards(boolean isForwards) {
        setBoolean(KEY_PREFERENCES, KEY_PLAYING_FORWARDS, isForwards);
    }
    public static boolean getPlayingForwards(){
        return getBoolean(KEY_PREFERENCES, KEY_PLAYING_FORWARDS, true);
    }
    public static void setPlayingSpeed(float speed) {
        setFloat(KEY_PREFERENCES, KEY_PLAYING_SPEED, speed);
    }
    public static float getPlayingSpeed(){
        return getFloat(KEY_PREFERENCES, KEY_PLAYING_SPEED, 1f);
    }
    public static void setTheme(int theme) {
        setInteger(KEY_PREFERENCES, KEY_THEME, theme);
    }
    public static int getTheme(){
        return getInteger(KEY_PREFERENCES, KEY_THEME, 0);
    }


    static final int PRIVATE_MODE = 0;
    private static final String SHARED_PREF_NAME = "Colours";

    static SharedPreferences getSharedPreference(String name){
        if (name.isEmpty()){
            return MainActivity.getAppContext().getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        }
        return MainActivity.getAppContext().getSharedPreferences(name, PRIVATE_MODE);
    }

    static SharedPreferences.Editor getEditor(String name){
        return getSharedPreference(name).edit();
    }

    static void clearAll(String prefName) {
        getEditor(prefName).clear().apply();
    }

    static void clearById(String prefName, String id){
        getEditor(prefName).remove(id).apply();
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region STRING

    static String getString(String key) {
        return getString("", key);
    }

    static void setString(String sharePrefName, String key, String value) {
        SharedPreferences.Editor editor = getEditor(sharePrefName);
        editor.putString(key, value);
        editor.apply();
    }

    static void setString(String key, String value) {
        setString("", key, value);
    }

    static String getString(String sharePrefName, String key) {
        return getSharedPreference(sharePrefName).getString(key, "");
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region BOOL

    protected static boolean getBoolean(String sharePrefName, String key, boolean defaultValue) {
        return getSharedPreference(sharePrefName).getBoolean(key, defaultValue);
    }

    protected static void setBoolean(String sharePrefName, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(sharePrefName);
        editor.putBoolean(key, value);
        editor.apply();
    }

    protected static boolean getBoolean(String key, boolean defaultValue){
        return getBoolean("", key, defaultValue);
    }

    protected static void setBoolean(String key, boolean value) {
        setBoolean("", key, value);
    }

// endregion

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region INT

    static int getInteger(String sharePrefName, String key, int defaultValue) {
        return getSharedPreference(sharePrefName).getInt(key, defaultValue);
    }

    static void setInteger(String sharePrefName, String key, int defaultValue) {
        SharedPreferences.Editor editor = getEditor(sharePrefName);
        editor.putInt(key, defaultValue);
        editor.apply();
    }

    static float getFloat(String sharePrefName, String key, float defaultValue) {
        return getSharedPreference(sharePrefName).getFloat(key, defaultValue);
    }

    static void setFloat(String sharePrefName, String key, float defaultValue) {
        SharedPreferences.Editor editor = getEditor(sharePrefName);
        editor.putFloat(key, defaultValue);
        editor.apply();
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region Date

    static void setDate(String key, Date value){
        setDate("", key, value);
    }

    static void setDate(String prefName, String key, Date value){
        if (value == null){
            return;
        }

        SharedPreferences.Editor editor = getEditor(prefName);
        editor.putLong(key, value.getTime());
        editor.apply();
    }

    static Date getDate(String key, Date defaultValue){
        return getDate("", key, defaultValue);
    }

    static Date getDate(String prefName, String key, Date defaultValue){
        Long value = getSharedPreference(prefName).getLong(key, 0);

        if (value == 0){
            return defaultValue;
        }

        return new Date(value);
    }

// endregion

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// region ARRAY

    /*
    static ArrayList<String> getArrayList(String prefName, String key){
        ArrayList<String> arrayList = null;
        try {
            String string = getString(prefName, key);
            arrayList = (ArrayList<String>) ObjectSerializer.deserialize(string);
        } catch (IOException e) { }
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    static void setArrayList(String prefName, String key, ArrayList<String> value){
        try {
            String string = ObjectSerializer.serialize(value);
            setString(prefName, key, string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
