package com.example.myapplication.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = DbConstance.DIET_TABLE)
public class DietEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dietId")
    public int dietId;

    @ColumnInfo(name = "water")
    public String water;
    @ColumnInfo(name = "bmi_range")
    public String bmi_range;
    @ColumnInfo(name = "morning_drink")
    public String morning_drink;
    @ColumnInfo(name = "morning_breakfast")
    public String morning_breakfast;
    @ColumnInfo(name = "lunch")
    public String lunch;
    @ColumnInfo(name = "evening_breakfast")
    public String evening_breakfast;
    @ColumnInfo(name = "dinner")
    public String dinner;

    @ColumnInfo(name = "exercise")
    public String exercise;
    public DietEntity() {

    }
}