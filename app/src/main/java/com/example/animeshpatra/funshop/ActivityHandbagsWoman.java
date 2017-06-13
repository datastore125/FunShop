package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

public class ActivityHandbagsWoman extends AppCompatActivity {
    Toolbar toolbar;
    ListView lv_productDetails;
    String value = "6";
    String GET_PRODUCT_REQUEST_URL ="http://220.225.80.177/onlineshoppingapp/show.asmx/GetProduct?catId="+value;
    ArrayList<FetchProductDetails> arrayList = new ArrayList<>();
    FetchProductDetails fetchProductDetails;
    RequestQueue productRequestQueue;
    String getProductID = "", getProductCategoryID = "", getProductName = "", getProductDescription = "", getProductMarketPrice = "", getProductWebPrice = "", getProductAvalibitily = "", getProductImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handbags_woman);
        lv_productDetails = (ListView) findViewById(R.id.lv_productDetails);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        lv_productDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityHandbagsWoman.this, FinalProduct.class);
                intent.putExtra("ID", arrayList.get(position).getProductID());
                intent.putExtra("CatID", arrayList.get(position).getCategoryID());
                intent.putExtra("ItemName", arrayList.get(position).getItemName());
                intent.putExtra("ItemDesc", arrayList.get(position).getItemDescription());
                intent.putExtra("MarketPrice", arrayList.get(position).getMarketPrice());
                intent.putExtra("WebPrice", arrayList.get(position).getWebPrice());
                intent.putExtra("Avalibility", arrayList.get(position).getAvalibitily());
                intent.putExtra("ProductImage", arrayList.get(position).getProductImage());
                startActivity(intent);
            }
        });

        productRequestQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_PRODUCT_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Products");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObjectCloth = jsonArray.getJSONObject(i);
                        getProductID = jsonObjectCloth.getString("Product_Id");
                        getProductCategoryID = jsonObjectCloth.getString("Category_Id");
                        getProductName = jsonObjectCloth.getString("Item_Name");
                        getProductDescription = jsonObjectCloth.getString("Item_Desc");
                        getProductMarketPrice = jsonObjectCloth.getString("Market_Price");
                        getProductWebPrice = jsonObjectCloth.getString("Web_Price");
                        getProductAvalibitily = jsonObjectCloth.getString("Availability");
                        getProductImage = jsonObjectCloth.getString("Product_Image");

                        fetchProductDetails = new FetchProductDetails();
                        fetchProductDetails.setProductID(getProductID);
                        fetchProductDetails.setCategoryID(getProductCategoryID);
                        fetchProductDetails.setItemName(getProductName);
                        fetchProductDetails.setItemDescription(getProductDescription);
                        fetchProductDetails.setMarketPrice(getProductMarketPrice);
                        fetchProductDetails.setWebPrice(getProductWebPrice);
                        fetchProductDetails.setAvalibitily(getProductAvalibitily);
                        fetchProductDetails.setProductImage(getProductImage);
                        arrayList.add(fetchProductDetails);
                    }
                    if (arrayList.size() > 0){
                        lv_productDetails.setAdapter(new MyAdapter());
                    }
                    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityHandbagsWoman.this);
                        dialog.setMessage("No data")
                                .setNegativeButton("Okay", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityHandbagsWoman.this, "Error Fetching Data", Toast.LENGTH_LONG).show();
            }
        });
        productRequestQueue.add(jsonObjectRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shoppingCart){
            startActivity(new Intent(this, CartItems.class));
        }
        if (id == R.id.home){
            startActivity(new Intent(this, CategoryTabActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.product_details,parent,false);
            ImageView iv_productImage = (ImageView) convertView.findViewById(R.id.iv_productImage);
            TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            TextView tv_marketPrice = (TextView) convertView.findViewById(R.id.tv_marketPrice);
            TextView tv_webPrice = (TextView) convertView.findViewById(R.id.tv_webPrice);
            TextView tv_availibility = (TextView) convertView.findViewById(R.id.tv_avalibility);

            tv_itemName.setText(arrayList.get(position).getItemName());
            tv_marketPrice.setText(arrayList.get(position).getMarketPrice());
            tv_webPrice.setText(arrayList.get(position).getWebPrice());
            tv_availibility.setText(arrayList.get(position).getAvalibitily());
            Picasso.with(ActivityHandbagsWoman.this).load(arrayList.get(position).getProductImage()).into(iv_productImage);
            return convertView;
        }
    }
}
