package com.example.myapplication.admin;

import android.content.Context;

import com.example.myapplication.db.DatabaseClient;
import com.example.myapplication.db.DietDao;
import com.example.myapplication.db.DietEntity;

import java.util.List;

public class DietAdminRepository {
    private DietDao dietDao;
    private DatabaseClient dbClient;
    public DietAdminRepository(Context context){
        this.dbClient = DatabaseClient.getInstance(context);
        this.dietDao = dbClient.getAppDatabase().getDietDao();
    }

    public List<DietEntity> getDietList(){
        return dietDao.getAllDietList();
    }

    public void addDiet(DietEntity dietEntity){
        dietDao.insertDietData(dietEntity);
    }

    public void updateDiet(DietEntity dietEntity){
        dietDao.updateAnExistingRow(dietEntity.dietId, dietEntity.bmi_range, dietEntity.morning_drink,
                dietEntity.morning_breakfast, dietEntity.lunch, dietEntity.evening_breakfast,
                dietEntity.dinner, dietEntity.exercise);
    }

    public DietEntity selectDiet(String ageRange){
        return dietDao.selectDiet(ageRange);
    }
    public void deleteAllDiet(){
         dietDao.deleteAllData();
    }
}
