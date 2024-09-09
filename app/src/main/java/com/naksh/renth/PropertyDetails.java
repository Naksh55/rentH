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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naksh.renth.Models.PropertyDetailsModel;
import com.naksh.renth.Models.UserTripDetailsModel;
import com.naksh.renth.databinding.ActivityPropertyDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PropertyDetails extends AppCompatActivity {
    ActivityPropertyDetailsBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference propertiesRef;
    //    private static final int GALLERY_REQUEST_CODE = 1001; // Request code for gallery intent
    private Uri selectedImageUri1, selectedImageUri2, selectedImageUri3, selectedImageUri4;

    private TextView fromdate;
    private TextView todate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    String id;
    String ownerId;
    String oName;
    EditText ownername;
    String propertyId;
    Spinner spinner,spinnerState,spinnerCity;
    ImageView propertyDPImageView,propertyDPImageView2,propertyDPImageView3,propertyDPImageView4;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE2 = 2;
    private static final int GALLERY_REQUEST_CODE3 = 3;
    private static final int GALLERY_REQUEST_CODE4 = 4;

    private Uri[] selectedImageUris = new Uri[4]; // Array to store four image URIs

    EditText nameOfPropertyEditText,priceOfPropertyEditText,addressEditText,propertyDescriptionEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nameOfPropertyEditText = findViewById(R.id.nameofproperty);
        spinner = findViewById(R.id.spinner);
        priceOfPropertyEditText = findViewById(R.id.priceofproperty);
        addressEditText = findViewById(R.id.addressEditText);
        spinnerState = findViewById(R.id.spinnerstate);
        spinnerCity = findViewById(R.id.spinnercity);
        propertyDescriptionEditText = findViewById(R.id.propertyDescriptionEditText);
        propertyDPImageView = findViewById(R.id.propertydp);
        ownerId = getIntent().getStringExtra("id");

//        propertyDPImageView2 = findViewById(R.id.propertydp2);
//        propertyDPImageView3 = findViewById(R.id.propertydp3);
//        propertyDPImageView4 = findViewById(R.id.propertydp4);

        // Example for propertyDPImageView2
        propertyDPImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
//        propertyDPImageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GALLERY_REQUEST_CODE2);
//            }
//        });
//
//// Example for propertyDPImageView3
//        propertyDPImageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GALLERY_REQUEST_CODE3);
//            }
//        });

// Example for propertyDPImageView4
//        propertyDPImageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GALLERY_REQUEST_CODE4);
//            }
//        });


        Spinner typeOfPropertySpinner = findViewById(R.id.spinner);
        Spinner cityspinner = findViewById(R.id.spinnercity);
        Spinner statespinner = findViewById(R.id.spinnerstate);
        typeOfPropertySpinner.setPrompt("");
        cityspinner.setPrompt("");
        statespinner.setPrompt("");


        String[] typeofpropertyoptions = {"House", "Flat", "Villa", "Bungalow", "Cottage", "Penthouse"};
        String[] citiesInAndhraPradesh = {
                "Visakhapatnam",
                "Vijayawada",
                "Guntur",
                "Nellore",
                "Kurnool",
                "Kakinada",
                "Rajamahendravaram",
                "Kadapa",
                "Mangalagiri-Tadepalli",
                "Tirupati",
                "Anantapuram",
                "Vizianagaram",
                "Ongole",
                "Eluru",
                "Proddatur",
                "Nandyal",
                "Adoni",
                "Madanapalle",
                "Machilipatnam",
                "Tenali",
                "Chittoor",
                "Hindupur",
                "Srikakulam",
                "Bhimavaram",
                "Tadepalligudem",
                "Guntakal",
                "Dharmavaram",
                "Gudivada",
                "Narasaraopet",
                "Tadipatri",
                "Chilakaluripet"
        };

        String[] citiesInArunachalPradesh = {
                "Aalo",
                "Along",
                "Anini",
                "Basar",
                "Bhalukpong",
                "Bomdila",
                "Changlang",
                "Daporijo",
                "Itanagar",
                "Khonsa",
                "Naharlagun",
                "Namsai",
                "Palin",
                "Pasighat",
                "Roing",
                "Seppa",
                "Tezu",
                "Tawang",
                "Yingkiong",
                "Ziro"
        };

        String[] citiesInAssam = {
                "Barpeta",
                "Bongaigaon",
                "Dhubri",
                "Dibrugarh",
                "Diphu",
                "Goalpara",
                "Golaghat",
                "Guwahati",
                "Haflong",
                "Jorhat",
                "Karimganj",
                "Kokrajhar",
                "Mangaldoi",
                "Nagaon",
                "Nalbari",
                " North Lakhimpur",
                "Sibsagar",
                "Silchar",
                "Tezpur",
                "Tinsukia"

        };

        String[] citiesInBihar = {
                "Ara",
                "Arrah",
                "Bagaha",
                "Begusarai",
                "Bettiah",
                "Bihar Sharif",
                "Buxar",
                "Chapra",
                "Darbhanga",
                "Dehri",
                "Gaya",
                "Hajipur",
                "Jamalpur",
                "Katihar",
                "Madhubani",
                "Motihari",
                "Munger",
                "Muzaffarpur",
                "Nawada",
                "Patna",
                "Purnia",
                "Saharsa",
                "Samastipur",
                "Sasaram",
                "Sitamarhi",
                "Siwan"

        };

        String[] citiesInChhattisgarh = {

                "Ambikapur",
                "Bhilai",
                "Bilaspur",
                "Dhamtari",
                "Durg",
                "Jagdalpur",
                "Korba",
                "Raigarh",
                "Raipur",
                "Rajnandgaon"

        };

        String[] citiesInGoa = {
                "Bicholim",
                "Calangute",
                "Curchorem",
                "Mapusa",
                "Margao",
                "Mormugao",
                "Panaji",
                "Pernem",
                "Ponda",
                "Sanquelim",
                "Valpoi"
        };

        String[] citiesInGujarat = {
                "Ahmedabad",
                "Amreli",
                "Anand",
                "Ankleshwar",
                "Bharuch",
                "Bhavnagar",
                "Bhuj",
                "Dahod",
                "Gandhidham",
                "Gandhinagar",
                "Godhra",
                "Jamnagar",
                "Junagadh",
                "Kandla",
                "Mehsana",
                "Morbi",
                "Nadiad",
                "Navsari",
                "Palanpur",
                "Patan",
                "Porbandar",
                "Rajkot",
                "Surat",
                "Surendranagar",
                "Valsad"

        };

        String[] citiesInHaryana = {
                "Ambala",
                "Bahadurgarh",
                "Bhiwani",
                "Charkhi Dadri",
                "Faridabad",
                "Fatehabad",
                "Gurugram",
                "Hansi",
                "Hisar",
                "Jagadhri",
                "Jind",
                "Kaithal",
                "Karnal",
                "Kurukshetra",
                "Mahendragarh",
                "Narnaul",
                "Palwal",
                "Panchkula",
                "Panipat",
                "Rewari",
                "Rohtak",
                "Sirsa",
                "Sonipat",
                "Yamunanagar"
        };

        String[] citiesInHimachal = {
                "Baijnath",
                "Baddi",
                "Bilaspur",
                "Chamba",
                "Dalhousie",
                "Dharamshala",
                "Hamirpur",
                "Kangra",
                "Kasauli",
                "Kullu",
                "Mandi",
                "Manali",
                "Nahan",
                "Paonta Sahib",
                "Parwanoo",
                "Rampur",
                "Shimla",
                "Solan",
                "Una"
        };

        String[] citiesInJharkhand = {
                "Bokaro",
                "Chaibasa",
                "Chirkunda",
                "Deoghar",
                "Dhanbad",
                "Dumka",
                "Giridih",
                "Gumla",
                "Hazaribagh",
                "Jamshedpur",
                "Jamtara",
                "Jharia",
                "Jhumri Telaiya",
                "Koderma",
                "Medininagar",
                "Phusro",
                "Ramgarh",
                "Ranchi",
                "Sahibganj",
                "Seraikela",
                "Simdega"
        };

        String[] citiesInKarnataka = {
                "Bagalkot",
                "Ballari",
                "Belagavi",
                "Bengaluru",
                "Bidar",
                "Chikkamagaluru",
                "Davanagere",
                "Dharwad",
                "Gadag",
                "Hassan",
                "Haveri",
                "Hubballi-Dharwad",
                "Kalaburagi",
                "Kolar",
                "Koppal",
                "Mandya",
                "Mangaluru",
                "Mysuru",
                "Raichur",
                "Shivamogga",
                "Tumakuru",
                "Udupi",
                "Vijayapura",
                "Yadgir"

        };

        String[] citiesInKerela = {
                " Adoor",
                "Alappuzha",
                "Angamaly",
                "Attingal",
                "Chalakudy",
                "Changanassery",
                "Cherthala",
                "Chittur-Thathamangalam",
                "Ernakulam",
                "Guruvayur",
                "Irinjalakuda",
                "Kalamassery",
                "Kannur",
                "Kasaragod",
                "Kattappana",
                "Kayamkulam",
                "Kollam",
                "Kottayam",
                "Kozhikode",
                "Kunnamkulam",
                "Malappuram",
                "Manjeri",
                "Neyyattinkara",
                "Nileshwaram",
                "Ottappalam",
                "Palakkad",
                "Payyannur",
                "Perinthalmanna",
                "Ponnani",
                "Thalassery",
                "Thiruvananthapuram",
                "Thrissur",
                "Tirur",
                "Vadakara"
        };

        String[] citiesInMP = {
                "Balaghat",
                "Barwani",
                "Betul",
                "Bhind",
                "Bhopal",
                "Burhanpur",
                "Chhindwara",
                "Damoh",
                "Datia",
                "Dewas",
                "Dhar",
                "Guna",
                "Gwalior",
                "Harda",
                "Hoshangabad",
                "Indore",
                "Itarsi",
                "Jabalpur",
                "Khandwa",
                "Khargone",
                "Mandsaur",
                "Morena",
                "Murwara",
                "Nagda",
                "Neemuch",
                "Pithampur",
                "Ratlam",
                "Rewa",
                "Sagar",
                "Satna",
                "Sehore",
                "Seoni",
                "Shahdol",
                "Shivpuri",
                "Singrauli",
                "Ujjain",
                "Vidisha"
        };

        String[] citiesInMaharashtra = {
                "Ahmednagar",
                "Akola",
                "Amravati",
                "Aurangabad",
                "Bhiwandi",
                "Dhule",
                "Ichalkaranji",
                "Jalgaon",
                "Kalyan-Dombivli",
                "Kolhapur",
                "Latur",
                "Malegaon",
                "Mumbai",
                "Nagpur",
                "Nanded",
                "Nashik",
                "Navi Mumbai",
                "Panvel",
                "Parbhani",
                "Pimpri-Chinchwad",
                "Pune",
                "Sangli",
                "Satara",
                "Solapur",
                "Thane",
                "Ulhasnagar",
                "Vasai-Virar"
        };

        String[] citiesInManipur = {
                "Bishnupur",
                "Churachandpur",
                "Imphal",
                "Kakching",
                "Mayang Imphal",
                "Moirang",
                "Moreh",
                "Thoubal",
                "Ukhrul"

        };

        String[] citiesInMeghalaya = {
                "Baghmara",
                "Cherrapunji",
                "Jowai",
                "Nongpoh",
                "Nongstoin",
                "Resubelpara",
                "Shillong",
                "Tura"
        };

        String[] citiesInMizoram = {
                "Aizawl",
                "Champhai",
                "Kolasib",
                "Lunglei",
                "Saiha",
                "Serchhip"
        };

        String[] citiesInNagaland = {
                "Dimapur",
                "Kohima (Capital)",
                "Mokokchung",
                "Mon",
                "Tuensang",
                "Wokha",
                "Zunheboto"
        };

        String[] citiesInOdisha = {
                "Angul",
                "Balangir",
                "Balasore",
                "Bargarh",
                "Baripada",
                "Bhadrak",
                "Bhubaneswar",
                "Brahmapur",
                "Cuttack",
                "Dhenkanal",
                "Jagatsinghpur",
                "Jajpur",
                "Jeypore",
                "Jharsuguda",
                "Kendrapara",
                "Kendujhar",
                "Khordha",
                "Koraput",
                "Nayagarh",
                "Paradip",
                "Puri",
                "Rayagada",
                "Rourkela",
                "Sambalpur",
                "Sundergarh",
        };

        String[] citiesInPunjab = {
                "Amritsar",
                "Barnala",
                "Batala",
                "Bathinda",
                "Faridkot",
                "Fatehgarh Sahib",
                "Fazilka",
                "Firozpur",
                "Gurdaspur",
                "Hoshiarpur",
                "Jagraon",
                "Jalandhar",
                "Kapurthala",
                "Khanna",
                "Ludhiana",
                "Malerkotla",
                "Moga",
                "Mohali",
                "Muktsar",
                "Pathankot",
                "Patiala",
                "Phagwara",
                "Rajpura",
                "Sangrur",
                "Sirhind-Fategarh"
        };

        String[] citiesInRajasthan = {
                "Abu Road",
                "Ajmer",
                "Alwar",
                "Banswara",
                "Baran",
                "Barmer",
                "Beawar",
                "Bharatpur",
                "Bhilwara",
                "Bhiwadi",
                "Bikaner",
                "Bundi",
                "Chittorgarh",
                "Churu",
                "Dausa",
                "Dholpur",
                "Hanumangarh",
                "Jaipur",
                "Jaisalmer",
                "Jalore",
                "Jhalawar",
                "Jhunjhunu",
                "Jodhpur",
                "Karauli",
                "Kota",
                "Nagaur",
                "Pali",
                "Pratapgarh",
                "Pushkar",
                "Rajsamand",
                "Sawai Madhopur",
                "Sikar",
                "Sirohi",
                "Sri Ganganagar",
                "Tonk",
                "Udaipur"
        };

        String[] citiesInSikkim = {
                "Gangtok",
                "Geyzing",
                "Mangan",
                "Namchi",
                "Singtam"
        };

        String[] citiesInTN = {
                "Chennai",
                "Coimbatore",
                "Cuddalore",
                "Dindigul",
                "Erode",
                "Hosur",
                "Kanchipuram",
                "Karur",
                "Krishnagiri",
                "Madurai",
                "Nagercoil",
                "Ooty",
                "Pollachi",
                "Pudukkottai",
                "Ramanathapuram",
                "Salem",
                "Sivakasi",
                "Thanjavur",
                "Thoothukudi",
                "Tiruchirappalli",
                "Tirunelveli",
                "Tiruppur",
                "Tiruvannamalai",
                "Vellore",
                "Viluppuram",
                "Virudhunagar"
        };

        String[] citiesInTelangana = {
                "Adilabad",
                "Bhadrachalam",
                "Hyderabad (Capital)",
                "Jagtial",
                "Karimnagar",
                "Khammam",
                "Kodad",
                "Mahabubnagar",
                "Mancherial",
                "Medak",
                "Miryalaguda",
                "Nalgonda",
                "Nizamabad",
                "Ramagundam",
                "Siddipet",
                "Suryapet",
                "Warangal"
        };

        String[] citiesInTripura = {
                "Agartala",
                "Belonia",
                "Dharmanagar",
                "Kailasahar",
                "Kamalpur",
                "Khowai",
                "Ranirbazar",
                "Sabroom",
                "Sonamura",
                "Udaipur"
        };

        String[] citiesInUP = {
                "Agra",
                "Aligarh",
                "Allahabad",
                "Amroha",
                "Ayodhya",
                "Azamgarh",
                "Banda",
                "Barabanki",
                "Bareilly",
                "Basti",
                "Bijnor",
                "Bulandshahr",
                "Chandausi",
                "Etawah",
                "Faizabad",
                "Farrukhabad",
                "Fatehpur",
                "Firozabad",
                "Ghaziabad",
                "Ghazipur",
                "Gorakhpur",
                "Greater Noida",
                "Hapur",
                "Hardoi",
                "Jaunpur",
                "Jhansi",
                "Kannauj",
                "Kanpur",
                "Lakhimpur",
                "Lalitpur",
                "Lucknow",
                "Mainpuri",
                "Mathura",
                "Meerut",
                "Mirzapur",
                "Moradabad",
                "Muzaffarnagar",
                "Noida",
                "Pilibhit",
                "Pratapgarh",
                "Rae Bareli",
                "Rampur",
                "Saharanpur",
                "Shahjahanpur",
                "Sitapur",
                "Sultanpur",
                "Unnao",
                "Varanasi"
        };

        String[] citiesInUttarakhand = {
                "Almora",
                "Bageshwar",
                "Chamoli",
                "Champawat",
                "Dehradun (Capital)",
                "Haldwani",
                "Haridwar",
                "Kashipur",
                "Khatima",
                "Kotdwar",
                "Nainital",
                "Pauri",
                "Pithoragarh",
                "Ramnagar",
                "Ranikhet",
                "Rishikesh",
                "Roorkee",
                "Rudrapur",
                "Tehri",
                "Udham Singh Nagar",
                "Uttarkashi"
        };

        String[] citiesInWestBengal = {
                "Alipurduar",
                "Asansol",
                "Baharampur",
                "Balurghat",
                "Bankura",
                "Baranagar",
                "Barasat",
                "Bardhaman (Burdwan)",
                "Barrackpore",
                "Basirhat",
                "Bhatpara",
                "Bidhannagar",
                "Chinsurah",
                "Cooch Behar",
                "Darjeeling",
                "Diamond Harbour",
                "Durgapur",
                "Haldia",
                "Howrah",
                "Islampur",
                "Jalpaiguri",
                "Kharagpur",
                "Kolkata (Capital)",
                "Krishnanagar",
                "Malda",
                "Midnapore",
                "Nabadwip",
                "Purulia",
                "Raiganj",
                "Rajpur Sonarpur",
                "Silliguri",
                "South Dumdum",
                "Titagarh"
        };

        String[] citiesInANA = {
                "Andaman and Nicobar Islands"
        };

        String[] citiesInChandigarh = {
                "Chandigarh"
        };


        String[] citiesInDNH = {
                "Dadra and Nagar Haveli"
        };

        String[] citiesInDD = {
                "Daman and Diu"
        };

        String[] citiesInDelhi = {
                "Delhi"
        };

        String[] citiesInLakshadweep = {
                "Lakshadweep"
        };

        String[] citiesInPuducherry = {
                "Puducherry"
        };

        String[] citiesInLadakh = {
                "Ladakh"
        };

        String[] citiesInJK = {
                "Jammu & Kashmir"
        };

// Populate cities map for Andhra Pradesh
        Map<String, String[]> citiesMap = new HashMap<>();
        citiesMap.put("Andhra Pradesh", citiesInAndhraPradesh);

        citiesMap.put("Arunachal Pradesh", citiesInArunachalPradesh);

        citiesMap.put("Assam", citiesInAssam);

        citiesMap.put("Bihar", citiesInBihar);

        citiesMap.put("Chhattisgarh", citiesInChhattisgarh);

        citiesMap.put("Goa", citiesInGoa);

        citiesMap.put("Gujarat", citiesInGujarat);

        citiesMap.put("Haryana", citiesInHaryana);

        citiesMap.put("Himachal Pradesh", citiesInHimachal);

        citiesMap.put("Jharkhand", citiesInJharkhand);

        citiesMap.put("Karnataka", citiesInKarnataka);

        citiesMap.put("Kerala", citiesInKerela);

        citiesMap.put("Madhya Pradesh", citiesInMP);

        citiesMap.put("Maharashtra", citiesInMaharashtra);

        citiesMap.put("Manipur", citiesInManipur);

        citiesMap.put("Meghalaya", citiesInMeghalaya);

        citiesMap.put("Mizoram", citiesInMizoram);

        citiesMap.put("Nagaland", citiesInNagaland);

        citiesMap.put("Odisha", citiesInOdisha);

        citiesMap.put("Punjab", citiesInPunjab);

        citiesMap.put("Rajasthan", citiesInRajasthan);

        citiesMap.put("Sikkim", citiesInSikkim);

        citiesMap.put("Tamil Nadu", citiesInTN);

        citiesMap.put("Telangana", citiesInTelangana);

        citiesMap.put("Tripura", citiesInTripura);

        citiesMap.put("Uttar Pradesh", citiesInUP);

        citiesMap.put("Uttarakhand", citiesInUttarakhand);

        citiesMap.put("West Bengal", citiesInWestBengal);

        citiesMap.put("Andaman and Nicobar Islands", citiesInANA);

        citiesMap.put("Chandigarh", citiesInChandigarh);

        citiesMap.put("Dadra and Nagar Haveli", citiesInDNH);

        citiesMap.put("Daman and Diu", citiesInDD);

        citiesMap.put("Delhi", citiesInDelhi);

        citiesMap.put("Lakshadweep", citiesInLakshadweep);

        citiesMap.put("Puducherry", citiesInPuducherry);

        citiesMap.put("Ladakh", citiesInLadakh);

        citiesMap.put("Jammu & Kashmir", citiesInJK);


        String[] statesOfIndia = {
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal",
                "Andaman and Nicobar Islands",
                "Chandigarh",
                "Dadra and Nagar Haveli",
                "Daman and Diu",
                "Delhi",
                "Lakshadweep",
                "Puducherry",
                "Ladakh",
                "Jammu & Kashmir"

        };


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeofpropertyoptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfPropertySpinner.setAdapter(adapter);

        ArrayAdapter<String> adapterState = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statesOfIndia);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespinner.setAdapter(adapterState);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{});
        cityspinner.setAdapter(cityAdapter);

