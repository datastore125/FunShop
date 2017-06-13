package com.example.animeshpatra.funshop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {

    RatingBar ratingBar;


    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(), "Thank you for giving us "+rating+" rating.", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(RatingFragment.this);
                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
