package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.UserPersonalDetailsModel;
import com.naksh.renth.Models.Users;
import com.naksh.renth.databinding.ActivityUserPersonalDetailsBinding;

import java.util.Objects;

//public class UserPersonalDetails extends AppCompatActivity {
//
//    ActivityUserPersonalDetailsBinding binding;
//    private FirebaseAuth mAuth;
//    ProgressDialog progressDialog;
//    DatabaseReference usersRef;
//    RadioGroup genderRadioGroup;
//    EditText name,age,gernder,phoneno,email;
//    String userName;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityUserPersonalDetailsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Initialize FirebaseAuth instance
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        usersRef = database.getReference("UserPersonalDetailsModel"); // Initialize usersRef
//        name=findViewById(R.id.onameet);
//        age=findViewById(R.id.oageet);
//        phoneno=findViewById(R.id.phonenoet);
//        email=findViewById(R.id.emailet);
//
//        progressDialog = new ProgressDialog(UserPersonalDetails.this);
//        progressDialog.setTitle("Saving personal details");
//        progressDialog.setMessage("Going to home...");
//
//        genderRadioGroup = findViewById(R.id.person);
//        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                int selectedId = binding.person.getCheckedRadioButtonId();
////                if (selectedId != -1) {
////                    if (!validation()) {
////                        return; // Return if validation fails
////                    }
//                if (!validation()) {
//                    return; // Return if validation fails
//                }
//                progressDialog.show();
//                String name = Objects.requireNonNull(binding.onameet.getText()).toString();
//                int age = Integer.parseInt(Objects.requireNonNull(binding.oageet.getText()).toString());
//                String gender = getSelectedGender();
//                String phoneNumber = binding.phonenoet.getText().toString();
//                String userEmail = binding.emailet.getText().toString();
//
//                // Generate a unique user ID (e.g., using push() method)
//                String userId = usersRef.push().getKey();
//                UserPersonalDetailsModel userPersonalDetailsModel = new UserPersonalDetailsModel(name, age, gender,userId, phoneNumber,userEmail);
//                assert userId != null;
//                usersRef.child(userId).setValue(userPersonalDetailsModel)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                progressDialog.dismiss(); // Dismiss the progress dialog
//                                if (task.isSuccessful()) {
//                                    Intent intent = new Intent(UserPersonalDetails.this, PropertyRecyclerActivityForUser.class);
//                                    intent.putExtra("user_id",userId);
//                                    startActivity(intent);
//                                    finish(); // Finish this activity after starting the next one
//                                } else {
//                                    Toast.makeText(UserPersonalDetails.this, "Failed to create user", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//
//
//        });
//    }
//    private String getSelectedGender() {
//            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
//            RadioButton selectedRadioButton = findViewById(selectedId);
//            if (selectedRadioButton != null) {
//                return selectedRadioButton.getText().toString();
//            } else {
////                Toast.makeText(UserPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
//                return ""; // Return an empty string or handle the case where no gender is selected
//            }
//        }
//
//
//
//    public boolean validation() {
//        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
//        String uname = name.getText().toString().trim();
//        String uage = age.getText().toString().trim();
//        String uphoneno = phoneno.getText().toString().trim();
//        String uemail=email.getText().toString().trim();
//        if (uname.isEmpty() || uage.isEmpty() || uphoneno.isEmpty()||uemail.isEmpty()||selectedId==-1) {
//            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
//        return true;
//    }

public class UserPersonalDetails extends AppCompatActivity {

    ActivityUserPersonalDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference usersRef;
    DatabaseReference usersRef2;

    RadioGroup genderRadioGroup;
    EditText name, age, gernder, phoneno, email;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference(); // Initialize usersRef
        usersRef2 = database.getReference("Users"); // Reference to the "Users" node

        name = findViewById(R.id.onameet);
        age = findViewById(R.id.oageet);
        phoneno = findViewById(R.id.phonenoet);
        email = findViewById(R.id.emailet);

