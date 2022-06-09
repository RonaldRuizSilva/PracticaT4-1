package com.example.practicat4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mostrarAnimesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_animes);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio service = retrofit.create(servicio.class);

        Call<List<anime>> listGetAnime = service.getlAnimes();
        listGetAnime.enqueue(new Callback<List<anime>>() {
            @Override
            public void onResponse(Call<List<anime>> call, Response<List<anime>> response) {

                String code = String.valueOf(response.code());
                if (code.equals("200")) {
                    Toast.makeText(getApplicationContext(), "ANIMES ENCONTRADOS", Toast.LENGTH_SHORT).show();
                    List<anime> myList = response.body();
                    adaptador adapterList = new adaptador(myList,getApplicationContext());
                    recyclerView.setAdapter(adapterList);
                } else {
                    Toast.makeText(getApplicationContext(), "NO HAY ANIMES", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<anime>> call, Throwable t) {

            }
        });

    }
}