package com.example.myapplication.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public int user_id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "contact")
    public String contact;
    @ColumnInfo(name = "gender")
    public String gender;
    @ColumnInfo(name = "height")
    public String height;
    @ColumnInfo(name = "weight")
    public String weight;
    @ColumnInfo(name = "age")
    public String age;
}
