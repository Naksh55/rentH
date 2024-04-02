package com.naksh.renth;//package com.naksh.renth;//package com.naksh.renth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;
//
//public class BookingScreen extends AppCompatActivity {
//
//    DatabaseReference database;
//
//    StorageReference storageReference;
//    TextView stateofproperty;
//    TextView nameOfProperty;
//    TextView typeOfProperty;
//    TextView city;
//    TextView address;
//
//
//    ImageView imageView;
//
//    TextView propertyDescription;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_screen);
//
//        // Retrieve the extras passed from the previous activity
//        Intent intent = getIntent();
//        if (intent != null) {
//            String propertyId = intent.getStringExtra("property_id");
//            String parentId = intent.getStringExtra("parent_id");
//            String parentInfo = intent.getStringExtra("parent_info");
//
//            // Show the retrieved values in a toast
//            getValuesFromDatabase(parentId);
////            Toast.makeText(this, "Property ID: " + propertyId, Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "Parent ID: " + parentId, Toast.LENGTH_SHORT).show();
////            Toast.makeText(this, "Parent Info: " + parentInfo, Toast.LENGTH_SHORT).show();
//        } else {
//            // Handle the case where intent is null
//            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
//        }
//
//
//        Button bookingbutton = findViewById(R.id.bookingbutton);
//        bookingbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BookingScreen.this, TripDetails.class);
//                startActivity(intent);
//            }
//        });
//        Intent intentT = getIntent();
//        // receive the value by getStringExtra() method and
//        // key must be same which is send by first activity
//        String str = intentT.getStringExtra("property_id");
//        System.out.println(str);
//    }
//
//    private void getValuesFromDatabase(String parentId) {
//        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
//                .getReference("PropertyDetailsModel");
//
//        // Assuming you have a parent ID (e.g., "PropertyDetailsModel")
////        String parentId = "PropertyDetailsModel";
//
//        // Query the database to find the parent record
//        database.child(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Parent record found
//                    // Retrieve child records
//                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                        String childKey = childSnapshot.getKey();
//                        String childValue = childSnapshot.getValue(String.class);
//
//                        stateofproperty= findViewById(R.id.stateofprperty);
//                        nameOfProperty = findViewById(R.id.nameofproperty);
//                        typeOfProperty =findViewById(R.id.typeodproperty);
//                        city = findViewById(R.id.cityofprperty);
//                        propertyDescription = findViewById(R.id.discription);
//                        address=findViewById(R.id.addressofprperty);
//                        imageView =findViewById(R.id.proimage);
//
//                        String state = dataSnapshot.child("state").getValue(String.class);
//                        String nop = dataSnapshot.child("nameofproperty").getValue(String.class);
//                        String top = dataSnapshot.child("typeofproperty").getValue(String.class);
//                        String cy = dataSnapshot.child("city").getValue(String.class);
//                        String pd = dataSnapshot.child("propertydiscription").getValue(String.class);
//                        String addressOfProperty = dataSnapshot.child("address").getValue(String.class);
//
//
//                        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
//
////                        Toast.makeText(BookingScreen.this, imageUrl, Toast.LENGTH_SHORT).show();
//                        stateofproperty.setText(Html.fromHtml("<b>State :</b> " + state));
//                        nameOfProperty.setText(Html.fromHtml("<b>Name :</b> " + nop));
//                        typeOfProperty.setText(Html.fromHtml("<b>Type of property :</b> " + top));
//                        city.setText(Html.fromHtml("<b>City :</b> " + cy));
//                        propertyDescription.setText(Html.fromHtml("<b>Description :</b> " + pd));
//                        address.setText(Html.fromHtml("<b>Address :</b> " + addressOfProperty));
//
//
//
//                        storageReference = FirebaseStorage.getInstance().getReference();
//                        Picasso.get().load(imageUrl).into(imageView);
//
//
//// Set the bitmap to the ImageView
//
//                    }
//                } else {
//                    // Parent record not found
//                    // Handle this case (e.g., show an error message)
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error (if any)
//            }
//        });
//    }
//
//
//}





