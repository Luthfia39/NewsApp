package com.example.beritaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        Intent detail_news = getIntent();

        String title = detail_news.getStringExtra("TITLE");
        TextView title_news = findViewById(R.id.news_title);
        title_news.setText(title);

        String tag = detail_news.getStringExtra("TAG");
        TextView tag_news = findViewById(R.id.news_tag);
        tag_news.setText(tag);

        String content = detail_news.getStringExtra("CONTENT");
        TextView content_news = findViewById(R.id.news_content);
        content_news.setText(content);
    }
}