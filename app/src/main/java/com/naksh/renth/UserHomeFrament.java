package com.naksh.renth;

import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFrament extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PropertyDetailsModel>list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserHomeFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomeFrament newInstance(String param1, String param2) {
        UserHomeFrament fragment = new UserHomeFrament();
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
        View view = inflater.inflate(R.layout.fragment_user_home_frament, container, false);

        List<String> data = new ArrayList<>();
        data.add("House 1");
        data.add("Item 2");
        data.add("Item 3");
        data.add("Item 4");
        data.add("Item 5");
        linearLayoutManager=new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        databaseReference= FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");
        recyclerView.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(getActivity(), list);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(linearLayoutManager); // Use the initialized layout manager

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        myAdapter=new MyAdapter(requireActivity(),list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PropertyDetailsModel propertyDetailsModel=dataSnapshot.getValue(PropertyDetailsModel.class);
                    list.add(propertyDetailsModel);


                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;



    }
}