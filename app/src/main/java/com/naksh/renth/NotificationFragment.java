package com.naksh.renth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_OWNER_ID = "id";
    private static final String ARG_NOTIFICATION_MESSAGE = "notification_message";
    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_USERNAME = "userName";
    private static final String ARG_PROPERTYID = "property_id";

    private RecyclerView recyclerView;
    private static List<String> notificationMessages; // List to hold notification messages
    private NotificationAdapter adapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String ownerId;
    private static String notificationMessage;
    private static String userId;
    private static String userName;
    private static String propertyId;




    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ownerId Parameter 1.
     * @param param2 Parameter 2.
     * @param  notificationMessage Parameter 3.
     * @param  userName Parameter 3.
     * @param  userId Parameter 3.
     *
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String ownerId, String param2, String notificationMessage, String userId, String userName) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_OWNER_ID, ownerId);
        args.putString(ARG_NOTIFICATION_MESSAGE, notificationMessage);
        args.putString(ARG_USER_ID, userId);
        args.putString(ARG_USERNAME, userName);
        args.putString(ARG_PROPERTYID, propertyId); // Assuming propertyId is also static
        fragment.setArguments(args);
        return fragment;
    }


@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
        ownerId = getArguments().getString(ARG_OWNER_ID);
        notificationMessage = getArguments().getString(ARG_NOTIFICATION_MESSAGE);
        notificationMessage = getArguments().getString(ARG_NOTIFICATION_MESSAGE);
        userId = getArguments().getString(ARG_USER_ID);
        userName = getArguments().getString(ARG_USERNAME);
        propertyId = getArguments().getString(ARG_PROPERTYID);

    }
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewNotifications); // Initialize the RecyclerView object
        notificationMessages = new ArrayList<>(); // Initialize the list to hold notification messages

        // Set up RecyclerView adapter
        adapter = new NotificationAdapter(getContext(), notificationMessages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch notification messages from Firebase
        // Fetch notification messages from Firebase
        DatabaseReference notificationRef = FirebaseDatabase.getInstance().getReference("NotificationMessage");
        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationMessages.clear(); // Clear existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ownerid = snapshot.child("ownerId").getValue(String.class);
                    Integer totalPriceInteger = snapshot.child("totalprice").getValue(Integer.class);
                    int totalprice = (totalPriceInteger != null) ? totalPriceInteger : 0; // Default value if totalprice is null
                    Log.d("Database Values", "OwnerId: " + ownerid + ", TotalPrice: " + totalprice);

                    if (ownerid != null && ownerid.equals(ownerId)) { // Replace currentUserOwnerId with the ownerId of the current user
                        String message = snapshot.child("notificationMessage").getValue(String.class);
//                        Integer totalPriceInteger = snapshot.child("totalprice").getValue(Integer.class);
//                        int totalprice = (totalPriceInteger != null) ? totalPriceInteger : 0; // Default value if totalprice is null
//                        Toast.makeText(getContext(), "price="+totalprice, Toast.LENGTH_SHORT).show();
// Now you can safely use 'totalprice' variable
                        Toast.makeText(getContext(), "price="+totalprice, Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("totalPrice", String.valueOf(totalprice));
// Create new fragment instance and set arguments
                        WalletFragment walletFragment = new WalletFragment();
                        walletFragment.setArguments(bundle);


                        if (message != null) {
                            notificationMessages.add(message);
                        }
                    }
                }
                adapter.reverseList(); // Call this method to reverse the list
                adapter.notifyDataSetChanged(); // Notify adapter that data set has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        return view;
    }

    // Method to add a new notification message
    // Method to add a new notification message
    // Method to add a new notification message
    public void addNotification(String notificationMessage) {
        if (adapter != null) {
            notificationMessages.add(notificationMessage);
            adapter.notifyDataSetChanged();
        } else {
            Log.e("NotificationFragment", "Adapter is null");
        }
    }

}