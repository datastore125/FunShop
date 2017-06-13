package com.example.animeshpatra.funshop;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ANIMESH PATRA on 25-05-2017.
 */

public class LoginRequest extends StringRequest {

    public static final String LOGIN_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/LogIn";
    private Map<String, String> params;

    public LoginRequest(String emailID, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("EmailId", emailID);
        params.put("Pwd", password);
    }
    public Map<String, String> getParams(){
        return params;
    }
}
