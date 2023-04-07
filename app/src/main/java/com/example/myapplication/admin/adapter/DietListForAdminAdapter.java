package com.example.myapplication.admin.adapter;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;

import com.example.myapplication.R;
import com.example.myapplication.admin.Callback;
import com.example.myapplication.db.DietEntity;


import java.util.List;


public class DietListForAdminAdapter extends ListAdapter<DietEntity, DietListForAdminAdapter.DietViewHolder> implements Filterable {


    private List<DietEntity> dietEntityList;
    private Context context;
    private Callback callback;
    public DietListForAdminAdapter(Context context, Callback callback) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.callback = callback;
    }

    public void setDataToAdapter(List<DietEntity> dietEntityList) {
        this.dietEntityList= dietEntityList;
        submitList(dietEntityList);
//        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_diet_list, parent, false);
        return new DietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {
        DietEntity dietEntity = (DietEntity) dietEntityList.get(position);
        holder.ageRangeTxt.setText(dietEntity.bmi_range);
    }

    class DietViewHolder extends RecyclerView.ViewHolder {

        TextView ageRangeTxt;
        public DietViewHolder(@NonNull View itemView) {
            super(itemView);
            ageRangeTxt = itemView.findViewById(R.id.ageRangeTxt);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(dietEntityList.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }

    public static final DiffUtil.ItemCallback<DietEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<DietEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull DietEntity oldInvoice, @NonNull DietEntity newInvoice) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DietEntity oldInvoice, @NonNull DietEntity newInvoice) {
            return false;
        }

    };
}
