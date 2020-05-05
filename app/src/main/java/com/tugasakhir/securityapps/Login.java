package com.tugasakhir.securityapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tugasakhir.securityapps.Atribut.ClassUrl;
import com.tugasakhir.securityapps.Atribut.EncryptMd5;
import com.tugasakhir.securityapps.PojoData.PojoLogUser;
import com.tugasakhir.securityapps.PojoData.PojoUserLogin;
import com.tugasakhir.securityapps.Preferensi.DataShared;
import com.tugasakhir.securityapps.Preferensi.VolleySing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextView TxtRegist;
    Button btnLogin;
    EditText edtUser;
    EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("TAG", "onCreate: ");

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.user_login);
        edtPass = findViewById(R.id.password_login);

        TxtRegist = findViewById(R.id.no_account);
        TxtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                String UserEnter = edtUser.getText().toString();
                String PassEnter = edtPass.getText().toString();
                String PassEncrypt = EncryptMd5.MD5(PassEnter);

                if (TextUtils.isEmpty(edtUser.getText())) {
                    edtUser.setError("User harus diisi");
                    edtUser.requestFocus();
                } else if (TextUtils.isEmpty(edtPass.getText())) {
                    edtPass.setError("Password harus diisi");
                    edtPass.requestFocus();
                } else {
                    Log.d("TAG", "onClick: Login");
                    String URL = BuildConfig.BASE_URL + "login.php?api_key=" + BuildConfig.API_KEY;
                    Log.d("URL", "URL: "+URL);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object1 = new JSONObject(response);
                                JSONArray array = object1.getJSONArray("data");

                                for(int i  = 0; i < array.length(); i++){
                                    JSONObject obj2 = array.getJSONObject(i);
                                    PojoUserLogin user = new PojoUserLogin(obj2);

                                    if(user.getLoginUser().equals(UserEnter) || user.getLoginPass().equals(PassEncrypt)){
                                        goToMainActivity();
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Connection Error" + error, Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
                    VolleySing.getInstance(Login.this).addToRequestQueue(stringRequest);

                }
            }

        });

    }

    private void goToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ingin Keluar Aplikasi?").setCancelable(false).setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Login.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
