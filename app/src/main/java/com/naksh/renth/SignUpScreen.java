//package com.naksh.renth;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.ThemedSpinnerAdapter;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.naksh.renth.Models.Users;
//import com.naksh.renth.databinding.ActivitySignUpScreenBinding;
//
//import java.util.Objects;
//
//public class SignUpScreen extends AppCompatActivity {
//    EditText etUsername, etemail, etPassword;
//    Button signupbtn;
//    private ActivitySignUpScreenBinding binding;
//    private FirebaseAuth mAuth;
//    private FirebaseDatabase database;
//    private DatabaseReference usersRef;
//    private ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        etemail = findViewById(R.id.etemail);
//        etPassword = findViewById(R.id.etPassword);
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Creating Account");
//        progressDialog.setMessage("We are creating your account");
//
//        signupbtn = findViewById(R.id.signup);
//        signupbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId = binding.person2.getCheckedRadioButtonId();
//                if (selectedId != -1) {
//                    progressDialog.show(); // Show progress dialog
//                    mAuth.createUserWithEmailAndPassword(binding.etemail.getText().toString(), binding.etPassword.getText().toString())
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    progressDialog.dismiss(); // Dismiss progress dialog
//                                    if (task.isSuccessful()) {
//                                        RadioButton selectedRadioButton = findViewById(selectedId);
//                                        String userType = selectedRadioButton.getText().toString();
//                                        Users users = new Users(
//                                                binding.etemail.getText().toString(),
//                                                binding.etPassword.getText().toString(),
//                                                userType
//                                        );
//
//                                        String userId = mAuth.getCurrentUser().getUid();
//                                        usersRef = database.getReference("Users");
//                                        usersRef.child(userId).setValue(users);
//                                        if (selectedId == R.id.u) {
//                                            startActivity(new Intent(SignUpScreen.this, UserPersonalDetails.class));
//                                        } else {
//                                            startActivity(new Intent(SignUpScreen.this, OwnerPersonalDetails.class));
//                                        }
//                                    } else {
//                                        Toast.makeText(SignUpScreen.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                } else {
//                    Toast.makeText(SignUpScreen.this, "Please select a user type", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naksh.renth.Models.Users;
import com.naksh.renth.databinding.ActivitySignUpScreenBinding;

import java.util.Objects;

public class SignUpScreen extends AppCompatActivity {
    EditText etUsername, etemail, etPassword;
    Button signupbtn;
    private ActivitySignUpScreenBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private ProgressDialog progressDialog;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = findViewById(R.id.etemail);
        password = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        signupbtn = findViewById(R.id.signup);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = binding.person2.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    if (!validation()) {
                        return; // Return if validation fails
                    }

                    progressDialog.show(); // Show progress dialog
                    mAuth.createUserWithEmailAndPassword(binding.etemail.getText().toString(), binding.etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss(); // Dismiss progress dialog
                                    if (task.isSuccessful()) {
                                        RadioButton selectedRadioButton = findViewById(selectedId);
                                        String userType = selectedRadioButton.getText().toString();
                                        Users users = new Users(
                                                binding.etemail.getText().toString(),
                                                binding.etPassword.getText().toString(),
                                                userType
                                        );

                                        String userId = mAuth.getCurrentUser().getUid();
                                        usersRef = database.getReference("Users");
                                        usersRef.child(userId).setValue(users);
                                        if (selectedId == R.id.u) {
                                            startActivity(new Intent(SignUpScreen.this, UserPersonalDetails.class));
                                        } else {

//                                            String useremail = binding.etemail.getText().toString();
//                                            Intent intent = new Intent(SignUpScreen.this, OwnerPersonalDetails.class);
//                                            intent.putExtra("keyname", useremail);
//                                            startActivity(intent);

                                            Intent intent = new Intent(SignUpScreen.this, OwnerPersonalDetails.class);
                                            startActivity(intent);


                                        }
                                    } else {
                                        Toast.makeText(SignUpScreen.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpScreen.this, "Please select a user type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean validation() {
        String eMail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (eMail.isEmpty()||userPassword.isEmpty()){
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
