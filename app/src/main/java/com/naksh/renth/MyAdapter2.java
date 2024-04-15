package com.naksh.renth;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        PropertyDetailsModel property = list2.get(holder.getAdapterPosition());
        holder.tvItem.setText(property.getNameofproperty());
        holder.tvItem1.setText(String.valueOf(property.getPriceofproperty()));
        Picasso.get().load(property.getImageUrl()).into(holder.img2);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img2.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();
                View v = dialogPlus.getHolderView();
                EditText name = v.findViewById(R.id.nameofpET);
                EditText discription = v.findViewById(R.id.descriptionofpET);
                EditText price = v.findViewById(R.id.priceofpET);
                EditText image = v.findViewById(R.id.imageurlET);
                Button btnupdate = v.findViewById(R.id.btnupdate);
                Button btndelete=v.findViewById(R.id.deletebtn);
                name.setText(property.getNameofproperty());
                discription.setText(property.getPropertydiscription());
                price.setText(String.valueOf(property.getPriceofproperty()));
                image.setText(property.getImageUrl());
                dialogPlus.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PropertyDetailsModel property = list2.get(holder.getAdapterPosition());

                        String propertyId = property.getPropertyId();
//
                        Map<String, Object> map = new HashMap<>();
                        map.put("nameofproperty", name.getText().toString());
                        map.put("propertydiscription", discription.getText().toString());
                        map.put("priceofproperty", price.getText().toString());
                        map.put("imageUrl", image.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("PropertyDetailsModel").child(
                                        propertyId).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(name.getContext(), "Data updated successfully.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(name.getContext(), "Data update failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = v.findViewById(R.id.nameofpET);

                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tvItem.getContext());
                builder.setTitle("Are you sure to delete?");
                builder.setMessage("Deleted data can't be restored");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PropertyDetailsModel property = list2.get(holder.getAdapterPosition());

                        String propertyId = property.getPropertyId();

                        FirebaseDatabase.getInstance().getReference().child("PropertyDetailsModel").child(propertyId).removeValue();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.tvItem.getContext(), "Canceled the deletion", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });





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
        Button btn,btn2;


        ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvItem1 = itemView.findViewById(R.id.tvItem1);
            img2 = itemView.findViewById(R.id.img2);
            btn = (Button)itemView.findViewById(R.id.updatebtn); // Replace "your_button_id" with the actual ID of your button
            btn2 = (Button)itemView.findViewById(R.id.deletebtn); // Replace "your_button_id" with the actual ID of your button


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
