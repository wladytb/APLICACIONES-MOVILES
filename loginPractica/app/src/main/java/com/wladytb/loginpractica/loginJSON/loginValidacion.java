package com.wladytb.loginpractica.loginJSON;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface loginValidacion {
    @GET("ws/login.php")
    Call<String> getStringResponse(@Query("usr") String usr,@Query("pass") String pass);
}
