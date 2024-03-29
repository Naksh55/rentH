package com.naksh.renth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String ARG_OWNER_ID = "owner_id";
    private static final String ARG_NOTIFICATION_MESSAGE = "notification_message";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String ownerId;
    private static String notificationMessage;


    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @param param2 Parameter 2.
     * @param  notificationMessage Parameter 3.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String id, String param2,String notificationMessage) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_OWNER_ID, ownerId);
        args.putString(ARG_NOTIFICATION_MESSAGE, notificationMessage);


        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//            ownerId = getArguments().getString(ARG_OWNER_ID);
//            notificationMessage = getArguments().getString(ARG_NOTIFICATION_MESSAGE);
//            Toast.makeText(getContext(),"message="+notificationMessage , Toast.LENGTH_SHORT).show();
//
//
//        }
//    }
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
        ownerId = getArguments().getString(ARG_OWNER_ID);
        notificationMessage = getArguments().getString(ARG_NOTIFICATION_MESSAGE);
        Toast.makeText(getContext(),"message="+notificationMessage , Toast.LENGTH_SHORT).show();
    }
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        TextView textView= view.findViewById(R.id.onotifications);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String ownerId = bundle.getString(ARG_PARAM1);
            if (ownerId != null) {
                Intent intent=new Intent();
                String id = intent.getStringExtra("body");
//                Toast.makeText(getContext(), "id="+ownerId, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "ownerId is null", Toast.LENGTH_SHORT).show();
            // Handle the case where ownerId is null
        }

        // Set notification message to TextView
        textView.setText(notificationMessage);

        return view;

    }
}