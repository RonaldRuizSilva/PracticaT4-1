package com.example.practicat4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface servicio {
    @GET("Anime")
    Call<List<anime>> getlAnimes();

    @POST("Anime")
    Call<Void> postCrearAnime(@Body anime anime);
}
