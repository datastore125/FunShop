package com.example.animeshpatra.funshop;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKids extends Fragment implements View.OnClickListener{

    ImageView iv_imageKids, iv_imageToys, iv_imageClothings, iv_imageBedding;
    RequestQueue requestQueue;
    RequestQueue subRequestQueue;
    public static final String CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/getcatagory?";
    public static final String SUB_CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/GetCatSubcatDetails?";

    public FragmentKids() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kids, container, false);
        iv_imageKids = (ImageView) rootView.findViewById(R.id.iv_imageKids);
        iv_imageToys = (ImageView) rootView.findViewById(R.id.iv_imageToys);
        iv_imageClothings = (ImageView) rootView.findViewById(R.id.iv_imageClothings);
        iv_imageBedding = (ImageView) rootView.findViewById(R.id.iv_imageBedding);
        iv_imageToys.setOnClickListener(this);
        iv_imageClothings.setOnClickListener(this);
        iv_imageBedding.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CATEGORY_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Category");

                    JSONObject jsonObject = jsonArray.getJSONObject(2);
                    String ID = jsonObject.getString("Cat_Id");
                    String Name = jsonObject.getString("Cat_Name");
                    String Image = jsonObject.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(Image).into(iv_imageKids);


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
                    JSONObject jsonObject = subJsonArray.getJSONObject(2);
                    JSONArray subJsonObjectArray = jsonObject.getJSONArray("SubCategory");
                    JSONObject subJsonObjectToys = subJsonObjectArray.getJSONObject(1);
                    String toysID = subJsonObjectToys.getString("Cat_Id");
                    String toysName = subJsonObjectToys.getString("Cat_Name");
                    String toysImage = subJsonObjectToys.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(toysImage).into(iv_imageToys);

                    JSONObject subJsonObjectClothings = subJsonObjectArray.getJSONObject(2);
                    String clothingsID = subJsonObjectClothings.getString("Cat_Id");
                    String clothingsName = subJsonObjectClothings.getString("Cat_Name");
                    String clothingsImage = subJsonObjectClothings.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(clothingsImage).into(iv_imageClothings);

                    JSONObject subJsonObjectBedding = subJsonObjectArray.getJSONObject(0);
                    String beddingID = subJsonObjectBedding.getString("Cat_Id");
                    String beddingName = subJsonObjectBedding.getString("Cat_Name");
                    String beddingImage = subJsonObjectBedding.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(beddingImage).into(iv_imageBedding);
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
        if (v == iv_imageToys){
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityToysKids.class);
            startActivity(intent);
        }
        if ( v == iv_imageClothings){
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityClotingKids.class);
            startActivity(intent);

        }
        if (v == iv_imageBedding){
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityBeddingKids.class);
            startActivity(intent);
        }

    }
}
