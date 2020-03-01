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

import com.rosihandie.moviecatalogue_sub_4.Adapter.TvShowAdapter;
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;
import com.rosihandie.moviecatalogue_sub_4.R;
import com.rosihandie.moviecatalogue_sub_4.ViewModel.TvshowViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    View view;
    private final static String LIST_STATE_KEY2 = "EXTRA_TVSHOW";
    private TvShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private TvshowViewModel tvshowViewModel;
    private RecyclerView rvTvshow;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvTvshow = view.findViewById(R.id.rv_tv_show);

        progressBar = view.findViewById(R.id.progress_load_tv);

        tvshowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvshowViewModel.class);
        tvshowViewModel.getTvShow().observe(this, getTvShow);
        tvshowViewModel.setTvShow(LIST_STATE_KEY2);

        showLoading(true);

        showRecyclerMovies();

        return view;
    }

    private void showRecyclerMovies() {
        rvTvshow.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvShowAdapter = new TvShowAdapter();
        rvTvshow.setAdapter(tvShowAdapter);
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShow) {
            if (tvShow != null) {
                tvShowAdapter.setTvData(tvShow);
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
