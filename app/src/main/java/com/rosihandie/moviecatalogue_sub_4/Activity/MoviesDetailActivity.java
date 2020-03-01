package com.rosihandie.moviecatalogue_sub_4.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rosihandie.moviecatalogue_sub_4.DataBase.MovieHelper;
import com.rosihandie.moviecatalogue_sub_4.Model.Movies;
import com.rosihandie.moviecatalogue_sub_4.R;

public class MoviesDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIES = "extra_movies";
    private ProgressBar progressBar;
    private MovieHelper movieHelper;
    private ImageButton btnFav, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIES);
        final String movieId = Integer.toString(selectedMovie.getId());

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        ImageButton btnBack = findViewById(R.id.backButton_movies);
        btnBack.setOnClickListener(this);

        btnFav = findViewById(R.id.btn_fav);
        btnFav.setOnClickListener(this);
        btnDel = findViewById(R.id.btn_del);
        btnDel.setOnClickListener(this);

        final TextView titleMovies = findViewById(R.id.detail_name);
        final TextView descriptionMovies = findViewById(R.id.detail_description);
        final TextView releaseMovies = findViewById(R.id.detail_release);
        final TextView ratingMovies = findViewById(R.id.detail_rating);
        final ImageView photoMovies = findViewById(R.id.detail_photo);
        final ImageView posterMovies = findViewById(R.id.detail_poster);

        progressBar = findViewById(R.id.progressBar_detail);
        progressBar.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();

        if (movieHelper.checkMovies(movieId)) {
            btnFav.setVisibility(View.GONE);
            btnDel.setVisibility(View.VISIBLE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Movies movies = getIntent().getParcelableExtra(EXTRA_MOVIES);

                        String rating = Double.toString(movies.getRating());
                        String image_url = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();
                        String image_backdrop = "https://image.tmdb.org/t/p/w185" + movies.getBackdrop();

                        String title = movies.getTitle();
                        String description = movies.getDescription();
                        String release = movies.getRelease();

                        titleMovies.setText(title);
                        descriptionMovies.setText(description);
                        releaseMovies.setText(release);
                        ratingMovies.setText(rating);
                        Glide.with(MoviesDetailActivity.this)
                                .load(image_url)
                                .placeholder(R.color.colorDefault)
                                .dontAnimate()
                                .into(photoMovies);

                        Glide.with(MoviesDetailActivity.this)
                                .load(image_backdrop)
                                .placeholder(R.color.colorDefault)
                                .dontAnimate()
                                .into(posterMovies);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton_movies) {
            onBackPressed();
            {
                finish();
            }
        } else if (v.getId() == R.id.btn_fav) {
            Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIES);
            String favToast = getString(R.string.toastFav);
            String favFail = getString(R.string.toastFavFail);
            long result = movieHelper.insertMovies(selectedMovie);
            if (result > 0) {
                btnFav.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
                Toast.makeText(MoviesDetailActivity.this, favToast, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MoviesDetailActivity.this, favFail, Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btn_del) {
            Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIES);
            String favDel = getString(R.string.toastDel);
            assert selectedMovie != null;
            movieHelper.deleteMovies(selectedMovie.getId());
            Toast.makeText(MoviesDetailActivity.this, favDel, Toast.LENGTH_SHORT).show();
            btnFav.setVisibility(View.VISIBLE);
            btnDel.setVisibility(View.GONE);
        }
    }
}
