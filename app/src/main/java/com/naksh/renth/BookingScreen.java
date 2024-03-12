package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class BookingScreen extends AppCompatActivity {

    DatabaseReference database;

    StorageReference storageReference;
    TextView stateofproperty;
    TextView nameOfProperty;
    TextView typeOfProperty;
    TextView city;
    TextView address;


    ImageView imageView;

    TextView propertyDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);

        // Retrieve the extras passed from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            String propertyId = intent.getStringExtra("property_id");
            String parentId = intent.getStringExtra("parent_id");
            String parentInfo = intent.getStringExtra("parent_info");

            // Show the retrieved values in a toast
            getValuesFromDatabase(parentId);
//            Toast.makeText(this, "Property ID: " + propertyId, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Parent ID: " + parentId, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Parent Info: " + parentInfo, Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case where intent is null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }


        Button bookingbutton = findViewById(R.id.bookingbutton);
        bookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingScreen.this, TripDetails.class);
                startActivity(intent);
            }
        });
//        Intent intent = getIntent();
//        // receive the value by getStringExtra() method and
//        // key must be same which is send by first activity
//        String str = intent.getStringExtra("property_id");
//        System.out.println(str);
    }

    private void getValuesFromDatabase(String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("PropertyDetailsModel");

        // Assuming you have a parent ID (e.g., "PropertyDetailsModel")
//        String parentId = "PropertyDetailsModel";

        // Query the database to find the parent record
        database.child(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Parent record found
                    // Retrieve child records
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                        String childKey = childSnapshot.getKey();
//                        String childValue = childSnapshot.getValue(String.class);

                        stateofproperty= findViewById(R.id.stateofprperty);
                        nameOfProperty = findViewById(R.id.nameofproperty);
                        typeOfProperty =findViewById(R.id.typeodproperty);
                        city = findViewById(R.id.cityofprperty);
                        propertyDescription = findViewById(R.id.discription);

                        imageView =findViewById(R.id.proimage);

                        String state = dataSnapshot.child("state").getValue(String.class);
                        String nop = dataSnapshot.child("nameofproperty").getValue(String.class);
                        String top = dataSnapshot.child("typeofproperty").getValue(String.class);
                        String cy = dataSnapshot.child("city").getValue(String.class);
                        String pd = dataSnapshot.child("propertydiscription").getValue(String.class);

                        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

//                        Toast.makeText(BookingScreen.this, imageUrl, Toast.LENGTH_SHORT).show();
                        stateofproperty.setText("State : " + state);
                        nameOfProperty.setText("Name : " + nop);
                        typeOfProperty.setText(top);
                        city.setText(cy);
                        propertyDescription.setText(pd);

                        storageReference = FirebaseStorage.getInstance().getReference();
                        Picasso.get().load(imageUrl).into(imageView);


// Set the bitmap to the ImageView

                    }
                } else {
                    // Parent record not found
                    // Handle this case (e.g., show an error message)
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error (if any)
            }
        });
    }


}