// Set listener for state spinner
        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = statesOfIndia[position];
                String[] cities = citiesMap.get(selectedState);
                // Update city spinner with cities corresponding to the selected state
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(PropertyDetails.this, android.R.layout.simple_spinner_item, cities);
                cityspinner.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PropertyDetails.this, "please select a state", Toast.LENGTH_SHORT).show();
            }
        });

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
        ownername = findViewById(R.id.oNAME);
        Intent i = getIntent();
        if (i != null) {
            oName = i.getStringExtra("oname");

        } else {
            Toast.makeText(this, "intent is null", Toast.LENGTH_SHORT).show();

        }
        if (oName != null) {
            ownername.setText(oName);
        } else {
            Toast.makeText(this, "oName is null", Toast.LENGTH_SHORT).show();
        }
//        binding.propertydp.setOnClickListener(v -> openGallery());

//        binding.nextbutton.setOnClickListener(v -> {
//            if (!validation()) {
//                return; // Return if validation fails
//            }
//            progressDialog.show();
//            if (selectedImageUri == null) {
//                // Show a toast message indicating that an image must be selected
//                Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss(); // Dismiss progress dialog as image upload is not required
//            } else {
//                progressDialog.show();
//
//                uploadImageToFirebaseStorage(ownerId, selectedImageUri);
//
////                storePropertyDetails(ownerId, null);
//            }
//
//        });
        binding.propertydp.setOnClickListener(v -> openGallery(GALLERY_REQUEST_CODE));
