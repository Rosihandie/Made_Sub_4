package com.rosihandie.moviecatalogue_sub_4.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_DESCRIPTION;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_POSTER;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_RATING;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_RELEASE;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_TITLE;
import static com.rosihandie.moviecatalogue_sub_4.DataBase.DbContract.FavoriteTvshow.COLUMN_TVID;

public class TvHelper {
    private static DbHelper databaseHelper;
    private static SQLiteDatabase database;
    private static TvHelper INSTANCE;
    private static final String DATABASE_TABLE = DbContract.FavoriteTvshow.TABLE_NAME;

    private TvHelper(Context context) {
        databaseHelper = new DbHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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

    public ArrayList<TvShow> getAllTv() {
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<TvShow> arrayList = new ArrayList<>();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_TVID))));
                tvShow.setTitleTv(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_TITLE)));
                tvShow.setDescriptionTv(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_DESCRIPTION)));
                tvShow.setRatingTv(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_RATING))));
                tvShow.setReleaseTv(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_RELEASE)));
                tvShow.setPhotoTv(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteTvshow.COLUMN_POSTER)));

                arrayList.add(tvShow);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvShow tvShow) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_TVID, tvShow.getId());
        initialValues.put(COLUMN_TITLE, tvShow.getTitleTv());
        initialValues.put(COLUMN_DESCRIPTION, tvShow.getDescriptionTv());
        initialValues.put(COLUMN_RATING, tvShow.getRatingTv());
        initialValues.put(COLUMN_RELEASE, tvShow.getReleaseTv());
        initialValues.put(COLUMN_POSTER, tvShow.getPhotoTv());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteTv(int id) {
        database = databaseHelper.getWritableDatabase();
        database.delete(DbContract.FavoriteTvshow.TABLE_NAME, DbContract.FavoriteTvshow.COLUMN_TVID + "=" + id, null);
    }

    public boolean checkTv(String id) {
        database = databaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + DbContract.FavoriteTvshow.TABLE_NAME + " WHERE " + DbContract.FavoriteTvshow.COLUMN_TVID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkTv = false;
        if (cursor.moveToFirst()) {
            checkTv = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTv;
    }
}
