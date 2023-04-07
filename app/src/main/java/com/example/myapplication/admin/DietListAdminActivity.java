package com.example.myapplication.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.example.myapplication.R;
import com.example.myapplication.admin.fragment.DietAddFragment;
import com.example.myapplication.admin.fragment.DietListFragment;
import com.example.myapplication.db.DietEntity;
import com.example.myapplication.utils.JsonParserVolley;
import com.example.myapplication.utils.WebUrl;

import org.json.JSONArray;
import org.json.JSONObject;

public class DietListAdminActivity extends AppCompatActivity implements Callback {

    private FrameLayout frameLay;
    private DietAddFragment dietAddFragment;
    private DietListFragment dietListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_list_admin);
        getSupportActionBar().setTitle("Diet List");
        initializeId();
    }

    private void initializeId() {
        frameLay = findViewById(R.id.frameLay);
        dietAddFragment = new DietAddFragment(this);
        dietListFragment = new DietListFragment(this);
        loadFragment(dietListFragment);
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//frame_container is your layout name in xml file
        transaction.add(R.id.frameLay, fragment,"sampleTag");
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void fragmentButtonClick(Fragment fragment) {
        Intent intent = new Intent(DietListAdminActivity.this, DietAddUpdateActivity.class);
        startActivityForResult(intent,101);
    }





    @Override
    public void onItemClick(DietEntity dietEntity){
        dietAddFragment.setDietEntity(dietEntity);
        Intent intent = new Intent(DietListAdminActivity.this, DietAddUpdateActivity.class);
        intent.putExtra("updateMode","mode");
        intent.putExtra("diet_entity", dietEntity);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dietListFragment.fetchAllDietFromServer();
    }
}