//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;
//
//public class BookingScreen extends AppCompatActivity {
//
//    TextView stateofproperty;
//    TextView nameOfProperty;
//    TextView typeOfProperty;
//    TextView city;
//    TextView address;
//    TextView propertyDescription;
//    TextView ownerNameTextView;
//    TextView ownerPhoneNumberTextView;
//    TextView ownerEmailTextView;
//    String parentId;
//        ImageView imageView;
//        StorageReference storageReference;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_screen);
//
//        // Retrieve the extras passed from the previous activity
//        Intent intent = getIntent();
//        if (intent != null) {
//            String propertyId = intent.getStringExtra("propertyId");
//            String parentId = intent.getStringExtra("parentId");
//            if (propertyId != null && parentId != null) {
//                // Retrieve property and owner details using the parent ID
//                retrieveDetailsFromDatabase(propertyId, parentId);
//                retrieveDetailsFromDatabase(String propert yId);
//            } else {
//                // Handle the case where either propertyId or parentId is null
//                Toast.makeText(this, "Property ID or Parent ID is null", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Handle the case where intent is null
//            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void retrieveDetailsFromDatabase(String propertyId, String parentId) {
//        // Use both propertyId and parentId to fetch property and owner details
//        // Implement the logic to retrieve property and owner details using propertyId and parentId
//    }
//
//
//
//    private void retrieveDetailsFromDatabase(String propertyId) {
//        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
//                .getReference("PropertyDetailsModel");
//
//        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Retrieve property details
//                    String state = dataSnapshot.child("state").getValue(String.class);
//                    String nop = dataSnapshot.child("nameofproperty").getValue(String.class);
//                    String top = dataSnapshot.child("typeofproperty").getValue(String.class);
//                    String cy = dataSnapshot.child("city").getValue(String.class);
//                    String pd = dataSnapshot.child("propertydiscription").getValue(String.class);
//                    String addressOfProperty = dataSnapshot.child("address").getValue(String.class);
//                        imageView =findViewById(R.id.proimage);
//                    // Set property details in respective TextViews
//                    stateofproperty = findViewById(R.id.stateofprperty);
//                    nameOfProperty = findViewById(R.id.nameofproperty);
//                    typeOfProperty = findViewById(R.id.typeodproperty);
//                    city = findViewById(R.id.cityofprperty);
//                    propertyDescription = findViewById(R.id.discription);
//                    address = findViewById(R.id.addressofprperty);
//                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
//
//                    stateofproperty.setText("State: " + state);
//                    nameOfProperty.setText("Name: " + nop);
//                    typeOfProperty.setText("Type of property: " + top);
//                    city.setText("City: " + cy);
//                    propertyDescription.setText("Description: " + pd);
//                    address.setText("Address: " + addressOfProperty);
//                    storageReference = FirebaseStorage.getInstance().getReference();
//                        Picasso.get().load(imageUrl).into(imageView);
//                    // Retrieve owner details using the parent ID
//                    retrieveOwnerDetails(parentId);
//                } else {
//                    // Handle the case where no property details are found
//                    Toast.makeText(BookingScreen.this, "No property details found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void retrieveOwnerDetails(String parentId) {
//        DatabaseReference ownerRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
//                .getReference("OwnerPersonalDetailsModel")
//                .child(parentId);
//
//        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Log.d("retrieveOwnerDetails", "DataSnapshot exists: " + dataSnapshot.toString());
//
//                    // Retrieve owner details
//                    String ownerName = dataSnapshot.child("oname").getValue(String.class);
//                    String ownerPhoneNumber = dataSnapshot.child("ophoneno").getValue(String.class);
//                    String ownerEmail = dataSnapshot.child("email").getValue(String.class);
//
//                    Log.d("retrieveOwnerDetails", "Owner Name: " + ownerName);
//                    Log.d("retrieveOwnerDetails", "Owner Phone Number: " + ownerPhoneNumber);
//                    Log.d("retrieveOwnerDetails", "Owner Email: " + ownerEmail);
//
//                    // Set owner details in respective TextViews
//                    ownerNameTextView = findViewById(R.id.oname);
//                    ownerPhoneNumberTextView = findViewById(R.id.opnumber);
//                    ownerEmailTextView = findViewById(R.id.oemail);
//
//                    ownerNameTextView.setText("Owner Name: " + ownerName);
//                    ownerPhoneNumberTextView.setText("Owner Contact Number: " + ownerPhoneNumber);
//                    ownerEmailTextView.setText("Owner Email: " + ownerEmail);
//                } else {
//                    // Handle the case where no owner details are found
//                    Toast.makeText(BookingScreen.this, "No owner details found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

