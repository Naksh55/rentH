package com.naksh.renth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
    ImageView dateImg;
    Dialog myDialog;
    int slot;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTripDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDialog = new Dialog(this);
//        TextView selectedDate = findViewById(R.id.fromDate);
        Intent intent = getIntent();
        if (intent != null) {
            Animation animation = AnimationUtils.loadAnimation(UserTripDetails.this, R.anim.slide_left_to_right);
            TextView tripdetails=findViewById(R.id.tripdetails);
            tripdetails.startAnimation(animation);
            TextView fdate=findViewById(R.id.fromDate);
            fdate.startAnimation(animation);
            TextView price=findViewById(R.id.priceofprperty);
            price.startAnimation(animation);
            TextView slot=findViewById(R.id.slots);
            slot.startAnimation(animation);
            TextView guests=findViewById(R.id.guests);
            guests.startAnimation(animation);
            String fromDate = intent.getStringExtra("from_date");
            String toDate = intent.getStringExtra("to_date");
            userId = intent.getStringExtra("user_id");
            userName = intent.getStringExtra("userName");
            ownerId = intent.getStringExtra("owner_id");
            propertyName = intent.getStringExtra("propertyName");
            phoneNo = intent.getStringExtra("phoneno");
            userEmail = intent.getStringExtra("userEmail");
            priceOfProperty=intent.getStringExtra("priceofproperty");
            Toast.makeText(this, "price:" +priceOfProperty, Toast.LENGTH_SHORT).show();

//            Toast.makeText(this, "userId:" + userId, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "userName:" + userName, Toast.LENGTH_SHORT).show();

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
        dateImg = findViewById(R.id.dateImg);
        dateImg.setOnClickListener(v -> showDatePickerDialog(fromDate));
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserTripDetailsModel");

        progressDialog = new ProgressDialog(UserTripDetails.this);
        progressDialog.setTitle("Saving trip details");
        progressDialog.setMessage("Make payment");
        NumberPicker numberPicker = findViewById(R.id.np);
        numberPicker.setMinValue(0);
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
                retrieveDetailsFromDatabase2(propertyId, parentId);

                int selectedSlots = binding.np.getValue();
                if (selectedSlots == 0) {
                    // Display a message prompting the user to select a value
                    Toast.makeText(UserTripDetails.this, "Please select the number of slots", Toast.LENGTH_SHORT).show();
                    return; // Exit the method as validation failed
                }
                String guestsText = binding.guests.getText().toString().trim();
                int guest;
                try {
                    guest = Integer.parseInt(guestsText);
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    Toast.makeText(UserTripDetails.this, "Please enter a valid number for guests", Toast.LENGTH_SHORT).show();
                    return; // Exit the method as validation failed
                }

                if (guestsText.isEmpty()) {
                    // Display a message prompting the user to fill in the field
                    Toast.makeText(UserTripDetails.this, "Please enter the number of guests", Toast.LENGTH_SHORT).show();
                    return; // Exit the method as validation failed
                }

//                if (guest > 10) {
//                    Toast.makeText(UserTripDetails.this, "Guest count should not be less than 10", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String selectedDateStr = Objects.requireNonNull(binding.fromDate.getText()).toString();
                Intent i=getIntent();
                i.putExtra("fDate",selectedDateStr);
                if(selectedDateStr.equals("Choose booking date!")){
                    Toast.makeText(UserTripDetails.this, "select a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();


//                Toast.makeText(UserTripDetails.this, "selectedDate="+selectedDateStr, Toast.LENGTH_SHORT).show();
                int pickerValue = binding.np.getValue();
                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
                if(guests>10){
                    Toast.makeText(UserTripDetails.this, "Guest count should not exceed 10", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    return; // Exit the method as validation failed
                }
                progressDialog.show();

                assert intent != null;
                String fromDate = intent.getStringExtra("from_date");
                String toDate = intent.getStringExtra("to_date");

                // Check if the selected date falls within the booking range
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Log.d(TAG, "selectedDateStr: " + selectedDateStr);
                    Log.d(TAG, "fromDate: " + fromDate);
                    Log.d(TAG, "toDate: " + toDate);
//                    Toast.makeText(UserTripDetails.this, "fromDate inside try="+fromDate, Toast.LENGTH_SHORT).show();
                    if (fromDate.equals("Choose booking date!")) {
                        Toast.makeText(UserTripDetails.this, "Invalid or missing from/to date", Toast.LENGTH_SHORT).show();
                        return; // Exit the method as validation failed

                    }
                    Toast.makeText(UserTripDetails.this, "selected date="+selectedDateStr, Toast.LENGTH_SHORT).show();
                    if (fromDate.equals("Select from date") || toDate.equals("Select to date")||selectedDateStr.equals("Choose booking date!")) {
                        // Invalid or missing from/to date
//                        progressDialog.dismiss();
//                        Toast.makeText(UserTripDetails.this, "Invalid or missing from/to date", Toast.LENGTH_SHORT).show();
                        selectedDateStr="11/11/1111";
                        fromDate="11/11/1111";
                        toDate="11/11/1111";

//                        return; // Exit the method
                    }
//                    Toast.makeText(UserTripDetails.this, "fdate=="+fromDate, Toast.LENGTH_SHORT).show();
                    // Both fromDate and toDate are not equal to "Select from/to date", proceed with date parsing
                    Date selectedDate = sdf.parse(selectedDateStr);
                    Toast.makeText(UserTripDetails.this, "sDate=="+selectedDate, Toast.LENGTH_SHORT).show();

                    Date from = sdf.parse(fromDate);
                    Date to = sdf.parse(toDate);
                    Toast.makeText(UserTripDetails.this, "fdate=="+fromDate, Toast.LENGTH_SHORT).show();

                    if (selectedDate != null && from != null) {
                        if (selectedDate.equals(from) || (selectedDate.after(from) && selectedDate.before(to)) || selectedDate.equals(to)) {
                            // Selected date is equal to or falls within the unavailable range
                            Toast.makeText(UserTripDetails.this, "Selected date is equal to or falls within the unavailable range", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
//                            progressDialog.dismiss();
                        } else {
//                            Toast.makeText(UserTripDetails.this, "........", Toast.LENGTH_SHORT).show();
                            progressDialog.show();

                            // Retrieve property details from the database to compare dates
                            DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel").child(propertyId);
                            String finalSelectedDateStr = selectedDateStr;
                            propertyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String propertyDateStr = dataSnapshot.child("fordate").getValue(String.class);
                                        String pop=dataSnapshot.child("priceofproperty").getValue(String.class);
                                        Intent i=getIntent();
                                        i.putExtra("priceofproperty",pop);
                                        Toast.makeText(UserTripDetails.this, "fordate="+propertyDateStr, Toast.LENGTH_SHORT).show();
                                        try {
//                                            Toast.makeText(UserTripDetails.this, "selectedDate="+selectedDate, Toast.LENGTH_SHORT).show();
//
//                                            Toast.makeText(UserTripDetails.this, "propertyDateStr="+propertyDateStr, Toast.LENGTH_SHORT).show();

                                            Date propertyDate = sdf.parse(propertyDateStr);
                                            Toast.makeText(UserTripDetails.this, "propertyDate="+propertyDate, Toast.LENGTH_SHORT).show();

                                            if (propertyDate != null) {
                                                if (selectedDate.equals(propertyDate)) {
                                                    // Selected date matches the date in the database
                                                    Toast.makeText(UserTripDetails.this, "Selected date matches the date in the database", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Selected date does not match
                                                    saveTripDetails(finalSelectedDateStr, slots, guests);
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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Slot system")
                .setMessage("The working of Slots System of our company is like, a person can book a single slot if  He/She do not will to live a whole day in a particular property. A single slot is of duration 7 Hours and after every slot there is a cleaning time of 1 Hour for property. A person also can book whole day by booking 3 slots which is 21 Hours, we will provide 1 hour complementary and there will be cleaning time of 2 Hours. So, the total time will be 24 Hours. For booking 2 days a person can book 6 slots which is 42 hours and we will provide them 4 Hours complementary and 2 hours will be for cleaning. So, the total time will be 48 Hours")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle logout action
                        Toast.makeText(UserTripDetails.this, "Going back", Toast.LENGTH_SHORT).show();

                    }
                });

        final AlertDialog logoutConfirmationDialog = alertDialogBuilder.create();
        ImageView infoImg = findViewById(R.id.infoImg);
        infoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show logout confirmation dialog
                logoutConfirmationDialog.show();
            }
        });
    }

    private void saveTripDetails(String selectedDateStr, int slots, int guests) {
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
                            intent.putExtra("propertyName", propertyName);
                            intent.putExtra("phoneno", phoneNo);
                            intent.putExtra("userEmail", userEmail);
                            intent.putExtra("fDate",selectedDateStr);
                            intent.putExtra("priceofproperty",priceOfProperty);

//                            Toast.makeText(UserTripDetails.this, "selectedDate="+selectedDateStr, Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            finish(); // Finish this activity after starting the next one
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(UserTripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void retrieveDetailsFromDatabase2(String propertyId,String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String priceLong = dataSnapshot.child("priceofproperty").getValue(String.class);
                    Toast.makeText(UserTripDetails.this, "priceofproperty="+priceLong, Toast.LENGTH_SHORT).show();
                    Intent i=getIntent();
                    i.putExtra("price",priceLong);
                    int pop = 0; // Default value
                    if (priceLong != null) {
                        try {
                            pop = (int) Long.parseLong(priceLong);
                        } catch (NumberFormatException e) {
                            // Handle the case where priceLong is not a valid long string
                            e.printStackTrace();
                        }
                    }
                    String priceString = "<b>₹" + (pop*slot)  + "</b>"; // Assuming pop is the price
                    String numericPart = priceString.replaceAll("[^\\d]", ""); // Remove non-numeric characters
                    int price = Integer.parseInt(numericPart); // Convert the numeric part to an integer
//                    TextView priceOfPropertyTextView = findViewById(R.id.priceofprperty);
                        String pricestring=(price + price*(0.2)+"0");
                    Toast.makeText(UserTripDetails.this, "price==="+pricestring, Toast.LENGTH_SHORT).show();
                    Intent intent=getIntent();
                    intent.putExtra("pp",pricestring);
//                    String priceString = "Price: <b>₹" + pop + ".00"+"</b>"; // Assuming pop is the price

//                    Toast.makeText(UserTripDetails.this, priceString, Toast.LENGTH_SHORT).show();
//                    CharSequence styledText = Html.fromHtml(priceString);
//                    priceOfPropertyTextView.setText(styledText);

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

    private void retrieveDetailsFromDatabase(String propertyId,String parentId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("PropertyDetailsModel");

        database.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve property details
                    String priceLong = dataSnapshot.child("priceofproperty").getValue(String.class);
                    Toast.makeText(UserTripDetails.this, "priceofproperty="+priceLong, Toast.LENGTH_SHORT).show();
                    Intent i=getIntent();
                    i.putExtra("price",priceLong);
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
                    String pricestring=(pop + pop*(0.2)+"0");
                    Intent intent=getIntent();
                    intent.putExtra("propertyPrice",pricestring);
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
    public  void showPopup(View v){
        TextView textView;
        ImageView imageView;
        Button btn;
        myDialog.setContentView(R.layout.custompopup);
        btn=(Button)myDialog.findViewById(R.id.btnupdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

}
