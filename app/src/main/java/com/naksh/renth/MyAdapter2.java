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

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<String> list2;
    // Constructor
    private MyAdapter.OnItemClickListener mListener;

    public MyAdapter2(Context context, ArrayList<String> list2, MyAdapter.OnItemClickListener listener) {
        this.context = context;
        this.list2= list2;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context); // Initialize mInflater
    }
    public MyAdapter2(Context context, ArrayList<String> list2) {
        this.context = context;
        this.list2 = list2;
        this.mInflater = LayoutInflater.from(context); // Initialize mInflater
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_row2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list2.get(position);
//        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;
        //        TextView tvItem;
        ImageView myImageView2;

        ViewHolder(View itemView) {
            super(itemView);
//            myImageView = itemView.findViewById(R.id.img2);
////            // Set click listener to the ImageView
//            myImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item clicked
            int position = getAdapterPosition();
            // Ensure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Get the item at the position
                String item = list2.get(position);
                // Create an intent to navigate to the desired activity
//                Intent intent = new Intent(myContext, UpdateOwnerPropertyDetails.class);
//                // Optionally, pass data to the destination activity
//                intent.putExtra("ITEM_NAME", item);
//                // Start the activity
//                myContext.startActivity(intent);
            }
        }
    }
}
