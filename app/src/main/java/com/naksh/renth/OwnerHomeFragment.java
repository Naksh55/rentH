package com.naksh.renth;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

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
    private static final String ARG_OWNER_ID = "id";
    private static final String ARG_OWNER_NAME = "ownerName";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String ownerId;
    private static String ownerName;



    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ownerId Parameter 1.
     * @param param2 Parameter 2.
     * @param ownerName Parameter 2.
     * @return A new instance of fragment OwnerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerHomeFragment newInstance(String ownerId, String param2,String ownerName) {
        OwnerHomeFragment fragment = new OwnerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_OWNER_ID, ownerId);
        args.putString(ARG_OWNER_NAME, ownerName);


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
            ownerName = getArguments().getString(ARG_OWNER_NAME);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_home_fragment, container, false);
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
        Query query = FirebaseDatabase.getInstance().getReference().child("PropertyDetailsModel");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                if (isAdded() && getActivity() != null) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("DataSnapshot", "Value: " + dataSnapshot.getValue());

                        PropertyDetailsModel propertyModel = dataSnapshot.getValue(PropertyDetailsModel.class);
                        if (propertyModel != null) {
                            Log.d("PropertyModel", "Name: " + propertyModel.getNameofproperty() + ", Price: " + propertyModel.getPriceofproperty());

                            // Check if the property belongs to the desired owner
                            String propertyOwnerName = propertyModel.getoName();
                            if (propertyOwnerName != null && propertyOwnerName.equals(ownerName)) {
                                list.add(propertyModel);
                            } else {
//                                Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    myAdapter2.notifyDataSetChanged();
                } else {
                    Log.d("FragmentLifecycle", "Fragment is not attached or context is not available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });


        return view;
    }


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
                        Intent intent =new Intent();
                        intent.putExtra("property_id", propertyId);
                        intent.putExtra("parent_id", parentId);
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