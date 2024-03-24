package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.naksh.renth.OwnerHomeFragment;
import com.naksh.renth.R;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    //    TextView ownerphoneno;
    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
    String ownerId;
    String propertyId,parentId;
    String ownerphoneno;

    TextView ownerNameTextView, ownerPhoneNumberTextView, ownerEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            ownerId = intent.getStringExtra("id");
//            Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();
            // Pass the ID to the fragment
            loadHomeFragmentWithOwnerId(ownerId);
            retrieveDetailsFromDatabase(ownerId);

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

    private void retrieveDetailsFromDatabase( String ownerId) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("OwnerPersonalDetailsModel");

        database.child(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String ownerName = snapshot.child("oname").getValue(String.class);
                    String ownerPhoneNumber = snapshot.child("ophoneno").getValue(String.class);
                    String ownerEmail = snapshot.child("email").getValue(String.class);
                    // Set owner details in respective TextViews
                    ownerNameTextView = findViewById(R.id.ownername);
                    ownerPhoneNumberTextView = findViewById(R.id.ownerphoneno);
                    ownerEmailTextView = findViewById(R.id.owneremail);
                    ownerphoneno = ownerPhoneNumber;
                    ownerNameTextView.setText(ownerName);
                    ownerPhoneNumberTextView.setText(ownerPhoneNumber);
                    ownerEmailTextView.setText(ownerEmail);

                } else {
                    // Handle the case where no owner details are found
                    Toast.makeText(OwnerHomeActivity.this, "No owner details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(OwnerHomeActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
