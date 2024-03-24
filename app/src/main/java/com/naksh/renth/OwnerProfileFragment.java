
        package com.naksh.renth;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProvider;

        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.naksh.renth.Models.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProfileFragment extends Fragment {
    TextView owneremail;
    TextView ownername;
    TextView ownerphoneno;

    // Declare textView2 as a class-level variable
    TextView textView2;
    TextView textView3;
    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OwnerProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ownerId Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
        public static OwnerProfileFragment newInstance(String ownerId, String param2) {
            OwnerProfileFragment fragment = new OwnerProfileFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, ownerId);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_owner_profile, container, false);
//        TextView textView = view.findViewById(R.id.owneremail);
//        TextView textView2 = view.findViewById(R.id.username);
//
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String receivedText2 = bundle.getString("nameOwner");
//            textView2.setText(receivedText2);
//            String receivedText3 = bundle.getString("emailOwner");
//            textView.setText(receivedText3);
//        }
//        return view;
//    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_owner_profile, container, false);
            TextView textView1= view.findViewById(R.id.owneremail);
            TextView textView2 = view.findViewById(R.id.username);
            TextView textView3 = view.findViewById(R.id.ownerphoneno);
            Bundle bundle = getArguments();

            if (bundle != null) {
                String ownerId = bundle.getString(ARG_PARAM1);
                if (ownerId != null) {
                    retrieveOwnerDetails(ownerId, textView2, textView1, textView3);
                }
            } else {
                Toast.makeText(requireContext(), "ownerId is null", Toast.LENGTH_SHORT).show();
                // Handle the case where ownerId is null
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext())
                    .setTitle("Logout Confirmation")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle logout action
                            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireContext(), LoginScreen.class);

                            startActivity(intent);

                            // Clear previous owner's details
                            textView1.setText("");
                            textView2.setText("");
                            textView3.setText("");
                        }
                    })
                    .setNegativeButton("No", null);

            final AlertDialog logoutConfirmationDialog = alertDialogBuilder.create();

            Button logoutButton = view.findViewById(R.id.logutbutton);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show logout confirmation dialog
                    logoutConfirmationDialog.show();
                }
            });
            return view;

        }




        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);



        }
        private void retrieveOwnerDetails(String ownerId,TextView textView3,TextView textView1,TextView textView2) {
            if (ownerId == null) {
                // Handle the case where ownerId is null
                Log.e("OwnerProfileFragment", "OwnerId is null");
                return;
            }
            if (textView1 == null || textView2 == null || textView3 == null) {
                Log.e("OwnerProfileFragment", "TextView references are null");
                return;
            }

            DatabaseReference ownerRef = FirebaseDatabase.getInstance("https://renth-aca8f-default-rtdb.firebaseio.com/")
                    .getReference("OwnerPersonalDetailsModel")
                    .child(ownerId);

            ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("OwnerProfileFragment", "DataSnapshot exists: " + dataSnapshot.exists());
                    if (dataSnapshot.exists()) {
                        // Retrieve owner details
                        String ownerName = dataSnapshot.child("oname").getValue(String.class);
                        String ownerEmail = dataSnapshot.child("email").getValue(String.class);
                        String ownerPhoneNo = dataSnapshot.child("ophoneno").getValue(String.class);

                        // Set owner details to TextViews
                        textView2.setText(Html.fromHtml("<b>Name:</b> " + ownerName));
                        textView1.setText(Html.fromHtml("<b>Email:</b> " + ownerEmail));
                        textView3.setText(Html.fromHtml("<b>PhoneNo:</b> " + ownerPhoneNo));

                    } else {
                        // Handle the case where no owner details are found
                        Toast.makeText(getContext(), "No owner details found", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
    }