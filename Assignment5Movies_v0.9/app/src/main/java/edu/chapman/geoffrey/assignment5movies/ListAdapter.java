package edu.chapman.geoffrey.assignment5movies;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
{

    private ArrayList<MovieCard> MovieList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder
    {



        public ImageView ivCard;
        public TextView tvCard;



        public ListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivCard = itemView.findViewById(R.id.ivCard);
            tvCard = itemView.findViewById(R.id.tvCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public ListAdapter(ArrayList<MovieCard> movieList)
    {
        MovieList = movieList;
    }




    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        ListViewHolder lvh = new ListViewHolder(v, mListener);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        MovieCard currentMoveCard = MovieList.get(position);

        holder.ivCard.setImageResource(currentMoveCard.getImageResource());
        holder.tvCard.setText(currentMoveCard.getMovieTitle());
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }
}
