package com.example.myapplication.admin.adapter;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.DietEntity;


import java.util.List;


public class DietListForAdminAdapter extends ListAdapter<Object, RecyclerView.ViewHolder> implements Filterable {


    private List<DietEntity> dietEntityList;
    public DietListForAdminAdapter(Context context, List<DietEntity> list) {
        super(DIFF_CALLBACK);
        this.dietEntityList = list;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public static final DiffUtil.ItemCallback<Object> DIFF_CALLBACK = new DiffUtil.ItemCallback<Object>() {
        @Override
        public boolean areItemsTheSame(@NonNull Object oldInvoice, @NonNull Object newInvoice) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldInvoice, @NonNull Object newInvoice) {
            return false;
        }

    };
}
