package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.Users;
import com.naksh.renth.databinding.ActivityLoginScreenBinding;

import java.security.acl.Owner;
import java.util.Objects;


public class LoginScreen extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference reference;
    EditText emailet;
    private String ownerId; // Declare ownerId variable
    // Method to check ownerId from "OwnerPersonalDetails" node
    private void checkOwnerPersonalDetails(String currentUserEmail) {
        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference()
                .child("OwnerPersonalDetailsModel");

        ownerRef.orderByChild("email").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DataSnapshot from OwnerPersonalDetailsModel", dataSnapshot.toString());

                if (dataSnapshot.exists()) {
                    // Email found in OwnerPersonalDetailsActivity node
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String ownerId = snapshot.child("id").getValue(String.class);
                        if (ownerId != null) {
                            // Pass the ownerId to OwnerHomeActivity
                            Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
                            intent.putExtra("id", ownerId);
                            startActivity(intent);
                            return; // Exit the loop after finding the matching email
                        }
                    }
                } else {
                    // Email not found in OwnerPersonalDetailsActivity node
                    Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
//    private void checkOwnerPersonalDetails(String currentUserUid) {
//        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference().child("OwnerPersonalDetailsModel").child(currentUserUid);
//
//        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Owner personal details found, retrieve and process the data
//                    String ownerId = dataSnapshot.child("id").getValue(String.class);
//                    Toast.makeText(LoginScreen.this, "Owner data  found", Toast.LENGTH_SHORT).show();
//
//                    // Retrieve other owner details as needed
//                } else {
//                    // Owner personal details not found
//                    Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle potential database error
//                Toast.makeText(LoginScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginScreen.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Logging in....");
        emailet = findViewById(R.id.emailet);
        TextView signuptext = findViewById(R.id.signuptext);
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(loginIntent);
            }
        });

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current user's email
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String currentUserEmail = currentUser.getEmail();
                    // Call the method to check owner details
                    checkOwnerPersonalDetails(currentUserEmail);
                } else {
                    // Handle the case where the current user is null
                    Toast.makeText(LoginScreen.this, "Current user is null", Toast.LENGTH_SHORT).show();
                }

                int selectedId = binding.person.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(LoginScreen.this, "Please select a category", Toast.LENGTH_SHORT).show();
                    return; // Return without attempting to sign in
                }

                // Getting the selected radio button and user type
                RadioButton selectedRadioButton = findViewById(selectedId);
                String userType = selectedRadioButton.getText().toString();

                // Showing progress dialog
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Retrieving user role from the database
                            String currentUserUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                            // Check user details from "Users" node
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Users")
                                    .child(currentUserUid);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        if (userType.equals("User")) {
                                            // If user type is User, navigate to UserHomeActivity
                                            startActivity(new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class));
                                            finish(); // Finish the current activity
                                        } else if (userType.equals("Owner")) {
                                            // If user type is Owner, check OwnerPersonalDetails
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                String ownerId = snapshot.child("id").getValue(String.class);
                                                if (ownerId != null) {
                                                    // Pass the ownerId to OwnerHomeActivity
                                                    Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
                                                    intent.putExtra("id", ownerId);
                                                    startActivity(intent);
                                                    finish(); // Finish the current activity
                                                    return; // Exit the loop after finding the matching email
                                                }
                                            }
                                        }
                                    } else {
                                        // Handle the case where the user data doesn't exist
                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle database error
                                }
                            });

                        } else {
                            Toast.makeText(LoginScreen.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Log.e("Firebase", "Error signing in", e);
                    }
                });
            }
        });
    }
}




//public class LoginScreen extends AppCompatActivity {
//    // Declare variables
//    private ActivityLoginScreenBinding binding;
//    private ProgressDialog progressDialog;
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Initialize Firebase authentication
//        auth = FirebaseAuth.getInstance();
//        progressDialog = new ProgressDialog(LoginScreen.this);
//        progressDialog.setTitle("Log in");
//        progressDialog.setMessage("Logging in....");
//        TextView signuptext = findViewById(R.id.signuptext);
//                            signuptext.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent loginIntent = new Intent(LoginScreen.this, SignUpScreen.class);
//                                    startActivity(loginIntent);
//                                }
//                            });
//        // Set onClickListener for login button
//        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get selected radio button ID
//                int selectedId = binding.person.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(LoginScreen.this, "Please select a category", Toast.LENGTH_SHORT).show();
//                    return; // Return without attempting to sign in
//                }
//
//                // Get the selected radio button and user type
//                RadioButton selectedRadioButton = findViewById(selectedId);
//                String userType = selectedRadioButton.getText().toString();
//
//                // Show progress dialog
//                progressDialog.show();
//
//                // Attempt to sign in with email and password
//                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString())
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressDialog.dismiss(); // Dismiss progress dialog
//                                if (task.isSuccessful()) {
//                                    // If sign in is successful, proceed with user validation
//                                    validateUser(userType);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(LoginScreen.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//    }
//
//    // Method to validate user and proceed accordingly
//    private void validateUser(String userType) {
//        // Get current user's UID
//        String currentUserUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//
//        // Check if the user is an owner
//        if (userType.equals("Owner")) {
//            // Retrieve ownerId from OwnerPersonalDetailsModel
//            DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference()
//                    .child("OwnerPersonalDetailsModel").child(currentUserUid);
//            ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // Owner exists, retrieve ownerId and start OwnerHomeActivity
//                        String ownerId = dataSnapshot.child("id").getValue(String.class);
//                        startOwnerHomeActivity(ownerId);
//                    } else {
//                        // Owner does not exist
//                        Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Handle database error
//                }
//            });
//        } else {
//            // Start UserHomeActivity for regular users
//            startActivity(new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class));
//        }
//    }
//
//    // Method to start OwnerHomeActivity with ownerId
//    private void startOwnerHomeActivity(String ownerId) {
//        Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
//        intent.putExtra("id", ownerId);
//        startActivity(intent);
//    }
//}
