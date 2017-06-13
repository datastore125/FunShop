package com.example.animeshpatra.funshop;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Attributes;

import static android.widget.Toast.LENGTH_LONG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMan extends Fragment implements View.OnClickListener{

    ImageView iv_imageMan, iv_imageClothing, iv_imageAccessories, iv_imageShoes;
    RequestQueue requestQueue;
    RequestQueue subRequestQueue;
    public static final String CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/getcatagory?";
    public static final String SUB_CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/GetCatSubcatDetails?";


    public FragmentMan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_man, container, false);
        iv_imageMan = (ImageView) rootView.findViewById(R.id.iv_imageMan);
        iv_imageClothing = (ImageView) rootView.findViewById(R.id.iv_imageClothing);
        iv_imageAccessories = (ImageView) rootView.findViewById(R.id.iv_imageAccessories);
        iv_imageShoes = (ImageView) rootView.findViewById(R.id.iv_imageShoes);
        iv_imageClothing.setOnClickListener(this);
        iv_imageAccessories.setOnClickListener(this);
        iv_imageShoes.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CATEGORY_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Category");

                    JSONObject jsonObject = jsonArray.getJSONObject(1);
                    String ID = jsonObject.getString("Cat_Id");
                    String Name = jsonObject.getString("Cat_Name");
                    String Image = jsonObject.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(Image).into(iv_imageMan);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        subRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonObjectRequest subJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, SUB_CATEGORY_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray subJsonArray = response.getJSONArray("Category");
                    JSONObject jsonObject = subJsonArray.getJSONObject(1);
                    JSONArray subJsonObjectArray = jsonObject.getJSONArray("SubCategory");
                    JSONObject subJsonObjectClothing = subJsonObjectArray.getJSONObject(0);
                    String clothingID = subJsonObjectClothing.getString("Cat_Id");
                    String clothingName = subJsonObjectClothing.getString("Cat_Name");
                    String clothingImage = subJsonObjectClothing.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(clothingImage).into(iv_imageClothing);

                    JSONObject subJsonObjectAccessories = subJsonObjectArray.getJSONObject(2);
                    String accessoriesID = subJsonObjectAccessories.getString("Cat_Id");
                    String accessoriesName = subJsonObjectAccessories.getString("Cat_Name");
                    String accessoriesImage = subJsonObjectAccessories.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(accessoriesImage).into(iv_imageAccessories);

                    JSONObject subJsonObjectShoes = subJsonObjectArray.getJSONObject(1);
                    String shoesID = subJsonObjectShoes.getString("Cat_Id");
                    String shoesName = subJsonObjectShoes.getString("Cat_Name");
                    String shoesImage = subJsonObjectShoes.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(shoesImage).into(iv_imageShoes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        subRequestQueue.add(subJsonObjectRequest);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        if (v == iv_imageClothing){
            Intent intent = new Intent (getActivity().getApplicationContext(), ActivityClothMan.class);
            startActivity(intent);
        }
        if (v == iv_imageAccessories){
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityAccessoriesMan.class);
            startActivity(intent);
        }
        if (v == iv_imageShoes){
            Intent intent =  new Intent(getActivity().getApplicationContext(), ActivityShoesMan.class);
            startActivity(intent);
        }

    }
}
