package com.example.practicat4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class crearActivity extends AppCompatActivity {

    EditText nombreAnimen,descripeAnimen;

    Button imagenBut,registrar;
    ImageView imagen;

    Uri uriImagen;
    String linkImagen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        nombreAnimen = findViewById(R.id.nombreAnimen);
        descripeAnimen = findViewById(R.id.descripeAnimen);
        imagenBut = findViewById(R.id.imagenBut);
        imagen = findViewById(R.id.imagen);
        registrar = findViewById(R.id.registrar);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio service = retrofit.create(servicio.class);

        imagenBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirImagen();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String nombre = nombreAnimen.getEditableText().toString().trim();
                String descripcion = descripeAnimen.getEditableText().toString().trim();


                if(nombre.equals("")){
                    Log.e("nombre", nombre);
                    Toast.makeText(crearActivity.this, "nombre requerido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(descripcion.equals("")){
                    Log.e("nombre", descripcion);
                    Toast.makeText(crearActivity.this, "descripcion requerido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(linkImagen.equals("")){
                    Log.e("nombre", linkImagen);
                    Toast.makeText(crearActivity.this, "imagen requerido", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("imagen", linkImagen);

                anime anime = new anime();
                anime.setNombre(nombre);
                anime.setDescripcion(descripcion);
                anime.setURL("");

                Call<Void> entre = service.postCrearAnime(anime);
                entre.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        String respuesta = String.valueOf(response.code());
                        Log.e("imagen", respuesta);
                        if (respuesta.equals("201")) {
                            Toast.makeText(getApplicationContext(), "Anime Registrado", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(getApplicationContext(), " Anime no Registrado", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    private void subirImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //noinspection deprecation
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 11) {
            uriImagen = data.getData();
            imagen.setImageURI(uriImagen);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImagen);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] image = outputStream.toByteArray();
                String encodedString = Base64.encodeToString(image, Base64.DEFAULT);
                linkImagen = encodedString;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}