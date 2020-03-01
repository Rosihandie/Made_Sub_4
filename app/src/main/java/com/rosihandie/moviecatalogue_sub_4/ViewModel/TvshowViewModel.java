package com.rosihandie.moviecatalogue_sub_4.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rosihandie.moviecatalogue_sub_4.BuildConfig;
import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {
    String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();

    public void setTvShow(final String tvShow) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShow tvShowItems = new TvShow(tvShow);
                        listItems.add(tvShowItems);
                    }

                    listTvShow.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }

}
