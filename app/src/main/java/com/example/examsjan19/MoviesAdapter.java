package com.example.examsjan19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movies;
    private Context context;


    public MoviesAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
        this.movies = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        ///fetch movie from 'position'.  how?
        Movie movie = movies.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.list_item_movie, null);
        
       //get and update textViews. how?
        ImageView imageView = rowView.findViewById(R.id.imageView2);
        TextView titleView = rowView.findViewById(R.id.title_txtview);
        TextView originalTextView = rowView.findViewById(R.id.originalTitle_txtview);
        TextView descriptionView = rowView.findViewById(R.id.descr_txtview);
        TextView avgScoreView = rowView.findViewById(R.id.average_score_textView);
        TextView releaseView = rowView.findViewById(R.id.release_textView);

        String url = "https://image.tmdb.org/t/p/w200/" + movie.getUrl();
        Picasso.get()
                .load(url)
                .into(imageView);
        titleView.setText(movie.getTitle());
        originalTextView.setText("Πρωτότυπος Τίτλος:"+ movie.getOriginalTitle());
        descriptionView.setText(movie.getDescription());
        avgScoreView.setText("Μέσος Όρος:" + movie.getAverage_score());
        releaseView.setText("Πρώτη Προβολή:"+ movie.getRelease_date());


        return  rowView;
    }

}