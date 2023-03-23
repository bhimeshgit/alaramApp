package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;

public class DietListAdminActivity extends AppCompatActivity {

    private RecyclerView dietListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_list_admin);
        generateId();
    }

    private void generateId() {
        dietListRecyclerView = findViewById(R.id.dietListRecyclerView);
    }
}