package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserHomeFrament userHomeFrament=new UserHomeFrament();
    UserProfileFragment userProfileFragment=new UserProfileFragment();
    String userId,userName,userEmail,userPhoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView textView1= findViewById(R.id.useremail);
        TextView textView2 = findViewById(R.id.username);
        TextView textView3 = findViewById(R.id.userphoneno);
        Intent intent=getIntent();
       userId= intent.getStringExtra("user_id");
       userName= intent.getStringExtra("name");
       userEmail=  intent.getStringExtra("userEmail");
        userPhoneno=intent.getStringExtra("phoneno");
        textView2.setText(Html.fromHtml("<b>Name:</b> " + userName));
        textView1.setText(Html.fromHtml("<b>Email:</b> " + userEmail));
        textView3.setText(Html.fromHtml("<b>PhoneNo:</b> " + userPhoneno));


        retrieveUserDetails(userId, textView2, textView1, textView3);

        bottomNavigationView = findViewById(R.id.bottomnavigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent=new Intent(UserProfileActivity.this,PropertyRecyclerActivityForUser.class);
                    startActivity(intent);
                    return true;
                } else if(item.getItemId()==R.id.profile) {
                    return true;

                }


                else{
                    // Handle other menu items if needed
                    return false;
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle logout action
                        Toast.makeText(UserProfileActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserProfileActivity.this, LoginScreen.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null);

        final AlertDialog logoutConfirmationDialog = alertDialogBuilder.create();

        Button logoutButton =findViewById(R.id.logutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show logout confirmation dialog
                logoutConfirmationDialog.show();
            }
        });
    }
    private void retrieveUserDetails(String userId, TextView textView3, TextView textView1, TextView textView2) {
        if (userId == null) {
            // Handle the case where ownerId is null
            Log.e("OwnerProfileFragment", "OwnerId is null");
            return;
        }
        if (textView1 == null || textView2 == null || textView3 == null) {
            Log.e("OwnerProfileFragment", "TextView references are null");
            return;
        }

        DatabaseReference ownerRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("UserPersonalDetailsModel")
                .child(userId);

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("OwnerProfileFragment", "DataSnapshot exists: " + dataSnapshot.exists());
                if (dataSnapshot.exists()) {
                    // Retrieve owner details
                    String ownerName = dataSnapshot.child("name").getValue(String.class);
                    String ownerEmail = dataSnapshot.child("uemail").getValue(String.class);
                    String ownerPhoneNo = dataSnapshot.child("phoneno").getValue(String.class);

                    // Set owner details to TextViews
                    textView2.setText(Html.fromHtml("<b>Name:</b> " + ownerName));
                    textView1.setText(Html.fromHtml("<b>Email:</b> " + ownerEmail));
                    textView3.setText(Html.fromHtml("<b>PhoneNo:</b> " + ownerPhoneNo));

                } else {
                    // Handle the case where no owner details are found
                    Toast.makeText(UserProfileActivity.this, "No owner details found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    }
