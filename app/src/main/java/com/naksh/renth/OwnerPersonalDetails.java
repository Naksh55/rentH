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
//package com.naksh.renth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.naksh.renth.Models.OwnerPersonalDetailsModel;
//import com.naksh.renth.Models.PropertyDetailsModel;
//import com.naksh.renth.databinding.ActivityOwnerPersonalDetailsBinding;
//
//import java.util.Objects;
//
//public class OwnerPersonalDetails extends AppCompatActivity {
//    ActivityOwnerPersonalDetailsBinding binding;
//    private FirebaseAuth mAuth;
//    ProgressDialog progressDialog;
//    DatabaseReference usersRef;
//    RadioGroup genderRadioGroup2;
//    String ownerId;
//    EditText email;
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
//        email=findViewById(R.id.oemailet);
//        binding.onextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                progressDialog.show();
//                String name = Objects.requireNonNull(binding.nameet.getText()).toString();
////                String age = Integer.parseInt(Objects.requireNonNull(binding.ageet.getText()).toString());
//                String ageString = Objects.requireNonNull(binding.ageet.getText()).toString();
//
//                String gender = getSelectedGender();
//                String phoneNumber = binding.ophonenoet.getText().toString();
//                String oemail = binding.oemailet.getText().toString();
//                if (name.trim().isEmpty() || ageString.trim().isEmpty() || phoneNumber.trim().isEmpty() || oemail.trim().isEmpty()) {
//                    // Display a toast indicating that all fields must be filled
//                    Toast.makeText(OwnerPersonalDetails.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method if any field is empty
//                }
//
//
//                int age;
//                try {
//                    // Try parsing ageString into an integer
//                    age = Integer.parseInt(ageString);
//                } catch (NumberFormatException e) {
//                    // Handle the case where ageString cannot be parsed into an integer
//                    Toast.makeText(OwnerPersonalDetails.this, "Age must be a number", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method if ageString cannot be parsed into an integer
//                }
//
//// Validate age range
//                if (age <= 0 || age >= 100) {
//                    // Display a toast indicating that the age is invalid
//                    Toast.makeText(OwnerPersonalDetails.this, "Enter a valid age", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method if age is invalid
//                }
//
//                // Validate if phoneNumber contains only digits and has a valid length
//                if (!phoneNumber.matches("\\d{10}")) {
//                    // Display a toast indicating that the phone number is invalid
//                    Toast.makeText(OwnerPersonalDetails.this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method if phoneNumber is invalid
//                }
//                // Validate gender
//                if (gender.isEmpty()) {
//                    // Display a toast indicating that the gender must be selected
//                    Toast.makeText(OwnerPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
//                    return; // Exit the method if gender is not selected
//                }
//
//
//                    // Query the "Users" node to check if ownerEmail exists
//                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
//                    userRef.orderByChild("email").equalTo(oemail).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                // Email exists in the "Users" node
//                                // Proceed with your logic
//                                // For example, you can start the next activity or perform other actions
//                            } else {
//                                // Email does not exist in the "Users" node
//                                // Display a toast indicating that the email is invalid
//                                Toast.makeText(OwnerPersonalDetails.this, "Invalid email", Toast.LENGTH_SHORT).show();
//                                // You may want to clear the email field or take other actions based on your requirements
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            // Handle database error
//                            Toast.makeText(OwnerPersonalDetails.this, "Database error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                // Generate a unique user ID
//                 ownerId = usersRef.push().getKey(); // Generate a unique user ID
//
//                // Set the id in the model
//                OwnerPersonalDetailsModel ownerPersonalDetailsModel = new OwnerPersonalDetailsModel(name,ageString, gender, phoneNumber, oemail,ownerId);
//                progressDialog.show();
//
//                // Save the data to Firebase using the generated userId
//                usersRef.child(ownerId).setValue(ownerPersonalDetailsModel)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                progressDialog.dismiss(); // Dismiss the progress dialog
//                                if (task.isSuccessful()) {
//                                    // Data saved successfully
//                                    // Proceed with your logic
//                                    // Retrieve owner name from the model
//                                    String ownerName = ownerPersonalDetailsModel.getOname();
//
//                                    Toast.makeText(OwnerPersonalDetails.this, ownerId, Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(OwnerPersonalDetails.this,PropertyDetails.class);
//                                    intent.putExtra("id", ownerId);
//                                    intent.putExtra("oname",ownerName);
//                                    // Pass ownerId to PropertyDetails activity
//                                    startActivity(intent);
//                                    // Proceed to the next activity or perform other actions
//                                } else {
//                                    progressDialog.dismiss(); // Dismiss the progress dialog
//
//                                    // Data save failed
//                                    Toast.makeText(OwnerPersonalDetails.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    Log.e("OwnerPersonalDetails", "Failed to save data", task.getException());
//                                }
//                            }
//                        });
//            }
//        });
//    }
//
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
//
//
//    }
//
//    private void checkOwnerPersonalDetails(String currentUserEmail, RadioButton selectedRadioButton) {
//        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference()
//                .child("OwnerPersonalDetailsModel");
//
//        ownerRef.orderByChild("oemail").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        String ownerId = snapshot.child("id").getValue(String.class);
//                        String ownername = snapshot.child("oname").getValue(String.class);
//
//                        if (ownerId != null) {
//                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
//                                    .child("Users");
//
//                            userRef.orderByChild("category").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                                            String uid = userSnapshot.getKey();
//
//                                            String eMailFromDatabase = userSnapshot.child("email").getValue(String.class); // Assuming email is stored under "email" child node
//
//                                            if (eMailFromDatabase != null && eMailFromDatabase.equals(email.getText().toString().trim())) {
//                                                String userTypeFromDatabase = userSnapshot.child("category").getValue(String.class);
//                                                String userType = selectedRadioButton.getText().toString();
//                                                Toast.makeText(OwnerPersonalDetails.this, userTypeFromDatabase + " " + userType, Toast.LENGTH_SHORT).show();
//
//                                                if (userType.equals(userTypeFromDatabase)) {
//                                                    Intent intent = new Intent(OwnerPersonalDetails.this, PropertyDetails.class);
////                                                    intent.putExtra("user_id", userId);
//                                                    intent.putExtra("id", ownerId);
//                                                    intent.putExtra("oname", ownername);
//
//
////                                                    intent.putExtra("notification_message", notificationMessage);
////                                                    intent.putExtra("userName", userName);
//                                                    startActivity(intent);
//                                                    finish(); // Finish the current activity
//                                                } else {
//                                                    Toast.makeText(OwnerPersonalDetails.this, "Role mismatch", Toast.LENGTH_SHORT).show();
//                                                }
//                                                return; // Exit the loop after finding the matching user
//                                            }
//                                        }
//                                        // If execution reaches here, it means email wasn't found
//                                        Toast.makeText(OwnerPersonalDetails.this, "User not found", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        // No user found with the specified category
//                                        Toast.makeText(OwnerPersonalDetails.this, "User data not found", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                    // Handle database error
//                                }
//                            });
//                        }
//
//                    }
//                } else {
//                    // Email not found in OwnerPersonalDetails node
//                    Toast.makeText(OwnerPersonalDetails .this, "Owner data not found", Toast.LENGTH_SHORT).show();
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
//}



