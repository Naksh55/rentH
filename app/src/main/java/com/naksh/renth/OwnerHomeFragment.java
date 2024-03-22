package com.naksh.renth;

import static android.content.Intent.getIntent;

import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.naksh.renth.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link OwnerHomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class OwnerHomeFragment extends Fragment {
    private RecyclerView recyclerView2;
//    DatabaseReference databaseReference;
    MyAdapter myAdapter2;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String>list;
String ownerId;

    private com.naksh.renth.MyAdapter2 adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerHomeFragment newInstance(String param1, String param2) {
        OwnerHomeFragment fragment = new OwnerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_home_fragment, container, false);

//        assert getArguments() != null;
//         ownerId = getArguments().getString("id");

//        Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
        Bundle args = getArguments();
        if (args != null && args.containsKey("id")) {
            ownerId = args.getString("id");
//            Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getContext(), "Owner ID is not available", Toast.LENGTH_SHORT).show();
        }

        DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference().child("PropertyDetailsModel").child("id");

//        ImageView imageView = view.findViewById(R.id.addpropertyimg);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Check if getArguments() is not null and contains "id" key
//                Bundle args = getArguments();
//                if (args != null && args.containsKey("id")) {
//                    String ownerId = args.getString("id");
//                    Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
//                    // Open PropertyDetails activity and pass the property ID
//                    Intent intent = new Intent(getActivity(), PropertyDetails.class);
//                    intent.putExtra("id", ownerId);
//                    startActivity(intent);
//                } else {
//                    // Handle the case where ownerId is not available
//                    Toast.makeText(getContext(), "Owner ID is not available", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



        ArrayList<String> data = new ArrayList<>();
        data.add("House 1");

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        MyAdapter2 adapter = new MyAdapter2(getActivity(), data);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        return view;


    }
}