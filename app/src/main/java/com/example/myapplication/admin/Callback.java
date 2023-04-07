package com.example.myapplication.admin;

import androidx.fragment.app.Fragment;

import com.example.myapplication.db.DietEntity;

public interface Callback {
    void fragmentButtonClick(Fragment fragment);
    default void onItemClick(DietEntity dietEntity){};
}
