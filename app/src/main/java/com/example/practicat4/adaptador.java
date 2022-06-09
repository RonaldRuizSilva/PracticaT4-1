package com.example.practicat4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adaptador extends RecyclerView.Adapter<adaptador.viewAdpterAnime> {

    List<anime> animeList;
    Context context;

    public adaptador(List<anime> animeList,Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewAdpterAnime onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_anime, parent, false);
        return new viewAdpterAnime(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewAdpterAnime holder, int position) {


        anime anime = animeList.get(position);
        holder.titulo.setText(anime.getNombre());
        holder.descripcion.setText(anime.getDescripcion());
        String image =  "https://ramenparados.com/wp-content/uploads/2022/02/guardianes-de-la-noche-anime-S3-1024x723.jpg";
        Picasso.get().load(image).into(holder.image_anime);

        holder.ver_anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity2.class);
                intent.putExtra("anime",anime);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    class viewAdpterAnime extends RecyclerView.ViewHolder {

        TextView titulo,descripcion;
        LinearLayout ver_anime;
        ImageView image_anime;

        public viewAdpterAnime(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            ver_anime = itemView.findViewById(R.id.ver_anime);
            image_anime = itemView.findViewById(R.id.image_anime);
        }
    }
}
