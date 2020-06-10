package com.ziffytech.machinetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ziffytech.machinetest.R;
import com.ziffytech.machinetest.model.DataListModel;
import com.ziffytech.machinetest.network.interfaces.OnClickListenerRecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.AppointmentViewHolder> {
    private Context mContext;
    private ArrayList<DataListModel> mDatatmodelList;
    private OnClickListenerRecyclerView mOnClickListenerRecyclerView;


    public DataAdapter(Context mContext, ArrayList<DataListModel> datamodelList) {
        this.mContext = mContext;
        this.mDatatmodelList = datamodelList;
    }

    public void refreshList(ArrayList<DataListModel> mDatatmodelList) {
        this.mDatatmodelList = mDatatmodelList;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListenerRecyclerView onClickListenerRecyclerView) {
        this.mOnClickListenerRecyclerView = onClickListenerRecyclerView;
    }


    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppointmentViewHolder holder, final int position) {
        final DataListModel listmodel = mDatatmodelList.get(position);

        //holder.name.setText(listmodel.getUser_name());
        //holder.phone.setText(listmodel.getPhone());
        holder.name.setOnClickListener(v -> mOnClickListenerRecyclerView.onClickRecyclerView(v, position));
        holder.phone.setOnClickListener(v -> mOnClickListenerRecyclerView.onClickRecyclerView(v, position));


    }

    @Override
    public int getItemCount() {
        return mDatatmodelList.size();
    }

    public void filterList(ArrayList<DataListModel> filterdNames) {
        this.mDatatmodelList = filterdNames;
        notifyDataSetChanged();
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;

        AppointmentViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
        }
    }
}


