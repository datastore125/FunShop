package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceSingleProduct extends AppCompatActivity {
    Toolbar toolbar;
    String price;
    TextView tv_totalAmount;
    EditText et_cardNo, et_cvvCode, et_expDate, etAmount, et_emailId;
    Button btn_pay;
    RadioGroup rdg_select;
    RadioButton rdb_cardPay, rdb_cashPay;
    String Amount = "";
    String amount,emailId, cardnumber, cvv, expiry;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_single_product);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        price = getIntent().getExtras().getString("Price");
        tv_totalAmount = (TextView) findViewById(R.id.tv_totalAmount);
        et_cardNo = (EditText) findViewById(R.id.et_cardNo);
        et_cvvCode = (EditText) findViewById(R.id.et_cvvCode);
        et_expDate = (EditText) findViewById(R.id.et_expDate);
        etAmount = (EditText) findViewById(R.id.etAmount);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        rdb_cardPay = (RadioButton) findViewById(R.id.rdb_cardPay);
        rdb_cashPay = (RadioButton) findViewById(R.id.rdb_cashPay);

        tv_totalAmount.setText(price);
        etAmount.setText(price);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", null);
        et_emailId.setText(email);

        rdg_select = (RadioGroup) findViewById(R.id.rdg_select);
        rdg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rdb_cashPay){
                    et_cardNo.setVisibility(View.GONE);
                    et_cvvCode.setVisibility(View.GONE);
                    et_expDate.setVisibility(View.GONE);
                    startActivity( new Intent(PlaceSingleProduct.this, CartItems.class));
                }
                else if ((checkedId == R.id.rdb_cardPay)){
                    et_cardNo.setVisibility(View.VISIBLE);
                    et_cvvCode.setVisibility(View.VISIBLE);
                    et_expDate.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = etAmount.getText().toString();
                emailId = et_emailId.getText().toString();
                cvv = et_cvvCode.getText().toString();
                cardnumber = et_cardNo.getText().toString();
                expiry = et_expDate.getText().toString();
                if(amount.equals("") || emailId.equals("") || cvv.equals("") || cardnumber.equals("") || expiry.equals("")) {
                    Toast.makeText(PlaceSingleProduct.this, "Please fill up the details", Toast.LENGTH_SHORT).show();
                }else{
                    Response.Listener<String> listener = new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                JSONObject jsonObject = jsonResponse.getJSONObject("Response");
                                message = jsonObject.getString("Messagetext");
                                Toast.makeText(PlaceSingleProduct.this, message, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    PaymentRequest paymentRequest = new PaymentRequest(cardnumber, cvv, expiry, amount, emailId, listener);
                    RequestQueue queue = Volley.newRequestQueue(PlaceSingleProduct.this);
                    queue.add(paymentRequest);
                }
            }
        });

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
}
