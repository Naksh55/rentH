package com.naksh.renth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections; // Import Collections utility class

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private  FirebaseRecyclerOptions<PropertyDetailsModel> options;
    private Context context;
    private ArrayList<PropertyDetailsModel> list;
    private OnItemClickListener mListener;
private ValueEventListener valueEventListener;
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
    public MyAdapter(@NonNull FirebaseRecyclerOptions<PropertyDetailsModel> options) {
        this.options=options;

    }

    public MyAdapter(ValueEventListener valueEventListener, ArrayList<PropertyDetailsModel> list) {
        this.valueEventListener=valueEventListener;
    }
    public void updateList(ArrayList<PropertyDetailsModel> newList) {
        list = newList;
        notifyDataSetChanged();
        Log.d("Adapter", "Dataset size after update: " + getItemCount());
//        Toast.makeText(context, getItemCount(), Toast.LENGTH_SHORT).show();

    }
    public void updateOptions(@NonNull FirebaseRecyclerOptions<PropertyDetailsModel> options) {
        this.options = options;
        notifyDataSetChanged();
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
        holder.stateofproperty.setText(String.valueOf(testingModel.getState()));
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_four));
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
        TextView nameofproperty, priceofproperty,stateofproperty;
        ImageView propertydp;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofproperty = itemView.findViewById(R.id.nameofproperty);
            priceofproperty = itemView.findViewById(R.id.priceofproperty);
            propertydp = itemView.findViewById(R.id.propertydp);
            stateofproperty = itemView.findViewById(R.id.stateofproperty);
            cardView = itemView.findViewById(R.id.cardview); // Initialize CardView attribute


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

//package com.naksh.renth;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.naksh.renth.Models.PropertyDetailsModel;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class MyAdapter extends FirebaseRecyclerAdapter<PropertyDetailsModel, MyAdapter.MyViewHolder> {
//
//    private Context context;
//    private OnItemClickListener mListener;
//    private ArrayList<PropertyDetailsModel> list;
//
//    public MyAdapter(@NonNull FirebaseRecyclerOptions<PropertyDetailsModel> options) {
//        super(options);
//
//    }
//    // Constructor with all arguments including listener
//    public MyAdapter(Context context,FirebaseRecyclerOptions<PropertyDetailsModel> options,OnItemClickListener listener) {
//        super(options);
//        this.context = context;
//        this.mListener = listener;
//    }
//
//    public MyAdapter(FirebaseRecyclerOptions<PropertyDetailsModel> options, OnItemClickListener onItemClickListener) {
//        super(options);
//        this.mListener = onItemClickListener;
//
//
//    }
//
////    public MyAdapter(Context context, ArrayList<PropertyDetailsModel> list) {
////        this.context = context;
////        this.list = list;
////    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull PropertyDetailsModel model) {
//        holder.bind(model);
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView nameofproperty, priceofproperty, stateofproperty;
//        ImageView propertydp;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameofproperty = itemView.findViewById(R.id.nameofproperty);
//            priceofproperty = itemView.findViewById(R.id.priceofproperty);
//            propertydp = itemView.findViewById(R.id.propertydp);
//            stateofproperty = itemView.findViewById(R.id.stateofproperty);
//        }
//
//        public void bind(PropertyDetailsModel model) {
//            nameofproperty.setText(model.getNameofproperty());
//            priceofproperty.setText(String.valueOf(model.getPriceofproperty()));
//            stateofproperty.setText(model.getState());
//            Picasso.get().load(model.getImageUrl()).into(propertydp);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && mListener != null) {
//                        mListener.onItemClick(model);
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(PropertyDetailsModel item);
//    }
//}
