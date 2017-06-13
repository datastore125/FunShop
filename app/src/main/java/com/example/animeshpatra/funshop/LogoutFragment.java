package com.example.animeshpatra.funshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    SQLiteDatabase db;
    String DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ItemLists;";

    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        CreateDatabase();
        db.execSQL(DELETE_TABLE_QUERY);

        Toast.makeText(getActivity(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        // Inflate the layout for this fragment
        return null;
    }
    private void CreateDatabase() {
        db = getActivity().openOrCreateDatabase("ShoppingCart", MODE_PRIVATE, null);
    }

}
