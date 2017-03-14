package com.exam.simongonzalez.umovienow.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exam.simongonzalez.umovienow.R;
import com.exam.simongonzalez.umovienow.model.Result;

import java.util.ArrayList;

/**
 * Created by simongonzalez on 3/14/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<Result> moviesResults;
    Activity activity;

    public MovieAdapter(ArrayList<Result> moviesResults, Activity activity) {
        this.moviesResults = moviesResults;
        this.activity = activity;
    }

    @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movie, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Result result = moviesResults.get(position);
        holder.tvTitle.setText(result.getOriginalTitle());
    }

    @Override
    public int getItemCount() {
        return moviesResults.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
