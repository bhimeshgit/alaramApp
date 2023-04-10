package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.AppSettingSharePref;

public class SetTimingActivity extends AppCompatActivity {

    private EditText morDrinkHr, morDrinkMin,morBrHr,morBrMin, lunchHr, lunchMin, eveBrHr, eveBrMin, dinnerHr, dinnerMin;
    private Button setBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timing);
        initializeid();
    }

    private void initializeid() {
        morDrinkHr = findViewById(R.id.morDrinkHr);
        morDrinkMin = findViewById(R.id.morDrinkMin);
        morBrHr = findViewById(R.id.morBrHr);
        morBrMin = findViewById(R.id.morBrMin);
        lunchHr = findViewById(R.id.lunchHr);
        lunchMin = findViewById(R.id.lunchMin);
        eveBrHr = findViewById(R.id.eveBrHr);
        eveBrMin = findViewById(R.id.eveBrMin);
        dinnerHr = findViewById(R.id.dinnerHr);
        dinnerMin = findViewById(R.id.dinnerMin);
        setBtn = findViewById(R.id.setBtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });

    }

    private void setTime() {
        AppSettingSharePref appSet = AppSettingSharePref.getInstance(SetTimingActivity.this);
        appSet.setMorDrinkHr(Integer.parseInt(morDrinkHr.getText().toString()));
        appSet.setMorDrinkMin(Integer.parseInt(morDrinkMin.getText().toString()));
        appSet.setMorBreakHr(Integer.parseInt(morBrHr.getText().toString()));
        appSet.setMorBreakMin(Integer.parseInt(morBrMin.getText().toString()));
        appSet.setLunchHr(Integer.parseInt(lunchHr.getText().toString()));
        appSet.setLunchMin(Integer.parseInt(lunchMin.getText().toString()));
        appSet.setEveBreakHr(Integer.parseInt(eveBrHr.getText().toString()));
        appSet.setEveBreakMin(Integer.parseInt(eveBrMin.getText().toString()));
        appSet.setDinnerHr(Integer.parseInt(dinnerHr.getText().toString()));
        appSet.setDinnerMin(Integer.parseInt(dinnerMin.getText().toString()));
        Toast.makeText(SetTimingActivity.this, "Time set successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}