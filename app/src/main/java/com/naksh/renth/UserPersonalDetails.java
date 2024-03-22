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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naksh.renth.Models.UserPersonalDetailsModel;
import com.naksh.renth.databinding.ActivityUserPersonalDetailsBinding;

import java.util.Objects;

public class UserPersonalDetails extends AppCompatActivity {

    ActivityUserPersonalDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference usersRef;
    RadioGroup genderRadioGroup;
    EditText name,age,gernder,phoneno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("UserPersonalDetailsModel"); // Initialize usersRef
        name=findViewById(R.id.onameet);
        age=findViewById(R.id.oageet);
        phoneno=findViewById(R.id.phonenoet);

        progressDialog = new ProgressDialog(UserPersonalDetails.this);
        progressDialog.setTitle("Saving personal details");
        progressDialog.setMessage("Going to home...");

        genderRadioGroup = findViewById(R.id.person);
        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int selectedId = binding.person.getCheckedRadioButtonId();
//                if (selectedId != -1) {
//                    if (!validation()) {
//                        return; // Return if validation fails
//                    }
                if (!validation()) {
                    return; // Return if validation fails
                }
                progressDialog.show();
                String name = Objects.requireNonNull(binding.onameet.getText()).toString();
                int age = Integer.parseInt(Objects.requireNonNull(binding.oageet.getText()).toString());
                String gender = getSelectedGender();
                String phoneNumber = binding.phonenoet.getText().toString();

                // Generate a unique user ID (e.g., using push() method)
                String userId = usersRef.push().getKey();
                UserPersonalDetailsModel userPersonalDetailsModel = new UserPersonalDetailsModel(name, age, gender, phoneNumber);
                assert userId != null;
                usersRef.child(userId).setValue(userPersonalDetailsModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss(); // Dismiss the progress dialog
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(UserPersonalDetails.this, PropertyRecyclerActivityForUser.class);
                                    startActivity(intent);
                                    finish(); // Finish this activity after starting the next one
                                } else {
                                    Toast.makeText(UserPersonalDetails.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                }
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
//                Toast.makeText(UserPersonalDetails.this, "Select gender", Toast.LENGTH_SHORT).show();
                return ""; // Return an empty string or handle the case where no gender is selected
            }
        }



    public boolean validation() {
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        String uname = name.getText().toString().trim();
        String uage = age.getText().toString().trim();
        String uphoneno = phoneno.getText().toString().trim();
        if (uname.isEmpty() || uage.isEmpty() || uphoneno.isEmpty()||selectedId==-1) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
}
