package com.rosihandie.moviecatalogue_sub_4.DataBase;

import android.provider.BaseColumns;

public class DbContract {
    static final class FavoriteMovies implements BaseColumns {

        static final String TABLE_NAME = "favorite_movies";
        static final String COLUMN_MOVIEID = "movieid";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_RATING = "rating";
        static final String COLUMN_RELEASE = "release";
        static final String COLUMN_POSTER = "poster";
    }

    static final class FavoriteTvshow implements BaseColumns {

        static final String TABLE_NAME = "favorite_tvshow";
        static final String COLUMN_TVID = "tv_id";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_RATING = "rating";
        static final String COLUMN_RELEASE = "release";
        static final String COLUMN_POSTER = "poster";
    }
}