//        binding.propertydp2.setOnClickListener(v -> openGallery(GALLERY_REQUEST_CODE2));
//        binding.propertydp3.setOnClickListener(v -> openGallery(GALLERY_REQUEST_CODE3));
//        binding.propertydp4.setOnClickListener(v -> openGallery(GALLERY_REQUEST_CODE4));
        binding.nextbutton.setOnClickListener(v -> {
            if (!validation()) {
                return;
            }
            progressDialog.show();
            uploadImageToFirebaseStorage(ownerId,selectedImageUri1);
        });
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

                    // Get the selected date as a Calendar object
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, month1 - 1, dayOfMonth); // Month is 0-based

                    // Get the current date
                    Calendar currentDate = Calendar.getInstance();

                    // Compare the selected date with the current date
                    if (selectedDate.before(currentDate)) {
                        // Show a toast indicating that the selected date is invalid
                        Toast.makeText(PropertyDetails.this, "Please choose a date after current date", Toast.LENGTH_SHORT).show();
                    } else {
                        // Set the selected date to the text view
                        textView.setText(date);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

//    private void openGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
//    }
private void openGallery(int requestCode) {
    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(galleryIntent, requestCode);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri1 = data.getData();
            binding.propertydp.setImageURI(selectedImageUri1);
        }
    }
