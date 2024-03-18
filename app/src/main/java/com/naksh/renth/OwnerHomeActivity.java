package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.naksh.renth.OwnerHomeFragment;
import com.naksh.renth.R;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    TextView ownerphoneno;
    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
    String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            ownerId = intent.getStringExtra("id");
            Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();

            // Pass the ID to the fragment
            loadHomeFragmentWithOwnerId(ownerId);

            bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
            bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    if (item.getItemId() == R.id.addproperty) {
                        if (ownerId != null) {
                            Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();

                            // Create the intent to start PropertyDetails activity
                            Intent intent = new Intent(OwnerHomeActivity.this, PropertyDetails.class);
                            intent.putExtra("id", ownerId);

                            // Start the activity with the intent
                            startActivity(intent);
                        } else {
                            Toast.makeText(OwnerHomeActivity.this, "Intent or ownerId is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                        else if (item.getItemId() == R.id.ohome) {
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
    }

    private void loadProfileFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment
        OwnerProfileFragment fragment = OwnerProfileFragment.newInstance(ownerId, null);
        Intent intent = new Intent(OwnerHomeActivity.this, OwnerHomeActivity.class);
        intent.putExtra("id", ownerId);
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
}
