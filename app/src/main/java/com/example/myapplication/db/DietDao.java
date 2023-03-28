package com.example.myapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DietDao {

    @Query("SELECT * FROM "+DbConstance.DIET_TABLE)
    LiveData<List<DietEntity>> getAllDietList();

    @Query("DELETE FROM "+ DbConstance.DIET_TABLE)
    void truncateTheList();

    @Insert
    void insertDietData(DietEntity dietEntity);

    @Query("DELETE FROM "+DbConstance.DIET_TABLE+" WHERE dietId = :dietId")
    void deleteTaskFromId(int dietId);

    @Query("SELECT * FROM "+DbConstance.DIET_TABLE+" WHERE age_range = :age_range_str")
    DietEntity selectDataFromAnId(String age_range_str);

    @Query("UPDATE "+DbConstance.DIET_TABLE+" SET  age_range = :age_range, " +
            "breakfast = :breakfast, lunch = :lunch, dinner = :dinner, dinner_time = :dinner_time," +
            " breakfast_time = :breakfast_time, lunch_time = :lunch_time WHERE dietId = :dietId")
    void updateAnExistingRow(int dietId, String age_range, String breakfast , String lunch, String dinner,
                             String breakfast_time, String lunch_time, String dinner_time);

}