package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_Name, et_Password, et_PhoneNumber, et_Email;
    TextView btn_Register, tv_home;
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_Name = (EditText) findViewById(R.id.et_Name);
        et_Password = (EditText) findViewById(R.id.et_Password);
        et_PhoneNumber = (EditText) findViewById(R.id.et_PhoneNumber);
        et_Email = (EditText) findViewById(R.id.et_Email);
        btn_Register = (TextView) findViewById(R.id.btn_Register);
        tv_home = (TextView) findViewById(R.id.tv_home);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        et_Email.setTypeface(custom_font);
        et_Name.setTypeface(custom_font);
        btn_Register.setTypeface(custom_font1);
        et_Password.setTypeface(custom_font);
        tv_home.setTypeface(custom_font);
        et_PhoneNumber.setTypeface(custom_font);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btn_Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String Name = et_Name.getText().toString();
        final String Password = et_Password.getText().toString();
        final String PhoneNumber = et_PhoneNumber.getText().toString();
        final String Email = et_Email.getText().toString();

        Response.Listener<String> listener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("Response");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    success = jsonObject.getString("Messagetext");
                    if (success.equals("Sucess")){
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                        dialog.setMessage("Registration Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(Name, Password, PhoneNumber, Email, listener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }
}
