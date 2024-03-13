//package com.naksh.renth;
//
//import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.naksh.renth.Models.PropertyDetailsModel;
//import com.naksh.renth.databinding.ActivityPropertyDetailsBinding;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Objects;
//import java.util.UUID;
//
//public class PropertyDetails extends AppCompatActivity {
//    ActivityPropertyDetailsBinding binding;
//    private FirebaseAuth mAuth;
//    ProgressDialog progressDialog;
//    DatabaseReference propertiesRef;
//    private static final int GALLERY_REQUEST_CODE = 1001; // Request code for gallery intent
//    private Uri selectedImageUri; // Uri to store the selected image
//    private TextView fromdate;
//    private TextView todate;
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//    private DatePickerDialog.OnDateSetListener mDateSetListener2;
//    String id;
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////
////        // Retrieve ownerId from intent extras
////        String ownerId = getIntent().getStringExtra("ownerId");
////
////        // Use ownerId as propertyId when storing property details to Firebase
////        String propertyId = ownerId; // Use ownerId as propertyId
////
////        Spinner typeOfPropertySpinner = findViewById(R.id.spinner);
////        typeOfPropertySpinner.setPrompt("");
////
////        String[] typeofpropertyoptions = {"House", "Flat", "Villa", "Bungalow", "Cottage", "Penthouse"};
////
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeofpropertyoptions);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        typeOfPropertySpinner.setAdapter(adapter);
////
////        fromdate = findViewById(R.id.fromdate);
////        fromdate.setOnClickListener(v -> showDatePickerDialog(fromdate));
////
////        todate = findViewById(R.id.todate);
////        todate.setOnClickListener(v -> showDatePickerDialog(todate));
////
////        mAuth = FirebaseAuth.getInstance();
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        propertiesRef = database.getReference("PropertyDetailsModel");
////        progressDialog = new ProgressDialog(PropertyDetails.this);
////        progressDialog.setTitle("Saving property details");
////        progressDialog.setMessage("Going to home...");
////
////        binding.propertydp.setOnClickListener(v -> openGallery());
////
////        binding.nextbutton.setOnClickListener(v -> {
////            progressDialog.show();
////            if (selectedImageUri != null) {
////                uploadImageToFirebaseStorage(selectedImageUri);
////            } else {
////                storePropertyDetails(null);
////            }
////        });
////
////        // Generate property id only once when the user initiates the process
////        id = propertiesRef.push().getKey();
////    }
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
//    setContentView(binding.getRoot());
//
//    // Retrieve ownerId from intent extras
//    String ownerId = getIntent().getStringExtra("ownerId");
//
//    Spinner typeOfPropertySpinner = findViewById(R.id.spinner);
//    typeOfPropertySpinner.setPrompt("");
//
//    String[] typeofpropertyoptions = {"House", "Flat", "Villa", "Bungalow", "Cottage", "Penthouse"};
//
//    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeofpropertyoptions);
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    typeOfPropertySpinner.setAdapter(adapter);
//
//    fromdate = findViewById(R.id.fromdate);
//    fromdate.setOnClickListener(v -> showDatePickerDialog(fromdate));
//
//    todate = findViewById(R.id.todate);
//    todate.setOnClickListener(v -> showDatePickerDialog(todate));
//
//    mAuth = FirebaseAuth.getInstance();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    propertiesRef = database.getReference("PropertyDetailsModel");
//    progressDialog = new ProgressDialog(PropertyDetails.this);
//    progressDialog.setTitle("Saving property details");
//    progressDialog.setMessage("Going to home...");
//
//    binding.propertydp.setOnClickListener(v -> openGallery());
//
//    binding.nextbutton.setOnClickListener(v -> {
//        progressDialog.show();
//        if (selectedImageUri != null) {
//            uploadImageToFirebaseStorage(selectedImageUri);
//        } else {
//            storePropertyDetails(ownerId,imageUrl);
//        }
//    });
//
//    // Generate property id only once when the user initiates the process
//    id = propertiesRef.push().getKey();
//}
//
//
//
//    private void showDatePickerDialog(TextView textView) {
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyDetails.this,
//                (view, year1, month1, dayOfMonth) -> {
//                    month1 = month1 + 1;
//                    String date = dayOfMonth + "/" + month1 + "/" + year1;
//                    textView.setText(date);
//                }, year, month, day);
//        datePickerDialog.show();
//    }
//
//    private void openGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            selectedImageUri = data.getData();
//            binding.propertydp.setImageURI(selectedImageUri);
//        }
//    }
//
//    private void uploadImageToFirebaseStorage(String ownerId,Uri imageUri) {
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
//
//        storageRef.putFile(imageUri)
//                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String imageUrl = uri.toString();
//                    storePropertyDetails(ownerId,imageUrl);
//                }))
//                .addOnFailureListener(e -> {
//                    progressDialog.dismiss();
//                    Toast.makeText(PropertyDetails.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }
//
//    private void storePropertyDetails(String ownerId,String imageUrl) {
//        String nameofproperty = Objects.requireNonNull(binding.nameofproperty.getText()).toString();
//        int priceofproperty = Integer.parseInt(Objects.requireNonNull(binding.priceofproperty.getText()).toString());
//        String typeofproperty = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
//        String address = Objects.requireNonNull(binding.addressEditText.getText()).toString();
//        String state = Objects.requireNonNull(binding.stateEditText.getText()).toString();
//        String city = Objects.requireNonNull(binding.cityEditText.getText()).toString();
//        String propertydiscription = Objects.requireNonNull(binding.propertyDescriptionEditText.getText()).toString();
//        String fromDateString = Objects.requireNonNull(binding.fromdate.getText()).toString();
//        String toDateString = Objects.requireNonNull(binding.todate.getText()).toString();
//
//        // Explicitly set the propertyId in the PropertyDetailsModel
//        PropertyDetailsModel propertyDetailsModel;
//
//        if (imageUrl != null) {
//            propertyDetailsModel = new PropertyDetailsModel( nameofproperty,  priceofproperty,  typeofproperty,  address,  state,  city,  propertydiscription,  ownerId, imageUrl);
//        } else {
//            propertyDetailsModel = new PropertyDetailsModel(nameofproperty, typeofproperty, address, city, state, propertydiscription, priceofproperty, imageUrl);
//        }
//
//        assert ownerId != null;
//        propertiesRef.child(ownerId).setValue(propertyDetailsModel)
//                .addOnCompleteListener(task -> {
//                    progressDialog.dismiss();
//                    if (task.isSuccessful()) {
//                        Intent intent = new Intent(PropertyDetails.this, OwnerHomeActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(PropertyDetails.this, "Failed to store property details", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//}

