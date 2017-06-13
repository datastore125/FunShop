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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_EmailID, et_password;
    private TextView btn_login;
    private TextView registerLink;
    String success;
    String getRegistrationID, getEmail, getPassword, getPhoneNo, getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_EmailID = (EditText) findViewById(R.id.et_EmailID);
        et_password = (EditText) findViewById(R.id.et_Email);
        btn_login = (TextView) findViewById(R.id.btn_Login);
        registerLink = (TextView) findViewById(R.id.tv_Register);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        btn_login.setTypeface(custom_font1);
        registerLink.setTypeface(custom_font);
        et_EmailID.setTypeface(custom_font);
        et_password.setTypeface(custom_font);

        registerLink.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == registerLink){
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        }
        if (v == btn_login){
            final String emailID = et_EmailID.getText().toString();
            final String password = et_password.getText().toString();
            Response.Listener<String> responseListener = new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        success = jsonResponse.getString("success");
                        if (success.equals("1")){
                            JSONArray jsonArray = jsonResponse.getJSONArray("User_Reg");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            getRegistrationID = jsonObject.getString("Reg_Id");
                            getEmail = jsonObject.getString("Email");
                            getPassword = jsonObject.getString("Pwd");
                            getName = jsonObject.getString("Name");
                            getPhoneNo = jsonObject.getString("Phoneno");
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("RegistrationId", getRegistrationID);
                            editor.putString("Name", getName);
                            editor.putString("Password", getPassword);
                            editor.putString("PhoneNo", getPhoneNo);
                            editor.putString("Email", getEmail);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, CategoryTabActivity.class);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                            dialog.setMessage("Login Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            LoginRequest loginRequest = new LoginRequest(emailID, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
    }
}
