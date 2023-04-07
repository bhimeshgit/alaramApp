package com.example.myapplication.admin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.myapplication.R;
import com.example.myapplication.admin.Callback;
import com.example.myapplication.admin.DietListAdminActivity;
import com.example.myapplication.admin.adapter.DietListForAdminAdapter;
import com.example.myapplication.admin.viewmodel.DietAdminViewModel;
import com.example.myapplication.db.DietEntity;
import com.example.myapplication.utils.JsonParserVolley;
import com.example.myapplication.utils.WebUrl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DietListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Callback callback;
    FloatingActionButton mAddFab;
    private DietListForAdminAdapter dietListForAdminAdapter;
    private DietAdminViewModel dietAdminViewModel;
    ProgressDialog pDialog;
    public DietListFragment() {
        // Required empty public constructor
    }
    public DietListFragment(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dielt_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialteViews(view);
        registerListener();
        setDataToView();
        fetchAllDietFromServer();
    }

    private void setDataToView() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                dietAdminViewModel.getDietList();
//            }
//        });


        dietAdminViewModel.getDietListObserver().observe(getViewLifecycleOwner(), dietEntities -> {
            recyclerView.setAdapter(dietListForAdminAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            dietListForAdminAdapter.setDataToAdapter(dietEntities);
        });



    }

    private void registerListener() {
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.fragmentButtonClick(DietListFragment.this);
            }
        });
    }

    private void initialteViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        mAddFab = view.findViewById(R.id.add_fab);
        dietListForAdminAdapter = new DietListForAdminAdapter(getActivity(), callback);

        dietAdminViewModel = new ViewModelProvider(getActivity()).get(DietAdminViewModel.class);
    }

    public void fetchAllDietFromServer() {
        showProgressLoad();
        final JsonParserVolley jsonParserVolley = new JsonParserVolley(getActivity());
        String url = WebUrl.GET_ALL_DIET;
        jsonParserVolley.executeRequest(Request.Method.GET, url,new JsonParserVolley.VolleyCallback() {
                    @Override
                    public void getResponse(String response) {
                        hideProgressLoad();
                        try {
                            if (response != null){
                                if(response.trim().length() > 0){
                                    JSONObject jsonObject=new JSONObject(response);
                                    String success = jsonObject.get("success").toString();
                                    if (success != null && success.trim().equals("1")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        List<DietEntity> dietEntityList = new ArrayList<>();
                                        for(int i=0;i<jsonArray.length();i++) {
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

                                        }

                                        dietAdminViewModel.setDietList(dietEntityList);
                                    }
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

    private void showProgressLoad(){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void hideProgressLoad(){
        if (pDialog != null){
            pDialog.dismiss();
        }
    }

}