package com.example.myapplication.user;

import android.content.Context;

import com.example.myapplication.db.DatabaseClient;
import com.example.myapplication.db.User;
import com.example.myapplication.db.UserDao;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private DatabaseClient dbClient;
    public UserRepository(Context context){
        this.dbClient = DatabaseClient.getInstance(context);
        this.userDao = dbClient.getAppDatabase().getUserDao();
    }

    public void addUser(User user){
        userDao.insertUserData(user);
    }

    public void updateUser(User user){
        userDao.updateUser(user.user_id, user.name,user.contact, user.gender, user.height, user.weight, user.age);

    }
    public List<User> getUser(String contact){
        return userDao.getUser(contact);
    }

    public List<User> getUserById(int uid){
        return userDao.getUserByUid(uid);
    }
}
