package edu.chapman.geoffrey.assignment5movies;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MovieService extends Service {


    private final IBinder myBinder = new MyLocalBinder();


    public MovieService()
    {
    }


    @Override
    public IBinder onBind(Intent intent) {


        showNotification(new MovieCard(R.drawable.ic_movie, "", "", ""));
        return myBinder;
    }


    public void showNotification(MovieCard movie)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MovieService.this, "movie")
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(movie.getMovieTitle())
                .setContentText("is playing")
                .setAutoCancel(true);

        Intent intent = new Intent(MovieService.this, ListMovies.class);
        PendingIntent pi = PendingIntent.getActivity(MovieService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }


    public class MyLocalBinder extends Binder
    {
        MovieService getService()
        {
            return MovieService.this;
        }
    }


}
