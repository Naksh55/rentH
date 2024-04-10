package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;

import java.util.ArrayList;

public class PropertyRecyclerActivityForUser extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    DatabaseReference database;
    DatabaseReference databaseReference;

    ArrayList<PropertyDetailsModel> list;
    BottomNavigationView bottomNavigationView;
    String userId;
    String ownerId;

    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_recycler_for_user);

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");
        databaseReference = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("OwnerPersonalDetailsModel");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent i=getIntent();
        if(i!=null) {
           userId=  i.getStringExtra("user_id");
            userName=  i.getStringExtra("userName");
            ownerId=  i.getStringExtra("id");

//            Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();


        }
        else{
            Toast.makeText(this, "intent is null", Toast.LENGTH_SHORT).show();
        }
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PropertyDetailsModel item) {
                String propertyName = item.getNameofproperty(); // Accessing the nameofproperty directly from the clicked item
                retrieveParentInfoFromDatabase(propertyName, item.getPropertyId()); // Pass propertyId as well
            }
        });

        recyclerView.setAdapter(myAdapter);

//        Query query = database.orderByChild("ownerId").equalTo(ownerId);
//
//        query.addValueEventListener(new ValueEventListener() {
//                                        @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear(); // Clear the list before adding new data
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
//                    list.add(testingModel);
//                }
//                myAdapter.reverseList(); // Call this method to reverse the list
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("PropertyRecyclerActivity", "Database error: " + error.getMessage());
//            }
//        });
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            list.clear(); // Clear the list before adding new data
//                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                                PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
//                                                list.add(testingModel);
//                                            }
//
//                                            // Log the number of properties retrieved
//                                            Log.d("PropertyRecyclerActivity", "Number of properties retrieved: " + list.size());
//
//                                            myAdapter.reverseList(); // Call this method to reverse the list
//                                            myAdapter.notifyDataSetChanged();
//                                        }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PropertyDetailsModel testingModel = dataSnapshot.getValue(PropertyDetailsModel.class);
                    list.add(testingModel);
                    myAdapter.reverseList(); // Call this method to reverse the list
                    myAdapter.notifyDataSetChanged();
                }
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
                });
            bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(PropertyRecyclerActivityForUser.this, UserProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });
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

                        Intent intent = new Intent(PropertyRecyclerActivityForUser.this, BookingScreen.class);
                        intent.putExtra("property_id", propertyId);
                        intent.putExtra("parent_id", parentId);
                        intent.putExtra("user_id",userId);
                        intent.putExtra("userName",userName);

                        Toast.makeText(PropertyRecyclerActivityForUser.this, "userId= " + userId, Toast.LENGTH_SHORT).show();
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
