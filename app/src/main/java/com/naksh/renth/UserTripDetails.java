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
import com.naksh.renth.Models.NotificationModel;
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
    String priceOfProperty;
    private TextView fdate;

    private SeekBar seekBar;
    private TextView progressText;
    String parentId;
    String propertyId;
    String userId;
    String userName;
    String ownerId;
    String propertyName;
    String phoneNo;
    String userEmail;

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
            userId = intent.getStringExtra("user_id");
            userName = intent.getStringExtra("userName");
            ownerId = intent.getStringExtra("owner_id");
            propertyName=intent.getStringExtra("propertyName");
            phoneNo=intent.getStringExtra("phoneno");
            userEmail=intent.getStringExtra("userEmail");
            Toast.makeText(this, "userId:" + userId, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "userName:" + userName, Toast.LENGTH_SHORT).show();

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

//        fromDate = (TextView) findViewById(R.id.fromDate);
//        fromDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                // Create a date picker dialog
//                DatePickerDialog datePickerDialog = new DatePickerDialog(UserTripDetails.this, mDateSetListener, year, month, day);
//                // Show the dialog
//                datePickerDialog.show();
//            }
//        });

//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet:date: " + dayOfMonth + "/" + month + "/" + year);
//                String date = dayOfMonth + "/" + month + "/" + year;
//                Toast.makeText(UserTripDetails.this, "Selected date" + date, Toast.LENGTH_SHORT).show();
//
//                fromDate.setText(date);
//
//            }
//        };

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

                int selectedSlots = binding.np.getValue();
                if (selectedSlots == 0) {
                    // Display a message prompting the user to select a value
                    Toast.makeText(UserTripDetails.this, "Please select the number of slots", Toast.LENGTH_SHORT).show();
                    return; // Exit the method as validation failed
                }
                String guestsText = binding.guests.getText().toString().trim();
                if (guestsText.isEmpty()) {
                    // Display a message prompting the user to fill in the field
                    Toast.makeText(UserTripDetails.this, "Please enter the number of guests", Toast.LENGTH_SHORT).show();
                    return; // Exit the method as validation failed
                }
