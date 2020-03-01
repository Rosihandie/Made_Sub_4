package com.rosihandie.moviecatalogue_sub_4.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private Integer id;
    private Double ratingTv;
    private String titleTv;
    private Double popularityTv;
    private String vote_countTv;
    private String original_languageTv;
    private String descriptionTv;
    private String releaseTv;
    private String photoTv;
    private String backdrop;

    public TvShow() {

    }

    //Setter


    public void setId(Integer id) {
        this.id = id;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    public void setRatingTv(Double ratingTv) {
        this.ratingTv = ratingTv;
    }

    public void setTitleTv(String titleTv) {
        this.titleTv = titleTv;
    }

    public void setPopularityTv(Double popularityTv) {
        this.popularityTv = popularityTv;
    }

    public void setVote_countTv(String vote_countTv) {
        this.vote_countTv = vote_countTv;
    }

    public void setOriginal_languageTv(String original_languageTv) {
        this.original_languageTv = original_languageTv;
    }

    public void setDescriptionTv(String descriptionTv) {
        this.descriptionTv = descriptionTv;
    }

    public void setReleaseTv(String releaseTv) {
        this.releaseTv = releaseTv;
    }

    public void setPhotoTv(String photoTv) {
        this.photoTv = photoTv;
    }


    //Getter


    public Integer getId() {
        return id;
    }

    public String getBackdrop() {
        return backdrop;
    }


    public Double getRatingTv() {
        return ratingTv;
    }

    public String getTitleTv() {
        return titleTv;
    }

    public Double getPopularityTv() {
        return popularityTv;
    }

    public String getVote_countTv() {
        return vote_countTv;
    }

    public String getOriginal_languageTv() {
        return original_languageTv;
    }

    public String getDescriptionTv() {
        return descriptionTv;
    }

    public String getReleaseTv() {
        return releaseTv;
    }

    public String getPhotoTv() {
        return photoTv;
    }

    public TvShow(JSONObject object) {
        try {

            Integer id = object.getInt("id");
            Double vote_average = object.getDouble("vote_average");
            String title = object.getString("name");
            Double popularity = object.getDouble("popularity");
            String vote_count = object.getString("vote_count");
            String original_language = object.getString("original_language");
            String overview = object.getString("overview");
            String release_date = object.getString("first_air_date");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");

            this.id = id;
            this.ratingTv = vote_average;
            this.titleTv = title;
            this.popularityTv = popularity;
            this.vote_countTv = vote_count;
            this.original_languageTv = original_language;
            this.descriptionTv = overview;
            this.releaseTv = release_date;
            this.photoTv = poster_path;
            this.backdrop = backdrop_path;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.ratingTv);
        dest.writeString(this.titleTv);
        dest.writeValue(this.popularityTv);
        dest.writeString(this.vote_countTv);
        dest.writeString(this.original_languageTv);
        dest.writeString(this.descriptionTv);
        dest.writeString(this.releaseTv);
        dest.writeString(this.photoTv);
        dest.writeString(this.backdrop);
    }

    protected TvShow(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ratingTv = (Double) in.readValue(Double.class.getClassLoader());
        this.titleTv = in.readString();
        this.popularityTv = (Double) in.readValue(Double.class.getClassLoader());
        this.vote_countTv = in.readString();
        this.original_languageTv = in.readString();
        this.descriptionTv = in.readString();
        this.releaseTv = in.readString();
        this.photoTv = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
