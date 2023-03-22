package com.example.myapplication.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = DbConstance.DIET_TABLE)
public class DietEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dietId")
    int dietId;
    @ColumnInfo(name = "age_range")
    String age_range;
    @ColumnInfo(name = "breakfast")
    String breakfast;
    @ColumnInfo(name = "lunch")
    String lunch;
    @ColumnInfo(name = "dinner")
    String dinner;
    @ColumnInfo(name = "dinner_time")
    String dinner_time;
    @ColumnInfo(name = "breakfast_time")
    String breakfast_time;
    @ColumnInfo(name = "lunch_time")
    String lunch_time;

    public DietEntity() {

    }
}