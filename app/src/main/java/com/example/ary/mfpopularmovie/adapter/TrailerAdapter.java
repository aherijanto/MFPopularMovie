package com.example.ary.mfpopularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ary.mfpopularmovie.R;
import com.example.ary.mfpopularmovie.model.Trailer;

import java.util.List;

/**
 * Created by ary on 7/27/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder>{

    private Context mContext;
    private List<Trailer> trailerList;


    public TrailerAdapter(Context mContext, List<Trailer> trailerList){

            this.mContext=mContext;
            this.trailerList=trailerList;

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

            View view= LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.trailer_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.MyViewHolder viewHolder, int i){

    }

    public int getItemCount(){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View view){

        }
    }
}
