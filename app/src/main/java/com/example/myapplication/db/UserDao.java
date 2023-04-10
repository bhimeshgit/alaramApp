package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM "+DbConstance.USER_TABLE+ " WHERE contact=:contact")
    List<User> getUser(String contact);

    @Query("SELECT * FROM "+DbConstance.USER_TABLE+ " WHERE user_id=:user_id")
    List<User> getUserByUid(int user_id);

    @Query("DELETE FROM "+ DbConstance.USER_TABLE)
    void truncateTheList();

    @Insert
    void insertUserData(User user);


    @Query("UPDATE "+DbConstance.USER_TABLE+" SET  name = :name, " +
            "contact = :contact, gender = :gender, " +
            "height = :height, weight = :weight, age = :age  WHERE user_id = :uid")
    void updateUser(int uid, String name,
                             String contact,  String gender, String height,
                             String weight, String age);

}

