package com.rosihandie.moviecatalogue_sub_4.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //static variable
    private static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "moviecatalogue";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_MOVIE = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s INTEGER," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",

            DbContract.FavoriteMovies.TABLE_NAME,
            DbContract.FavoriteMovies._ID,
            DbContract.FavoriteMovies.COLUMN_MOVIEID,
            DbContract.FavoriteMovies.COLUMN_TITLE,
            DbContract.FavoriteMovies.COLUMN_DESCRIPTION,
            DbContract.FavoriteMovies.COLUMN_RATING,
            DbContract.FavoriteMovies.COLUMN_RELEASE,
            DbContract.FavoriteMovies.COLUMN_POSTER
    );

    private static final String CREATE_TABLE_TVSHOW = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s INTEGER," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",

            DbContract.FavoriteTvshow.TABLE_NAME,
            DbContract.FavoriteTvshow._ID,
            DbContract.FavoriteTvshow.COLUMN_TVID,
            DbContract.FavoriteTvshow.COLUMN_TITLE,
            DbContract.FavoriteTvshow.COLUMN_DESCRIPTION,
            DbContract.FavoriteTvshow.COLUMN_RATING,
            DbContract.FavoriteTvshow.COLUMN_RELEASE,
            DbContract.FavoriteTvshow.COLUMN_POSTER
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteMovies.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteTvshow.TABLE_NAME);
        onCreate(db);
    }
}
