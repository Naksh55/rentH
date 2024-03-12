package com.naksh.renth;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.naksh.renth.Models.PropertyDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<PropertyDetailsModel> list;
    // Define a listener interface
    public interface OnItemClickListener {
        void onItemClick(PropertyDetailsModel item);
    }
    private OnItemClickListener mListener;
    // Constructor to initialize the context and data list
    public MyAdapter(Context context, ArrayList<PropertyDetailsModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }
    public MyAdapter(Context context, ArrayList<PropertyDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_row,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PropertyDetailsModel testingModel=list.get(position);
        holder.nameofproperty.setText(testingModel.getNameofproperty());
        holder.priceofproperty.setText(String.valueOf(testingModel.getPriceofproperty()));
//        holder.age.setText(String.valueOf(testingModel.getAge()));
        Picasso.get().load(testingModel.getImageUrl()).into(holder.propertydp);
        holder.propertydp.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(testingModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameofproperty,priceofproperty;
        ImageView propertydp;

        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            nameofproperty=itemView.findViewById(R.id.nameofproperty);
            priceofproperty=itemView.findViewById(R.id.priceofproperty);
            propertydp = itemView.findViewById(R.id.propertydp);


        }
    }
}

