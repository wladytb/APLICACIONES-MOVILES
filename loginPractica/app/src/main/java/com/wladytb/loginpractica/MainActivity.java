package com.wladytb.loginpractica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wladytb.loginpractica.loginJSON.loginValidacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText txtUser, txtPassword;
    Button btnenviar;
    TextView lblerror;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        lblerror = (TextView) findViewById(R.id.lblerror);
        btnenviar = (Button) findViewById(R.id.btnLogin);
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl("https://revistas.uteq.edu.ec/")
                        .build();
                loginValidacion scalarService = retrofit.create(loginValidacion.class);
                Call<String> stringCall = scalarService.getStringResponse(txtUser.getText().toString(), txtPassword.getText().toString());
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()) {
                            lblerror.setText("estado: " + response.code());
                            return;
                        } else {
                            String responseString = response.body();
                            if (responseString.equals("Login Correcto!")) {
                                Intent intent = new Intent(MainActivity.this, principal.class);
                                MainActivity.this.startActivity(intent);
                            } else {
                                lblerror.setText(responseString);
                                return;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        lblerror.setText(t.getMessage());
                    }
                });
            }
        });
    }
}