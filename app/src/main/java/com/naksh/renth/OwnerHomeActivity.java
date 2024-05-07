//package com.naksh.renth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Html;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.naksh.renth.Models.PropertyDetailsModel;
//import com.naksh.renth.OwnerHomeFragment;
//import com.naksh.renth.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OwnerHomeActivity extends AppCompatActivity {
//    TextView owneremail;
//    TextView ownername;
//    //    TextView ownerphoneno;
//    private RecyclerView recyclerView2;
//
//    BottomNavigationView bottomNavigationView2;
//    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
//    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
//    NotificationFragment notificationFragment = new NotificationFragment();
//
//    String ownerId;
//    String propertyId,parentId;
//    String ownerphoneno;
//    String notificationMessage;
//    String userName;
//    String userId;
//    String ownerName;
//
//    TextView ownerNameTextView, ownerPhoneNumberTextView, ownerEmailTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_owner_home);
//        recyclerView2 = findViewById(R.id.recyclerView2);
//
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("owner_id")) {
//            ownerId = intent.getStringExtra("owner_id");
//            retrieveNotification( ownerId);
//
//            userId=intent.getStringExtra("user_id");
//            ownerName=intent.getStringExtra("oname");
////            Toast.makeText(this, "ownerName==="+ownerName, Toast.LENGTH_SHORT).show();
//            notificationMessage=intent.getStringExtra("notification_message");
//            userName=intent.getStringExtra("userName");
////            Toast.makeText(OwnerHomeActivity.this, "userId="+userId, Toast.LENGTH_SHORT).show();
////            Toast.makeText(OwnerHomeActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
////            Toast.makeText(OwnerHomeActivity.this, "notification="+notificationMessage, Toast.LENGTH_LONG).show();
//            // Pass the ID to the fragment
//            loadHomeFragmentWithOwnerId(ownerId);
//            retrievePropertiesForOwner(ownerId);
//
//
//        }
//        else{
//            Toast.makeText(this, "intent is null", Toast.LENGTH_SHORT).show();
//        }
//            bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
//            bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(MenuItem item) {
//                    if (item.getItemId() == R.id.addproperty) {
//                        if (ownerId != null) {
////                            Toast.makeText(OwnerHomeActivity.this, ownerId, Toast.LENGTH_SHORT).show();
//
//                            // Create the intent to start PropertyDetails activity
//                            Intent intent = new Intent(OwnerHomeActivity.this, PropertyDetails.class);
//                            intent.putExtra("owner_id", ownerId);
//                            intent.putExtra("oname", ownerName);
//
//
//                            // Start the activity with the intent
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(OwnerHomeActivity.this, "Intent or ownerId is null", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (item.getItemId() == R.id.ohome) {
//                        OwnerHomeFragment ownerHomeFragment = OwnerHomeFragment.newInstance(ownerId, null,ownerName);
//
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHomeFragment).commit();
//                        return true;
//                    } else if (item.getItemId() == R.id.onotifications) {
//                        // Create the NotificationFragment instance with the necessary data
//                        NotificationFragment notificationFragment = NotificationFragment.newInstance(ownerId, null, notificationMessage, userId, userName);
//
//                        // Check if notificationFragment is not null
//                        // Call addNotification method to add the received notification message
//                        notificationFragment.addNotification(notificationMessage);
//
//                        // Replace the fragment container with the NotificationFragment
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
//                    }
//
//                    return false;
//
//                }
//            });
//
//            ImageView profileImg = findViewById(R.id.profileimg);
//            profileImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    loadProfileFragmentWithOwnerId(ownerId);
//                }
//            });
//            // Other initialization code...
//
//    }
//
//    private void loadProfileFragmentWithOwnerId(String ownerId) {
//        // Create a new instance of the OwnerProfileFragment with ownerId
//        OwnerProfileFragment fragment = OwnerProfileFragment.newInstance(ownerId, null);
//
//        // Start a new transaction to replace the container with the fragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();
//    }
//
//    private void loadHomeFragmentWithOwnerId(String ownerId) {
////        Toast.makeText(this, "name====="+ownerName, Toast.LENGTH_SHORT).show();
//        // Create a new instance of the OwnerProfileFragment
//        OwnerHomeFragment homeFragment = OwnerHomeFragment.newInstance(ownerId, null,ownerName);
//
//        // Start a new transaction to replace the container with the fragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, homeFragment)
//                .commit();
//    }
//
//    private void retrievePropertiesForOwner(String ownerId) {
//        DatabaseReference propertiesRef = FirebaseDatabase.getInstance().getReference()
//                .child("PropertyDetailsModel");
//
//        // Query properties for the specific owner
//        propertiesRef.orderByChild("id").equalTo(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    ArrayList<PropertyDetailsModel> propertyList = new ArrayList<>();
//
//                    // Iterate through each property and extract details
//                    for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
//                        // Extract property details
//                        String propertyName = propertySnapshot.child("nameofproperty").getValue(String.class);
//                        String propertyType = propertySnapshot.child("typeofproperty").getValue(String.class);
//                        String propertyPrice = propertySnapshot.child("priceofproperty").getValue(String.class);
//
//                        // Add property to the list
//                        PropertyDetailsModel property = new PropertyDetailsModel(propertyName, propertyType, propertyPrice);
//                        propertyList.add(property);
//                    }
//
//                    // Initialize RecyclerView adapter
//                    MyAdapter2 myAdapter2 = new MyAdapter2(OwnerHomeActivity.this, propertyList);
//
//                    // Set adapter to RecyclerView
//                    recyclerView2.setAdapter(myAdapter2);
//                } else {
//                    // Handle the case where no properties are found for the owner
////                    Toast.makeText(OwnerHomeActivity.this, "No properties found for this owner", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//                Toast.makeText(OwnerHomeActivity.this, "Failed to retrieve properties: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void retrieveNotification(String id) {
//        Toast.makeText(this, "ownerId in method=" + id, Toast.LENGTH_SHORT).show();
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("NotificationMessage").child(id);
//
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Retrieve property details
//                    String notificationMessage = dataSnapshot.child("notificationMessage").getValue(String.class);
//                    String userId = dataSnapshot.child("userId").getValue(String.class);
//                    String propertyId = dataSnapshot.child("propertyId").getValue(String.class);
//                    String ownerid = dataSnapshot.child("ownerId").getValue(String.class);
//
//                    // You can directly retrieve the ownerId from the method parameter
//
//                    // Assuming you want to send this data to another activity
//                    Intent intent = getIntent();
//                    intent.putExtra("notification_message", notificationMessage);
//                    intent.putExtra("owner_id", ownerid); // Use the ownerId from the method parameter
//                    intent.putExtra("user_id", userId);
//                    intent.putExtra("propertyId", propertyId);
//                    Toast.makeText(OwnerHomeActivity.this, "userId=" + userId, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(OwnerHomeActivity.this, "ownerId inside onDataChange=" + ownerid, Toast.LENGTH_LONG).show();
//                } else {
//                    // Handle the case where no property details are found
//                    Toast.makeText(OwnerHomeActivity.this, "No property details found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle database error
//            }
//        });
//    }
//
//
////    private void retrieveNotifications() {
////        DatabaseReference database = FirebaseDatabase.getInstance().getReference("NotificationMessage");
////
////        database.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists()) {
////                    // Iterate through each child node to retrieve notification details
////                    for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
////                        String notificationKey = notificationSnapshot.getKey();
////                        String notificationMessage = notificationSnapshot.child("notificationMessage").getValue(String.class);
////                        String ownerId = notificationSnapshot.child("ownerId").getValue(String.class);
////                        String userId = notificationSnapshot.child("userId").getValue(String.class);
////                        String propertyId = notificationSnapshot.child("propertyId").getValue(String.class);
////
////                        // Process each notification
////                        processNotification(notificationKey, notificationMessage, ownerId, userId, propertyId);
////                    }
////                } else {
////                    // Handle the case where no notifications are found
////                    Toast.makeText(OwnerHomeActivity.this, "No notifications found", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                // Handle database error
////            }
////        });
////    }
////
////    private void processNotification(String notificationKey, String notificationMessage, String ownerId, String userId, String propertyId) {
////        // Assuming you want to handle each notification data here
////        Intent intent = getIntent();
////        intent.putExtra("notification_key", notificationKey);
////        intent.putExtra("notification_message", notificationMessage);
////        intent.putExtra("owner_id", ownerId);
////        intent.putExtra("user_id", userId);
////        intent.putExtra("propertyId", propertyId);
////        startActivity(intent);
////    }
////
//
//
//}

        package com.naksh.renth;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Html;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.android.material.navigation.NavigationBarView;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.naksh.renth.Models.PropertyDetailsModel;
        import com.naksh.renth.OwnerHomeFragment;
        import com.naksh.renth.R;

        import java.util.ArrayList;
        import java.util.List;

public class OwnerHomeActivity extends AppCompatActivity {
    TextView owneremail;
    TextView ownername;
    //    TextView ownerphoneno;
    private RecyclerView recyclerView2;

    BottomNavigationView bottomNavigationView2;
    OwnerProfileFragment ownerProfile = new OwnerProfileFragment();
    OwnerHomeFragment ownerHome = new OwnerHomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    String ownerId;
    String propertyId, parentId;
    String ownerphoneno;
    String notificationMessage;
    String userName;
    String userId;
    String ownerName;
    FirebaseDatabase database;
    String phoneNo;

    TextView ownerNameTextView, ownerPhoneNumberTextView, ownerEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        recyclerView2 = findViewById(R.id.recyclerView2);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            database = FirebaseDatabase.getInstance();
            ownerId = intent.getStringExtra("id");
            phoneNo=intent.getStringExtra("phoneno");

        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("NotificationMessage");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String nodeId = dataSnapshot.getChildren().iterator().next().getKey();
                    retrieveNotification(nodeId);

                    String nId=dataSnapshot.child("notificationId").getValue(String.class);
                    String notificationMessage = dataSnapshot.child("notificationMessage").getValue(String.class);
                    String userId = dataSnapshot.child("userId").getValue(String.class);
                    String propertyId = dataSnapshot.child("propertyId").getValue(String.class);
                    String ownerid = dataSnapshot.child("ownerId").getValue(String.class);

                } else {
                    // Handle the case where no property details are found
                    Toast.makeText(OwnerHomeActivity.this, "No property details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        userId = intent.getStringExtra("user_id");
        ownerName = intent.getStringExtra("oname");
        notificationMessage = intent.getStringExtra("notification_message");
        userName = intent.getStringExtra("userName");
        loadHomeFragmentWithOwnerId(ownerId);
        retrievePropertiesForOwner(ownerId);

        bottomNavigationView2 = findViewById(R.id.bottomnavigation2);
        bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.addproperty) {
                    if (ownerId != null) {
                        Intent intent = new Intent(OwnerHomeActivity.this, PropertyDetails.class);
                        intent.putExtra("id", ownerId);
                        intent.putExtra("oname", ownerName);
                        // Start the activity with the intent
                        startActivity(intent);
                    } else {
                        Toast.makeText(OwnerHomeActivity.this, "Intent or ownerId is null", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId() == R.id.ohome) {
                    OwnerHomeFragment ownerHomeFragment = OwnerHomeFragment.newInstance(ownerId, null, ownerName);

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, ownerHomeFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.onotifications) {
                    // Create the NotificationFragment instance with the necessary data
                    NotificationFragment notificationFragment = NotificationFragment.newInstance(ownerId, null, notificationMessage, userId, userName);
                    // Call addNotification method to add the received notification message
                    notificationFragment.addNotification(notificationMessage);
                    // Replace the fragment container with the NotificationFragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
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
        ImageView walletImg = findViewById(R.id.walletImg);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWalletFragmentWithOwnerId(ownerId);
            }
        });

    }


    private void loadProfileFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment with ownerId
        OwnerProfileFragment fragment = OwnerProfileFragment.newInstance(ownerId, null);

        // Start a new transaction to replace the container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void loadHomeFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment
        OwnerHomeFragment homeFragment = OwnerHomeFragment.newInstance(ownerId, null,ownerName);

        // Start a new transaction to replace the container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();
    }

    private void loadWalletFragmentWithOwnerId(String ownerId) {
        // Create a new instance of the OwnerProfileFragment with ownerId
        WalletFragment fragment = WalletFragment.newInstance(ownerId, null);

        // Start a new transaction to replace the container with the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    private void retrievePropertiesForOwner(String ownerId) {
        DatabaseReference propertiesRef = FirebaseDatabase.getInstance().getReference()
                .child("PropertyDetailsModel");

        // Query properties for the specific owner
        propertiesRef.orderByChild("id").equalTo(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<PropertyDetailsModel> propertyList = new ArrayList<>();

                    // Iterate through each property and extract details
                    for (DataSnapshot propertySnapshot : dataSnapshot.getChildren()) {
                        // Extract property details
                        String propertyName = propertySnapshot.child("nameofproperty").getValue(String.class);
                        String propertyType = propertySnapshot.child("typeofproperty").getValue(String.class);
                        String propertyPrice = propertySnapshot.child("priceofproperty").getValue(String.class);
                        String oName = propertySnapshot.child("oName").getValue(String.class);
                        String ownerID = propertySnapshot.child("ownerId").getValue(String.class);

                        Intent intent=getIntent();
                        intent.putExtra("ownerName",oName);
                        intent.putExtra("oID",ownerID);

                        // Add property to the list
                        PropertyDetailsModel property = new PropertyDetailsModel(propertyName, propertyType, propertyPrice);
                        propertyList.add(property);
                    }

                    // Initialize RecyclerView adapter
                    MyAdapter2 myAdapter2 = new MyAdapter2(OwnerHomeActivity.this, propertyList);

                    // Set adapter to RecyclerView
                    recyclerView2.setAdapter(myAdapter2);
                } else {
                    // Handle the case where no properties are found for the owner
//                    Toast.makeText(OwnerHomeActivity.this, "No properties found for this owner", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(OwnerHomeActivity.this, "Failed to retrieve properties: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveNotification(String ownerId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("NotificationMessage").child(ownerId);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String notificationMessage = dataSnapshot.child("notificationMessage").getValue(String.class);
                    String userId = dataSnapshot.child("userId").getValue(String.class);
                    String propertyId = dataSnapshot.child("propertyId").getValue(String.class);
                    String ownerid = dataSnapshot.child("ownerId").getValue(String.class);
                    String notificationId=dataSnapshot.child("notificationId").getValue(String.class);
                    // You can directly retrieve the ownerId from the method parameter
                    // Assumingyou want to send this data to another activity
                    Intent intent = getIntent();
                    intent.putExtra("notification_message", notificationMessage);
                    intent.putExtra("owner_id", ownerid); // Use the ownerId from the method parameter
                    intent.putExtra("user_id", userId);
                    intent.putExtra("propertyId", propertyId);

                } else {
                    // Handle the case where no property details are found
                    Toast.makeText(OwnerHomeActivity.this, "No property details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}

