package com.rosihandie.moviecatalogue_sub_4.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rosihandie.moviecatalogue_sub_4.Activity.MoviesDetailActivity;
import com.rosihandie.moviecatalogue_sub_4.Model.Movies;
import com.rosihandie.moviecatalogue_sub_4.R;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<Movies> listMovies = new ArrayList<>();

    public void setData(ArrayList<Movies> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment, viewGroup, false);
        MoviesViewHolder moviesHolder = new MoviesViewHolder(v);
        return moviesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(listMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tv_title;
        TextView tv_release;
        TextView tv_rating;
        TextView tv_description;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_name);
            tv_release = itemView.findViewById(R.id.tv_item_date);
            tv_rating = itemView.findViewById(R.id.tv_item_rating);
            tv_description = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_item);

            itemView.setOnClickListener(this);
        }

        void bind(Movies movies) {
            String vote_average = Double.toString(movies.getRating());
            String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPhoto();

            tv_title.setText(movies.getTitle());
            tv_release.setText(movies.getRelease());
            tv_rating.setText(vote_average);
            tv_description.setText(movies.getDescription());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorDefault)
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movies movie = listMovies.get(position);

            movie.setTitle(movie.getTitle());
            movie.setDescription(movie.getDescription());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MoviesDetailActivity.class);
            moveWithObjectIntent.putExtra(MoviesDetailActivity.EXTRA_MOVIES, movie);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
