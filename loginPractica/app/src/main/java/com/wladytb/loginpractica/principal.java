package com.wladytb.loginpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wladytb.loginpractica.loginJSON.jsonParseRevista;
import com.wladytb.loginpractica.modelo.revista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class principal extends AppCompatActivity {
    TextView lblRevista;
    EditText txtId;
    Button btnenviarretrofit, btnenviarGoogleVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtId = (EditText) findViewById(R.id.txtIdRevista);
        btnenviarretrofit = (Button) findViewById(R.id.btnBuscarRetrofit);
        lblRevista = (TextView) findViewById(R.id.lblrevistas);
        btnenviarretrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirRetrofit();
            }
        });
        btnenviarGoogleVolley = (Button) findViewById(R.id.btnBuscarGoogleVolley);
        btnenviarGoogleVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirGoolgeVolley();
            }
        });
    }

    public void consumirRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://revistas.uteq.edu.ec/").
                        addConverterFactory(GsonConverterFactory.create()).build();

        jsonParseRevista jsn = retrofit.create(jsonParseRevista.class);
        Call<List<revista>> call = jsn.getRevistas(Integer.parseInt(txtId.getText().toString()));
        call.enqueue(new Callback<List<revista>>() {
            @Override
            public void onResponse(Call<List<revista>> call, Response<List<revista>> response) {
                if (!response.isSuccessful()) {
                    lblRevista.setText("estado: " + response.code());
                    return;
                }
                lblRevista.setText("servicio web consumido con Retrofit \n");
                List<revista> lista = response.body();
                for (revista rvt : lista) {
                    String data = "";
                    data += "issue_id: " + rvt.getIssue_id() + "\n" +
                            "volume: " + rvt.getVolume() + "\n" +
                            "number: " + rvt.getNumber() + "\n" +
                            "year: " + rvt.getYear() + "\n" +
                            "data_published: " + rvt.getDate_published() + "\n" +
                            "title: " + rvt.getTitle() + "\n" +
                            "doi: " + rvt.getDoi() + "\n" +
                            "cover: " + rvt.getCover() + "\n \n";
                    lblRevista.append(data);
                }
            }

            @Override
            public void onFailure(Call<List<revista>> call, Throwable t) {
                lblRevista.setText(t.getMessage());
            }
        });
    }

    public void consumirGoolgeVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + Integer.parseInt(txtId.getText().toString()), null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lblRevista.setText("servicio web consumido con Google Volley \n");
                        for (int i = 0; i < response.length(); i++) {
                            String data = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                data += "issue_id: " + jsonObject.getString("issue_id") + "\n" +
                                        "volume: " + jsonObject.getString("volume") + "\n" +
                                        "number: " + jsonObject.getString("number") + "\n" +
                                        "year: " + jsonObject.getString("year") + "\n" +
                                        "date_published: " + jsonObject.getString("date_published") + "\n" +
                                        "title: " + jsonObject.getString("title") + "\n" +
                                        "doi: " + jsonObject.getString("doi") + "\n" +
                                        "cover: " + jsonObject.getString("cover") + "\n \n";
                                lblRevista.append(data);
                            } catch (JSONException e) {
                                lblRevista.setText(e.getMessage());
                            }
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblRevista.setText(error.getMessage());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}