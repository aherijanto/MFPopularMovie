package com.example.ary.mfpopularmovie.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ary on 8/8/17.
 */

public class MovieParcel implements Parcelable {

    private String mCaption;
    private String mId;
    private String mUrl;
    private String plot;
    private Double rating;
    private String release_date;
    private String mTitle;
    private boolean favourite;

    public MovieParcel(){

    }

    protected MovieParcel(Parcel in) {
        mCaption = in.readString();
        mId = in.readString();
        mUrl = in.readString();
        plot = in.readString();
        release_date = in.readString();
        favourite = in.readByte() != 0;
        mTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCaption);
        dest.writeString(mId);
        dest.writeString(mUrl);
        dest.writeString(plot);
        dest.writeDouble(rating);
        dest.writeString(release_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieParcel> CREATOR = new Creator<MovieParcel>() {
        @Override
        public MovieParcel createFromParcel(Parcel in) {
            return new MovieParcel(in);
        }

        @Override
        public MovieParcel[] newArray(int size) {
            return new MovieParcel[size];
        }
    };
}
