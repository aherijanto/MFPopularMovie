package com.example.ary.mfpopularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ary on 7/1/17.
 */

public class Movie {
    @SerializedName("poster_path")
    private String posterpath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releasedate;

    @SerializedName("genre_ids")
    private List<Integer> genreid=new ArrayList<Integer>();

    @SerializedName("id")
    private Integer id;

    @SerializedName("original_title")
    private String originaltitle;

    @SerializedName("original_language")
    private String originallanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("vote_count")
    private Integer votecount;

    @SerializedName("vote_average")
    private Double voteaverage;


    public Movie(String posterpath,Boolean adult,String overview,String releasedate,List<Integer> genreid,Integer id, String originaltitle,
                String originallanguage, String title, String backdrop, Double popularity,Integer votecount, Double voteaverage){

        this.posterpath=posterpath;
        this.adult=adult;
        this.overview=overview;
        this.releasedate=releasedate;
        this.genreid=genreid;
        this.id=id;
        this.originaltitle=originaltitle;
        this.originallanguage=originallanguage;
        this.title=title;
        this.backdrop=backdrop;
        this.popularity=popularity;
        this.voteaverage=voteaverage;

    }

    String imageURL="https://image.tmdb.org/t/p/w500";

    public String getPosterpath(){
        return "https://image.tmdb.org/t/p/w500" + posterpath;
    }

    public void setPosterpath(String posterpath){
        this.posterpath=posterpath;
    }

    public boolean isAdult(){
        return adult;
    }

    public void setAdult(boolean adult){
        this.adult=adult;

    }

    public String getOverview(){
        return overview;

    }

    public void setOverview(String overview){
        this.overview=overview;
    }

    public String getReleasedate(){
        return releasedate;
    }
    public void setReleasedate(String releasedate){
        this.releasedate=releasedate;
    }

    public List<Integer> getGenreid(){
        return genreid;
    }

    public void setGenreid(List<Integer> genreid){
        this.genreid=genreid;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public String getOriginaltitle(){
        return originaltitle;

    }

    public void setOriginaltitle(){
        this.originaltitle=originaltitle;
    }

    public String getBackdrop(){
        return backdrop;
    }

    public void setBackdrop(String backdrop){
        this.backdrop=backdrop;
    }

    public Double getPopularity(){
        return popularity;
    }

    public void setPopularity(Double popularity){
        this.popularity=popularity;
    }

    public Double getVoteAverage(){
        return voteaverage;
    }

    public void setVoteAverage(Double voteaverage){
        this.voteaverage=voteaverage;
    }
}
