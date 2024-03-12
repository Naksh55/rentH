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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.naksh.renth.Models.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProfileFragment extends Fragment {
TextView owneremail;
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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerProfileFragment newInstance(String param1, String param2) {
        OwnerProfileFragment fragment = new OwnerProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
    TextView textView = view.findViewById(R.id.owneremail);
    TextView textView2 = view.findViewById(R.id.username);
    TextView textView3 = view.findViewById(R.id.ownerphoneno);

    Bundle bundle = getArguments();

    if (bundle != null) {
        String receivedText2 = bundle.getString("nameOwner");
        textView2.setText(receivedText2); // Set name text to textView2
        String receivedText3 = bundle.getString("emailOwner");
        textView.setText(receivedText3); // Set email text to textView
        String receivedText4 = bundle.getString("ownerPhoneNo");
        textView3.setText(receivedText4);
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

}