package com.example.examsjan19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    MoviesAdapter moviesAdapter;

    public MovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesAdapter = new MoviesAdapter(getContext(), new ArrayList<Movie>());
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchArticles();
    }

    private void fetchArticles(){
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(moviesAdapter);
        fetchMoviesTask.execute();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
       //...... moviesAdapter =
        ListView newsListView = (ListView)rootView.findViewById(R.id.listview_articles);
        newsListView.setAdapter(moviesAdapter);
       //......


        return rootView;
    }
}