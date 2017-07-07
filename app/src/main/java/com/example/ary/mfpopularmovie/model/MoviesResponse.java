package com.example.ary.mfpopularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ary on 7/1/17.
 */

public class MoviesResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("totalresults")
    private int totalresults;
    @SerializedName("totalpages")
    private int totalpages;

    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page=page;
    }

    public List<Movie> getResults(){
        return results;
    }

    public void setResults(List<Movie> results){
        this.results=results;
    }

    public List<Movie> getMovies(){
        return results;
    }

    public void setMovies (List<Movie> results){
        this.results=results;
    }

    public int getTotalresults(){
        return totalresults;
    }

    public void setTotalresults(int totalresults){
        this.totalresults=totalresults;
    }

    public int getTotalpages(){
        return totalpages;
    }

    public void setTotalpages(int totalpages){
        this.totalpages=totalpages;
    }
}
