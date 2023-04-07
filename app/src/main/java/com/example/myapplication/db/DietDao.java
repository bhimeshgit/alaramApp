package com.example.myapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DietDao {

    @Query("SELECT * FROM "+DbConstance.DIET_TABLE)
    List<DietEntity> getAllDietList();

    @Query("DELETE FROM "+ DbConstance.DIET_TABLE)
    void truncateTheList();

    @Insert
    void insertDietData(DietEntity dietEntity);

    @Query("DELETE FROM "+DbConstance.DIET_TABLE+" WHERE dietId = :dietId")
    void deleteTaskFromId(int dietId);

    @Query("DELETE FROM "+DbConstance.DIET_TABLE)
    void deleteAllData();

    @Query("SELECT * FROM "+DbConstance.DIET_TABLE+" WHERE bmi_range = :age_range_str")
    DietEntity selectDiet(String age_range_str);

    @Query("UPDATE "+DbConstance.DIET_TABLE+" SET  bmi_range = :age_range, " +
            "morning_drink = :morning_drink, morning_breakfast = :morning_breakfast, " +
            "lunch = :lunch, dinner = :dinner, evening_breakfast = :evening_breakfast," +
            "exercise = :exercise, dinner = :dinner WHERE dietId = :dietId")
    void updateAnExistingRow(int dietId, String age_range, String morning_drink,
                             String morning_breakfast,  String lunch, String evening_breakfast,
                             String dinner, String exercise);

}