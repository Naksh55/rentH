package com.naksh.renth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {
String propertyId,parentId;
int slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent=getIntent();
        if(intent!= null) {
            propertyId = intent.getStringExtra("property_id");
            slots = intent.getIntExtra("slot", 0); // 0 is the default value if "slot" extra is not found
            if (slots == 0) {
                Toast.makeText(this, "Slots is null", Toast.LENGTH_SHORT).show();
                return; // Exit the method as slots is null
            } else {
                Toast.makeText(this, "Slots: " + slots, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, String.valueOf(slots), Toast.LENGTH_SHORT).show();

            if (propertyId != null) {
                parentId = intent.getStringExtra("parent_id");
                retrieveDetailsFromDatabase(propertyId, parentId);
            }
            else{
                Toast.makeText(this, "property_id is null", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }
        Button payment=findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this,LoginScreen.class);
                startActivity(intent);
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
                    Long priceLong = dataSnapshot.child("priceofproperty").getValue(Long.class);
                    if (slots == 0) {
                        Toast.makeText(PaymentActivity.this, "slots cannot be null", Toast.LENGTH_SHORT).show();
                    } else {
//                    Toast.makeText(UserTripDetails.this, Math.toIntExact(priceLong), Toast.LENGTH_SHORT).show();
                        int pop = priceLong != null ? priceLong.intValue() : 0; // Default value if priceLong is null
                        int numOfSlots = Integer.parseInt(String.valueOf(slots));

                        TextView priceOfPropertyTextView = findViewById(R.id.ratepernightprice);
                        String priceString = "<b>₹" + (pop*slots)  + "</b>"; // Assuming pop is the price
                        String numericPart = priceString.replaceAll("[^\\d]", ""); // Remove non-numeric characters
                        int price = Integer.parseInt(numericPart); // Convert the numeric part to an integer
//                    Toast.makeText(PaymentActivity.this, priceString, Toast.LENGTH_SHORT).show();
                        CharSequence styledText = Html.fromHtml(priceString);
                        priceOfPropertyTextView.setText(styledText);
                        TextView totalPrice = findViewById(R.id.totalprice);
                        String priceString2 = "<b>₹" + (price + price*(0.2)) + "0" + "</b>";
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
}