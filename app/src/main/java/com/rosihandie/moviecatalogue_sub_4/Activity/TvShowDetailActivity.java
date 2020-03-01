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
import com.rosihandie.moviecatalogue_sub_4.DataBase.TvHelper;
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;
import com.rosihandie.moviecatalogue_sub_4.R;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private ProgressBar progressBar;
    private TvHelper tvHelper;
    private ImageButton btnFav, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        TvShow selectedTv = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String tvId = Integer.toString(selectedTv.getId());

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        ImageButton btnBack = findViewById(R.id.backButton_tv);
        btnBack.setOnClickListener(this);

        btnFav = findViewById(R.id.btn_fav_tv);
        btnFav.setOnClickListener(this);
        btnDel = findViewById(R.id.btn_del_tv);
        btnDel.setOnClickListener(this);

        final TextView titleTvshow = findViewById(R.id.detail_name_tv);
        final TextView descriptionTvshow = findViewById(R.id.detail_description_tv);
        final TextView releaseTvshow = findViewById(R.id.detail_release_tv);
        final TextView ratingTvshow = findViewById(R.id.detail_rating_tv);
        final ImageView photoTvshow = findViewById(R.id.detail_photo_tv);
        final ImageView posterTvshow = findViewById(R.id.detail_poster_tv);

        progressBar = findViewById(R.id.progressBar_detail_tv);
        progressBar.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();

        if (tvHelper.checkTv(tvId)) {
            btnFav.setVisibility(View.GONE);
            btnDel.setVisibility(View.VISIBLE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

                        String rating = Double.toString(tvShow.getRatingTv());
                        String image_url = "https://image.tmdb.org/t/p/w185" + tvShow.getPhotoTv();
                        String image_backdrop = "https://image.tmdb.org/t/p/w185" + tvShow.getBackdrop();

                        String title = tvShow.getTitleTv();
                        String description = tvShow.getDescriptionTv();
                        String release = tvShow.getReleaseTv();

                        titleTvshow.setText(title);
                        descriptionTvshow.setText(description);
                        releaseTvshow.setText(release);
                        ratingTvshow.setText(rating);

                        Glide.with(TvShowDetailActivity.this)
                                .load(image_url)
                                .placeholder(R.color.colorDefault)
                                .dontAnimate()
                                .into(photoTvshow);

                        Glide.with(TvShowDetailActivity.this)
                                .load(image_backdrop)
                                .placeholder(R.color.colorDefault)
                                .dontAnimate()
                                .into(posterTvshow);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton_tv) {
            onBackPressed();
            {
                finish();
            }
        } else if (v.getId() == R.id.btn_fav_tv) {
            TvShow selectedTv = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            String favToast = getString(R.string.toastFav);
            String favFail = getString(R.string.toastFavFail);
            long result = tvHelper.insertTv(selectedTv);
            if (result > 0) {
                btnFav.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
                Toast.makeText(TvShowDetailActivity.this, favToast, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(TvShowDetailActivity.this, favFail, Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btn_del_tv) {
            TvShow selectedTv = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            String favDel = getString(R.string.toastDel);
            assert selectedTv != null;
            tvHelper.deleteTv(selectedTv.getId());
            Toast.makeText(TvShowDetailActivity.this, favDel, Toast.LENGTH_SHORT).show();
            btnFav.setVisibility(View.VISIBLE);
            btnDel.setVisibility(View.GONE);
        }
    }
}
