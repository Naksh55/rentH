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
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.naksh.renth.databinding.ActivityLoginScreenBinding;
//import android.content.Context;
//import android.content.SharedPreferences;
//import java.util.Objects;
//
//
//
//public class LoginScreen extends AppCompatActivity {
//    private ActivityLoginScreenBinding binding;
//    ProgressDialog progressDialog;
//    FirebaseAuth auth;
//    DatabaseReference reference;
//    EditText email;
//    EditText password;
//    String notificationMessage;
//    String ownerId;
//    String userId;
//    String userName;
////    private static final String PREF_NAME = "last_screen_pref";
////    private static final String LAST_SCREEN_KEY = "last_screen";
////    private void saveLastScreen(String screenName) {
////        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString(LAST_SCREEN_KEY, screenName);
////        editor.apply();
////    }
//
//    // Method to get the last visited screen from SharedPreferences
////    private String getLastScreen() {
////        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
////        return sharedPreferences.getString(LAST_SCREEN_KEY, null);
////    }
//
//    // Method to check ownerId from "OwnerPersonalDetails" node
//    private void checkOwnerPersonalDetails(String currentUserEmail) {
//        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference()
//                .child("OwnerPersonalDetailsModel");
//
//        ownerRef.orderByChild("email").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("DataSnapshot from OwnerPersonalDetailsModel", dataSnapshot.toString());
//
//                if (dataSnapshot.exists()) {
//                    // Email found in OwnerPersonalDetailsActivity node
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        String ownerId = snapshot.child("id").getValue(String.class);
//                        if (ownerId != null) {
//                            // Pass the ownerId to OwnerHomeActivity
//                            Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
//                            intent.putExtra("id", ownerId);
//                            startActivity(intent);
//                            return; // Exit the loop after finding the matching email
//                        }
//                    }
//                } else {
//                    // Email not found in OwnerPersonalDetailsActivity node
//                    Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle database error
//            }
//        });
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        auth = FirebaseAuth.getInstance();
//        Intent i=getIntent();
//        if(i!=null){
//            notificationMessage =i.getStringExtra("notification_message");
//            userId=i.getStringExtra("user_id");
//            userName=i.getStringExtra("userName");
////            Toast.makeText(this, "id="+userId, Toast.LENGTH_SHORT).show();
//
//        }
//        progressDialog = new ProgressDialog(LoginScreen.this);
//        progressDialog.setTitle("Log in");
//        progressDialog.setMessage("Logging in....");
//        email = findViewById(R.id.emailet);
//        password = findViewById(R.id.etPassword);
//        TextView signuptext = findViewById(R.id.signuptext);
//        signuptext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent loginIntent = new Intent(LoginScreen.this, SignUpScreen.class);
//                startActivity(loginIntent);
//            }
//        });
//
//
//        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!validation()) {
//                    return; // Return if validation fails
//                }
//
//                int selectedId = binding.person.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(LoginScreen.this, "Please select a category", Toast.LENGTH_SHORT).show();
//                    return; // Return without attempting to sign in if no category is selected
//                }
//
//                // Getting the selected radio button and user type
//                RadioButton selectedRadioButton = findViewById(selectedId);
//                String userType = selectedRadioButton.getText().toString();
//
//                // Showing progress dialog
//                progressDialog.show();
//
//                // Attempting to sign in with email and password
//                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString())
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressDialog.dismiss(); // Dismiss the progress dialog
//
//                                if (task.isSuccessful()) {
//                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                                    if (currentUser != null) {
//                                        // Redirecting based on user type
//                                        if (userType.equals("User")) {
//                                            checkUserDetails(currentUser.getEmail(), selectedRadioButton);
//                                        } else if (userType.equals("Owner")) {
//                                            checkOwnerPersonalDetails(currentUser.getEmail(), selectedRadioButton);
//                                        }
//                                    } else {
//                                        // Current user is null
//                                        Toast.makeText(LoginScreen.this, "Current user is null", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    // Sign-in failed
//                                    Toast.makeText(LoginScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Handle authentication failure
//                                progressDialog.dismiss(); // Dismiss the progress dialog
//                                Log.e("Firebase", "Error signing in", e);
////                                Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
//
////        if (auth.getCurrentUser() != null) {
////            // Get the last visited screen
////            String lastScreen = getLastScreen();
////            if (lastScreen != null) {
////                // Open the last visited screen
////                Intent intent = null;
////                try {
////                    intent = new Intent(LoginScreen.this, Class.forName(lastScreen));
////                } catch (ClassNotFoundException e) {
////                    throw new RuntimeException(e);
////                }
////                startActivity(intent);
////                finish(); // Finish the current activity
////            } else {
////                // If last screen is not available, open default screen
////                startActivity(new Intent(LoginScreen.this, OwnerHomeActivity.class));
////                finish(); // Finish the current activity
////            }
////        }
//    }
//
////        @Override
////        protected void onPause() {
////            super.onPause();
////            // Save the current screen to SharedPreferences when the activity is paused
////            saveLastScreen(LoginScreen.class.getName());
////        }
//
//
//
//
//    // Method to check ownerId from "OwnerPersonalDetails" node
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
//                                            String userTypeFromDatabase = userSnapshot.child("category").getValue(String.class); // Retrieve userType from the database
//                                            String userType = selectedRadioButton.getText().toString();
//
//                                            Toast.makeText(LoginScreen.this, userTypeFromDatabase + " " + userType, Toast.LENGTH_SHORT).show();
//
//                                            if (userType.equals(userTypeFromDatabase)) {
//                                                // User type matches, redirect to OwnerHomeActivity
//                                                Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
//                                                intent.putExtra("id", ownerId);
////                                                String notificationMessage = "Your property (ID: " + ownerId + ") has been booked by a user.";
//
//                                                intent.putExtra("notification_message",notificationMessage);
////                                                Toast.makeText(LoginScreen.this, notificationMessage, Toast.LENGTH_SHORT).show();
//                                                intent.putExtra("user_id",userId);
//                                                intent.putExtra("userName",userName);
//                                                startActivity(intent);
//                                                finish(); // Finish the current activity
//                                                return;
//                                            } else {
//                                                // User type doesn't match
//                                                Toast.makeText(LoginScreen.this, "Role mismatch", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    } else {
//                                        // No user found with the specified category
//                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                    // Handle database error
//                                }
//                            });
//                        }
//                    }
//                } else {
//                    // Email not found in OwnerPersonalDetails node
//                    Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
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
//
//    // Method to check if the current user is a user
//    private void checkUserDetails(String currentUserEmail, RadioButton selectedRadioButton) {
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
//                .child("UserPersonalDetailsModel");
//
//        userRef.orderByChild("uemail").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // User found in the database
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        String userId = snapshot.child("id").getValue(String.class);
//                        String username=snapshot.child("name").getValue(String.class);
//                        if (userId != null) {
//                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
//                                    .child("Users");
//
//                            userRef.orderByChild("category").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//
//                                            String userTypeFromDatabase = userSnapshot.child("category").getValue(String.class); // Retrieve userType from the database
//                                            String userType = selectedRadioButton.getText().toString();
//                                            Toast.makeText(LoginScreen.this, userTypeFromDatabase + " " + userType, Toast.LENGTH_SHORT).show();
//
//                                            if (userType.equals(userTypeFromDatabase)) {
//                                                Intent intent = new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class);
//                                                intent.putExtra("user_id", userId);
//                                                intent.putExtra("notification_message", notificationMessage);
//                                                intent.putExtra("userName", username);
//                                                Toast.makeText(LoginScreen.this, "userId="+userId, Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(LoginScreen.this, "userName="+username, Toast.LENGTH_SHORT).show();
//
//                                                startActivity(intent);
//                                                // User type matches, redirect to PropertyRecyclerActivityForUser
//                                            } else {
//                                                // User type doesn't match
//                                                Toast.makeText(LoginScreen.this, "Role mismatch", Toast.LENGTH_SHORT).show();
//                                            }
//                                            return; // Exit the loop after finding the matching email
//                                        }
//                                    } else {
//                                        // Email not found in UserPersonalDetails node
//                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                        } else {
//                            Toast.makeText(LoginScreen.this, "UserId is null", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } else {
//                    Toast.makeText(LoginScreen.this, "user not found", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//
//                    public boolean validation () {
//                        String eMail = email.getText().toString().trim();
//                        String userPassword = password.getText().toString().trim();
//                        if (eMail.isEmpty() || userPassword.isEmpty()) {
//                            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }
//                        return true;
//
//                    }
//                }




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
        import com.naksh.renth.databinding.ActivityLoginScreenBinding;

        import android.content.Context;
        import android.content.SharedPreferences;
        import java.util.Objects;



