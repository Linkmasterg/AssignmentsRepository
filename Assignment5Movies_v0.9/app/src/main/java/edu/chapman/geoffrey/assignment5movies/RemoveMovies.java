package edu.chapman.geoffrey.assignment5movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RemoveMovies extends AppCompatActivity {

    Button btnRemove;
    EditText movieToRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_movies);


        btnRemove = (Button) findViewById(R.id.btnRemove);
        movieToRemove = (EditText) findViewById(R.id.etMovieRemove);


        btnRemove.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent removeMovie = new Intent(RemoveMovies.this, ListMovies.class);
                removeMovie.putExtra("movieToRemove", movieToRemove.getText().toString());
                startActivity(removeMovie);
            }
        });





    }






}
