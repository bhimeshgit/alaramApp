package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettingSharePref {

    private static AppSettingSharePref appSettingSharePref = new AppSettingSharePref();
    private static SharedPreferences sharePre;
    private static SharedPreferences.Editor editor;

    private AppSettingSharePref() {
    }

    public static AppSettingSharePref getInstance(Context context) {
        try {
            if (sharePre == null) {
                sharePre = context.getSharedPreferences("PropertySharePref", 0);
                editor = sharePre.edit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appSettingSharePref;
    }


    public String getUserName() {
        String username = "";
        try {
            username = sharePre.getString("username", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.apply();
    }


    public int getUid() {
        int username = -1;
        try {
            username = sharePre.getInt("uid", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public void setUid(int username) {
        editor.putInt("uid", username);
        editor.apply();
    }

    public int getAlarm() {
        int username = -1;
        try {
            username = sharePre.getInt("alarm", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public void setAlarm(int username) {
        editor.putInt("alarm", username);
        editor.apply();
    }


}
