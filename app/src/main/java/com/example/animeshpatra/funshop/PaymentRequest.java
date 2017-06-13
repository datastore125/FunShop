package com.example.animeshpatra.funshop;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ANIMESH PATRA on 03-06-2017.
 */

public class PaymentRequest extends StringRequest {
    public static final String PAYMENT_REQUEST = "http://220.225.80.177/apptransaction/WebService.asmx/Transaction";
    private Map <String, String> params;
    public PaymentRequest(String cardnumber, String cvvcode, String expdate, String amount, String emailId, Response.Listener<String> listener) {
        super(Method.POST, PAYMENT_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("cardNo", cardnumber);
        params.put("cvvCode", cvvcode);
        params.put("expdate", expdate);
        params.put("amount", amount);
        params.put("emailid", emailId);
    }
    public Map<String, String> getParams(){
        return params;
    }
}
