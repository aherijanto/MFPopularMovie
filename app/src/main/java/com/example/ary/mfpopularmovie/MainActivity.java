package com.example.ary.mfpopularmovie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ary.mfpopularmovie.adapter.MoviesAdapter;
import com.example.ary.mfpopularmovie.api.Client;
import com.example.ary.mfpopularmovie.api.Service;
import com.example.ary.mfpopularmovie.model.Movie;
import com.example.ary.mfpopularmovie.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipecontainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();

    SharedPreferences.OnSharedPreferenceChangeListener mlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlistener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String s) {
                Log.d(LOG_TAG, "Pref Updated");
                checkSortOrder();
            }
        };

        loadview();
        swipecontainer = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipecontainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipecontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadview();
                Toast.makeText(MainActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();

        }

        return null;

    }

    public void loadview() {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Movies Data...");
        pd.setCancelable(true);
        pd.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();
        adapter = new MoviesAdapter(this, movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();


    }

    private void loadJSON() {
        try {
            if (BuildConfig.MY_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please get your API Key from TMDB", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client Client = new Client();

            Service apiService = Client.getClient().create(Service.class);
            retrofit2.Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.MY_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(retrofit2.Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipecontainer.isRefreshing()) {
                        swipecontainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(retrofit2.Call<MoviesResponse> call, Throwable t) {
                    Log.d("Something Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Fetching Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadJSONTopRated() {
        try {
            if (BuildConfig.MY_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please get your API Key from TMDB", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client Client = new Client();

            Service apiService = Client.getClient().create(Service.class);
            retrofit2.Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.MY_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(retrofit2.Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipecontainer.isRefreshing()) {
                        swipecontainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(retrofit2.Call<MoviesResponse> call, Throwable t) {
                    Log.d("Something Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Fetching Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void checkSortOrder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(this.getString(R.string.sortorder_key), this.getString(R.string.mostPopular));

        if (sortOrder.equals(this.getString(R.string.mostPopular))) {
            Log.d(LOG_TAG, "Sorting By Most Popular Movie");
            loadJSON();

        } else {
            Log.d(LOG_TAG, "Sorting By Top Rated Movie");
            loadJSONTopRated();
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}

    /*@Override
    /*public void onResume(){
        super.onResume();
        if (movieList.isEmpty()){
            checkSortOrder();
        }
    }*/

    /*Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Log.d(LOG_TAG,"Pref Updated");
            checkSortOrder();

        }
    }*/