//                progressDialog.show();

                String selectedDateStr = Objects.requireNonNull(binding.fromDate.getText()).toString();
                int pickerValue = binding.np.getValue();
                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());

                assert intent != null;
                String fromDate = intent.getStringExtra("from_date");
                String toDate = intent.getStringExtra("to_date");

                // Check if the selected date falls within the booking range
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Log.d(TAG, "selectedDateStr: " + selectedDateStr);
                    Log.d(TAG, "fromDate: " + fromDate);
                    Log.d(TAG, "toDate: " + toDate);

                    if (fromDate.equals("Select from date") || toDate.equals("Select to date")) {
                        // Invalid or missing from/to date
//                        progressDialog.dismiss();
                        Toast.makeText(UserTripDetails.this, "Invalid or missing from/to date", Toast.LENGTH_SHORT).show();
                        fromDate="11/11/1111";
                        toDate="11/11/1111";

//                        return; // Exit the method
                    }

                    // Both fromDate and toDate are not equal to "Select from/to date", proceed with date parsing
                    Date selectedDate = sdf.parse(selectedDateStr);
                    Date from = sdf.parse(fromDate);
                    Date to = sdf.parse(toDate);

                    if (selectedDate != null && from != null) {
                        if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
                            // Selected date is equal to or falls within the unavailable range
                            Toast.makeText(UserTripDetails.this, "Selected date is equal to or falls within the unavailable range", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                        } else {
//                            Toast.makeText(UserTripDetails.this, "........", Toast.LENGTH_SHORT).show();
                            progressDialog.show();

                            // Retrieve property details from the database to compare dates
                            DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel").child(propertyId);
                            propertyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String propertyDateStr = dataSnapshot.child("fordate").getValue(String.class);
                                        try {
//                                            Toast.makeText(UserTripDetails.this, "selectedDate="+selectedDate, Toast.LENGTH_SHORT).show();
//
//                                            Toast.makeText(UserTripDetails.this, "propertyDateStr="+propertyDateStr, Toast.LENGTH_SHORT).show();

                                            Date propertyDate = sdf.parse(propertyDateStr);
//                                            Toast.makeText(UserTripDetails.this, "propertyDate="+propertyDate, Toast.LENGTH_SHORT).show();

                                            if (propertyDate != null) {
                                                if (selectedDate.equals(propertyDate)) {
                                                    // Selected date matches the date in the database
                                                    Toast.makeText(UserTripDetails.this, "Selected date matches the date in the database", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Selected date does not match
                                                    saveTripDetails(selectedDateStr, slots, guests);
                                                }
                                            } else {
                                                // Failed to parse property date
                                                Toast.makeText(UserTripDetails.this, "Failed to parse property date", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            progressDialog.dismiss(); // Dismiss the progress dialog
                                            Toast.makeText(UserTripDetails.this, "Failed to parse property date", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Property details not found in the database
                                        Toast.makeText(UserTripDetails.this, "Property details not found", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle database error
                                    progressDialog.dismiss();
                                    Toast.makeText(UserTripDetails.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(UserTripDetails.this, "Date must not be null", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    progressDialog.dismiss(); // Dismiss the progress dialog
                    Toast.makeText(UserTripDetails.this, "Failed to parse date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        private void saveTripDetails (String selectedDateStr,int slots, int guests){
            UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
            databaseReference.child(propertyId).setValue(userTripDetailsModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Start PaymentActivity with necessary intent extras
                                Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
                                intent.putExtra("slot", slots);
                                intent.putExtra("property_id", propertyId);
                                intent.putExtra("parent_id", parentId);
                                intent.putExtra("user_id", userId);
                                intent.putExtra("userName", userName);
                                intent.putExtra("owner_id", ownerId);
                                intent.putExtra("propertyName",propertyName);
                                intent.putExtra("phoneno",phoneNo);
                                intent.putExtra("userEmail",userEmail);
                                startActivity(intent);

                                finish(); // Finish this activity after starting the next one
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


   

    //.....
//        binding.bookingbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                String selectedDateStr = Objects.requireNonNull(binding.fromDate.getText()).toString();
//                Toast.makeText(UserTripDetails.this, selectedDateStr, Toast.LENGTH_SHORT).show();
//                int pickerValue = binding.np.getValue();
//                Log.d(TAG, "Picker value: '" + pickerValue + "'");
//                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
//                Toast.makeText(UserTripDetails.this, "slot when clicked=" + slots, Toast.LENGTH_SHORT).show();
//                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
//                assert intent != null;
//                String fromDate = intent.getStringExtra("from_date");
//                String toDate = intent.getStringExtra("to_date");
//                // Check if the selected date falls within the booking range
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                    Log.d(TAG, "selectedDateStr: " + selectedDateStr);
//                    Log.d(TAG, "fromDate: " + fromDate);
//                    Log.d(TAG, "toDate: " + toDate);
//
//                    if (fromDate.equals("Select from date") || toDate.equals("Select to date")) {
//                        // Invalid or missing from/to date
//                        progressDialog.dismiss();
//                        Toast.makeText(UserTripDetails.this, "Invalid or missing from/to date", Toast.LENGTH_SHORT).show();
//                        return; // Exit the method
//                    }
//
//                    // Both fromDate and toDate are not equal to "Select from/to date", proceed with date parsing
//                    Date selectedDate = sdf.parse(selectedDateStr);
//                    Date from = sdf.parse(fromDate);
//                    Date to = sdf.parse(toDate);
//
//                    if (selectedDate != null && from != null) {
//                        if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
//                            // Selected date is equal to or falls within the unavailable range
//                            Toast.makeText(UserTripDetails.this, "Selected date is equal to or falls within the unavailable range", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        } else {
//                            // Save trip details to the TripDetailsModel node
//                            UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
//                            databaseReference.child(propertyId).setValue(userTripDetailsModel)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                progressDialog.dismiss();
//                                                // Start PaymentActivity with necessary intent extras
//                                                Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
//                                                intent.putExtra("slot", slots);
//                                                intent.putExtra("property_id", propertyId);
//                                                intent.putExtra("parent_id", parentId);
//                                                intent.putExtra("user_id", userId);
//                                                intent.putExtra("userName", userName);
//                                                startActivity(intent);
//                                                finish(); // Finish this activity after starting the next one
//                                            } else {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                        }
//                    } else {
//                        Toast.makeText(UserTripDetails.this, "Date must not be null", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss(); // Dismiss the progress dialog
//                    Toast.makeText(UserTripDetails.this, "Failed to parse date", Toast.LENGTH_SHORT).show();
//                } finally {
//                    UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
//                    databaseReference.child(propertyId).setValue(userTripDetailsModel)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        progressDialog.dismiss();
//                                        // Start PaymentActivity with necessary intent extras
//                                        Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
//                                        intent.putExtra("slot", slots);
//                                        intent.putExtra("property_id", propertyId);
//                                        intent.putExtra("parent_id", parentId);
//                                        intent.putExtra("user_id", userId);
//                                        intent.putExtra("userName", userName);
//                                        startActivity(intent);
//                                        finish(); // Finish this activity after starting the next one
//                                    } else {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }
//        });
//    }

        //........


//        binding.bookingbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                String selectedDateStr = Objects.requireNonNull(binding.fromDate.getText()).toString();
//                Toast.makeText(UserTripDetails.this, selectedDateStr, Toast.LENGTH_SHORT).show();
//                int pickerValue = binding.np.getValue();
//                Log.d(TAG, "Picker value: '" + pickerValue + "'");
//                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
//                Toast.makeText(UserTripDetails.this, "slot when clicked="+slots, Toast.LENGTH_SHORT).show();
//                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
//                assert intent != null;
//                String fromDate = intent.getStringExtra("from_date");
//                String toDate = intent.getStringExtra("to_date");
//                // Check if the selected date falls within the booking range
//                if (fromDate.equals("Select from date") || toDate.equals("Select to date")) {
//                    progressDialog.dismiss();
//                    Toast.makeText(UserTripDetails.this, "Invalid or missing from/to date", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method
//                }
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                    Log.d(TAG, "selectedDateStr: " + selectedDateStr);
//                    Log.d(TAG, "fromDate: " + fromDate);
//                    Log.d(TAG, "toDate: " + toDate);
//
//                    if (fromDate.equals("Select from date") && toDate.equals("Select to date")) {
//                        // Check if fromDate and toDate contain only digits and slashes (indicating valid date format)
//                        // Both fromDate and toDate contain only digits and slashes, proceed with date parsing
//                        Date selectedDate = sdf.parse(selectedDateStr);
//                        Date from = sdf.parse(fromDate);
//                        Date to = sdf.parse(toDate);
//
//
//                        if (selectedDate != null && from != null) {
//                            if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
//                                // Selected date is equal to from date
//                                Toast.makeText(UserTripDetails.this, "Selected date is equal to unavailable date", Toast.LENGTH_SHORT).show();
//                                progressDialog.dismiss();
//                            } else {
//                                // Save trip details to the TripDetailsModel node
//                                UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
//                                databaseReference.child(propertyId).setValue(userTripDetailsModel)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
////                                        progressDialog.dismiss(); // Dismiss the progress dialog
//                                                if (task.isSuccessful()) {
//                                                    progressDialog.dismiss();
//
//                                                    Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
//                                                    intent.putExtra("slot", slots);
//                                                    Toast.makeText(UserTripDetails.this, "slot=" + slots, Toast.LENGTH_SHORT).show();
//                                                    intent.putExtra("property_id", propertyId);
//                                                    intent.putExtra("parent_id", parentId);
//                                                    intent.putExtra("user_id", userId);
//                                                    intent.putExtra("userName", userName);
//
//                                                    Toast.makeText(UserTripDetails.this, "userId=" + userId + "userName=" + userName, Toast.LENGTH_SHORT).show();
//                                                    startActivity(intent);
//                                                    finish(); // Finish this activity after starting the next one
//                                                } else {
//                                                    progressDialog.dismiss(); // Dismiss the progress dialog
//
//                                                    Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                            }
//                        } else {
//                            Toast.makeText(UserTripDetails.this, "Date must not be null", Toast.LENGTH_SHORT).show();
//
//                        }
//                    } else {
//
//                        // Check if fromDate and toDate contain only digits and slashes (indicating valid date format)
//                        // Both fromDate and toDate contain only digits and slashes, proceed with date parsing
//                        Date selectedDate = sdf.parse(selectedDateStr);
//                        Date from = sdf.parse(fromDate);
//                        Date to = sdf.parse(toDate);
//
//
//                        if (selectedDate != null && from != null) {
//                            if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
//                                // Selected date is equal to from date
//                                Toast.makeText(UserTripDetails.this, "Selected date is equal to unavailable date", Toast.LENGTH_SHORT).show();
//                                progressDialog.dismiss();
//                            } else {
//                                // Save trip details to the TripDetailsModel node
//                                UserTripDetailsModel userTripDetailsModel = new UserTripDetailsModel(selectedDateStr, slots, guests);
//                                databaseReference.child(propertyId).setValue(userTripDetailsModel)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
////                                        progressDialog.dismiss(); // Dismiss the progress dialog
//                                                if (task.isSuccessful()) {
//                                                    progressDialog.dismiss();
//
//                                                    Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
//                                                    intent.putExtra("slot", slots);
//                                                    Toast.makeText(UserTripDetails.this, "slot=" + slots, Toast.LENGTH_SHORT).show();
//                                                    intent.putExtra("property_id", propertyId);
//                                                    intent.putExtra("parent_id", parentId);
//                                                    intent.putExtra("user_id", userId);
//                                                    intent.putExtra("userName", userName);
//
//                                                    Toast.makeText(UserTripDetails.this, "userId=" + userId + "userName=" + userName, Toast.LENGTH_SHORT).show();
//                                                    startActivity(intent);
//                                                    finish(); // Finish this activity after starting the next one
//                                                } else {
//                                                    progressDialog.dismiss(); // Dismiss the progress dialog
//
//                                                    Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                            }
//                        } else {
//                            Toast.makeText(UserTripDetails.this, "Date must not be null", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }
//                    catch (ParseException e) {
//                        e.printStackTrace();
////                        progressDialog.dismiss(); // Dismiss the progress dialog
//
//                        // Handle parsing error
//                    }
//                finally {
//                    Toast.makeText(UserTripDetails.this, "inside finally block", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//
//                    Intent intent = new Intent(UserTripDetails.this, PaymentActivity.class);
//                    intent.putExtra("slot", slots);
//                    Toast.makeText(UserTripDetails.this, "slot=" + slots, Toast.LENGTH_SHORT).show();
//                    intent.putExtra("property_id", propertyId);
//                    intent.putExtra("parent_id", parentId);
//                    intent.putExtra("user_id", userId);
//                    intent.putExtra("userName", userName);
//
//                    Toast.makeText(UserTripDetails.this, "userId=" + userId + "userName=" + userName, Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                    finish(); // Finish this activity after starting the next one
//                }
//            }
//        });
//        // Declare selectedDate as a String
//        String selectedDate = fromDate.getText().toString();
//
//    }

    private void showDatePickerDialog(TextView textView) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(UserTripDetails.this,
                (view, year1, month1, dayOfMonth) -> {
                    month1 = month1 + 1;
                    String date = dayOfMonth + "/" + month1 + "/" + year1;

                    // Get the selected date as a Calendar object
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, month1 - 1, dayOfMonth); // Month is 0-based

                    // Get the current date
                    Calendar currentDate = Calendar.getInstance();

                    // Compare the selected date with the current date
                    if (selectedDate.before(currentDate)) {
                        // Show a toast indicating that the selected date is invalid
                        Toast.makeText(UserTripDetails.this, "Please choose a date after current date", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set the selected date to the text view
                        textView.setText(date);
                    }
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
                    String priceLong = dataSnapshot.child("priceofproperty").getValue(String.class);
                    int pop = 0; // Default value
                    if (priceLong != null) {
                        try {
                            pop = (int) Long.parseLong(priceLong);
                        } catch (NumberFormatException e) {
                            // Handle the case where priceLong is not a valid long string
                            e.printStackTrace();
                        }
                    }

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
