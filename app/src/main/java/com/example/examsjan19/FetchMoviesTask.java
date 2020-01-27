package com.example.examsjan19;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<Movie>> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    

    public static final String SERVICE_BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movies = new ArrayList<>() ;
    public FetchMoviesTask(MoviesAdapter moviesAdapter){
       //should keep moviesAdapter as a field. Do it!
        this.moviesAdapter = moviesAdapter;
    }
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String articleJsonString = null;

        try {
        
            final String ARTICLES_URL  = "?with_genres=28&primary_release_year=2017&language=el&api_key=00e296a756b4c7d0cd966a6e37e0ed90";

            Uri builtUri = Uri.parse(SERVICE_BASE_URL + ARTICLES_URL);


            URL url = new URL(builtUri.toString());
            

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            articleJsonString = buffer.toString();
            Log.d("art" , articleJsonString);
            return  getArticlesFromJSON(articleJsonString);
            
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attempting to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if(movies.size() > 0){
            this.moviesAdapter.clear();
            for (Movie movie : movies){
                moviesAdapter.add(movie);
            }
        }
    }
    private ArrayList<Movie> getArticlesFromJSON(String responseJsonStr) throws JSONException {

        try{

            JSONObject response = new JSONObject(responseJsonStr);
            JSONArray moviesJSON = response.getJSONArray("results");

            for (int i=0; i<moviesJSON.length(); i++){
                JSONObject movieJSON = moviesJSON.getJSONObject(i);
                String original_title = movieJSON.getString("original_title");
                String description = movieJSON.getString("overview");
                String title = movieJSON.getString("title");
                String url = movieJSON.getString("poster_path");
                String release_date = movieJSON.getString("release_date");
                String average_score = "" + movieJSON.getDouble("vote_average");

                Movie movie = new Movie(original_title,title,description,url,release_date,average_score);

                movies.add(movie);

                Log.d("GK", movie.toString());
            }
            //parse json and create movies

            Log.d(LOG_TAG, "Movie Fetching Complete. " + movies.size() + "movies inserted");

            return movies; //actually here should return movies

        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return movies;
        }
    }
}