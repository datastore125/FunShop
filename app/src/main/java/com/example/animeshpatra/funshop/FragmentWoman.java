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
public class FragmentWoman extends Fragment implements View.OnClickListener{

    ImageView iv_imageWoman, iv_imageClothing, iv_imageShoes, iv_imageHandBags, iv_imageJewelry;
    RequestQueue requestQueue;
    RequestQueue subRequestQueue;
    public static final String CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/getcatagory?";
    public static final String SUB_CATEGORY_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/GetCatSubcatDetails?";

    public FragmentWoman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_woman, container, false);

        iv_imageWoman = (ImageView) rootView.findViewById(R.id.iv_imageWoman);
        iv_imageClothing = (ImageView) rootView.findViewById(R.id.iv_imageClothing);
        iv_imageShoes = (ImageView) rootView.findViewById(R.id.iv_imageShoes);
        iv_imageHandBags = (ImageView) rootView.findViewById(R.id.iv_imageHandBags);
        iv_imageJewelry = (ImageView) rootView.findViewById(R.id.iv_imageJewelry);
        iv_imageClothing.setOnClickListener(this);
        iv_imageShoes.setOnClickListener(this);
        iv_imageHandBags.setOnClickListener(this);
        iv_imageJewelry.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CATEGORY_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Category");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String ID = jsonObject.getString("Cat_Id");
                    String Name = jsonObject.getString("Cat_Name");
                    String Image = jsonObject.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(Image).into(iv_imageWoman);


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
                    JSONObject jsonObject = subJsonArray.getJSONObject(0);
                    JSONArray subJsonObjectArray = jsonObject.getJSONArray("SubCategory");
                    JSONObject subJsonObjectClothing = subJsonObjectArray.getJSONObject(0);
                    String clothingID = subJsonObjectClothing.getString("Cat_Id");
                    String clothingName = subJsonObjectClothing.getString("Cat_Name");
                    String clothingImage = subJsonObjectClothing.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(clothingImage).into(iv_imageClothing);

                    JSONObject subJsonObjectHandBags = subJsonObjectArray.getJSONObject(2);
                    String handBagsID = subJsonObjectHandBags.getString("Cat_Id");
                    String handBagsName = subJsonObjectHandBags.getString("Cat_Name");
                    String handbagsImage = subJsonObjectHandBags.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(handbagsImage).into(iv_imageHandBags);

                    JSONObject subJsonObjectShoes = subJsonObjectArray.getJSONObject(1);
                    String shoesID = subJsonObjectShoes.getString("Cat_Id");
                    String shoesName = subJsonObjectShoes.getString("Cat_Name");
                    String shoesImage = subJsonObjectShoes.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(shoesImage).into(iv_imageShoes);

                    JSONObject subJsonObjectJewelry = subJsonObjectArray.getJSONObject(3);
                    String jewelryID = subJsonObjectJewelry.getString("Cat_Id");
                    String jewelryName = subJsonObjectJewelry.getString("Cat_Name");
                    String jewelryImage = subJsonObjectJewelry.getString("Cat_Image");
                    Picasso.with(getActivity().getApplicationContext()).load(jewelryImage).into(iv_imageJewelry);
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
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityClothingWoman.class);
            startActivity(intent);

        }
        if (v == iv_imageShoes){
            Intent intent = new Intent(getActivity().getApplicationContext(), ActivityShoesWoman.class);
            startActivity(intent);
        }
        if (v == iv_imageHandBags){
            Intent intent = new Intent (getActivity().getApplicationContext(), ActivityHandbagsWoman.class);
            startActivity(intent);

        }
        if (v == iv_imageJewelry){
            Intent intent = new Intent (getActivity().getApplicationContext(), ActivityJewelryWomen.class);
            startActivity(intent);

        }

    }
}
