package com.naksh.renth;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import androidx.appcompat.widget.SearchView;

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
    String userEmail;
    String userPhoneno;
//hi
    private  SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_recycler_for_user);
        invalidateOptionsMenu();
        searchView=findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("PropertyDetailsModel");
        databaseReference = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/").getReference("OwnerPersonalDetailsModel");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent i = getIntent();
        if (i != null) {
            userId = i.getStringExtra("user_id");
            userName = i.getStringExtra("userName");
            ownerId = i.getStringExtra("id");
            userId = i.getStringExtra("user_id");
            userEmail = i.getStringExtra("userEmail");
            userPhoneno = i.getStringExtra("phoneno");
//            Toast.makeText(this,"phoneno="+userPhoneno, Toast.LENGTH_SHORT).show();


        } else {
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
                    intent.putExtra("user_id", userId);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("phoneno", userPhoneno);
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
                        String ownerId = snapshot.child("ownerId").getValue(String.class); // Assuming email is stored under "email" child node
                        Intent intent = new Intent(PropertyRecyclerActivityForUser.this, BookingScreen.class);
                        intent.putExtra("property_id", propertyId);
                        intent.putExtra("parent_id", parentId);
                        intent.putExtra("user_id", userId);
                        intent.putExtra("userName", userName);
                        intent.putExtra("owner_id", ownerId);
                        intent.putExtra("userEmail",userEmail);
                        intent.putExtra("phoneno",userPhoneno);
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


    private void filter(String query) {
        ArrayList<PropertyDetailsModel> filteredList = new ArrayList<>();
        for (PropertyDetailsModel item : list) {
            // Check if the state is not null before proceeding
            if (item.getState() != null && item.getState().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        // Update the dataset with the filtered results
        myAdapter.updateList(filteredList);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("SearchDebug", "onCreateOptionsMenu called");
        Toast.makeText(this, "onCreateOptionsMenu called", Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SearchDebug", "onQueryTextSubmit: " + query);
                Toast.makeText(PropertyRecyclerActivityForUser.this, "Query submitted: " + query, Toast.LENGTH_SHORT).show();
                filter(query);
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchDebug", "onQueryTextChange: " + newText);
                Toast.makeText(PropertyRecyclerActivityForUser.this, "Query changed: " + newText, Toast.LENGTH_SHORT).show();
                filter(newText);
                txtSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");

        // Query the database to find properties with state names that start with the search string
        Query query = databaseReference.orderByChild("state")
                .startAt(str)
                .endAt(str + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<PropertyDetailsModel> filteredList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PropertyDetailsModel property = snapshot.getValue(PropertyDetailsModel.class);
                    if (property != null && property.getState() != null && property.getState().toLowerCase().startsWith(str.toLowerCase())) {
                        filteredList.add(property);
                        // Display property details in a toast
                    }
                    else{
                        Toast.makeText(PropertyRecyclerActivityForUser.this, "data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                // Update the dataset with the filtered results
                myAdapter.updateList(filteredList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PropertyRecyclerActivity", "Database query cancelled.", databaseError.toException());
            }
        });
    }
}
