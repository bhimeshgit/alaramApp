package com.example.myapplication.admin;

import androidx.lifecycle.LiveData;

import com.example.myapplication.db.DietDao;
import com.example.myapplication.db.DietEntity;

import java.util.List;

public class DietAdminRepository {
    private DietDao dietDao;
    public DietAdminRepository(DietDao dietDao){
        this.dietDao = dietDao;
    }

    public LiveData<List<DietEntity>> getDietList(){
        return dietDao.getAllDietList();
    }

    public void addDiet(DietEntity dietEntity){
        dietDao.insertDietData(dietEntity);
    }
}
