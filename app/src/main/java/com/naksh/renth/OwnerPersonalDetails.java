//package com.naksh.renth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.naksh.renth.Models.OwnerPersonalDetailsModel;
//import com.naksh.renth.Models.UserPersonalDetailsModel;
//import com.naksh.renth.databinding.ActivityOwnerPersonalDetailsBinding;
//import com.naksh.renth.databinding.ActivityUserPersonalDetailsBinding;
//
//import java.util.Objects;
//
//public class OwnerPersonalDetails extends AppCompatActivity {
//    ActivityOwnerPersonalDetailsBinding binding;
//    private FirebaseAuth mAuth;
//    ProgressDialog progressDialog;
//    DatabaseReference usersRef;
//    RadioGroup genderRadioGroup2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOwnerPersonalDetailsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Initialize FirebaseAuth instance
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        usersRef = database.getReference("OwnerPersonalDetailsModel"); // Initialize usersRef
//        progressDialog = new ProgressDialog(OwnerPersonalDetails.this);
//        progressDialog.setTitle("Saving personal details");
//        progressDialog.setMessage("Going to home...");
//        genderRadioGroup2 = findViewById(R.id.ownergender);
//        binding.onextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                String name = Objects.requireNonNull(binding.nameet.getText()).toString();
//                int age = Integer.parseInt(Objects.requireNonNull(binding.ageet.getText()).toString());
//                String gender = getSelectedGender();
//                String phoneNumber = binding.ophonenoet.getText().toString();
//
//
//
//                // Generate a unique user ID (e.g., using push() method)
//                String userId = usersRef.push().getKey();
//                OwnerPersonalDetailsModel ownerPersonalDetailsModel = new OwnerPersonalDetailsModel(name, age, gender, phoneNumber);
//                assert userId != null;
//                usersRef.child(userId).setValue(ownerPersonalDetailsModel)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                progressDialog.dismiss(); // Dismiss the progress dialog
//                                if (task.isSuccessful()) {
//                                    Intent intent = new Intent(OwnerPersonalDetails.this, OwnerHomeActivity.class);
//                                    startActivity(intent);
//                                    finish(); // Finish this activity after starting the next one
//                                } else {
//                                    Toast.makeText(OwnerPersonalDetails.this, "Failed to create user", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//    }
//
//    private String getSelectedGender() {
//        int selectedId = genderRadioGroup2.getCheckedRadioButtonId();
//        RadioButton selectedRadioButton = findViewById(selectedId);
//        if (selectedRadioButton != null) {
//            return selectedRadioButton.getText().toString();
//        } else {
//            Toast.makeText(OwnerPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
//            return ""; // Return an empty string or handle the case where no gender is selected
//        }
//    }
//}
//
//
//
package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naksh.renth.Models.OwnerPersonalDetailsModel;
import com.naksh.renth.databinding.ActivityOwnerPersonalDetailsBinding;

import java.util.Objects;

public class OwnerPersonalDetails extends AppCompatActivity {
    ActivityOwnerPersonalDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference usersRef;
    RadioGroup genderRadioGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("OwnerPersonalDetailsModel"); // Initialize usersRef
        progressDialog = new ProgressDialog(OwnerPersonalDetails.this);
        progressDialog.setTitle("Saving personal details");
        progressDialog.setMessage("Going to home...");
        genderRadioGroup2 = findViewById(R.id.ownergender);
        binding.onextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name = Objects.requireNonNull(binding.nameet.getText()).toString();
                int age = Integer.parseInt(Objects.requireNonNull(binding.ageet.getText()).toString());
                String gender = getSelectedGender();
                String phoneNumber = binding.ophonenoet.getText().toString();
                String email = binding.oemailet.getText().toString();


                // Generate a unique user ID (e.g., using push() method)
                String userId = usersRef.push().getKey();
                OwnerPersonalDetailsModel ownerPersonalDetailsModel = new OwnerPersonalDetailsModel(name, age, gender, phoneNumber,email);
                assert userId != null;
                usersRef.child(userId).setValue(ownerPersonalDetailsModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss(); // Dismiss the progress dialog
                                if (task.isSuccessful()) {
                                    // Pass owner's name to OwnerHomeActivity
                                    String ownername = binding.nameet.getText().toString();
                                    String ownerEmail = binding.oemailet.getText().toString();
                                    String ownerPhone = binding.ophonenoet.getText().toString();


                                    Intent intent = new Intent(OwnerPersonalDetails.this, OwnerHomeActivity.class);
                                    intent.putExtra("ownerName", ownername);
                                    intent.putExtra("ownerEmail", ownerEmail);
                                    intent.putExtra("ownerPhone", ownerPhone);
                                    startActivity(intent);

                                    finish(); // Finish this activity after starting the next one
                                } else {
                                    Toast.makeText(OwnerPersonalDetails.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private String getSelectedGender() {
        int selectedId = genderRadioGroup2.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        } else {
            Toast.makeText(OwnerPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
            return ""; // Return an empty string or handle the case where no gender is selected
        }
    }
}
