package com.naksh.renth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naksh.renth.Models.PropertyDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private MyAdapter2.OnItemClickListener mListener;
    private ArrayList<PropertyDetailsModel> list2;


    public MyAdapter2(Context context, ArrayList<PropertyDetailsModel> list2, MyAdapter2.OnItemClickListener listener) {
        this.context = context;
        this.list2 = list2;
        this.mListener = listener;
    }

    public MyAdapter2(Context context, ArrayList<PropertyDetailsModel> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_row2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PropertyDetailsModel property = list2.get(position);
        holder.tvItem.setText(property.getNameofproperty());
        holder.tvItem1.setText(String.valueOf(property.getPriceofproperty()));
        Picasso.get().load(property.getImageUrl()).into(holder.img2);
        // Bind property details to views in ViewHolder
        // For example:
        // holder.textView.setText(property.getPropertyName());
        // holder.imageView.setImageResource(property.getPropertyImageResourceId());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(property);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvItem1;
        ImageView img2;

        ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvItem1 = itemView.findViewById(R.id.tvItem1);
            img2 = itemView.findViewById(R.id.img2);
            // Initialize your views here
            // For example:
            // myImageView = itemView.findViewById(R.id.img2);
        }
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(PropertyDetailsModel property);
    }
}
