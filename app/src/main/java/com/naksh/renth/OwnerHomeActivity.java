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
        } else {
            Toast.makeText(OwnerHomeActivity.this, "Intent or ownerId is null", Toast.LENGTH_SHORT).show();
        }

        // Initialize TextViews
        owneremail = findViewById(R.id.owneremail);
        ownername = findViewById(R.id.ownername);
        ownerphoneno = findViewById(R.id.ownerphoneno);

        // Set values to TextViews
        owneremail.setText(intent.getStringExtra("ownerEmail"));
        ownername.setText(intent.getStringExtra("ownerName"));
        ownerphoneno.setText(intent.getStringExtra("ownerPhone"));

        // Create a new instance of OwnerHomeFragment with ownerId
// Create a new instance of OwnerHomeFragment with ownerId
        OwnerHomeFragment fragment = OwnerHomeFragment.newInstance(ownerId, null);
        Bundle args = new Bundle();
        args.putString("id", ownerId);
        fragment.setArguments(args);

        // Load the fragment into the activity
        loadFragmentWithOwnerId(fragment);

        // Other initialization code...

        // ImageView click listener...
    }

    private void loadFragmentWithOwnerId(OwnerHomeFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
