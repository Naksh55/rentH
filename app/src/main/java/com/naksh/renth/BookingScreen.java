package com.naksh.renth;

import static android.content.ContentValues.TAG;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    ImageView imageView,imageView2,imageView3,imageView4;
    StorageReference storageReference;
    String ownerphoneno;
    private String propertyId;
    String userName;

//    String fromDate;
//    String toDate;
String userId;
String phonoNo;
    String ownerId;

//    String ownerId;
    String userEmail;


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
             ownerId = intent.getStringExtra("owner_id");
             phonoNo=intent.getStringExtra("phoneno");
             userEmail=intent.getStringExtra("userEmail");
//            Toast.makeText(this, "ownerId in booking screen==="+ownerId, Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "phoneno in booking screen==="+phonoNo, Toast.LENGTH_LONG).show();

//            Toast.makeText(this, "userId=="+userId, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "userName=="+userName, Toast.LENGTH_SHORT).show();

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

        Button bookingbutton = findViewById(R.id.bookingbutton);
        bookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call the method with fromDate and toDate as arguments
                handleBookingButtonClick(propertyId);
            }
        });
    }

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
                String propertyName = dataSnapshot.child("nameofproperty").getValue(String.class);
                String price=dataSnapshot.child("priceofproperty").getValue(String.class);


//                Toast.makeText(BookingScreen.this, "From:"+fromDate+"    To: "+todate, Toast.LENGTH_LONG).show();

//                Intent intent = getIntent();
//                if (intent != null) {
                Intent intent = new Intent(BookingScreen.this, UserTripDetails.class);
                    intent.putExtra("from_date",fromDate);
                    intent.putExtra("to_date",todate);
                    intent.putExtra("property_id",propertyId);
                    intent.putExtra("user_id",userId);
                intent.putExtra("userName",userName);
                intent.putExtra("owner_id",ownerId);
                intent.putExtra("propertyName",propertyName);
                intent.putExtra("phoneno",phonoNo);
                intent.putExtra("userEmail",userEmail);
                intent.putExtra("priceofproperty",price);
//                Toast.makeText(BookingScreen.this, "price==="+price, Toast.LENGTH_SHORT).show();

//                Toast.makeText(BookingScreen.this, "phoneNo="+phonoNo+"userName="+userName, Toast.LENGTH_SHORT).show();
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
                    String ownerId = dataSnapshot.child("ownerId").getValue(String.class);

                    String nop = dataSnapshot.child("nameofproperty").getValue(String.class);
                    String top = dataSnapshot.child("typeofproperty").getValue(String.class);
                    String cy = dataSnapshot.child("city").getValue(String.class);
                    String pd = dataSnapshot.child("propertydiscription").getValue(String.class);
                    String addressOfProperty = dataSnapshot.child("address").getValue(String.class);
                    String fromDateValue = dataSnapshot.child("fordate").getValue(String.class);
                    String toDateValue = dataSnapshot.child("todate").getValue(String.class);
                    imageView =findViewById(R.id.proimage);
                    imageView2 =findViewById(R.id.proimage2);
                    imageView3 =findViewById(R.id.proimage3);
                    imageView4 =findViewById(R.id.proimage4);

                    // Set property details in respective TextViews
                    stateofproperty = findViewById(R.id.stateofprperty);
                    nameOfProperty = findViewById(R.id.nameofproperty);
                    typeOfProperty = findViewById(R.id.typeodproperty);
                    city = findViewById(R.id.cityofprperty);
                    propertyDescription = findViewById(R.id.discription);
                    address = findViewById(R.id.addressofprperty);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String imageUrl2 = dataSnapshot.child("propertydp2").getValue(String.class);
                    String imageUrl3 = dataSnapshot.child("propertydp3").getValue(String.class);
                    String imageUrl4 = dataSnapshot.child("propertydp4").getValue(String.class);

//                    textView2.setText(Html.fromHtml("<b>Name:</b> " + ownerName));

                    stateofproperty.setText(Html.fromHtml("<b>State: </b> " + state));
                    nameOfProperty.setText(Html.fromHtml("<b>Name: </b>" + nop));
                    typeOfProperty.setText(Html.fromHtml("<b>Type of property:</b> " + top));
                    city.setText(Html.fromHtml("<b>City:</b> " + cy));
                    propertyDescription.setText(Html.fromHtml("<b>Description:</b> " + pd));
                    address.setText(Html.fromHtml("<b>Address:</b> " + addressOfProperty));
                    storageReference = FirebaseStorage.getInstance().getReference();
                    Picasso.get().load(imageUrl).into(imageView);
                    Picasso.get().load(imageUrl2).into(imageView2);
                    Picasso.get().load(imageUrl3).into(imageView3);
                    Picasso.get().load(imageUrl4).into(imageView4);

                    Animation animation = AnimationUtils.loadAnimation(BookingScreen.this, R.anim.slide_left_to_right);
                    // Apply  animation to the TextViews
                    stateofproperty.startAnimation(animation);
                    nameOfProperty.startAnimation(animation);
                    typeOfProperty.startAnimation(animation);
                    address.startAnimation(animation);                    // Retrieve owner details using the parent ID
                    city.startAnimation(animation);                    // Retrieve owner details using the parent ID
                    propertyDescription.startAnimation(animation);
// Retrieve owner details using the parent ID
                    retrieveOwnerDetails(ownerId, propertyId);
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

    private void retrieveOwnerDetails(String ownerId,String propertyId) {
        DatabaseReference ownerRef = FirebaseDatabase.getInstance()
                .getReference("OwnerPersonalDetailsModel")
                .child(ownerId);

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve owner details
                    String ownerId = dataSnapshot.child("id").getValue(String.class);

                    String ownerName = dataSnapshot.child("oname").getValue(String.class);
                    String ownerPhoneNumber = dataSnapshot.child("ophoneno").getValue(String.class);
                    String ownerEmail = dataSnapshot.child("oemail").getValue(String.class);
                    Intent intent=getIntent();
                    intent.putExtra("phoneNo",ownerPhoneNumber);
                    // Set owner details in respective TextViews
                    ownerNameTextView = findViewById(R.id.oname);
                    ownerPhoneNumberTextView = findViewById(R.id.opnumber);
                    ownerEmailTextView = findViewById(R.id.oemail);
                    ownerphoneno = ownerPhoneNumber;

                    ownerNameTextView.setText(Html.fromHtml("<b>Owner Name:</b> " + ownerName));
                    ownerPhoneNumberTextView.setText(Html.fromHtml("<b>Owner Contact:</b> <font color='#4848EF'>" + ownerPhoneNumber + "</font>"));
                    ownerEmailTextView.setText(Html.fromHtml("<b>Owner Email:</b> " + ownerEmail));
//                    Animation animation = AnimationUtils.loadAnimation(BookingScreen.this, R.anim.slide_left_to_right);
//                    // Apply  animation to the TextViews
                    Animation animation = AnimationUtils.loadAnimation(BookingScreen.this, R.anim.slide_left_to_right);

                    ownerNameTextView.startAnimation(animation);
                    ownerPhoneNumberTextView.startAnimation(animation);
                    ownerEmailTextView.startAnimation(animation);

                } else {

                    Toast.makeText(BookingScreen.this, "parentId="+parentId, Toast.LENGTH_SHORT).show();

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
