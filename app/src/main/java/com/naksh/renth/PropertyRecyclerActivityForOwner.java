package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;

import java.util.ArrayList;

public class PropertyRecyclerActivityForOwner extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private MyAdapter2 myAdapter2;
    DatabaseReference databaseReference;
    DatabaseReference database;
    String ownerId;


    ArrayList<PropertyDetailsModel> list;
    BottomNavigationView bottomNavigationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_recycler_for_owner);
        Intent intent=getIntent();
        if (intent!=null){
            intent.getStringExtra("id");
        }
        recyclerView2 = findViewById(R.id.recyclerView2);
        database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");
        databaseReference = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("OwnerPersonalDetailsModel");
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(myAdapter2);

        list = new ArrayList<>();
        myAdapter2 = new MyAdapter2(this, list, new MyAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(PropertyDetailsModel item) {
                String propertyName = item.getNameofproperty(); // Accessing the nameofproperty directly from the clicked item
                retrieveParentInfoFromDatabase(propertyName, item.getPropertyId()); // Pass propertyId as well
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

                        Intent intent = new Intent(PropertyRecyclerActivityForOwner.this, LoginScreen.class);
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