        progressDialog = new ProgressDialog(UserPersonalDetails.this);
        progressDialog.setTitle("Saving personal details");
        progressDialog.setMessage("Going to home...");

        genderRadioGroup = findViewById(R.id.person);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) {
                    return; // Return if validation fails
                }

                String name = Objects.requireNonNull(binding.onameet.getText()).toString();
                int age = Integer.parseInt(Objects.requireNonNull(binding.oageet.getText()).toString());
                String gender = getSelectedGender();
                String phoneNumber = binding.phonenoet.getText().toString();
                String userEmail = binding.emailet.getText().toString();

                // Validate age range
                if (age <= 0 || age >= 100) {
                    // Display a toast indicating that the age is invalid
                    Toast.makeText(UserPersonalDetails.this, "Enter a valid age", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if age is invalid
                }

                // Validate if phoneNumber contains only digits and has a valid length
                if (!phoneNumber.matches("\\d{10}")) {
                    // Display a toast indicating that the phone number is invalid
                    Toast.makeText(UserPersonalDetails.this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    return; // Exit the method if phoneNumber is invalid
                }
                // Validate gender
                if (gender.isEmpty()) {
                    // Display a toast indicating that the gender must be selected
                    Toast.makeText(UserPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if gender is not selected
                }
                progressDialog.show();

                // Generate a unique user ID (e.g., using push() method)
                String userId = usersRef.push().getKey();
                UserPersonalDetailsModel userPersonalDetailsModel = new UserPersonalDetailsModel(name, age, gender, userId, phoneNumber, userEmail);
                assert userId != null;

                // Check if the entered email exists in the database
                usersRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean emailExists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Users user = snapshot.getValue(Users.class);
                            if (user != null && user.getEmail().equals(userEmail)) {
                                emailExists = true;
                                break;
                            }
                        }

                        if (emailExists) {
                            // Generate a unique user ID (e.g., using push() method)
                            String userId = usersRef.push().getKey();
                            UserPersonalDetailsModel userPersonalDetailsModel = new UserPersonalDetailsModel(name, age, gender, userId, phoneNumber, userEmail);
                            assert userId != null;
                            usersRef.child("UserPersonalDetailsModel").child(userId).setValue(userPersonalDetailsModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss(); // Dismiss the progress dialog
                                            if (task.isSuccessful()) {
//                                                String userName=dataSnapshot.child("name").getValue(String.class);
//                                                String userEmail=dataSnapshot.child("uemail").getValue(String.class);
//                                                String userPhoneNo=dataSnapshot.child("phoneno").getValue(String.class);

                                                Intent intent = new Intent(UserPersonalDetails.this, PropertyRecyclerActivityForUser.class);
                                                intent.putExtra("user_id", userId);
                                                intent.putExtra("userName", userPersonalDetailsModel.getName());
                                                intent.putExtra("userEmail", userPersonalDetailsModel.getUemail());
                                                intent.putExtra("phoneno", userPersonalDetailsModel.getPhoneno());
                                                Toast.makeText(UserPersonalDetails.this, "phoneno="+phoneno+"email="+userEmail, Toast.LENGTH_LONG).show();

                                                startActivity(intent);
                                                finish(); // Finish this activity after starting the next one
                                            } else {
                                                Toast.makeText(UserPersonalDetails.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(UserPersonalDetails.this, " Email must be same as registered email.", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Toast.makeText(UserPersonalDetails.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private String getSelectedGender() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        } else {
            return ""; // Return an empty string or handle the case where no gender is selected
        }
    }

    public boolean validation() {
//        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        String uname = name.getText().toString().trim();
        String uage = age.getText().toString().trim();
        String uphoneno = phoneno.getText().toString().trim();
        String uemail = email.getText().toString().trim();
        if (uname.isEmpty() || uage.isEmpty() || uphoneno.isEmpty() || uemail.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}


