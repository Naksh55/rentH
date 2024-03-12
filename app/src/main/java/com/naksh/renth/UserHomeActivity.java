package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
UserHomeFrament userHomeFrament=new UserHomeFrament();
UserProfileFragment userProfileFragment=new UserProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, userHomeFrament).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userHomeFrament).commit();
                    return true;
                } else if(item.getItemId()==R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userProfileFragment).commit();
                    return true;
                }
                else{
                    // Handle other menu items if needed
                    return false;
                }
            }
            });
    }
}