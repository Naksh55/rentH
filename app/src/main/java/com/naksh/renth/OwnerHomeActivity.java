package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    TextView ownerphoneno;



    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();

    //    OwnerNotificationFragment ownerNotification=new OwnerNotificationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);
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


//        owneremail = findViewById(R.id.owneremail);
//        String owneremail = getIntent().getStringExtra("ownerName2");
//        ownername.setText(owneremail);

        bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHome).commit();

        bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.ohome) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHome).commit();
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
}