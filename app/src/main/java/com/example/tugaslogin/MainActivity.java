package com.example.tugaslogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText et_email;
    private EditText et_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                AndroidNetworking.post("http://192.168.6.119/TugasLogin/Login.php")
                        .addBodyParameter("Email", email)
                        .addBodyParameter("Password", password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject json = response.getJSONObject("hasil");
                                    boolean sukses = json.getBoolean("sukses");
                                    if (sukses) {
                                        Toast.makeText(MainActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}
