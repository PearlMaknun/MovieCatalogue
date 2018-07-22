package com.peralmaknun.dicodingmade.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    String judul, tanggalrilis, sinopsis, poster;

    TextView Judul, Rilis, Sinopsis;
    ImageView Poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        judul = getIntent().getStringExtra("judul");
        tanggalrilis = getIntent().getStringExtra("tanggalrilis");
        sinopsis = getIntent().getStringExtra("sinopsis");
        poster = getIntent().getStringExtra("poster");

        Judul = findViewById(R.id.judul);
        Rilis = findViewById(R.id.tanggalrilis);
        Sinopsis = findViewById(R.id.sinopsis);
        Poster = findViewById(R.id.poster);

        Judul.setText(judul);
        Rilis.setText(tanggalrilis);
        Sinopsis.setText(sinopsis);
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+poster)
                .into(Poster);
    }
}
