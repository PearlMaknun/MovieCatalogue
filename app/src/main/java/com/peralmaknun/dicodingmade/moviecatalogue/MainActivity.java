package com.peralmaknun.dicodingmade.moviecatalogue;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    showList();
                }
                return false;
            }
        });
        return true;
    }

    private void searchMovie(String key){
        progressDialog = ProgressDialog.show(this, null, "Mengambil....", true, false);

        final RecyclerView recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        Call<MovieResponse> call = apiService.searchMovie(API_KEY, key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                final List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_movie, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showList() {
        progressDialog = ProgressDialog.show(this, null, "Mengambil....", true, false);

        final RecyclerView recyclerView = findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        Call<MovieResponse> call = apiService.getPopular(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                final List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.adapter_movie, getApplicationContext()));

                recyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}