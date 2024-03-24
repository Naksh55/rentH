package com.naksh.renth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.UserTripDetailsModel;
import com.naksh.renth.databinding.ActivityUserTripDetailsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UserTripDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityUserTripDetailsBinding binding;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView fromDate;
    private TextView slots;
    private TextView guests;
    int priceOfProperty;
    private TextView fdate;

    private SeekBar seekBar;
    private TextView progressText;
    String parentId;
    String propertyId;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTripDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        TextView selectedDate = findViewById(R.id.fromDate);
        Intent intent = getIntent();
        if (intent != null) {

            String fromDate = intent.getStringExtra("from_date");
            String toDate = intent.getStringExtra("to_date");
            propertyId = intent.getStringExtra("property_id");
            if (propertyId != null) {
                parentId = intent.getStringExtra("parent_id");

                retrieveDetailsFromDatabase(propertyId, parentId);

            } else {

                Toast.makeText(this, "Property ID is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where intent is null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }


        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(v -> showDatePickerDialog(fromDate));
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserTripDetailsModel");

        progressDialog = new ProgressDialog(UserTripDetails.this);
        progressDialog.setTitle("Saving trip details");
        progressDialog.setMessage("Make payment");

        fromDate = (TextView) findViewById(R.id.fromDate);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                // Create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserTripDetails.this, mDateSetListener, year, month, day);
                // Show the dialog
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet:date: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                Toast.makeText(UserTripDetails.this, "Selected date"+date, Toast.LENGTH_SHORT).show();

                fromDate.setText(date);

            }
        };

        NumberPicker numberPicker = findViewById(R.id.np);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(6);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Display the selected number using a Toast
                Toast.makeText(UserTripDetails.this, "Selected number: " + newVal, Toast.LENGTH_SHORT).show();
            }
        });
        binding.bookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String selectedDateStr = Objects.requireNonNull(binding.fromDate.getText()).toString();
                Toast.makeText(UserTripDetails.this, selectedDateStr, Toast.LENGTH_SHORT).show();
                int pickerValue = binding.np.getValue();
                Log.d(TAG, "Picker value: '" + pickerValue + "'");
                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
                Toast.makeText(UserTripDetails.this, "slot when clicked="+slots, Toast.LENGTH_SHORT).show();
                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
                assert intent != null;
                String fromDate = intent.getStringExtra("from_date");
                String toDate = intent.getStringExtra("to_date");
                // Check if the selected date falls within the booking range
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date selectedDate = sdf.parse(selectedDateStr);
                    Date from = sdf.parse(fromDate);
                    Date to = sdf.parse(toDate);

                    if (selectedDate != null && from != null ) {
                        if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
                            // Selected date is equal to from date
                            Toast.makeText(UserTripDetails.this, "Selected date is equal to unavailable date", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            // Save trip details to the TripDetailsModel node
                            UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
                            databaseReference.child(propertyId).setValue(userTripDetailsModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
//                                        progressDialog.dismiss(); // Dismiss the progress dialog
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();

                                                Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
                                                intent.putExtra("slot",slots);
                                                Toast.makeText(UserTripDetails.this, "slot="+slots, Toast.LENGTH_SHORT).show();
                                                intent.putExtra("property_id",propertyId);
                                                intent.putExtra("parent_id",parentId);

                                                startActivity(intent);
                                                finish(); // Finish this activity after starting the next one
                                            } else {
                                                progressDialog.dismiss(); // Dismiss the progress dialog

                                                Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                    else{
                            Toast.makeText(UserTripDetails.this, "Date must not be null", Toast.LENGTH_SHORT).show();

                        }
                }
                    catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing error
                    }
            }
        });
        // Declare selectedDate as a String
        String selectedDate = fromDate.getText().toString();

    }
    private void showDatePickerDialog(TextView textView) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(UserTripDetails.this,
                (view, year1, month1, dayOfMonth) -> {
                    month1 = month1 + 1;
                    String date = dayOfMonth + "/" + month1 + "/" + year1;
                    textView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void retrieveDetailsFromDatabase(String propertyId,String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    Long priceLong = dataSnapshot.child("priceofproperty").getValue(Long.class);
//                    Toast.makeText(UserTripDetails.this, Math.toIntExact(priceLong), Toast.LENGTH_SHORT).show();
                    int pop = priceLong != null ? priceLong.intValue() : 0; // Default value if priceLong is null

                    TextView priceOfPropertyTextView = findViewById(R.id.priceofprperty);
                    String priceString = "Price: <b>₹" + pop + ".00"+"</b>"; // Assuming pop is the price
//                    Toast.makeText(UserTripDetails.this, priceString, Toast.LENGTH_SHORT).show();
                    CharSequence styledText = Html.fromHtml(priceString);
                    priceOfPropertyTextView.setText(styledText);

//b
                } else {
                    // Handle the case where no property details are found
                    Toast.makeText(UserTripDetails.this, "No property details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(UserTripDetails.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}