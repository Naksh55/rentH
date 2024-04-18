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
String userId,userName,userEmail,userPhoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, userHomeFrament).commit();
        Intent intent=getIntent();
        userId = intent.getStringExtra("user_id");
        userName= intent.getStringExtra("name");
        userEmail=  intent.getStringExtra("userEmail");
        userPhoneno=intent.getStringExtra("phoneno");

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userHomeFrament).commit();
                    return true;
                } else if(item.getItemId()==R.id.profile) {
                    Intent intent=new Intent(UserHomeActivity.this,UserProfileActivity.class);
                    intent.putExtra("user_id",userId);
                    intent.putExtra("name",userName);
                    intent.putExtra("userEmail",userEmail);
                    intent.putExtra("phoneno",userPhoneno);


                    startActivity(intent);
                }
                else{
                    // Handle other menu items if needed
                    return false;
                }
                return false;
            }
            });
    }
}