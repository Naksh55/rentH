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
            Toast.makeText(UserTripDetails.this,"from date="+fromDate, Toast.LENGTH_SHORT).show();

            Toast.makeText(UserTripDetails.this, "to date="+toDate, Toast.LENGTH_SHORT).show();

            String propertyId = intent.getStringExtra("property_id");
            if (propertyId != null) {
                parentId = intent.getStringExtra("parent_id");

//                // Retrieve booking dates from intent extras
//                String fromDate = intent.getStringExtra("from_date");
//                String toDate = intent.getStringExtra("to_date");
                retrieveDetailsFromDatabase(propertyId, parentId);
                // Perform any necessary operations with fromDate and toDate
            } else {
                // Handle the case where propertyId is null
                Toast.makeText(this, "Property ID is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where intent is null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }

        // Your existing code for setting up UI components and listeners goes here...

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
                Toast.makeText(UserTripDetails.this, date, Toast.LENGTH_SHORT).show();

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
                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
                assert intent != null;
                String fromDate = intent.getStringExtra("from_date");
                String toDate = intent.getStringExtra("to_date");
                Toast.makeText(UserTripDetails.this, "everything is fine till now", Toast.LENGTH_SHORT).show();
                // Check if the selected date falls within the booking range
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date selectedDate = sdf.parse(selectedDateStr);
                    Date from = sdf.parse(fromDate);

                    if (selectedDate != null && from != null && selectedDate.equals(from)) {
                        // Selected date is equal to from date
                        Toast.makeText(UserTripDetails.this, "Selected date is equal to unavailable date", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        // Save trip details to the TripDetailsModel node
                        UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
                        databaseReference.setValue(userTripDetailsModel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss(); // Dismiss the progress dialog
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
                                            startActivity(intent);
                                            finish(); // Finish this activity after starting the next one
                                        } else {
                                            Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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
// Method to check if the selected date falls within the booking range
//private void isDateWithinRange(String selectedDateStr, String fromDate, String toDate) {
    // Parse the fromDate and toDate strings into Date objects for comparison
//    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//    try {
//        Date selected = sdf.parse(selectedDateStr);
//        Date from = sdf.parse(fromDate);
//        Date to = sdf.parse(toDate);
//
//        // Check if the selected date falls within the range
//        assert selected != null;
//        return !selected.before(from) && !selected.after(to);
//    } catch (ParseException e) {
//        e.printStackTrace();
//        return false; // Handle parsing error, return false as default

//}







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
        DatabaseReference database = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                .getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    Long priceLong = dataSnapshot.child("priceofproperty").getValue(Long.class);
                    int pop = priceLong != null ? priceLong.intValue() : 0; // Default value if priceLong is null
                    TextView priceOfPropertyTextView = findViewById(R.id.priceofprperty);
                    String priceString = "Price: <b>₹" + pop + "</b>"; // Assuming pop is the price
                    CharSequence styledText = Html.fromHtml(priceString);
                    priceOfPropertyTextView.setText(styledText);

//
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