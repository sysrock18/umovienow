package com.exam.simongonzalez.umovienow.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exam.simongonzalez.umovienow.MovieDetailActivity;
import com.exam.simongonzalez.umovienow.R;
import com.exam.simongonzalez.umovienow.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by simongonzalez on 3/14/17.
 */

public class MovieAdapter extends RecyclerView.Adapter {

    ArrayList<Result> moviesResults;
    Activity activity;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public MovieAdapter(ArrayList<Result> moviesResults, Activity activity, RecyclerView recyclerView) {
        this.moviesResults = moviesResults;
        this.activity = activity;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager llm = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = llm.getItemCount();
                            lastVisibleItem = llm.findLastVisibleItemPosition();

                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.cardview_movie, parent, false);

            vh = new MovieViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            final Result result= (Result) moviesResults.get(position);

            ((MovieViewHolder) holder).tvTitle.setText(result.getTitle());

            Picasso.with(activity).load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath()).into(((MovieViewHolder) holder).imgPosterMovie);

            ((MovieViewHolder) holder).cdMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(activity, MovieDetailActivity.class);
                    myIntent.putExtra("movieId", result.getId());
                    myIntent.putExtra("movieTitle", result.getTitle());
                    activity.startActivity(myIntent);
                }
            });

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return moviesResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return moviesResults.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public void updateList(ArrayList<Result> moviesResults) {
        this.moviesResults.addAll(moviesResults);
        notifyDataSetChanged();
    }

    public void addLoader() {
        this.moviesResults.add(null);
        notifyItemInserted(this.moviesResults.size() - 1);
    }

    public void removeLoader() {
        this.moviesResults.remove(this.moviesResults.size() - 1);
        notifyItemRemoved(this.moviesResults.size());
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private CardView cdMovie;
        private ImageView imgPosterMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            cdMovie = (CardView) itemView.findViewById(R.id.cdMovie);
            imgPosterMovie = (ImageView) itemView.findViewById(R.id.imgPosterMovie);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}
