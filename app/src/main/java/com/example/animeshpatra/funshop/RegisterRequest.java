package com.example.animeshpatra.funshop;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ANIMESH PATRA on 25-05-2017.
 */

public class RegisterRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL = "http://220.225.80.177/onlineshoppingapp/show.asmx/Registration";
    private Map<String, String> params;

    public RegisterRequest(String name, String password, String phoneno, String emailID, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Name", name);
        params.put("Password", password);
        params.put("PhNo", phoneno);
        params.put("EmailId", emailID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
