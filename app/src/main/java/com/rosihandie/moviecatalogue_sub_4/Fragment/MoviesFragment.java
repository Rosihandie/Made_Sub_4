package com.rosihandie.moviecatalogue_sub_4.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosihandie.moviecatalogue_sub_4.Adapter.MoviesAdapter;
import com.rosihandie.moviecatalogue_sub_4.Model.Movies;
import com.rosihandie.moviecatalogue_sub_4.R;
import com.rosihandie.moviecatalogue_sub_4.ViewModel.MoviesViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    View view;
    private final static String LIST_STATE_KEY = "EXTRA_MOVIES";
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;
    private RecyclerView rvMovies;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_movies, container, false);
        rvMovies = view.findViewById(R.id.rv_movies);

        progressBar = view.findViewById(R.id.progress_load);

        moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, getMovies);
        moviesViewModel.setMovies(LIST_STATE_KEY);

        showLoading(true);

        showRecyclerMovies();

        return view;
    }

    private void showRecyclerMovies() {
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        moviesAdapter = new MoviesAdapter();
        rvMovies.setAdapter(moviesAdapter);
    }

    private Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null) {
                moviesAdapter.setData(movies);
            }

            showLoading(false);

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