//
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class BookingScreen extends AppCompatActivity {
//
//    TextView nameOfPropertyTextView;
//    TextView ownerNameTextView;
//    String parentId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_screen);
//
//        // Initialize TextViews
//        nameOfPropertyTextView = findViewById(R.id.nameofproperty);
//        ownerNameTextView = findViewById(R.id.oname);
//
//        // Retrieve the extras passed from the previous activity
//        Intent intent = getIntent();
//        if (intent != null) {
//            String propertyId = intent.getStringExtra("property_id");
//            String parentId = intent.getStringExtra("parent_id");
//
//            if (propertyId != null && parentId != null) {
//                // Retrieve property and owner details using propertyId and parentId
//                retrieveDetailsFromDatabase(propertyId, parentId);
//            } else {
//                // Handle the case where either propertyId or parentId is null
//                Toast.makeText(this, "Property ID or Parent ID is null", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Handle the case where intent is null
//            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    private void retrieveDetailsFromDatabase(String propertyId, String parentId) {
//        DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel").child(propertyId);
//        propertyRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Retrieve property details
//                    String propertyName = dataSnapshot.child("nameofproperty").getValue(String.class);
//                    // Set property details to TextView
//                    nameOfPropertyTextView.setText("Name of Property: " + propertyName);
//
//                    // Retrieve owner details
//                    DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference("OwnerPersonalDetailsModel").child(parentId);
//                    ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                // Retrieve owner details
//                                String ownerName = dataSnapshot.child("oname").getValue(String.class);
//                                // Set owner details to TextView
//                                ownerNameTextView.setText("Owner Name: " + ownerName);
//                            } else {
//                                // Handle case where owner details are not found
//                                Toast.makeText(BookingScreen.this, "No owner details found", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            // Handle database error
//                            Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    // Handle case where property details are not found
//                    Toast.makeText(BookingScreen.this, "No property details found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}



import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
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

import org.w3c.dom.Text;

public class BookingScreen extends AppCompatActivity {

    TextView stateofproperty;
    TextView nameOfProperty;
    TextView typeOfProperty;
    TextView city;
    TextView address;
    TextView propertyDescription;
    TextView ownerNameTextView;
    TextView ownerPhoneNumberTextView;
    TextView ownerEmailTextView;
    String parentId;
    ImageView imageView;
    StorageReference storageReference;
    String ownerphoneno;
    private String propertyId;
    String userName;

//    String fromDate;
//    String toDate;
String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);

        TextView phonetext = findViewById(R.id.opnumber);
        TextView name = findViewById(R.id.nameofproperty);

        phonetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + ownerphoneno));
                startActivity(intent);
            }
        });

        // Retrieve the extras passed from the previous activity
        Intent intent = getIntent();