//@Override
//protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    if (resultCode == RESULT_OK && data != null && data.getData() != null) {
//        switch (requestCode) {
//            case GALLERY_REQUEST_CODE:
//                selectedImageUri1 = data.getData();
//                binding.propertydp.setImageURI(selectedImageUri1);
//                break;
//            case GALLERY_REQUEST_CODE2:
//                selectedImageUri2 = data.getData();
//                binding.propertydp2.setImageURI(selectedImageUri2);
//                break;
//            case GALLERY_REQUEST_CODE3:
//                selectedImageUri3 = data.getData();
//                binding.propertydp3.setImageURI(selectedImageUri3);
//                break;
//            case GALLERY_REQUEST_CODE4:
//                selectedImageUri4 = data.getData();
//                binding.propertydp4.setImageURI(selectedImageUri4);
//                break;
//        }
//    }

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
//private void uploadImagesToFirebaseStorage() {
//            if (selectedImageUri1 == null || selectedImageUri2 == null || selectedImageUri3 == null || selectedImageUri4 == null) {
//                Toast.makeText(this, "Please choose all four images", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                return;
//            }
//        }
//
//    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//
//    uploadSingleImage(storageRef, selectedImageUri1, uri1 -> {
//        uploadSingleImage(storageRef, selectedImageUri2, uri2 -> {
//            uploadSingleImage(storageRef, selectedImageUri3, uri3 -> {
//                uploadSingleImage(storageRef, selectedImageUri4, uri4 -> {
//                    storePropertyDetails(ownerId, uri1.toString(), uri2.toString(), uri3.toString(), uri4.toString());
//                });
//            });
//        });
//    });
//}

    private void uploadSingleImage(StorageReference storageRef, Uri imageUri, OnSuccessListener<Uri> onSuccessListener) {
        if (imageUri == null) {
            Log.e("UploadError", "Image URI is null");
            Toast.makeText(this, "Failed to upload image: Image URI is null", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(onSuccessListener))
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Log.e("UploadError", "Failed to upload image: " + e.getMessage());
                    Toast.makeText(PropertyDetails.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void storePropertyDetails(String ownerId, String imageUrl) {
        String nameofproperty = Objects.requireNonNull(binding.nameofproperty.getText()).toString();
        String priceofproperty = String.valueOf(Integer.parseInt(Objects.requireNonNull(binding.priceofproperty.getText()).toString()));
        String typeofproperty = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
        String address = Objects.requireNonNull(binding.addressEditText.getText()).toString();
        String state =  ((Spinner)findViewById(R.id.spinnerstate)).getSelectedItem().toString();
        String city =  ((Spinner)findViewById(R.id.spinnercity)).getSelectedItem().toString();
        String propertydiscription = Objects.requireNonNull(binding.propertyDescriptionEditText.getText()).toString();
        String fromDateString = Objects.requireNonNull(binding.fromdate.getText()).toString();
        String toDateString = Objects.requireNonNull(binding.todate.getText()).toString();
        propertyId = propertiesRef.push().getKey();


        // Explicitly set the propertyId in the PropertyDetailsModel
        PropertyDetailsModel propertyDetailsModel;

        if (imageUrl != null) {
            propertyDetailsModel = new PropertyDetailsModel(nameofproperty, priceofproperty, typeofproperty, address, state, city, propertydiscription, ownerId, imageUrl, fromDateString, toDateString,oName,propertyId);
        } else {
            propertyDetailsModel = new PropertyDetailsModel(nameofproperty, priceofproperty, typeofproperty, address, state, city, propertydiscription, ownerId, imageUrl, fromDateString, toDateString,oName,propertyId);
        }
        //        Toast.makeText(PropertyDetails.this, ownerId, Toast.LENGTH_SHORT).show();
        assert ownerId != null;
//            propertiesRef.child(ownerId).push().setValue(propertyDetailsModel)

// Set the owner ID within the propertyDetailsModel
        propertyDetailsModel.setOwnerId(ownerId);

// Set the property details under the generated key
        assert propertyId != null;
        propertiesRef.child(propertyId).setValue(propertyDetailsModel)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(PropertyDetails.this, OwnerHomeActivity.class);
                        intent.putExtra("id", ownerId);
                        intent.putExtra("propertyId", propertyId);
                        intent.putExtra("oname",oName);

                        startActivity(intent);
                    } else {
                        Toast.makeText(PropertyDetails.this, "Failed to store property details", Toast.LENGTH_SHORT).show();
                    }
                });
    }