public class LoginScreen extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference reference;
    EditText email;
    EditText password;
    String notificationMessage;
    String ownerId;
    String userId;
    String userName;//    private static final String PREF_NAME = "last_screen_pref";
//    private static final String LAST_SCREEN_KEY = "last_screen";
//    private void saveLastScreen(String screenName) {
//        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(LAST_SCREEN_KEY, screenName);
//        editor.apply();
//    }

    // Method to get the last visited screen from SharedPreferences
//    private String getLastScreen() {
//        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(LAST_SCREEN_KEY, null);
//    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        Intent i = getIntent();
        if (i != null) {
            notificationMessage = i.getStringExtra("notification_message");
            userId = i.getStringExtra("user_id");
            userName = i.getStringExtra("userName");
//            Toast.makeText(this, "id="+userId, Toast.LENGTH_SHORT).show();
        }

        progressDialog = new ProgressDialog(LoginScreen.this);
        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Logging in....");
        email = findViewById(R.id.emailet);
        password = findViewById(R.id.etPassword);
        password = findViewById(R.id.etPassword);
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
                if (!validation()) {
                    return; // Return if validation fails
                }

                int selectedId = binding.person.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(LoginScreen.this, "Please select a category", Toast.LENGTH_SHORT).show();
                    return; // Return without attempting to sign in if no category is selected
                }

                // Getting the selected radio button and user type
                RadioButton selectedRadioButton = findViewById(selectedId);
                String userType = selectedRadioButton.getText().toString();

                // Showing progress dialog
                progressDialog.show();

                // Attempting to sign in with email and password
                auth.signInWithEmailAndPassword(binding.emailet.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss(); // Dismiss the progress dialog

                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if (currentUser != null) {
                                        // Redirecting based on user type
                                        if (userType.equals("User")) {
                                            checkUserDetails(currentUser.getEmail(), selectedRadioButton);
                                        } else if (userType.equals("Owner")) {
                                            checkOwnerPersonalDetails(currentUser.getEmail(), selectedRadioButton);
                                        }
                                    } else {
                                        // Current user is null
                                        Toast.makeText(LoginScreen.this, "Current user is null", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Sign-in failed
                                    Toast.makeText(LoginScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle authentication failure
                                progressDialog.dismiss(); // Dismiss the progress dialog
                                Log.e("Firebase", "Error signing in", e);
//                                Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

//        if (auth.getCurrentUser() != null) {
//            // Get the last visited screen
//            String lastScreen = getLastScreen();
//            if (lastScreen != null) {
//                // Open the last visited screen
//                Intent intent = null;
//                try {
//                    intent = new Intent(LoginScreen.this, Class.forName(lastScreen));
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                startActivity(intent);
//                finish(); // Finish the current activity
//            } else {
//                // If last screen is not available, open default screen
//                startActivity(new Intent(LoginScreen.this, OwnerHomeActivity.class));
//                finish(); // Finish the current activity
//            }
//        }
    }

//        @Override
//        protected void onPause() {
//            super.onPause();
//            // Save the current screen to SharedPreferences when the activity is paused
//            saveLastScreen(LoginScreen.class.getName());
//        }


    // Method to check ownerId from "OwnerPersonalDetails" node
    private void checkOwnerPersonalDetails(String currentUserEmail, RadioButton selectedRadioButton) {
        DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference()
                .child("OwnerPersonalDetailsModel");

        ownerRef.orderByChild("oemail").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String ownerId = snapshot.child("id").getValue(String.class);

                        if (ownerId != null) {
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Users");

                            userRef.orderByChild("category").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                            String userTypeFromDatabase = userSnapshot.child("category").getValue(String.class); // Retrieve userType from the database
                                            String userType = selectedRadioButton.getText().toString();

                                            Toast.makeText(LoginScreen.this, userTypeFromDatabase + " " + userType, Toast.LENGTH_SHORT).show();

                                            if (userType.equals(userTypeFromDatabase)) {
                                                // User type matches, redirect to OwnerHomeActivity
                                                Intent intent = new Intent(LoginScreen.this, OwnerHomeActivity.class);
                                                intent.putExtra("id", ownerId);
                                                String notificationMessage = "Your property (ID: " + ownerId + ") has been booked by a user.";
                                                intent.putExtra("userName", userName);
                                                intent.putExtra("user_id", userId);

                                                intent.putExtra("notification_message", notificationMessage);
                                                startActivity(intent);
                                                finish(); // Finish the current activity
                                                return;
                                            } else {
                                                // User type doesn't match
                                                Toast.makeText(LoginScreen.this, "Role mismatch", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        // No user found with the specified category
                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle database error
                                }
                            });
                        }
                    }
                } else {
                    // Email not found in OwnerPersonalDetails node
                    Toast.makeText(LoginScreen.this, "Owner data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }


    // Method to check if the current user is a user
    private void checkUserDetails(String currentUserEmail, RadioButton selectedRadioButton) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("UserPersonalDetailsModel");

        userRef.orderByChild("uemail").equalTo(currentUserEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User found in the database
                    for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
                        String userId = snapshott.child("id").getValue(String.class);
                        String userName = snapshott.child("name").getValue(String.class);

                        if (userId != null) {
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                                    .child("Users");

                            userRef.orderByChild("category").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        for (DataSnapshot userSnapshott : dataSnapshot.getChildren()) {
                                            String userTypeFromDatabase = userSnapshott.child("category").getValue(String.class); // Retrieve userType from the database
                                            String userType = selectedRadioButton.getText().toString();
                                            Toast.makeText(LoginScreen.this, userTypeFromDatabase + " " + userType, Toast.LENGTH_SHORT).show();

                                            if (userType.equals(userTypeFromDatabase)) {
                                                Intent intent = new Intent(LoginScreen.this, PropertyRecyclerActivityForUser.class);
                                                // User type matches, redirect to PropertyRecyclerActivityForUser
                                                intent.putExtra("user_id", userId);
                                                intent.putExtra("notification_message", notificationMessage);
                                                intent.putExtra("userName", userName);
                                                Toast.makeText(LoginScreen.this, "userId=" + userId, Toast.LENGTH_SHORT).show();
                                                Toast.makeText(LoginScreen.this, "userName=" + userName, Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                                finish(); // Finish the current activity

                                            } else {
                                                // User type doesn't match
                                                Toast.makeText(LoginScreen.this, "Role mismatch", Toast.LENGTH_SHORT).show();
                                            }
//                                            return;
                                        }
                                    }
//                                        return; // Exit the loop after finding the matching email
                                    else {
                                        // Email not found in UserPersonalDetails node
                                        Toast.makeText(LoginScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    
                                }
                            });
                        } else {
                            // Email not found in UserPersonalDetails node
                            Toast.makeText(LoginScreen.this, "userId is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginScreen.this, " data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
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





