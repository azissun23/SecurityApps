package com.tugasakhir.securityapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tugasakhir.securityapps.Atribut.EncryptMd5;
import com.tugasakhir.securityapps.Atribut.Server;
import com.tugasakhir.securityapps.Preferensi.VolleySing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button registBtn;
    EditText UserRegist, PassRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserRegist = findViewById(R.id.user_regist);
        PassRegist = findViewById(R.id.password_regist);
        registBtn = findViewById(R.id.btn_register);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick : ");
                final String user = UserRegist.getText().toString();
                final String password = PassRegist.getText().toString();
                final String PassEncrypt = EncryptMd5.MD5(password);

                if (TextUtils.isEmpty(user)) {
                    UserRegist.setError("Masukan email");
                    UserRegist.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    PassRegist.setError("Masukan password");
                    PassRegist.requestFocus();
                } else {
                    Log.d("TAG", "onClick : ");
                    Server.Daftar(user, PassEncrypt,Register.this);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    },1600);
                }
            }
        });
    }
}
