package com.naksh.renth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_TOTALPRICE = "totalprice";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int totalprice;


    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ownerId Parameter 1.
     * @param param2 Parameter 2.
     * @param  totalprice Parameter 3.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String ownerId, String param2,int totalprice) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ownerId);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_TOTALPRICE, String.valueOf(totalprice));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            totalprice = Integer.parseInt(Objects.requireNonNull(requireArguments().getString(ARG_TOTALPRICE)));

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
//        Toast.makeText(getContext(), "price="+totalprice, Toast.LENGTH_SHORT).show();
        // Retrieve data from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String totalPrice2 = bundle.getString("totalPrice");
            Toast.makeText(getContext(), "price="+totalPrice2, Toast.LENGTH_SHORT).show();

            // Find the TextView in your fragment's layout
            TextView totalPriceTextView = view.findViewById(R.id.price);

            // Set the text of the TextView with totalPrice
            totalPriceTextView.setText(totalprice);
            // Use totalPrice as needed
        }

        // Inflate the layout for this fragment
        return view;
    }
}