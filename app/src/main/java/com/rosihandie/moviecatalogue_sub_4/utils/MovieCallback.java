package com.rosihandie.moviecatalogue_sub_4.utils;

import com.rosihandie.moviecatalogue_sub_4.Model.Movies;

import java.util.ArrayList;

public interface MovieCallback {
    void preExecute();

    void postExecute(ArrayList<Movies> movies);
}
