package com.example.practicat4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    EditText nombreAnimen,descripeAnimen;

    Button registrar;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        anime anime = (anime) getIntent().getSerializableExtra("anime");



        nombreAnimen = findViewById(R.id.nombreAnimen);
        descripeAnimen = findViewById(R.id.descripeAnimen);
        imagen = findViewById(R.id.imagen);
        registrar = findViewById(R.id.registrar);

        nombreAnimen.setText(anime.getNombre());
        descripeAnimen.setText(anime.getDescripcion());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio service = retrofit.create(servicio.class);

    }
}