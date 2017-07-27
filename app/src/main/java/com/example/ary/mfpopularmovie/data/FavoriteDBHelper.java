package com.example.ary.mfpopularmovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ary.mfpopularmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ary on 7/27/17.
 */

public class FavoriteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="favorite.db";
    private static final int DATABASE_VERSION=1;

    public static final String LOGTAG="FAVORITE";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    public FavoriteDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        db=dbHandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG,"Database Closed");
        dbHandler.close();
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_FAVORITE_TABLE="CREATE TABLE"+FavoriteContract.FavoriteEntry.TABLE_NAME+"("+
                FavoriteContract.FavoriteEntry._ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+"INTEGER,"+
                FavoriteContract.FavoriteEntry.COLUMN_TITLE+"TEXT NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING+"REAL NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH+"TEXT NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS+"TEXT NOT NULL"+
                ");" ;

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addFavorite(Movie movie){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE,movie.getOriginaltitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH,movie.getPosterpath());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,movie.getOverview());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,null,values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+"="+id,null);

    }

    public List<Movie> getAllFavorite(){
        String[] columns={
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS

        };
        String sortOrder= FavoriteContract.FavoriteEntry._ID+"ASC;";
        List<Movie> favoriteList=new ArrayList<Movie>();
        SQLiteDatabase db=getReadableDatabase();

        Cursor mCursor=db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,null,null,null,null,sortOrder);

        if (mCursor.moveToFirst()){
            do {
                Movie nMovie = new Movie();
                nMovie.setId(Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                nMovie.setOriginaltitle(mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                nMovie.setVoteAverage((Double.parseDouble((mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_USERRATING))))));
                nMovie.setPosterpath(mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH)));
                nMovie.setOverview(mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS)));

                favoriteList.add(nMovie);
            }while (mCursor.moveToNext());
        }
        mCursor.close();
        db.close();
        return favoriteList;
    }
}
