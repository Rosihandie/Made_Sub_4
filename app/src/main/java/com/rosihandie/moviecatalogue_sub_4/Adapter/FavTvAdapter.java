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
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;
import com.rosihandie.moviecatalogue_sub_4.R;

import java.util.ArrayList;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavTvViewHolder> {
    private ArrayList<TvShow> tvList = new ArrayList<>();
    private final Activity activity;

    public FavTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvList(ArrayList<TvShow> tvList) {
        if (tvList.size() > 0) {
            this.tvList.clear();
        }
        this.tvList.addAll(tvList);

        notifyDataSetChanged();
    }

    public void addItem(TvShow tvShow) {
        this.tvList.add(tvShow);
        notifyItemInserted(tvList.size() - 1);
    }

    public void removeItem(int position) {
        this.tvList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tvList.size());
    }

    @NonNull
    @Override
    public FavTvAdapter.FavTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favorite, parent, false);
        FavTvAdapter.FavTvViewHolder favTvViewHolder = new FavTvAdapter.FavTvViewHolder(v);
        return favTvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvAdapter.FavTvViewHolder holder, int position) {
        holder.bind(tvList.get(position));

    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class FavTvViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tv_title;

        FavTvViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_favorite);
            tv_title = itemView.findViewById(R.id.tv_favorite_movies);

        }

        void bind(TvShow tvShow) {
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPhotoTv();

            tv_title.setText(tvShow.getTitleTv());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorDefault)
                    .dontAnimate()
                    .into(imgPhoto);
        }

    }
}
