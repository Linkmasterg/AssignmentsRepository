package edu.chapman.geoffrey.assignment5movies;

public class MovieCard
{
    private int imageResource;

    private String movieTitle;
    private String movieDate;
    private String movieFile;


    public MovieCard(int iR, String t, String d, String f)
    {
        imageResource = iR;

        movieTitle = t;
        movieDate = d;
        movieFile = f;
    }


    public int getImageResource() {
        return imageResource;
    }


    /* GETTERS */

    public String getMovieTitle()
    {
        return movieTitle;
    }

    public String getMovieDate()
    {
        return movieDate;
    }

    public String getMovieFile()
    {
        return movieFile;
    }



    /* SETTERS */

    public void setMovieTitle(String title)
    {
        movieTitle = title;
    }

    public void setMovieDate(String date)
    {
        movieDate = date;
    }

    public void setMovieFile(String file)
    {
        movieFile = file;
    }



}
