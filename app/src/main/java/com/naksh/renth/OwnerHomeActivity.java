package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.naksh.renth.OwnerHomeFragment;
import com.naksh.renth.R;

import java.util.ArrayList;
import java.util.List;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    //    TextView ownerphoneno;
    private RecyclerView recyclerView2;

    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    String ownerId;
    String propertyId,parentId;
    String ownerphoneno;
    String notificationMessage;
    String userName;
    String userId;

    TextView ownerNameTextView, ownerPhoneNumberTextView, ownerEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);
        recyclerView2 = findViewById(R.id.recyclerView2);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            ownerId = intent.getStringExtra("id");
            userId=intent.getStringExtra("user_id");
            notificationMessage=intent.getStringExtra("notification_message");
            userName=intent.getStringExtra("userName");
            Toast.makeText(OwnerHomeActivity.this, "userId="+userId, Toast.LENGTH_SHORT).show();
            Toast.makeText(OwnerHomeActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
            Toast.makeText(OwnerHomeActivity.this, "notification="+notificationMessage, Toast.LENGTH_LONG).show();
            // Pass the ID to the fragment
            loadHomeFragmentWithOwnerId(ownerId);
            retrievePropertiesForOwner(ownerId);

        }
        else{
            Toast.makeText(this, "intent is null", Toast.LENGTH_SHORT).show();
        }
            bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
            bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    if (item.getItemId() == R.id.addproperty) {
                        if (ownerId != null) {
//                            Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();

                            // Create the intent to start PropertyDetails activity
                            Intent intent = new Intent(OwnerHomeActivity.this, PropertyDetails.class);
                            intent.putExtra("id", ownerId);

                            // Start the activity with the intent
                            startActivity(intent);
                        } else {
                            Toast.makeText(OwnerHomeActivity.this, "Intent or ownerId is null", Toast.LENGTH_SHORT).show();
                        }
                    } else if (item.getItemId() == R.id.ohome) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHome).commit();
                        return true;
                    } else if (item.getItemId()==R.id.onotifications) {
                        NotificationFragment notificationFragment = NotificationFragment.newInstance(ownerId, null, notificationMessage,userId,userName);
                        Toast.makeText(OwnerHomeActivity.this, "notification message="+notificationMessage, Toast.LENGTH_SHORT).show();
                        // Replace the fragment container with the NotificationFragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();

                    }

                    return false;

                }
            });

            ImageView profileImg = findViewById(R.id.profileimg);
            profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadProfileFragmentWithOwnerId(ownerId);
                }
            });
            // Other initialization code...

    }

    private void loadProfileFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment with ownerId
        OwnerProfileFragment fragment = OwnerProfileFragment.newInstance(ownerId, null);

        // Start a new transaction to replace the container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void loadHomeFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment
        OwnerHomeFragment homeFragment = OwnerHomeFragment.newInstance(ownerId, null);

        // Start a new transaction to replace the container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();
    }

    private void retrievePropertiesForOwner(String ownerId) {
        DatabaseReference propertiesRef = FirebaseDatabase.getInstance().getReference()
                .child("PropertyDetailsModel");

        // Query properties for the specific owner
        propertiesRef.orderByChild("id").equalTo(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<PropertyDetailsModel> propertyList = new ArrayList<>();

                    // Iterate through each property and extract details
                    for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                        // Extract property details
                        String propertyName = propertySnapshot.child("nameofproperty").getValue(String.class);
                        String propertyType = propertySnapshot.child("typeofproperty").getValue(String.class);
                        int propertyPrice = propertySnapshot.child("priceofproperty").getValue(Integer.class);

                        // Add property to the list
                        PropertyDetailsModel property = new PropertyDetailsModel(propertyName, propertyType, propertyPrice);
                        propertyList.add(property);
                    }

                    // Initialize RecyclerView adapter
                    MyAdapter2 myAdapter2 = new MyAdapter2(OwnerHomeActivity.this, propertyList);

                    // Set adapter to RecyclerView
                    recyclerView2.setAdapter(myAdapter2);
                } else {
                    // Handle the case where no properties are found for the owner
                    Toast.makeText(OwnerHomeActivity.this, "No properties found for this owner", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(OwnerHomeActivity.this, "Failed to retrieve properties: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
