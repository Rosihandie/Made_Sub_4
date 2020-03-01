package com.rosihandie.moviecatalogue_sub_4.utils;

import com.rosihandie.moviecatalogue_sub_4.Model.TvShow;

import java.util.ArrayList;

public interface TvCallback {
    void preExecute();

    void postExecute2(ArrayList<TvShow> tv);
}