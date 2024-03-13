package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    TextView ownerphoneno;



    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
     String ownerId;

    //    OwnerNotificationFragment ownerNotification=new OwnerNotificationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);
        ownerId = getIntent().getStringExtra("id");
//        Intent i=new Intent(OwnerHomeActivity.this,PropertyDetails.class);
//        i.putExtra("id", ownerId); // Pass ownerId to PropertyDetails activity

        OwnerHomeFragment fragment = OwnerHomeFragment.newInstance(ownerId, null);

//        Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();
//        ownername
        owneremail = findViewById(R.id.owneremail);
        String useremail = getIntent().getStringExtra("ownerEmail");
        owneremail.setText(useremail);

        ownername = findViewById(R.id.ownername);
        String nameOwner = getIntent().getStringExtra("ownerName");
        ownername.setText(nameOwner);


        ownerphoneno = findViewById(R.id.ownerphoneno);
        String oPhone = getIntent().getStringExtra("ownerPhone");
        ownerphoneno.setText(oPhone);

        loadFragmentWithOwnerId(fragment); // Call the method to load the fragment with ownerId


//        owneremail = findViewById(R.id.owneremail);
//        String owneremail = getIntent().getStringExtra("ownerName2");
//        ownername.setText(owneremail);

        bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHome).commit();

        bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.ohome) {
                    if (ownerId != null) {
                        Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();

                        // Create the intent to start PropertyDetails activity
                        Intent intent = new Intent(OwnerHomeActivity.this, PropertyDetails.class);
                        intent.putExtra("id", ownerId);

                        // Start the activity with the intent
                        startActivity(intent);

                    } else {
                        Toast.makeText(OwnerHomeActivity.this, "Owner ID is null", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                } else {
                    // Handle other menu items if needed
                    return false;
                }
            }
        });


        ImageView profileimg = findViewById(R.id.profileimg);
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToPass = ownername.getText().toString();
                String textToPass2 = owneremail.getText().toString();
                String textToPass3 = ownerphoneno.getText().toString();


                Bundle bundle = new Bundle();
                bundle.putString("nameOwner", textToPass);
                bundle.putString("emailOwner", textToPass2);
                bundle.putString("ownerPhoneNo", textToPass3);


                ownerProfile.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ownerProfile)
                        .addToBackStack(null) // Add this transaction to the back stack
                        .commit();
            }
        });

    }

    private void loadFragmentWithOwnerId(OwnerHomeFragment fragment) {
        // Create a new instance of your fragment
        // Begin the transaction to add the fragment to the layout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment) // Replace fragment_container with the ID of your fragment container
                .commit();
    }
}
