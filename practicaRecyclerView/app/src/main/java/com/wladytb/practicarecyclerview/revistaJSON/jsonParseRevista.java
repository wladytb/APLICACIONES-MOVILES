package com.wladytb.practicarecyclerview.revistaJSON;
import com.wladytb.practicarecyclerview.modelo.revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface jsonParseRevista {
    @GET("ws/issues.php")
    Call<List<revista>> getRevistas(@Query("j_id") int id);
}
