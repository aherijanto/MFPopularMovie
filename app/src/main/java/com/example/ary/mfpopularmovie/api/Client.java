package com.example.ary.mfpopularmovie.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ary on 7/1/17.
 */

public class Client {
    public static final String BASE_URL="http://api.themoviedb.org/3/";
    public static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }

}
