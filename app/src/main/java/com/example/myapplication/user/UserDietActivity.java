package com.example.myapplication.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.myapplication.R;
import com.example.myapplication.admin.Callback;
import com.example.myapplication.admin.DietAddUpdateActivity;
import com.example.myapplication.admin.DietAdminRepository;
import com.example.myapplication.admin.SetTimingActivity;
import com.example.myapplication.admin.viewmodel.DietAdminViewModel;
import com.example.myapplication.db.DietEntity;
import com.example.myapplication.db.User;
import com.example.myapplication.receiver.AlarmReceiver;
import com.example.myapplication.utils.AppSettingSharePref;
import com.example.myapplication.utils.JsonParserVolley;
import com.example.myapplication.utils.WebUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserDietActivity extends AppCompatActivity {

    private AutoCompleteTextView ageAutoTxtView;
    private EditText edt_water, edt_morning, edt_morning_break, edt_lunch, edt_eve_break, edt_dinner, edt_exercise;

    private Button alarmBtn, btnSetTime;
    private ProgressDialog pDialog;
    private float userBmi = 0;
    private String userBmiRange = "";
    private DietAdminRepository dietAdminRepository;
    private  AppSettingSharePref appSeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_diet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diet Recommended");
        ageAutoTxtView = findViewById(R.id.ageAutoTxtView);
        edt_water= findViewById(R.id.edt_water);
        edt_morning= findViewById(R.id.edt_morning);
        edt_morning_break= findViewById(R.id.edt_morning_break);
        edt_lunch= findViewById(R.id.edt_lunch);
        edt_eve_break= findViewById(R.id.edt_eve_break);
        edt_dinner= findViewById(R.id.edt_dinner);
        edt_exercise = findViewById(R.id.edt_exercise);
        alarmBtn = findViewById(R.id.btnAdd);
        dietAdminRepository = new DietAdminRepository(this);
        appSeting = AppSettingSharePref.getInstance(UserDietActivity.this);

        if (getIntent().hasExtra("bmi")){
            userBmi = getIntent().getFloatExtra("bmi", 0);
        }
        setAlarmButton();
        fetchDietFromServer();

    }

    private void setAlarmButton() {
        if(AppSettingSharePref.getInstance(this).getAlarm() == 1){
            alarmBtn.setText("Cancel Alarm");
        } else{
            alarmBtn.setText("Start Alarm");
        }
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmBtn.getText().equals("Start Alarm")){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            DietEntity dietEntity = dietAdminRepository.selectDiet(userBmiRange);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAllAlarmAsPerDiet(dietEntity);
                                }
                            });
                        }
                    });
                }else{
                    AppSettingSharePref.getInstance(UserDietActivity.this).setAlarm(0);

                    AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                    PendingIntent pIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    aManager.cancel(pIntent1);
                    PendingIntent pIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    aManager.cancel(pIntent2);
                    PendingIntent pIntent3 = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    aManager.cancel(pIntent3);
                    PendingIntent pIntent4 = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    aManager.cancel(pIntent4);
                    PendingIntent pIntent5 = PendingIntent.getBroadcast(getApplicationContext(), 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    aManager.cancel(pIntent5);
                    Toast.makeText(UserDietActivity.this, "Alarm cancelled successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void setAllAlarmAsPerDiet(DietEntity dietEntity) {
       setMorningDrinkAlarm(dietEntity);
       setLunchAlarm(dietEntity);
       setMorningBreakfastAlarm(dietEntity);
       setEveningBreakfastAlarm(dietEntity);
       setDinnerAlarm(dietEntity);
        AppSettingSharePref.getInstance(this).setAlarm(1);
        Toast.makeText(UserDietActivity.this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setMorningDrinkAlarm(DietEntity dietEntity) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Morning Drink");
        intent.putExtra("message",dietEntity.morning_drink);
        PendingIntent pendingIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, appSeting.getMorDrinkHr());  // set hour
        cal.set(Calendar.MINUTE, appSeting.getMorDrinkMin());          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }

    private void setMorningBreakfastAlarm(DietEntity dietEntity) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Morning Breakfast");
        intent.putExtra("message",dietEntity.morning_breakfast);
        PendingIntent pendingIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, appSeting.getMorBreakHr());  // set hour
        cal.set(Calendar.MINUTE, appSeting.getMorBreakMin());          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }
    private void setLunchAlarm(DietEntity dietEntity) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Lunch");
        intent.putExtra("message",dietEntity.lunch);
        PendingIntent pendingIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, appSeting.getLunchHr());  // set hour
        cal.set(Calendar.MINUTE, appSeting.getLunchMin());          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }
    private void setEveningBreakfastAlarm(DietEntity dietEntity) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Evening Breakfast");
        intent.putExtra("message",dietEntity.evening_breakfast);
        PendingIntent pendingIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, appSeting.getEveBreakHr());  // set hour
        cal.set(Calendar.MINUTE, appSeting.getEveBreakMin());          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }
    private void setDinnerAlarm(DietEntity dietEntity) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Dinner");
        intent.putExtra("message",dietEntity.dinner);
        PendingIntent pendingIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 4, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, appSeting.getDinnerHr());  // set hour
        cal.set(Calendar.MINUTE, appSeting.getDinnerMin());          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }
    public void fetchDietFromServer() {
        String bmiRange = "";
        if (userBmi < 18.5){
            bmiRange = "< 18.5";
        } else if(userBmi>=18.5 && userBmi<=24.9){
            bmiRange = "18.5 to 24.9";
        } else if(userBmi>=25.0 && userBmi<=29.9){
            bmiRange =  "25.0 to 29.9";
        } else if(userBmi>=30.0){
            bmiRange =  ">=30.0";
        }
        userBmiRange = bmiRange;
        showProgressLoad();
        final JsonParserVolley jsonParserVolley = new JsonParserVolley(UserDietActivity.this);
        jsonParserVolley.addParameter("bmi_range",bmiRange);
        String url = WebUrl.GET_USER_DIET;
        jsonParserVolley.executeRequest(Request.Method.POST, url,new JsonParserVolley.VolleyCallback() {
                    @Override
                    public void getResponse(String response) {
                        hideProgressLoad();
                        try {
                            if (response != null){
                                if(response.trim().length() > 0){
                                    setDietResponse(response);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("stest","response="+e.getMessage());
                        }
                    }
                }
        );

    }

    private void setDietResponse(String response) {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        dietAdminRepository.deleteAllDiet();
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.get("success").toString();
                        List<DietEntity> dietEntityList = new ArrayList<>();
                        if (success != null && success.trim().equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                DietEntity dietEntity = new DietEntity();
                                dietEntity.bmi_range = jsonObject1.get("bmi_range").toString();
                                dietEntity.water = jsonObject1.get("water").toString();
                                dietEntity.morning_drink = jsonObject1.get("morning_drink").toString();
                                dietEntity.morning_breakfast = jsonObject1.get("morning_break").toString();
                                dietEntity.lunch = jsonObject1.get("lunch").toString();
                                dietEntity.evening_breakfast = jsonObject1.get("evening_break").toString();
                                dietEntity.dinner = jsonObject1.get("dinner").toString();
                                dietEntity.exercise = jsonObject1.get("exercise").toString();
                                dietEntityList.add(dietEntity);
                                dietAdminRepository.addDiet(dietEntity);
                            }
                            AppSettingSharePref appSet = AppSettingSharePref.getInstance(UserDietActivity.this);
                            JSONArray jsonArrayTime = jsonObject.getJSONArray("time");
                            for (int i = 0; i < jsonArrayTime.length(); i++) {
                                JSONObject jsonObject1 = jsonArrayTime.getJSONObject(i);
                                appSet.setMorDrinkHr(Integer.parseInt(jsonObject1.get("mor_drink_hr").toString()));
                                appSet.setMorDrinkMin(Integer.parseInt(jsonObject1.get("mor_drink_min").toString()));
                                appSet.setMorBreakHr(Integer.parseInt(jsonObject1.get("mor_break_hr").toString()));
                                appSet.setMorBreakMin(Integer.parseInt(jsonObject1.get("mor_break_min").toString()));
                                appSet.setLunchHr(Integer.parseInt(jsonObject1.get("lunch_hr").toString()));
                                appSet.setLunchMin(Integer.parseInt(jsonObject1.get("lunch_min").toString()));
                                appSet.setEveBreakHr(Integer.parseInt(jsonObject1.get("eve_br_hr").toString()));
                                appSet.setEveBreakMin(Integer.parseInt(jsonObject1.get("eve_br_min").toString()));
                                appSet.setDinnerHr(Integer.parseInt(jsonObject1.get("dineer_hr").toString()));
                                appSet.setDinnerMin(Integer.parseInt(jsonObject1.get("dinner_min").toString()));
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(dietEntityList.size() > 0) {
                                    setAllFieldsForUpdate(dietEntityList.get(0));
                                }
                            }
                        });
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAllFieldsForUpdate(DietEntity dietEntityReceived) {
        ageAutoTxtView.setText(dietEntityReceived.bmi_range);
        edt_water.setText(dietEntityReceived.water);
        edt_morning.setText(dietEntityReceived.morning_drink);
        edt_morning_break.setText(dietEntityReceived.morning_breakfast);
        edt_lunch.setText(dietEntityReceived.lunch);
        edt_eve_break.setText(dietEntityReceived.evening_breakfast);
        edt_dinner.setText(dietEntityReceived.dinner);
        edt_exercise.setText(dietEntityReceived.exercise);

        ageAutoTxtView.setEnabled(false);
        edt_water.setEnabled(false);
        edt_morning.setEnabled(false);
        edt_morning_break.setEnabled(false);
        edt_lunch.setEnabled(false);
        edt_eve_break.setEnabled(false);
        edt_dinner.setEnabled(false);
        edt_exercise.setEnabled(false);

        edt_water.setTextColor(Color.BLACK);
        edt_morning.setTextColor(Color.BLACK);
        edt_morning_break.setTextColor(Color.BLACK);
        edt_lunch.setTextColor(Color.BLACK);
        edt_eve_break.setTextColor(Color.BLACK);
        edt_dinner.setTextColor(Color.BLACK);
        edt_exercise.setTextColor(Color.BLACK);


    }
    private void showProgressLoad(){
        pDialog = new ProgressDialog(UserDietActivity.this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void hideProgressLoad(){
        if (pDialog != null){
            pDialog.dismiss();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}