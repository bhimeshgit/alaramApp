package com.example.myapplication.admin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.admin.DietAdminRepository;
import com.example.myapplication.db.DietEntity;

import java.util.List;

public class DietAdminViewModel extends AndroidViewModel {
    private DietAdminRepository dietAdminRepository;
    private MutableLiveData<List<DietEntity>> dietListLiveData = new MutableLiveData<>();
    public DietAdminViewModel(@NonNull Application application) {
        super(application);
        dietAdminRepository = new DietAdminRepository(application);
    }

    public void addDiet(DietEntity dietEntity){
        dietAdminRepository.addDiet(dietEntity);
    }

    public void updateDiet(DietEntity dietEntity){
        dietAdminRepository.updateDiet(dietEntity);
    }

    public boolean isDietExist(String age_range){
        DietEntity dietEntity = dietAdminRepository.selectDiet(age_range);
        if (dietEntity == null){
            return false;
        } else {
            return true;
        }
    }

    public void getDietList(){
        List<DietEntity> dietEntityList = dietAdminRepository.getDietList();
        dietListLiveData.postValue(dietEntityList);
    }

    public void setDietList(List<DietEntity> dietEntityList){
        dietListLiveData.setValue(dietEntityList);
    }

    public LiveData<List<DietEntity>> getDietListObserver() {
        return dietListLiveData;
    }
}
