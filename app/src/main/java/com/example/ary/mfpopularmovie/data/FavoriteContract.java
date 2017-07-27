package com.example.ary.mfpopularmovie.data;

import android.provider.BaseColumns;

/**
 * Created by ary on 7/27/17.
 */

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME="favorite";
        public static final String COLUMN_MOVIEID="movieid";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_USERRATING="userrating";
        public static final String COLUMN_POSTERPATH="posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS="overview";
    }
}
