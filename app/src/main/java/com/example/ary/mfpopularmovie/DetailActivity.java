package com.example.ary.mfpopularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

/**
 * Created by ary on 7/1/17.
 */

public class DetailActivity extends AppCompatActivity {
    TextView nameofMovies,plotSynopsis,userRating,releaseDate;
    ImageView imageView;

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
  }

