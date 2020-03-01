package com.rosihandie.moviecatalogue_sub_4.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rosihandie.moviecatalogue_sub_4.Model.Movies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_DESCRIPTION;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_MOVIEID;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_POSTER;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_RATING;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_RELEASE;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.COLUMN_TITLE;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteMovies.TABLE_NAME;

public class MovieHelper {

    private static DbHelper databaseHelper;
    private static SQLiteDatabase database;
    private static MovieHelper INSTANCE;
    private static final String DATABASE_TABLE = TABLE_NAME;

    private MovieHelper(Context context) {
        databaseHelper = new DbHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movies> getAllMovies() {
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<Movies> arrayList = new ArrayList<>();
        Movies movies;
        if (cursor.getCount() > 0) {
            do {
                movies = new Movies();
                movies.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_MOVIEID))));
                movies.setTitle(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_TITLE)));
                movies.setDescription(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_DESCRIPTION)));
                movies.setRating(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_RATING))));
                movies.setRelease(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_RELEASE)));
                movies.setPhoto(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovies.COLUMN_POSTER)));

                arrayList.add(movies);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovies(Movies movies) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_MOVIEID, movies.getId());
        initialValues.put(COLUMN_TITLE, movies.getTitle());
        initialValues.put(COLUMN_DESCRIPTION, movies.getDescription());
        initialValues.put(COLUMN_RATING, movies.getRating());
        initialValues.put(COLUMN_RELEASE, movies.getRelease());
        initialValues.put(COLUMN_POSTER, movies.getPhoto());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteMovies(int id) {
        database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_MOVIEID + "=" + id, null);
    }

    public boolean checkMovies(String id) {
        database = databaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkMovies = false;
        if (cursor.moveToFirst()) {
            checkMovies = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovies;
    }
}
