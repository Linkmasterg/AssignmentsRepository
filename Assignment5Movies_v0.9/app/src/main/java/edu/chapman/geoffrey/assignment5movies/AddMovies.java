package edu.chapman.geoffrey.assignment5movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMovies extends AppCompatActivity {

    EditText movieTitle;
    EditText movieDate;
    EditText movieFile;
    Button btnAddMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movies);

        movieTitle = (EditText) findViewById(R.id.movieTitle);
        movieDate = (EditText) findViewById(R.id.movieDate);
        movieFile = (EditText) findViewById(R.id.movieFile);
        btnAddMovie = (Button) findViewById(R.id.btnAddMovie);


        btnAddMovie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Intent addMovie = new Intent(AddMovies.this, ListMovies.class);


                addMovie.putExtra("movieTitle", movieTitle.getText().toString());
                addMovie.putExtra("movieDate", movieDate.getText().toString());
                addMovie.putExtra("movieFile", movieFile.getText().toString());


                startActivity(addMovie);
            }
        });

    }

}