package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.OwnerPersonalDetailsModel;
import com.naksh.renth.databinding.ActivityOwnerPersonalDetailsBinding;

import java.util.Objects;

public class OwnerPersonalDetails extends AppCompatActivity {
    ActivityOwnerPersonalDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference usersRef;
    RadioGroup genderRadioGroup2;
    String ownerId;
    EditText email;

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
        email=findViewById(R.id.oemailet);
        binding.onextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog.show();
                String name = Objects.requireNonNull(binding.nameet.getText()).toString();
                String ageString = Objects.requireNonNull(binding.ageet.getText()).toString();

                String gender = getSelectedGender();
                String phoneNumber = binding.ophonenoet.getText().toString();
                String oemail = binding.oemailet.getText().toString();
                if (name.trim().isEmpty() || ageString.trim().isEmpty() || phoneNumber.trim().isEmpty() || oemail.trim().isEmpty()) {
                    // Display a toast indicating that all fields must be filled
                    Toast.makeText(OwnerPersonalDetails.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if any field is empty
                }

                int age;
                try {
                    // Try parsing ageString into an integer
                    age = Integer.parseInt(ageString);
                } catch (NumberFormatException e) {
                    // Handle the case where ageString cannot be parsed into an integer
                    Toast.makeText(OwnerPersonalDetails.this, "Age must be a number", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if ageString cannot be parsed into an integer
                }

                // Validate age range
                if (age <= 0 || age >= 100) {
                    // Display a toast indicating that the age is invalid
                    Toast.makeText(OwnerPersonalDetails.this, "Enter a valid age", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if age is invalid
                }

                // Validate if phoneNumber contains only digits and has a valid length
                if (!phoneNumber.matches("\\d{10}")) {
                    // Display a toast indicating that the phone number is invalid
                    Toast.makeText(OwnerPersonalDetails.this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if phoneNumber is invalid
                }
                // Validate gender
                if (gender.isEmpty()) {
                    // Display a toast indicating that the gender must be selected
                    Toast.makeText(OwnerPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if gender is not selected
                }

                // Query the "Users" node to check if ownerEmail exists
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
                userRef.orderByChild("email").equalTo(oemail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Email exists in the "Users" node
                            // Proceed with your logic
                            // For example, you can start the next activity or perform other actions
                            saveDataAndNavigate();
                        } else {
                            // Email does not exist in the "Users" node
                            // Display a toast indicating that the email is invalid
                            Toast.makeText(OwnerPersonalDetails.this, "Invalid email", Toast.LENGTH_SHORT).show();
                            // You may want to clear the email field or take other actions based on your requirements
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                        Toast.makeText(OwnerPersonalDetails.this, "Database error", Toast.LENGTH_SHORT).show();
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

    private void saveDataAndNavigate() {
        // Generate a unique user ID
        ownerId = usersRef.push().getKey(); // Generate a unique user ID

        // Set the id in the model
        String name = Objects.requireNonNull(binding.nameet.getText()).toString();
        String ageString = Objects.requireNonNull(binding.ageet.getText()).toString();
        String gender = getSelectedGender();
        String phoneNumber = binding.ophonenoet.getText().toString();
        String oemail = binding.oemailet.getText().toString();

        OwnerPersonalDetailsModel ownerPersonalDetailsModel = new OwnerPersonalDetailsModel(name,ageString, gender, phoneNumber, oemail,ownerId);
        progressDialog.show();

        // Save the data to Firebase using the generated userId
        usersRef.child(ownerId).setValue(ownerPersonalDetailsModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss(); // Dismiss the progress dialog
                        if (task.isSuccessful()) {
                            // Data saved successfully
                            // Proceed with your logic
                            // Retrieve owner name from the model
                            String ownerName = ownerPersonalDetailsModel.getOname();

                            Toast.makeText(OwnerPersonalDetails.this, ownerId, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OwnerPersonalDetails.this,OwnerHomeActivity.class);
                            intent.putExtra("id", ownerId);
                            intent.putExtra("oname",ownerName);
                            // Pass ownerId to PropertyDetails activity
                            startActivity(intent);
                            // Proceed to the next activity or perform other actions
                        } else {
                            progressDialog.dismiss(); // Dismiss the progress dialog

                            // Data save failed
                            Toast.makeText(OwnerPersonalDetails.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("OwnerPersonalDetails", "Failed to save data", task.getException());
                        }
                    }
                });
    }
}