//        String fromDate = null;
//        String toDate = null;
        if (intent != null) {
             String fromDate = intent.getStringExtra("from_date");
             String toDate = intent.getStringExtra("to_date");
             userId=intent.getStringExtra("user_id");
             userName=intent.getStringExtra("userName");
            Toast.makeText(this, "======="+userId, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "======="+userName, Toast.LENGTH_SHORT).show();

//            Toast.makeText(this, "to date="+toDate, Toast.LENGTH_SHORT).show();


            propertyId = intent.getStringExtra("property_id");
            System.out.println("Property ID");
            System.out.println(propertyId);
            if (propertyId != null) {
                parentId = intent.getStringExtra("parent_id");

                // Retrieve property and owner details using the parent ID
                retrieveDetailsFromDatabase(propertyId, parentId);
            } else {
                // Handle the case where propertyId is null
                Toast.makeText(this, "Property ID is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where intent is null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");
                database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String fromDate = dataSnapshot.child("fordate").getValue(String.class);
                            String todate = dataSnapshot.child("todate").getValue(String.class);
//                            Toast.makeText(BookingScreen.this, "From:"+fromDate+"    To: "+todate, Toast.LENGTH_LONG).show();

//                Intent intent = getIntent();
//                if (intent != null) {
                            Intent intent = new Intent(BookingScreen.this, UserTripDetails.class);
                            intent.putExtra("from_date",fromDate);
                            intent.putExtra("to_date",todate);
                            intent.putExtra("user_id",userId);
                            intent.putExtra("userName",userName);

                            Toast.makeText(BookingScreen.this, "userId=="+userId, Toast.LENGTH_SHORT).show();
                            Toast.makeText(BookingScreen.this, "userName=="+userName, Toast.LENGTH_SHORT).show();


                            startActivity(intent);
//                    Toast.makeText(BookingScreen.this, fDate + "From date is null", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BookingScreen.this, tDate + "to date is null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(BookingScreen.this, "Intent is null", Toast.LENGTH_SHORT).show();
//
//                }
                        }
                        else{
                            Toast.makeText(BookingScreen.this, "snapshot dosent exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                    }
                });

            }
        });
        Button bookingbutton = findViewById(R.id.bookingbutton);
        bookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call the method with fromDate and toDate as arguments
                handleBookingButtonClick(propertyId);
            }
        });
    }
        // Assuming you have the ownerId from your previous data retrieval process

// Retrieve the phone number from the database
//        DatabaseReference ownerRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
//                .getReference("OwnerPersonalDetailsModel")
//                .child("ophoneno");
//
//        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Retrieve the phone number
//                    String phoneNumber = dataSnapshot.getValue(String.class);
//
//                    // Set an onClickListener to the TextView
//                    phonetext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // Open the phone app with the phone number
//                            Intent intent = new Intent(Intent.ACTION_DIAL);
//                            intent.setData(Uri.parse("tel:" + phoneNumber));
//                            startActivity(intent);
//                        }
//                    });
//                } else {
//                    // Handle the case where no phone number is found
//                    Toast.makeText(BookingScreen.this, "Phone number not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        String fromDate;
//        String toDate;
        // Declare fromDate and toDate as final outside the OnClickListener


// Define a method to handle button click
private void handleBookingButtonClick(String propertyId) {
//     Check if fromDate and toDate are not null before using them
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");
    database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                String fromDate = dataSnapshot.child("fordate").getValue(String.class);
                String todate = dataSnapshot.child("todate").getValue(String.class);
//                Toast.makeText(BookingScreen.this, "From:"+fromDate+"    To: "+todate, Toast.LENGTH_LONG).show();

//                Intent intent = getIntent();
//                if (intent != null) {
                Intent intent = new Intent(BookingScreen.this, UserTripDetails.class);
                    intent.putExtra("from_date",fromDate);
                    intent.putExtra("to_date",todate);
                    intent.putExtra("property_id",propertyId);
                    intent.putExtra("user_id",userId);
                intent.putExtra("userName",userName);

                Toast.makeText(BookingScreen.this, "id="+userId+"userName="+userName, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
//                    Toast.makeText(BookingScreen.this, fDate + "From date is null", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BookingScreen.this, tDate + "to date is null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(BookingScreen.this, "Intent is null", Toast.LENGTH_SHORT).show();
//
//                }
            }
        }

        @Override
        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

        }
    });

//    if (fromDate != null && toDate != null) {
//                    // Your existing code to create intent and put extra values
//                    Intent tripDetailsIntent = new Intent();
//                    tripDetailsIntent.putExtra("from_date", fromDate);
//                    tripDetailsIntent.putExtra("to_date", toDate);
//                    // Other code...
//                } else {
//                    // Handle the case where fromDate or toDate is null
//                    Toast.makeText(BookingScreen.this, "From date or to date is null", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(BookingScreen.this, "failed", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }

//
//        @Override
//        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
//
//        }
//    });
}





    private void retrieveDetailsFromDatabase(String propertyId,String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String state = dataSnapshot.child("state").getValue(String.class);
                    String nop = dataSnapshot.child("nameofproperty").getValue(String.class);
                    String top = dataSnapshot.child("typeofproperty").getValue(String.class);
                    String cy = dataSnapshot.child("city").getValue(String.class);
                    String pd = dataSnapshot.child("propertydiscription").getValue(String.class);
                    String addressOfProperty = dataSnapshot.child("address").getValue(String.class);
                    String fromDateValue = dataSnapshot.child("fordate").getValue(String.class);
                    String toDateValue = dataSnapshot.child("todate").getValue(String.class);
                    imageView =findViewById(R.id.proimage);
                    // Set property details in respective TextViews
                    stateofproperty = findViewById(R.id.stateofprperty);
                    nameOfProperty = findViewById(R.id.nameofproperty);
                    typeOfProperty = findViewById(R.id.typeodproperty);
                    city = findViewById(R.id.cityofprperty);
                    propertyDescription = findViewById(R.id.discription);
                    address = findViewById(R.id.addressofprperty);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
//                    textView2.setText(Html.fromHtml("<b>Name:</b> " + ownerName));

                    stateofproperty.setText(Html.fromHtml("<b>State: </b> " + state));
                    nameOfProperty.setText(Html.fromHtml("<b>Name: </b>" + nop));
                    typeOfProperty.setText(Html.fromHtml("<b>Type of property:</b> " + top));
                    city.setText(Html.fromHtml("<b>City:</b> " + cy));
                    propertyDescription.setText(Html.fromHtml("<b>Description:</b> " + pd));
                    address.setText(Html.fromHtml("<b>Address:</b> " + addressOfProperty));
                    storageReference = FirebaseStorage.getInstance().getReference();
                    Picasso.get().load(imageUrl).into(imageView);
                    // Retrieve owner details using the parent ID
                    retrieveOwnerDetails(parentId, propertyId);

                    Intent intent = new Intent();
                    intent.putExtra("property_id", propertyId);
                    intent.putExtra("parent_id", parentId);
                    intent.putExtra("from_date", fromDateValue); // Use different variable names to avoid confusion
                    intent.putExtra("to_date", toDateValue); // Use different variable names to avoid confusion
                    if (fromDateValue != null) {
//                        Toast.makeText(BookingScreen.this, fromDateValue, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "From date is null");
                    }

                    if (toDateValue != null) {
//                        Toast.makeText(BookingScreen.this, toDateValue, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "To date is null");
                    }

                } else {
                    // Handle the case where no property details are found
                    Toast.makeText(BookingScreen.this, "No property details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveOwnerDetails(String parentId,String propertyId) {
        DatabaseReference ownerRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("OwnerPersonalDetailsModel")
                .child(parentId);

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve owner details
                    String ownerName = dataSnapshot.child("oname").getValue(String.class);
                    String ownerPhoneNumber = dataSnapshot.child("ophoneno").getValue(String.class);
                    String ownerEmail = dataSnapshot.child("email").getValue(String.class);
                    // Set owner details in respective TextViews
                    ownerNameTextView = findViewById(R.id.oname);
                    ownerPhoneNumberTextView = findViewById(R.id.opnumber);
                    ownerEmailTextView = findViewById(R.id.oemail);
                    ownerphoneno = ownerPhoneNumber;

                    ownerNameTextView.setText(Html.fromHtml("<b>Owner Name:</b> " + ownerName));
                    ownerPhoneNumberTextView.setText(Html.fromHtml("<b>Owner Contact:</b> <font color='#4848EF'>" + ownerPhoneNumber + "</font>"));
                    ownerEmailTextView.setText(Html.fromHtml("<b>Owner Email:</b> " + ownerEmail));
                } else {
                    // Handle the case where no owner details are found
                    Toast.makeText(BookingScreen.this, "No owner details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(BookingScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
