package com.app.sdchatbot.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static final String MY_PREFERENCE_NAME = "com.app.sdchatbot";
    private static final String MY_FILE_NAME = "my_file_name";
    private static final String DEFAULT_TEXT = "No files in here.";


    public static void saveFileName(Context context, String fileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MY_FILE_NAME,fileName);
        editor.apply();
    }

    public static String loadPdfFile(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MY_FILE_NAME, DEFAULT_TEXT);
    }
}
