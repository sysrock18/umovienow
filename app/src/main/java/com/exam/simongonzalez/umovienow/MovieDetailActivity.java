package com.exam.simongonzalez.umovienow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieId = (TextView) findViewById(R.id.tvMovieId);
        String id = getIntent().getStringExtra("movieId");
        String title = getIntent().getStringExtra("movieTitle");
        tvMovieId.setText(id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
