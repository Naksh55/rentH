package com.naksh.renth;

import android.content.Context;
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
import java.util.Collections; // Import Collections utility class

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<PropertyDetailsModel> list;
    private OnItemClickListener mListener;

    // Constructor with all arguments including listener
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
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PropertyDetailsModel testingModel = list.get(position);
        holder.nameofproperty.setText(testingModel.getNameofproperty());
        holder.priceofproperty.setText(String.valueOf(testingModel.getPriceofproperty()));
        Picasso.get().load(testingModel.getImageUrl()).into(holder.propertydp);

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(testingModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameofproperty, priceofproperty;
        ImageView propertydp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofproperty = itemView.findViewById(R.id.nameofproperty);
            priceofproperty = itemView.findViewById(R.id.priceofproperty);
            propertydp = itemView.findViewById(R.id.propertydp);
        }
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(PropertyDetailsModel item);
    }

    // Method to reverse the list
    public void reverseList() {
        Collections.reverse(list);
        notifyDataSetChanged();
    }
}
