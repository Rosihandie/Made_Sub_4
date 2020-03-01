package com.rosihandie.moviecatalogue_sub_4.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rosihandie.moviecatalogue_sub_4.Model.Movies;
import com.rosihandie.moviecatalogue_sub_4.R;

import java.util.ArrayList;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder> {
    private ArrayList<Movies> movieList = new ArrayList<>();
    private final Activity activity;

    public FavMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovieList(ArrayList<Movies> movieList) {
        if (movieList.size() > 0) {
            this.movieList.clear();
        }
        this.movieList.addAll(movieList);

        notifyDataSetChanged();
    }

    public void addItem(Movies movies) {
        this.movieList.add(movies);
        notifyItemInserted(movieList.size() - 1);
    }

    public void removeItem(int position) {
        this.movieList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movieList.size());
    }


    @NonNull
    @Override
    public FavMovieAdapter.FavMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favorite, parent, false);
        FavMovieViewHolder favMovieViewHolder = new FavMovieViewHolder(v);
        return favMovieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.FavMovieViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class FavMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tv_title;

        FavMovieViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_favorite);
            tv_title = itemView.findViewById(R.id.tv_favorite_movies);

        }

        void bind(Movies movies) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();
            tv_title.setText(movies.getTitle());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorDefault)
                    .dontAnimate()
                    .into(imgPhoto);
        }

    }
}
