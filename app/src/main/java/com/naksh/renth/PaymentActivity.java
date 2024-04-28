package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.naksh.renth.NotificationFragment;
import com.phonepe.intent.sdk.api.B2BPGRequest;
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.PhonePeInitException;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {
    String propertyId,parentId;
    private static final String CHANNEL_ID = "ButtonNotificationChannel";
    String username; // Declare username as a class member variable
    String userId;

    int slots;
    String notificationMessage;
    String ownerId;
    String propertyName;
    String phoneNo;
    String userEmail;
    String userName;
    String fromDate;
    String priceofproperty;
    String pop;
    String newpriceofproperty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
//        int price = 100;
//        int newprice=price*100;
        try {
            PhonePe.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        if (intent != null) {
            priceofproperty=intent.getStringExtra("pp");
//            slot=intent.getStringExtra("slot");
            pop=intent.getStringExtra("propertyPrice");
            Toast.makeText(this, "price="+priceofproperty, Toast.LENGTH_SHORT).show();

//            Toast.makeText(this, "price="+priceofproperty, Toast.LENGTH_SHORT).show();
            Animation animation = AnimationUtils.loadAnimation(PaymentActivity.this, R.anim.slide_left_to_right);

            TextView ratepernight=findViewById(R.id.ratepernight);
            ratepernight.startAnimation(animation);
            TextView cleaningcharges=findViewById(R.id.cleaningcharges);
            cleaningcharges.startAnimation(animation);
            TextView renthfee=findViewById(R.id.renthfee);
            renthfee.startAnimation(animation);
            TextView ratepernightprice=findViewById(R.id.ratepernightprice);
            ratepernightprice.startAnimation(animation);
            TextView cleaningprice=findViewById(R.id.cleaningprice);
            cleaningprice.startAnimation(animation);
            TextView renthfeeprice=findViewById(R.id.renthfeeprice);
            renthfeeprice.startAnimation(animation);

            propertyId = intent.getStringExtra("property_id");
            fromDate=intent.getStringExtra("fDate");
//            Toast.makeText(this, "from date="+fromDate, Toast.LENGTH_SHORT).show();
            ownerId = intent.getStringExtra("owner_id");
            propertyName = intent.getStringExtra("propertyName");
            phoneNo = intent.getStringExtra("phoneno");
//            Toast.makeText(this, "phoneno=" + phoneNo, Toast.LENGTH_SHORT).show();
            userEmail = intent.getStringExtra("userEmail");
            userName = intent.getStringExtra("userName");
//            Toast.makeText(this, "ownerId=" + ownerId, Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "propertyId in payment antivity="+propertyId, Toast.LENGTH_LONG).show();
            slots = intent.getIntExtra("slot", 0); // 0 is the default value if "slot" extra is not found
            if (slots == 0) {
                Toast.makeText(this, "Slots is null", Toast.LENGTH_SHORT).show();
                return; // Exit the method as slots is null
            } else {
//                Toast.makeText(this, "Slots: " + slots, Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(this, String.valueOf(slots), Toast.LENGTH_SHORT).show();

            if (propertyId != null) {
                parentId = intent.getStringExtra("id");

                userId = intent.getStringExtra("user_id");
                username = intent.getStringExtra("userName");
                notificationMessage = intent.getStringExtra("notification_message");
//                Toast.makeText(this,notificationMessage , Toast.LENGTH_SHORT).show();

//                Toast.makeText(this, "userId="+userId, Toast.LENGTH_SHORT).show();
                retrieveDetailsFromDatabase(propertyId, parentId);
                retrieveOwnerIDs();

            } else {
                Toast.makeText(this, "property_id is null", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            username = user.getDisplayName();

            // Use the username here
        }
//

        Button payment = findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                        .child("UserPersonalDetailsModel")
                        .child(userId); // Assuming you have the userId available

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String userName = dataSnapshot.child("name").getValue(String.class);
                            String userId = dataSnapshot.child("id").getValue(String.class);
//                            Toast.makeText(PaymentActivity.this, "name="+userName, Toast.LENGTH_SHORT).show();
                                if (userName != null) {
                                String notificationMessage = "Your property " + propertyName + " has been booked by " + userName +" "+"on "+fromDate+ "\nCustomer contact: " + phoneNo + "\nCustomer Email: " + userEmail;
                                    Toast.makeText(PaymentActivity.this, "notifocationmesage="+notificationMessage, Toast.LENGTH_LONG).show();
//                                Toast.makeText(PaymentActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                intent.putExtra("notification_message", notificationMessage);

//                                NotificationFragment notificationFragment = new NotificationFragment();
//                                notificationFragment.addNotification("notification_message");
// Assuming you have a reference to the NotificationFragment
                                String param2 = "";
                                String id = "";
                                NotificationFragment notificationFragment = NotificationFragment.newInstance(id, param2, notificationMessage, userId, userName);

                                notificationFragment.addNotification("notification_message");

                                intent.putExtra("user_id", userId);
                                intent.putExtra("userName", userName);
                                intent.putExtra("property_id", propertyId);
                                intent.putExtra("id", ownerId);

//                                Toast.makeText(PaymentActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
                                createNotificationMessage(notificationMessage, userId, propertyId, ownerId);
//                                startActivity(intent);
                            } else {
                                Toast.makeText(PaymentActivity.this, "User name not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PaymentActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(PaymentActivity.this, "Failed to retrieve user details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            }

        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Terms and Conditions")
                .setMessage("......")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                                .child("UserPersonalDetailsModel")
                                .child(userId); // Assuming you have the userId available

                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String userName = dataSnapshot.child("name").getValue(String.class);
                                    String userId = dataSnapshot.child("id").getValue(String.class);
//                            Toast.makeText(PaymentActivity.this, "name="+userName, Toast.LENGTH_SHORT).show();
                                    if (userName != null) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                                        String currentTime = sdf.format(new Date());
                                        String notificationMessage = "Your property " + propertyName + " has been booked by " + userName +" "+"on "+fromDate+" "+"for "+slots+" slots "+ "at "+currentTime+"\nCustomer contact: " + phoneNo + "\nCustomer Email: " + userEmail;
                                        Toast.makeText(PaymentActivity.this, "message="+notificationMessage, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(PaymentActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
                                        Intent intent = getIntent();
                                        intent.putExtra("notification_message", notificationMessage);

//                                NotificationFragment notificationFragment = new NotificationFragment();
//                                notificationFragment.addNotification("notification_message");
// Assuming you have a reference to the NotificationFragment
                                        String param2 = "";
                                        String id = "";
                                        NotificationFragment notificationFragment = NotificationFragment.newInstance(id, param2, notificationMessage, userId, userName);

                                        notificationFragment.addNotification("notification_message");

                                        intent.putExtra("user_id", userId);
                                        intent.putExtra("userName", userName);
                                        intent.putExtra("property_id", propertyId);
                                        intent.putExtra("id", ownerId);

//                                Toast.makeText(PaymentActivity.this, "userName="+userName, Toast.LENGTH_SHORT).show();
                                        createNotificationMessage(notificationMessage, userId, propertyId, ownerId);
//                                startActivity(intent);
                                    } else {
                                        Toast.makeText(PaymentActivity.this, "User name not found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(PaymentActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle database error
                                Toast.makeText(PaymentActivity.this, "Failed to retrieve user details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        // Handle logout action
                        JSONObject data = new JSONObject();
//                String upiId = "7973958511@ybl";

                        try {
                            data.put("merchantTransactionId", "MT7850590068188104");
                            data.put("merchantId", "PGTESTPAYUAT");
//                    data.put("merchantId", upiId);

                            data.put("merchantUserId", "MUID123");
                            data.put("amount", 480000);
                            data.put("mobileNumber", "9999999999");
                            data.put("callbackUrl", "https://webhook.site/7fbbf7fb-c73c-4504-8467-14b0845d7c38");
                            JSONObject paymentInstrument = new JSONObject();
                            paymentInstrument.put("type", "PAY_PAGE");
                            paymentInstrument.put("targetApp", "com.phonepe.simulator");
                            data.put("paymentInstrument", paymentInstrument);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Convert data HashMap to JSON and then encode to Base64
                        String base64Body = Base64.encodeToString(data.toString().getBytes(Charset.defaultCharset()), Base64.NO_WRAP);
                        String checksum = sha256(base64Body + "/pg/v1/pay" + "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399") + "###1";
                        B2BPGRequest b2BPGRequest = new B2BPGRequestBuilder()
                                .setData(base64Body)
                                .setChecksum(checksum)
                                .setUrl("/pg/v1/pay")
                                .build();
                        try {
                            startActivityForResult(PhonePe.getImplicitIntent(PaymentActivity.this, b2BPGRequest, ""), 1);
                        } catch (PhonePeInitException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Reject", null);


        final AlertDialog logoutConfirmationDialog = alertDialogBuilder.create();
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show logout confirmation dialog
                logoutConfirmationDialog.show();
            }
        });
    }


    private void sendNotificationToOwner( String propertyId) {
        // Retrieve owner's device token from the database based on ownerId
        // Construct notification message
        String notificationMessage = "Your property (ID: " + propertyId + ") has been booked by a user.";
        // Create a HashMap to store key-value pairs
        // Create a HashMap to store key-value pairs
        Intent intent=getIntent();
        intent.putExtra("notification_message",notificationMessage);
        Map<String, String> dataMap = new HashMap<>();
// Add key-value pairs to the map
        dataMap.put("title", "Booking Notification");
        dataMap.put("body", notificationMessage);

// Pass the map to the setData method
        FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(propertyId)
                .setData(dataMap)
                .build());
    }

    private void retrieveOwnerIDs() {
        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference().child("OwnerPersonalDetailsModel");

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if there are any owner details in the database
                if (dataSnapshot.exists()) {
                    // Iterate through each owner's details
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Retrieve the ID of each owner
                        String ownerId = snapshot.child("id").getValue(String.class);
                        if (ownerId != null) {
                            // Now you have the owner ID, you can use it as needed
                            // For example, you can send a notification to this owner
                            // or perform any other action
                            sendNotificationToOwner(ownerId);
                        }
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, "id is null", Toast.LENGTH_SHORT).show();                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur during the retrieval process
            }
        });
    }


    private void retrieveDetailsFromDatabase(String propertyId,String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String priceLong = dataSnapshot.child("priceofproperty").getValue(String.class);
                    int pop=0;
                    if (priceLong != null) {
                        try {
                            pop = (int) Long.parseLong(priceLong);
                        } catch (NumberFormatException e) {
                            // Handle the case where priceLong is not a valid long string
                            e.printStackTrace();
                        }


                        TextView priceOfPropertyTextView = findViewById(R.id.ratepernightprice);
                        String priceString = "<b>₹" + (pop*slots)  + "</b>"; // Assuming pop is the price
                        String numericPart = priceString.replaceAll("[^\\d]", ""); // Remove non-numeric characters
                        int price = Integer.parseInt(numericPart); // Convert the numeric part to an integer
//                    Toast.makeText(PaymentActivity.this, priceString, Toast.LENGTH_SHORT).show();
                        CharSequence styledText = Html.fromHtml(priceString);
                        priceOfPropertyTextView.setText(styledText);
                        TextView totalPrice = findViewById(R.id.totalprice);
                        String priceString2 = "<b>₹" + (price + price*(0.2)) + "0" + "</b>";
//                        String pricestring=(price + price*(0.2)+"0");
//                        Intent i=getIntent();
//                        i.putExtra("propertyPrice",pricestring);
                        CharSequence styledText2 = Html.fromHtml(priceString2);
                        totalPrice.setText(styledText2);// Assuming pop is the price
//                        TextView cleaningCharges = findViewById(R.id.cleaningprice);
//                        String priceString3 = "<b>₹" + (pop + pop * 0.2) + "0" + "</b>";
//                        CharSequence styledText3 = Html.fromHtml(priceString3);
//                        totalPrice.setText(styledText3);

                    }
                }
                else {
                    // Handle the case where no property details are found
                    Toast.makeText(PaymentActivity.this, "No property details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(PaymentActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationMessage(String notificationMessage, String userId, String propertyId,String ownerId) {
        DatabaseReference notificationRef = FirebaseDatabase.getInstance().getReference("NotificationMessage");
        String notificationId = notificationRef.push().getKey(); // Generate unique ID for the notification

        if (notificationId != null) {
            DatabaseReference newNotificationRef = notificationRef.child(notificationId);
            newNotificationRef.child("notificationId").setValue(notificationId);

            newNotificationRef.child("notificationMessage").setValue(notificationMessage);
            newNotificationRef.child("userId").setValue(userId);
            newNotificationRef.child("ownerId").setValue(ownerId);

            newNotificationRef.child("propertyId").setValue(propertyId)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Notification message created successfully
                                Toast.makeText(PaymentActivity.this, "Notification message created successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to create notification message
                                Toast.makeText(PaymentActivity.this, "Failed to create notification message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Failed to generate notification ID
            Toast.makeText(PaymentActivity.this, "Failed to generate notification ID", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Payment was successful
                // Handle success, e.g., show a success message to the user
                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(PaymentActivity.this,PropertyRecyclerActivityForUser.class);
                i.putExtra("slot", slots);
                i.putExtra("property_id", propertyId);
                i.putExtra("parent_id", parentId);
                i.putExtra("user_id", userId);
                i.putExtra("userName", userName);
                i.putExtra("owner_id", ownerId);
                i.putExtra("propertyName", propertyName);
                i.putExtra("phoneno", phoneNo);
                i.putExtra("userEmail", userEmail);
//                createNotificationMessage(notificationMessage, userId, propertyId, ownerId);

                startActivity(i);
            } else if (resultCode == RESULT_CANCELED) {
                // Payment was canceled by the user
                // Handle cancellation, e.g., show a message indicating that the payment was canceled
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
            } else {
                // Payment failed or an error occurred
                // Handle failure, e.g., display an error message to the user
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(Charset.defaultCharset()));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
