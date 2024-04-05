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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    MyAdapter2 myAdapter2;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PropertyDetailsModel> list;

    DatabaseReference propertyRef;

    private com.naksh.renth.MyAdapter2 adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_OWNER_ID = "owner_id";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String ownerId;


    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ownerId Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerHomeFragment newInstance(String ownerId, String param2) {
        OwnerHomeFragment fragment = new OwnerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_OWNER_ID, ownerId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            ownerId = getArguments().getString(ARG_OWNER_ID);

        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_owner_home_fragment, container, false);
//        Toast.makeText(requireContext(), "id="+ownerId, Toast.LENGTH_SHORT).show();
//        recyclerView2 = view.findViewById(R.id.recyclerView2);
//        list = new ArrayList<>();
//        myAdapter2 = new MyAdapter2(getActivity(), list, new MyAdapter2.OnItemClickListener() {
//            @Override
//            public void onItemClick(PropertyDetailsModel item) {
//                String propertyName = item.getNameofproperty(); // Accessing the nameofproperty directly from the clicked item
//                retrieveParentInfoFromDatabase(propertyName, item.getPropertyId()); // Pass propertyId as well
//            }
//        });
//
//        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView2.setAdapter(myAdapter2);
//
//        // Here, you should use propertyRef to query properties belonging to the owner
//        Query query = FirebaseDatabase.getInstance().getReference()
//                .child("PropertyDetailsModel")
//                .orderByChild("id")
//                .equalTo(ownerId);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear(); // Clear the list before adding new data
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
//                    list.add(testingModel);
//                }
//                myAdapter2.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("OwnerHomeFragment", "Database error: " + error.getMessage());
//            }
//        });
//
//        return view;
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_home_fragment, container, false);
        Toast.makeText(requireContext(), "ownerId=   "+ownerId, Toast.LENGTH_LONG).show();
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        list = new ArrayList<>();
        myAdapter2 = new MyAdapter2(getActivity(), list, new MyAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(PropertyDetailsModel item) {
                String propertyName = item.getNameofproperty();
                retrieveParentInfoFromDatabase(propertyName, item.getPropertyId());
            }
        });

        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setAdapter(myAdapter2);

        // Query to fetch all properties
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("PropertyDetailsModel");

        query.addValueEventListener(new ValueEventListener() {
            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear(); // Clear the list before adding new data
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
//                    // Check if the property belongs to the owner
//                    assert testingModel != null;
//                    if (testingModel.getPropertyId().equals(ownerId)) {
//                        list.add(testingModel);
//                    }
//                    else{
//                        Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                myAdapter2.notifyDataSetChanged();
//            }
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
                    // Check if the ownerId retrieved from the PropertyDetailsModel is not null
                    if (testingModel != null && testingModel.getId() != null) {
                        // Check if the ownerId matches the ownerId of the current user
                        if (ownerId != null && ownerId.equals(testingModel.getId())) {
                            list.add(testingModel);
                        }
                    }
                }
                myAdapter2.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OwnerHomeFragment", "Database error: " + error.getMessage());
            }
        });

        return view;
    }




//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_owner_home_fragment, container, false);
//
////        assert getArguments() != null;
////         ownerId = getArguments().getString("id");
//
////        Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
//        Bundle args = getArguments();
//        if (args != null && args.containsKey("id")) {
//            ownerId = args.getString("id");
////            Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
//        } else {
////            Toast.makeText(getContext(), "Owner ID is not available", Toast.LENGTH_SHORT).show();
//        }
//
//         propertyRef = FirebaseDatabase.getInstance().getReference().child("PropertyDetailsModel").child("id");
//
////        ImageView imageView = view.findViewById(R.id.addpropertyimg);
////        imageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // Check if getArguments() is not null and contains "id" key
////                Bundle args = getArguments();
////                if (args != null && args.containsKey("id")) {
////                    String ownerId = args.getString("id");
////                    Toast.makeText(getContext(), ownerId, Toast.LENGTH_SHORT).show();
////                    // Open PropertyDetails activity and pass the property ID
////                    Intent intent = new Intent(getActivity(), PropertyDetails.class);
////                    intent.putExtra("id", ownerId);
////                    startActivity(intent);
////                } else {
////                    // Handle the case where ownerId is not available
////                    Toast.makeText(getContext(), "Owner ID is not available", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//
//
//
//        list= new ArrayList<>();
//        myAdapter2 = new MyAdapter(this, list, new MyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(PropertyDetailsModel item) {
//                String propertyName = item.getNameofproperty(); // Accessing the nameofproperty directly from the clicked item
//                retrieveParentInfoFromDatabase(propertyName, item.getPropertyId()); // Pass propertyId as well
//            }
//        });
//
//
//        // Initialize RecyclerView and adapter
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
//        MyAdapter2 adapter = new MyAdapter2(getActivity(), list);
//
//        // Set the layout manager for the RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        // Set the adapter to the RecyclerView
//        recyclerView.setAdapter(adapter);
//        propertyRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear(); // Clear the list before adding new data
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
//                    list.add(testingModel);
//                }
//                myAdapter2.reverseList(); // Call this method to reverse the list
//                myAdapter2.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("PropertyRecyclerActivity", "Database error: " + error.getMessage());
//            }
//        });
//
//        return view;
//
//
//    }



    private void retrieveParentInfoFromDatabase(String propertyName, String propertyId) {
        DatabaseReference parentRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");
        Query query = parentRef.orderByChild("nameofproperty").equalTo(propertyName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String parentId = snapshot.getKey();
                        String propertyId = snapshot.getKey();

                        Intent intent = new Intent(requireContext(), BookingScreen.class);
                        intent.putExtra("property_id", propertyId);
                        intent.putExtra("parent_id", parentId);
//                        Toast.makeText(PropertyRecyclerActivityForUser.this, "Property ID: " + propertyId, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(PropertyRecyclerActivityForUser.this, "Parent ID: " + parentId, Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                        break;
                    }
                } else {
                    Log.e("PropertyRecyclerActivity", "No parent found for property: " + propertyName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PropertyRecyclerActivity", "Database query cancelled.", databaseError.toException());
            }
        });
    }

}