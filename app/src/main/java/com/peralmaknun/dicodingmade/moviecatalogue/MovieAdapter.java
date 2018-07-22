package com.peralmaknun.dicodingmade.moviecatalogue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private int clayout;
    private Context context;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout clayout;
        TextView cjudul;
        TextView tanggalrilis;
        TextView sinopsis;
        TextView rating;
        ImageView cposter;


        public MovieViewHolder(View itemView) {
            super(itemView);
            clayout = itemView.findViewById(R.id.main_adapter);
            cjudul = itemView.findViewById(R.id.judul);
            tanggalrilis = itemView.findViewById(R.id.tanggalrilis);
            sinopsis = itemView.findViewById(R.id.sinopsis);
            cposter = itemView.findViewById(R.id.poster);
        }
    }

    public MovieAdapter(List<Movie> movieList, int clayout, Context context) {
        this.movieList = movieList;
        this.clayout = clayout;
        this.context = context;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(clayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.cjudul.setText(movieList.get(position).getTitle());
        holder.tanggalrilis.setText(movieList.get(position).getReleaseDate());
        holder.sinopsis.setText(movieList.get(position).getOverview());
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+movieList.get(position).getPosterPath())
                .into(holder.cposter);

        holder.clayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetail(movieList.get(position).getTitle(),
                        movieList.get(position).getReleaseDate(),
                        movieList.get(position).getOverview(),
                        movieList.get(position).getBackdropPath());
            }
        });
    }

    public void getDetail(String judul, String tanggalrilis, String sinopsis, String poster){
        Intent goToDetail = new Intent(context, DetailActivity.class);
        goToDetail.putExtra("judul", judul);
        goToDetail.putExtra("tanggalrilis", tanggalrilis);
        goToDetail.putExtra("sinopsis", sinopsis);
        goToDetail.putExtra("poster", poster);
        context.startActivity(goToDetail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}