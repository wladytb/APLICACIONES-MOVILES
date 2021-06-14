package com.wladytb.practicarecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wladytb.practicarecyclerview.revistaJSON.jsonParseRevista;
import com.wladytb.practicarecyclerview.modelo.revista;
import com.wladytb.practicarecyclerview.revistaJSON.listAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class viewRevista extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_revista);
        Bundle parametro = this.getIntent().getExtras();
        if (parametro != null) {
            getdata(Integer.parseInt(parametro.getString("idRevista")));

        }

    }
    public void getdata(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://revistas.uteq.edu.ec/").
                        addConverterFactory(GsonConverterFactory.create()).build();

        jsonParseRevista jsn = retrofit.create(jsonParseRevista.class);
        Call<List<revista>> call = jsn.getRevistas(id);
        call.enqueue(new Callback<List<revista>>() {
            @Override
            public void onResponse(Call<List<revista>> call, Response<List<revista>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<revista> lista = response.body();
                listAdapter listAdapter= new listAdapter(lista,viewRevista.this);
                RecyclerView recyclerView=  findViewById(R.id.miRecycleView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(viewRevista.this));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<revista>> call, Throwable t) {
            }
        });
    }
}