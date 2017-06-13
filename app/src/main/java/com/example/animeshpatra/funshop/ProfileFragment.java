package com.example.animeshpatra.funshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    Toolbar toolbar;
    TextView tv_regID, tv_Name, tv_emailAdd, tv_password, tv_phoneNo;
    Button btn_okay;
    public static final String DEFAULT = "No data";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tv_regID = (TextView) view.findViewById(R.id.tv_regID);
        tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        tv_emailAdd = (TextView) view.findViewById(R.id.tv_emailAdd);
        tv_password = (TextView) view.findViewById(R.id.tv_password);
        tv_phoneNo = (TextView) view.findViewById(R.id.tv_phoneNo);
        btn_okay = (Button) view.findViewById(R.id.btn_okay);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        String R_ID = sharedPreferences.getString("RegistrationId", DEFAULT);
        String Name = sharedPreferences.getString("Name", DEFAULT);
        String Email = sharedPreferences.getString("Email", DEFAULT);
        String Password = sharedPreferences.getString("Password", DEFAULT);
        String PhoneNo = sharedPreferences.getString("PhoneNo", DEFAULT);
        tv_regID.setText(R_ID);
        tv_Name.setText(Name);
        tv_emailAdd.setText(Email);
        tv_password.setText(Password);
        tv_phoneNo.setText(PhoneNo);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ProfileFragment.this);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_category_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shoppingCart){
            startActivity(new Intent(getActivity(), CartItems.class));
        }
        if (id == R.id.home){
            startActivity(new Intent(getActivity(), CategoryTabActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
