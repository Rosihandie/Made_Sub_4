package com.rosihandie.moviecatalogue_sub_4.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.moviecatalogue_sub_4.Activity.MoviesDetailActivity;
import com.rosihandie.moviecatalogue_sub_4.Activity.TvShowDetailActivity;
import com.rosihandie.moviecatalogue_sub_4.Adapter.FavMovieAdapter;
import com.rosihandie.moviecatalogue_sub_4.Adapter.FavTvAdapter;
import com.rosihandie.moviecatalogue_sub_4.DataBase.MovieHelper;
import com.rosihandie.moviecatalogue_sub_4.DataBase.TvHelper;
import com.rosihandie.moviecatalogue_sub_4.Model.Movies;
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;
import com.rosihandie.moviecatalogue_sub_4.R;
import com.rosihandie.moviecatalogue_sub_4.utils.ItemClickSupport;
import com.rosihandie.moviecatalogue_sub_4.utils.MovieCallback;
import com.rosihandie.moviecatalogue_sub_4.utils.TvCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements MovieCallback, TvCallback {
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    private FavMovieAdapter favMovieAdapter;
    private FavTvAdapter favTvAdapter;
    private RecyclerView rvMovie, rvTv;
    private final static String LIST_STATE_KEY = "EXTRA_MOVIES";
    private final static String LIST_STATE_KEY2 = "EXTRA_TVSHOW";


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_favorite_movies);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovie.setHasFixedSize(true);

        rvTv = view.findViewById(R.id.rv_favorite_tvshow);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTv.setHasFixedSize(true);

        MovieHelper movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.open();

        TvHelper tvHelper = TvHelper.getInstance(getActivity());
        tvHelper.open();

        favMovieAdapter = new FavMovieAdapter(getActivity());
        rvMovie.setAdapter(favMovieAdapter);

        favTvAdapter = new FavTvAdapter(getActivity());
        rvTv.setAdapter(favTvAdapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieHelper, this).execute();
            new LoadTvAsync(tvHelper, this).execute();
        } else {
            final ArrayList<Movies> moviesState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert moviesState != null;
            moviesArrayList.addAll(moviesState);
            favMovieAdapter.setMovieList(moviesState);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
                intent.putExtra(MoviesDetailActivity.EXTRA_MOVIES, moviesState.get(position));
                startActivity(intent);
            });

            final ArrayList<TvShow> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY2);
            assert tvState != null;
            tvShowArrayList.addAll(tvState);
            favTvAdapter.setTvList(tvState);
            ItemClickSupport.addTo(rvTv).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
                intent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvState.get(position));
                startActivity(intent);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getParcelableArrayList(LIST_STATE_KEY);
        outState.getParcelableArrayList(LIST_STATE_KEY2);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(final ArrayList<Movies> movies) {
        favMovieAdapter.setMovieList(movies);
        rvMovie.setAdapter(favMovieAdapter);
        moviesArrayList.addAll(movies);
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
            intent.putExtra(MoviesDetailActivity.EXTRA_MOVIES, movies.get(position));
            startActivity(intent);
        });
    }

    @Override
    public void postExecute2(final ArrayList<TvShow> tv) {
        favTvAdapter.setTvList(tv);
        rvTv.setAdapter(favTvAdapter);
        tvShowArrayList.addAll(tv);
        ItemClickSupport.addTo(rvTv).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tv.get(position));
            startActivity(intent);
        });
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {
        private final WeakReference<MovieHelper> weakMovieReference;
        private final WeakReference<MovieCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, MovieCallback callback) {
            weakMovieReference = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            return weakMovieReference.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
        private final WeakReference<TvHelper> weakTvReference;
        private final WeakReference<TvCallback> weakCallback;

        public LoadTvAsync(TvHelper tvHelper, TvCallback callback) {
            weakTvReference = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return weakTvReference.get().getAllTv();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute2(tvShows);
        }

    }
}
