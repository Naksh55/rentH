package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.List;

public class PropertyRecyclerActivityForUser extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    DatabaseReference database;
    ArrayList<PropertyDetailsModel> list;
    BottomNavigationView bottomNavigationView;
    UserHomeFrament userHomeFrament=new UserHomeFrament();
    UserProfileFragment userProfileFragment=new UserProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_property_recycler_for_user);
        recyclerView = findViewById(R.id.recyclerView);
        database= FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");
        recyclerView.setHasFixedSize(true);
//        adapter = new MyAdapter(this, getData());
        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PropertyDetailsModel testingModel=dataSnapshot.getValue(PropertyDetailsModel.class);
                    list.add(testingModel);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter=new MyAdapter(this, list, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PropertyDetailsModel item) {
                // Retrieve the property ID for the clicked item
                String propertyId = item.getPropertyId();

                String propertyName = item.getNameofproperty(); // Accessing the nameofproperty directly from the clicked item

                // Show the property name in a toast
//                Toast.makeText(PropertyRecyclerActivityForUser.this, propertyName, Toast.LENGTH_SHORT).show();

                retrieveParentInfoFromDatabase(propertyName, propertyId);
                // Check if propertyId is not null or empty
//                if (propertyId != null && !propertyId.isEmpty()) {
//                    // Create an intent to start the BookingScreen activity
//                    Intent intent = new Intent(PropertyRecyclerActivityForUser.this, BookingScreen.class);
//
//                    // Put the propertyId as an extra in the intent
//                    intent.putExtra("property_id", propertyId);
//
//                    // Start the next activity with the intent
//                    startActivity(intent);
//                } else {
//                    // Handle the case where propertyId is null or empty
//                    Log.e("PropertyRecyclerActivity", "Property ID is null or empty");
//                }
            }


            private void retrieveParentInfoFromDatabase(String propertyName, String propertyId) {
                // Get a reference to your Firebase database
//                Toast.makeText(PropertyRecyclerActivityForUser.this, "Clicked", Toast.LENGTH_SHORT).show();

                DatabaseReference parentRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");

                // Assuming you have a child node named "properties" where property names are stored
                // and you want to search within this node for the matching property name
                Query query = parentRef.orderByChild("nameofproperty").equalTo(propertyName);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Loop through the results, assuming there might be multiple matches (though ideally, it should be unique)
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Retrieve the parent ID from the matching record
                                String parentId = snapshot.getKey();

//                                Toast.makeText(PropertyRecyclerActivityForUser.this, parentId, Toast.LENGTH_SHORT).show();

                                // Assuming you have retrieved additional parent information
                                String parentInfo = "Parent information for property: " + propertyName;

                                // Create an intent to start the BookingScreen activity
                                Intent intent = new Intent(PropertyRecyclerActivityForUser.this, BookingScreen.class);

                                // Put the propertyId, parentId, and parentInfo as extras in the intent
                                intent.putExtra("property_id", propertyId);
                                intent.putExtra("parent_id", parentId);
                                intent.putExtra("parent_info", parentInfo);

                                // Start the next activity with the intent
                                startActivity(intent);

                                // Assuming we only need one match, break out of the loop
                                break;
                            }
                        } else {
                            // Handle the case where no matching parent is found
                            Log.e("PropertyRecyclerActivity", "No parent found for property: " + propertyName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors
                        Log.e("PropertyRecyclerActivity", "Database query cancelled.", databaseError.toException());
                    }
                });
            }

        });
        recyclerView.setAdapter(myAdapter);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    return true;
                } else if(item.getItemId()==R.id.profile) {
                    Intent intent=new Intent(PropertyRecyclerActivityForUser.this,UserProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                else{
                    // Handle other menu items if needed
                    return false;
                }
            }
        });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("House 1");
        return data;
    }
}