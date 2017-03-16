package com.exam.simongonzalez.umovienow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgPoster;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvPopularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgPoster = (ImageView) findViewById(R.id.imgPoster);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        String title = getIntent().getStringExtra("movieTitle");
        String imgPosterUrl = "https://image.tmdb.org/t/p/w500/" + getIntent().getStringExtra("moviePoster");
        String overview = getIntent().getStringExtra("movieOverview");
        String releaseDate = getIntent().getStringExtra("movieReleaseDate");
        Double popularity = getIntent().getDoubleExtra("moviePopularity", 0);

        tvTitle.setText(title);
        tvOverview.setText(overview);
        Picasso.with(this).load(imgPosterUrl).into(imgPoster);
        tvPopularity.setText(String.valueOf(popularity));
        tvReleaseDate.setText(releaseDate);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
