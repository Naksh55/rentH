package com.naksh.renth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.naksh.renth.Models.TripDetailsModel;
import com.naksh.renth.Models.UserPersonalDetailsModel;
import com.naksh.renth.databinding.ActivityPropertyDetailsBinding;
import com.naksh.renth.databinding.ActivityTripDetailsBinding;

import java.util.Calendar;
import java.util.Objects;

public class TripDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityTripDetailsBinding binding;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView fromDate;
    private TextView slots;
    private TextView guests;




    private TextView fdate;

    private SeekBar seekBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fromDate = findViewById(R.id.fromDate);
        fromDate.setOnClickListener(v -> showDatePickerDialog(fromDate));
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TripDetailsModel");

        progressDialog = new ProgressDialog(TripDetails.this);
        progressDialog.setTitle("Saving trip details");
        progressDialog.setMessage("Make payment");

//        seekBar = findViewById(R.id.sliderSB);
//        progressText = findViewById(R.id.guests);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Called when progress is changed
//                progressText.setText(" " + progress);
//            }

//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        fromDate = (TextView) findViewById(R.id.fromDate);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                // Create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(TripDetails.this, mDateSetListener, year, month, day);

                // Show the dialog
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG, "onDateSet:date: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
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
                Toast.makeText(TripDetails.this, "Selected number: " + newVal, Toast.LENGTH_SHORT).show();
            }
        });

//        binding.bookingbutton.setOnClickListener(v -> {
//            progressDialog.show();
//            if (selectedImageUri != null) {
//                uploadImageToFirebaseStorage(selectedImageUri);
//            } else {
//                storePropertyDetails(null);
//            }
//        });

        binding.bookingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String fromDate = Objects.requireNonNull(binding.fromDate.getText()).toString();
                int pickerValue = binding.np.getValue();
                Log.d(TAG, "Picker value: '" + pickerValue + "'");
                int slots = Integer.parseInt(String.valueOf(pickerValue).trim());
                int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());


                // Generate a unique user ID (e.g., using push() method)
                String userId = databaseReference.push().getKey();
                TripDetailsModel tripDetailsModel = new TripDetailsModel(fromDate, slots, guests);
                assert userId != null;
                databaseReference.child(userId).setValue(tripDetailsModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss(); // Dismiss the progress dialog
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(TripDetails.this, LoginScreen.class);
                                    startActivity(intent);
                                    finish(); // Finish this activity after starting the next one
                                } else {
                                    Toast.makeText(TripDetails.this, "Failed to save trip details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    private void showDatePickerDialog(TextView textView) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TripDetails.this,
                (view, year1, month1, dayOfMonth) -> {
                    month1 = month1 + 1;
                    String date = dayOfMonth + "/" + month1 + "/" + year1;
                    textView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

//    private void storePropertyDetails(String imageUrl) {
//        String fromDate = Objects.requireNonNull(binding.fromDate.getText()).toString();
//        int guests = Integer.parseInt(Objects.requireNonNull(binding.guests.getText()).toString());
//        int slots = Integer.parseInt(Objects.requireNonNull(binding.slots.getText()).toString());
//        String propertyId = databaseReference.push().getKey();
//
//
//
//        TripDetailsModel tripDetailsModel;
//
//
//
//        assert propertyId != null;
//        databaseReference.child(propertyId).setValue(tripDetailsModel)
//                .addOnCompleteListener(task -> {
//                    progressDialog.dismiss();
//                    if (task.isSuccessful()) {
//                        Intent intent=new Intent(TripDetails.this,PropertyRecyclerActivityForUser.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(TripDetails.this, "Failed to store property details", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