package com.naksh.renth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.naksh.renth.databinding.ActivityPropertyDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PropertyDetails extends AppCompatActivity {
    ActivityPropertyDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference propertiesRef;
    private static final int GALLERY_REQUEST_CODE = 1001; // Request code for gallery intent
    private Uri selectedImageUri; // Uri to store the selected image
    private TextView fromdate;
    private TextView todate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// Retrieve propertyId from Intent extras
        // Retrieve ownerId from intent extras
        String ownerId = getIntent().getStringExtra("id");
//        Toast.makeText(PropertyDetails.this, ownerId, Toast.LENGTH_SHORT).show();
        Spinner typeOfPropertySpinner = findViewById(R.id.spinner);
        typeOfPropertySpinner.setPrompt("");

        String[] typeofpropertyoptions = {"House", "Flat", "Villa", "Bungalow", "Cottage", "Penthouse"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeofpropertyoptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfPropertySpinner.setAdapter(adapter);

        fromdate = findViewById(R.id.fromdate);
        fromdate.setOnClickListener(v -> showDatePickerDialog(fromdate));

        todate = findViewById(R.id.todate);
        todate.setOnClickListener(v -> showDatePickerDialog(todate));

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        propertiesRef = database.getReference("PropertyDetailsModel");
        progressDialog = new ProgressDialog(PropertyDetails.this);
        progressDialog.setTitle("Saving property details");
        progressDialog.setMessage("Going to home...");

        binding.propertydp.setOnClickListener(v -> openGallery());

        binding.nextbutton.setOnClickListener(v -> {
            progressDialog.show();
            if (selectedImageUri != null) {
                uploadImageToFirebaseStorage(ownerId, selectedImageUri);
            } else {
                storePropertyDetails(ownerId, null);
            }
        });

        // Generate property id only once when the user initiates the process
        id = propertiesRef.push().getKey();
    }

    private void showDatePickerDialog(TextView textView) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyDetails.this,
                (view, year1, month1, dayOfMonth) -> {
                    month1 = month1 + 1;
                    String date = dayOfMonth + "/" + month1 + "/" + year1;
                    textView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            binding.propertydp.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageToFirebaseStorage(String ownerId, Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    storePropertyDetails(ownerId, imageUrl);
                }))
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(PropertyDetails.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void storePropertyDetails(String ownerId, String imageUrl) {
        String nameofproperty = Objects.requireNonNull(binding.nameofproperty.getText()).toString();
        int priceofproperty = Integer.parseInt(Objects.requireNonNull(binding.priceofproperty.getText()).toString());
        String typeofproperty = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
        String address = Objects.requireNonNull(binding.addressEditText.getText()).toString();
        String state = Objects.requireNonNull(binding.stateEditText.getText()).toString();
        String city = Objects.requireNonNull(binding.cityEditText.getText()).toString();
        String propertydiscription = Objects.requireNonNull(binding.propertyDescriptionEditText.getText()).toString();
        String fromDateString = Objects.requireNonNull(binding.fromdate.getText()).toString();
        String toDateString = Objects.requireNonNull(binding.todate.getText()).toString();

        // Explicitly set the propertyId in the PropertyDetailsModel
        PropertyDetailsModel propertyDetailsModel;

        if (imageUrl != null) {
            propertyDetailsModel = new PropertyDetailsModel(nameofproperty, priceofproperty, typeofproperty, address, state, city, propertydiscription, ownerId, imageUrl);
        } else {
            propertyDetailsModel = new PropertyDetailsModel(nameofproperty, typeofproperty, address, city, state, propertydiscription, priceofproperty, imageUrl);
        }
//        Toast.makeText(PropertyDetails.this, ownerId, Toast.LENGTH_SHORT).show();
        assert ownerId != null;
        propertiesRef.child(ownerId).setValue(propertyDetailsModel)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, ownerId, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PropertyDetails.this, OwnerHomeActivity.class);
                        intent.putExtra("id", ownerId);

                        startActivity(intent);
                    } else {
                        Toast.makeText(PropertyDetails.this, "Failed to store property details", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

