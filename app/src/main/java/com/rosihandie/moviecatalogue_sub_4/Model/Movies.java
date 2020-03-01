package com.rosihandie.moviecatalogue_sub_4.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movies implements Parcelable {

    private Integer id;
    private Double rating;
    private String title;
    private Double popularity;
    private String vote_count;
    private String original_language;
    private String description;
    private String release;
    private String photo;
    private String backdrop;

    public Movies() {

    }

    //Setter

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    //Getter

    public String getBackdrop() {
        return backdrop;
    }

    public Integer getId() {
        return id;
    }

    public String getVote_count() {
        return vote_count;
    }

    public Double getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getDescription() {
        return description;
    }

    public String getRelease() {
        return release;
    }

    public String getPhoto() {
        return photo;
    }


    public Movies(JSONObject object) {
        try {

            Integer id = object.getInt("id");
            Double vote_average = object.getDouble("vote_average");
            String title = object.getString("title");
            Double popularity = object.getDouble("popularity");
            String vote_count = object.getString("vote_count");
            String original_language = object.getString("original_language");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");

            this.id = id;
            this.rating = vote_average;
            this.title = title;
            this.popularity = popularity;
            this.vote_count = vote_count;
            this.original_language = original_language;
            this.description = overview;
            this.release = release_date;
            this.photo = poster_path;
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
        dest.writeValue(this.rating);
        dest.writeString(this.title);
        dest.writeValue(this.popularity);
        dest.writeString(this.vote_count);
        dest.writeString(this.original_language);
        dest.writeString(this.description);
        dest.writeString(this.release);
        dest.writeString(this.photo);
        dest.writeString(this.backdrop);
    }

    protected Movies(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.title = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.vote_count = in.readString();
        this.original_language = in.readString();
        this.description = in.readString();
        this.release = in.readString();
        this.photo = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

}
