package com.naksh.renth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naksh.renth.Models.Users;
import com.naksh.renth.databinding.ActivityLoginScreenBinding;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference reference;
    EditText emailet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginScreen.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Logging in....");
        emailet=findViewById(R.id.emailet);


//        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View v) {
//                int selectedId = binding.person.getCheckedRadioButtonId();
//                RadioButton selectedRadioButton = findViewById(selectedId);
//                String userType = selectedRadioButton.getText().toString();
////                Toast.makeText(LoginScreen.this, userType, Toast.LENGTH_SHORT).show();
//                if (selectedId == -1) {
//                    Toast.makeText(LoginScreen.this, "Please select a category", Toast.LENGTH_SHORT).show();
//                    return; // Return without attempting to sign in
//                }
//                progressDialog.show();
//                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            // Retrieve the user's role from the database
//                            String currentUserUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserUid);
//                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//                                        String role = dataSnapshot.child("category").getValue(String.class);
//                                        RadioButton selectedRadioButton = findViewById(selectedId);
//                                        String userType = selectedRadioButton.getText().toString();
//                                        int selectedId = binding.person.getCheckedRadioButtonId();
//                                        String category = dataSnapshot.child("category").getValue(String.class);
//                                        if (role != null ) {
//                                            // Role matches the selected radio button, proceed to the appropriate screen
//                                            assert category != null;
//                                            if (category.equals("User") && userType.equals("User")) {
//                                                startActivity(new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class));
//                                            } else if(category.equals("Owner") && userType.equals("Owner")){
//                                                startActivity(new Intent(LoginScreen.this, OwnerHomeActivity.class));
//                                            }
//                                            else {
//                                                // Role doesn't match the selected radio button, show error message
//                                                Toast.makeText(LoginScreen.this, "Choose correct category!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    } else {
//                                        // Handle the case where the user data doesn't exist
//                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    // Handle error
//                                    Toast.makeText(LoginScreen.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else {
//                            Toast.makeText(LoginScreen.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Attempting to sign in with email and password
                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Retrieving user role from the database
                            String currentUserUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserUid);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String role = dataSnapshot.child("category").getValue(String.class);
                                        String category = dataSnapshot.child("category").getValue(String.class);
                                        if (role != null) {
                                            // Role matches the selected radio button, proceed to the appropriate screen
                                            assert category != null;
                                            if (category.equals("User") && userType.equals("User")) {
                                                startActivity(new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class));
//                                                startActivity(new Intent(LoginScreen.this, UserHomeActivity.class));

                                            } else if (category.equals("Owner") && userType.equals("Owner")) {
                                                // If login is successful, pass email to OwnerHomeActivity\

//                                                String useremail = binding.emailet.getText().toString();
//                                                Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
//                                                intent.putExtra("keyname", useremail);
//                                                startActivity(intent);

                                                Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
                                                startActivity(intent);


                                            } else {
                                                // Role doesn't match the selected radio button, show error message
                                                Toast.makeText(LoginScreen.this, "Choose correct category!", Toast.LENGTH_SHORT).show();
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
                            // Handle unsuccessful login attempt
                            Toast.makeText(LoginScreen.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email=binding.emailet.getText().toString();
//                if(!email.isEmpty()){
//                    readData(email);
//                }
//                else {
//                    Toast.makeText(LoginScreen.this,"Enter email",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        if (auth.getCurrentUser()!=null){
//            Intent intent=new Intent(LoginScreen.this,SignUpScreen.class);
//            startActivity(intent);
//        }
        TextView signuptext = findViewById(R.id.signuptext);
        signuptext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(loginIntent);
            }
        });
//                @SuppressLint("CutPasteId") EditText ownerEmailTextView = findViewById(R.id.emailet);
//                String textToPass = ownerEmailTextView.getText().toString();
//                OwnerProfileFragment fragment = new OwnerProfileFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("text_key", textToPass);
//                fragment.setArguments(bundle);






//        binding.loginbtn.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick (View v){
//
//
//                // Rest of your login logic goes here
//            }
//        });
//    }


//    private void readData(String email) {
//        reference=FirebaseDatabase.getInstance().getReference("Users");
//        reference.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(task.isSuccessful()){
//                    if(task.getResult().exists()){
//                        Toast.makeText(LoginScreen.this,"Successfully read",Toast.LENGTH_SHORT).show();
//                        DataSnapshot dataSnapshot= task.getResult();
//                        String email=String.valueOf(dataSnapshot.child("email").getValue());
//                        binding.owneremail.setText(email);
//
//                    }
//                    else{
//                        Toast.makeText(LoginScreen.this,"User dosen't exist",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                else{
//                    Toast.makeText(LoginScreen.this,"Failed to read",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//    }

    }
}


