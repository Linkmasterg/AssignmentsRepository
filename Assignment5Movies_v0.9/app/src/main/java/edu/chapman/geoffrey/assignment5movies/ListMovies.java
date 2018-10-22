package edu.chapman.geoffrey.assignment5movies;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.chapman.geoffrey.assignment5movies.MovieService.MyLocalBinder;


public class ListMovies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    ArrayList<MovieCard> movieCards = new ArrayList<>();

    MovieService mService;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);

        loadMovies();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ListAdapter(movieCards);

        recyclerView.setLayoutManager(layoutManager);





        final Intent i = getIntent();
        final String movieTitle = i.getStringExtra("movieTitle");
        final String movieDate = i.getStringExtra("movieDate");
        final String movieFile = i.getStringExtra("movieFile");
        final String movieToRemove = i.getStringExtra("movieToRemove");
        Bundle extras = i.getExtras();

        if (extras != null)
        {

            if (extras.containsKey("movieTitle") && extras.containsKey("movieDate") && extras.containsKey("movieFile"))
            {
                MovieCard newMovie = new MovieCard(R.drawable.ic_movie, "null", "null", "null");

                newMovie.setMovieTitle(movieTitle);
                newMovie.setMovieDate(movieDate);
                newMovie.setMovieFile(movieFile);
                movieCards.add(newMovie);

            }

            if (extras.containsKey("movieToRemove"))
            {

                deleteMovie(movieToRemove);

            }


            adapter.notifyDataSetChanged();
            saveMovies();
        }
        recyclerView.setAdapter(adapter);





        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                showNotification(movieCards.get(position));
            }
        });










    }






    private void saveMovies()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movieCards);
        editor.putString("list", json);
        editor.apply();
    }

    private void loadMovies()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list", null);
        Type type = new TypeToken<ArrayList<MovieCard>>() {}.getType();
        movieCards = gson.fromJson(json, type);

        if (movieCards == null)
        {
            movieCards = new ArrayList<>();
        }
    }

    private void clearMovies()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        movieCards.clear();
    }

    private void deleteMovie(String movieToDelete)
    {
        if (movieCards != null)
        {
            for (int i = 0; i < movieCards.size(); ++i)
            {
                Toast.makeText(this, "Removing " + movieToDelete, Toast.LENGTH_LONG).show();
                if (movieCards.get(i).getMovieTitle().equals(movieToDelete))
                {
                    movieCards.remove(i);
                }
            }
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            saveMovies();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:

                Toast.makeText(this, "Add a Movie", Toast.LENGTH_SHORT).show();


                Intent addMovie = new Intent(ListMovies.this, AddMovies.class);
                startActivity(addMovie);

                return true;


            case R.id.menuRemove:

                Toast.makeText(this, "Remove a Movie", Toast.LENGTH_SHORT).show();

                Intent removeMovie = new Intent(ListMovies.this, RemoveMovies.class);
                startActivity(removeMovie);


                adapter.notifyDataSetChanged();
                return true;


            case R.id.menuClear:
                Toast.makeText(this, "Clearing Movies", Toast.LENGTH_SHORT).show();

                clearMovies();
                adapter.notifyDataSetChanged();
                return true;




            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private ServiceConnection myConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            MyLocalBinder binder = (MyLocalBinder) iBinder;
            mService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            isBound = false;
        }
    };


    public void showNotification(MovieCard movie)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ListMovies.this, "movie")
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(movie.getMovieTitle())
                .setContentText("is playing")
                .setAutoCancel(true);

        Intent intent = new Intent(ListMovies.this, MovieDetails.class);
        intent.putExtra("movieTitle", movie.getMovieTitle());
        intent.putExtra("movieDate", movie.getMovieDate());
        intent.putExtra("movieFile", movie.getMovieFile());

        PendingIntent pi = PendingIntent.getActivity(ListMovies.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
