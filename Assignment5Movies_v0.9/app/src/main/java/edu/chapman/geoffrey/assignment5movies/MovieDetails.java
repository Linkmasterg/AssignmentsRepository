package edu.chapman.geoffrey.assignment5movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MovieDetails extends AppCompatActivity {

    MovieService mService;


    TextView title;
    TextView date;
    TextView file;
    Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        title = (TextView) findViewById(R.id.tvMovieTitle);
        date = (TextView) findViewById(R.id.tvMovieDate);
        file = (TextView) findViewById(R.id.tvMovieFile);

        btnStopService = (Button) findViewById(R.id.btnStopService);



        final Intent i = getIntent();
        final String movieTitle = i.getStringExtra("movieTitle");
        final String movieDate = i.getStringExtra("movieDate");
        final String movieFile = i.getStringExtra("movieFile");

        Bundle extras = i.getExtras();

        if (extras != null)
        {

            if (extras.containsKey("movieTitle") && extras.containsKey("movieDate") && extras.containsKey("movieFile"))
            {
                title.setText(movieTitle);
                date.setText(movieDate);
                file.setText(movieFile);
            }
        }


        btnStopService.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Intent stopService = new Intent(MovieDetails.this, ListMovies.class);
                startActivity(stopService);
            }
        });




    }




}
