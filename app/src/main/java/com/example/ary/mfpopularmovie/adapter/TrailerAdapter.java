package com.example.ary.mfpopularmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ary.mfpopularmovie.R;
import com.example.ary.mfpopularmovie.model.MoviesResponse;
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
        viewHolder.title.setText(trailerList.get(i).getName());

    }

    public int getItemCount(){
        return trailerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView mThumbnail;

        public MyViewHolder(View view){
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            mThumbnail=(ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){

               @Override
                public void onClick(View v){

                 int pos=getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        Trailer clickedDataItem=trailerList.get(pos);
                        String videoId=trailerList.get(pos).getKey();
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube"+videoId));

                        intent.putExtra("VIDEO_ID",videoId);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(),"You clicked"+clickedDataItem.getName(),Toast.LENGTH_LONG).show();

                    }
                }

            });
        }
    }
}
