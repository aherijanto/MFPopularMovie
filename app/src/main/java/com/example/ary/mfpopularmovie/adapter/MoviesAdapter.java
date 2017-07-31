package com.example.ary.mfpopularmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ary.mfpopularmovie.DetailActivity;
import com.example.ary.mfpopularmovie.R;
import com.example.ary.mfpopularmovie.model.Movie;

import java.util.List;

/**
 * Created by ary on 7/1/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    public MoviesAdapter(Context mContext, List<Movie> movieList){
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card,viewGroup,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder,int i){
        viewHolder.title.setText(movieList.get(i).getOriginaltitle());
        String vote=Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);


        Glide.with(mContext)
                .load(movieList.get(i).getPosterpath())
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            userrating=(TextView) view.findViewById(R.id.userrating);
            thumbnail=(ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        Movie clickeddataItem= movieList.get(pos);
                        Intent intent=new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title",movieList.get(pos).getOriginaltitle());
                        intent.putExtra("poster_path",movieList.get(pos).getPosterpath());
                        intent.putExtra("overview",movieList.get(pos).getOverview());
                        intent.putExtra("vote_average",Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("id",movieList.get(pos).getId());
                        intent.putExtra("release_date",movieList.get(pos).getReleasedate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(),clickeddataItem.getOriginaltitle(),Toast.LENGTH_SHORT).show();
                    }
                }






            });

        }
    }
}
