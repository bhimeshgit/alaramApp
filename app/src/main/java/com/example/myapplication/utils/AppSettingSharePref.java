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

    public int getMorDrinkHr() {
        int username = -1;
        try {
            username = sharePre.getInt("morDrinkHr", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setMorDrinkHr(int username) {
        editor.putInt("morDrinkHr", username);
        editor.apply();
    }

    public int getMorDrinkMin() {
        int username = -1;
        try {
            username = sharePre.getInt("morDrinkMin", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setMorDrinkMin(int username) {
        editor.putInt("morDrinkMin", username);
        editor.apply();
    }
    public int getMorBreakMin() {
        int username = -1;
        try {
            username = sharePre.getInt("morBreakMin", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public void setMorBreakMin(int username) {
        editor.putInt("morBreakMin", username);
        editor.apply();
    }
    public void setMorBreakHr(int username) {
        editor.putInt("morBreakHr", username);
        editor.apply();
    }
    public int getMorBreakHr() {
        int username = -1;
        try {
            username = sharePre.getInt("morBreakHr", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public int getLunchHr() {
        int username = -1;
        try {
            username = sharePre.getInt("lunchHr", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setLunchHr(int username) {
        editor.putInt("lunchHr", username);
        editor.apply();
    }

    public int getLunchMin() {
        int username = -1;
        try {
            username = sharePre.getInt("lunchMin", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setLunchMin(int username) {
        editor.putInt("lunchMin", username);
        editor.apply();
    }

    public int getEveBreakHr() {
        int username = -1;
        try {
            username = sharePre.getInt("EveBreakHr", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setEveBreakHr(int username) {
        editor.putInt("EveBreakHr", username);
        editor.apply();
    }

    public int getEveBreakMin() {
        int username = -1;
        try {
            username = sharePre.getInt("EveBreakMin", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setEveBreakMin(int username) {
        editor.putInt("EveBreakMin", username);
        editor.apply();
    }

    public int getDinnerHr() {
        int username = -1;
        try {
            username = sharePre.getInt("DinnerHr", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setDinnerHr(int username) {
        editor.putInt("DinnerHr", username);
        editor.apply();
    }

    public int getDinnerMin() {
        int username = -1;
        try {
            username = sharePre.getInt("DinnerMin", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    public void setDinnerMin(int username) {
        editor.putInt("DinnerMin", username);
        editor.apply();
    }
}
