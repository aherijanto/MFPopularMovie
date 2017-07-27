package com.example.ary.mfpopularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ary.mfpopularmovie.api.Client;
import com.example.ary.mfpopularmovie.api.Service;
import com.example.ary.mfpopularmovie.model.Trailer;
import com.example.ary.mfpopularmovie.model.TrailerResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ary on 7/1/17.
 */

public class DetailActivity extends AppCompatActivity {
    TextView nameofMovies,plotSynopsis,userRating,releaseDate;
    ImageView imageView;
    private RecyclerView recyclerView;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        imageView=(ImageView) findViewById(R.id.thumbnail_image_header);
        nameofMovies=(TextView) findViewById(R.id.movietitle);
        plotSynopsis=(TextView) findViewById(R.id.plotsynopsis);
        userRating=(TextView) findViewById(R.id.userrating);
        releaseDate=(TextView) findViewById(R.id.releasedate);

        Intent intentNow=getIntent();
        if (intentNow.hasExtra("original_title")){
            String thumbnail=getIntent().getExtras().getString("poster_path");
            String movieName=getIntent().getExtras().getString("original_title");
            String synopsis=getIntent().getExtras().getString("overview");
            String rating=getIntent().getExtras().getString("vote_average");
            String releaseDate1=getIntent().getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.load)
                    .into(imageView);

            nameofMovies.setText(movieName);
            plotSynopsis.setText(synopsis);
            releaseDate.setText(releaseDate1);
            userRating.setText(rating);
        }else
            {
                Toast.makeText(this,"No API Data",Toast.LENGTH_SHORT).show();

            }
            initViews();
    }

        private void initCollapsingToolbar(){
            final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

            collapsingToolbarLayout.setTitle(" ");
            AppBarLayout appBarLayout=(AppBarLayout) findViewById(R.id.appBar);
            appBarLayout.setExpanded(true);

            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow=false;
            int scroll=-1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scroll==-1){
                    scroll = appBarLayout.getTotalScrollRange();

                }

                if (scroll + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShow=true;
                }
                else if (isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow=false;
                }
            }
        });


    }

    private void initViews(){

        trailerList=new ArrayList<Trailer>();
        adapter=new TrailerAdapter (this,trailerList);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view1);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.Adapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void loadJSON(){
        int movie_id=getIntent().getExtras().getInt("id");

        try{
            if (BuildConfig.MY_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please obtain your API key",Toast.LENGTH_SHORT).show();
                return;
            }
            Client mClient=new Client();
            Service apiService=Client.getClient().create(Service.class);
            Call<TrailerResponse> mCall=apiService.getMovieTrailer(movie_id,BuildConfig.MY_API_TOKEN);
            mCall.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    List<Trailer> mTrailer=response.body().getResults();
                    recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(),mTrailer));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Something not right",t.getMessage());
                    Toast.makeText(DetailActivity.this,"Error fetching trailer",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Something went wrong",e.getMessage());
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
  }

