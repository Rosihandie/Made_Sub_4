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
import com.rosihandie.moviecatalogue_sub_4.Activity.TvShowDetailActivity;
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;
import com.rosihandie.moviecatalogue_sub_4.R;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private ArrayList<TvShow> listTvshow = new ArrayList<>();

    public void setTvData(ArrayList<TvShow> items) {
        listTvshow.clear();
        listTvshow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_tv, viewGroup, false);
        TvShowAdapter.TvShowViewHolder tvshowHolder = new TvShowAdapter.TvShowViewHolder(v);
        return tvshowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowViewHolder holder, int position) {
        holder.bind(listTvshow.get(position));
    }

    @Override
    public int getItemCount() {
        return listTvshow.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tv_title;
        TextView tv_release;
        TextView tv_rating;
        TextView tv_description;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_name_tv);
            tv_release = itemView.findViewById(R.id.tv_item_date_tv);
            tv_rating = itemView.findViewById(R.id.tv_item_rating_tv);
            tv_description = itemView.findViewById(R.id.tv_item_description_tv);
            imgPhoto = itemView.findViewById(R.id.img_item_tv);

            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            String vote_average = Double.toString(tvShow.getRatingTv());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPhotoTv();

            tv_title.setText(tvShow.getTitleTv());
            tv_release.setText(tvShow.getReleaseTv());
            tv_rating.setText(vote_average);
            tv_description.setText(tvShow.getDescriptionTv());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorDefault)
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TvShow tvShow = listTvshow.get(position);

            tvShow.setTitleTv(tvShow.getTitleTv());
            tvShow.setDescriptionTv(tvShow.getDescriptionTv());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
