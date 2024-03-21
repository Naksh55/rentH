package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserHomeFrament userHomeFrament=new UserHomeFrament();
    UserProfileFragment userProfileFragment=new UserProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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


    }
