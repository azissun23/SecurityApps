package com.tugasakhir.securityapps.Atribut;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tugasakhir.securityapps.BuildConfig;
import com.tugasakhir.securityapps.Login;
import com.tugasakhir.securityapps.PojoData.PojoUserLogin;
import com.tugasakhir.securityapps.Preferensi.VolleySing;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
    public static String Url = BuildConfig.BASE_URL;
    public static RequestQueue queue;
    public static StringRequest stringRequest;

    public static void Daftar(String user, String password, Context context){

        String URLDaftar = Url+"login.php?user="+user+"&password="+password+"&api_key=" +BuildConfig.API_KEY;
        stringRequest = new StringRequest(Request.Method.GET, URLDaftar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    PojoUserLogin user = new PojoUserLogin(obj);

//                    String kode = obj.getString("kode");
//                    String pesan = obj.getString("pesan");
//                    Log.d("TAG", "onResponse: " +kode + pesan);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