//    private void storePropertyDetails(String ownerId, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4) {
//        String nameofproperty = Objects.requireNonNull(binding.nameofproperty.getText()).toString();
//        String priceofproperty = String.valueOf(Integer.parseInt(Objects.requireNonNull(binding.priceofproperty.getText()).toString()));
//        String typeofproperty = binding.spinner.getSelectedItem().toString();
//        String address = Objects.requireNonNull(binding.addressEditText.getText()).toString();
//        String state = binding.spinnerstate.getSelectedItem().toString();
//        String city = binding.spinnercity.getSelectedItem().toString();
//        String propertydiscription = Objects.requireNonNull(binding.propertyDescriptionEditText.getText()).toString();
//        String fromDateString = Objects.requireNonNull(binding.fromdate.getText()).toString();
//        String toDateString = Objects.requireNonNull(binding.todate.getText()).toString();
//        propertyId = propertiesRef.push().getKey();
//
//        PropertyDetailsModel propertyDetailsModel = new PropertyDetailsModel(nameofproperty, priceofproperty, typeofproperty, address, state, city, propertydiscription, ownerId, imageUrl1, imageUrl2, imageUrl3, imageUrl4, fromDateString, toDateString, oName, propertyId);
//        assert ownerId != null;
//            propertiesRef.child(ownerId).push().setValue(propertyDetailsModel);
//
////// Set the owner ID within the propertyDetailsModel
//        propertyDetailsModel.setOwnerId(ownerId);
//
//        propertiesRef.child(propertyId).setValue(propertyDetailsModel).addOnCompleteListener(task -> {
//            progressDialog.dismiss();
//            if (task.isSuccessful()) {
//                Intent intent = new Intent(PropertyDetails.this, OwnerHomeActivity.class);
//                intent.putExtra("id", ownerId);
//                intent.putExtra("propertyId", propertyId);
//                intent.putExtra("oname", oName);
//                startActivity(intent);
//            } else {
//                Log.e("DatabaseError", "Failed to store property details: " + task.getException().getMessage());
//                Toast.makeText(PropertyDetails.this, "Failed to store property details", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public boolean validation() {
//        String oname = nameOfPropertyEditText.getText().toString().trim();
//        String price = priceOfPropertyEditText.getText().toString().trim();
//        String address=addressEditText.getText().toString().trim();
//        String pd=propertyDescriptionEditText.getText().toString().trim();
//        String fromDate=fromdate.getText().toString().trim();
//        String toDate=todate.getText().toString().trim();
//
//
//        if (oname.isEmpty() || price.isEmpty()||address.isEmpty()||pd.isEmpty()) {
//            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
//        // Check if spinners have a valid selection
//        if (spinner.getSelectedItemPosition() == 0 || spinnerState.getSelectedItemPosition() == 0 || spinnerCity.getSelectedItemPosition() == 0) {
//            Toast.makeText(this, "Please select a option from the spinners", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (propertyDPImageView.getDrawable() == null) {
//            Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(fromDate.equals("Select from date")){
//            Toast.makeText(this, "Please select from date", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
//        if(toDate.equals("Select to date")){
//            Toast.makeText(this, "Please select to date", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
//        return true;
//    }
//
//}
//

    public boolean validation() {
        String oname = binding.nameofproperty.getText().toString().trim();
        String price = binding.priceofproperty.getText().toString().trim();
        String address = binding.addressEditText.getText().toString().trim();
        String pd = binding.propertyDescriptionEditText.getText().toString().trim();
        String fromDate = binding.fromdate.getText().toString().trim();
        String toDate = binding.todate.getText().toString().trim();

        if (oname.isEmpty() || price.isEmpty() || address.isEmpty() || pd.isEmpty()) {
            Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.spinner.getSelectedItemPosition() == 0 || binding.spinnerstate.getSelectedItemPosition() == 0 || binding.spinnercity.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select an option from the spinners", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.propertydp.getDrawable() == null || binding.propertydp.getDrawable() == null ) {
            Toast.makeText(this, "Please choose all four images", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fromDate.equals("Select from date")) {
            Toast.makeText(this, "Please select from date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (toDate.equals("Select to date")) {
            Toast.makeText(this, "Please select to date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}