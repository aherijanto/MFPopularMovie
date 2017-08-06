package com.example.ary.mfpopularmovie.myContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ary.mfpopularmovie.data.FavoriteContract;
import com.example.ary.mfpopularmovie.data.FavoriteDBHelper;

import java.util.HashMap;

/**
 * Created by ary on 8/6/17.
 */

public class FavoriteProvider extends ContentProvider {
    static final String PROVIDER_NAME="com.example.ary.mfpopularmovie.myContentProvider.FavoriteProvider";
    static final String myURL="content://"+PROVIDER_NAME+"/myfavorite";
    static final Uri CONTENT_URL=Uri.parse(myURL);

    static final String mId="myid";
    static final String mTitle="myTitle";
    static final String mRating="myRating";
    static final String mPoster="myPoster";
    static final String mPlot="myPlot";

    static final int UriCode=1;
    private static HashMap<String,String> values;


    static final UriMatcher uriMatcher;

    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"myfavorite",UriCode);
    }

    SQLiteDatabase myDB;
    static final String myDBName=FavoriteDBHelper.DATABASE_NAME;
    static final String myTableName= FavoriteContract.FavoriteEntry.TABLE_NAME;
    static final int myDBVersion=FavoriteDBHelper.DATABASE_VERSION;
    static final String mCreateTable="CREATE TABLE"+" "+FavoriteContract.FavoriteEntry.TABLE_NAME+"("+
            FavoriteContract.FavoriteEntry._ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+
            FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+"INTEGER,"+
            FavoriteContract.FavoriteEntry.COLUMN_TITLE+"TEXT NOT NULL,"+
            FavoriteContract.FavoriteEntry.COLUMN_USERRATING+"REAL NOT NULL,"+
            FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH+"TEXT NOT NULL,"+
            FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS+"TEXT NOT NULL"+
            ");" ;


    @Override
    public boolean onCreate() {
        FavoriteDBHelper myDBHelper= new FavoriteDBHelper(getContext());
        myDB=myDBHelper.getWritableDatabase();
        if (myDB!=null){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder=new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(myTableName);

        switch (uriMatcher.match(uri)){
            case UriCode:
                sqLiteQueryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri"+uri);


        }

        Cursor myCursor=sqLiteQueryBuilder.query(myDB,projection,selection,selectionArgs,null,null,sortOrder);
        myCursor.setNotificationUri(getContext().getContentResolver(),uri);



        return myCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)){
            case UriCode:
                return "vnd.android.cursor.dir/myfavorite";

            default:
                throw new IllegalArgumentException("Unsupported Uri "+uri);


        }
        
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
