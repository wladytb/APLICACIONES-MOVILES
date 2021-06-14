package com.wladytb.practicarecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgview;
    EditText txtIdRevista;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgview = (ImageView) findViewById(R.id.imgview);
        imgview.setImageResource(R.drawable.uteq);
        txtIdRevista = (EditText) findViewById(R.id.txtIdRevista);
        btnBuscar = (Button) findViewById(R.id.btnbuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, viewRevista.class);
                intent.putExtra("idRevista", txtIdRevista.getText().toString().trim());
                MainActivity.this.startActivity(intent);
            }
        });
    }


}