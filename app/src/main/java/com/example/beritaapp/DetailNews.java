package com.example.beritaapp;

import static com.example.beritaapp.listNews.key;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailNews extends AppCompatActivity {
    DatabaseReference mDatabaseReference;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Intent detail_news = getIntent();

        title = detail_news.getStringExtra("TITLE");
        TextView title_news = findViewById(R.id.news_title);
        title_news.setText(title);

        String tag = detail_news.getStringExtra("TAG");
        TextView tag_news = findViewById(R.id.news_tag);
        tag_news.setText(tag);

        String content = detail_news.getStringExtra("CONTENT");
        TextView content_news = findViewById(R.id.news_content);
        content_news.setText(content);

        int age = detail_news.getIntExtra("MIN-AGE", 1);

        String writer = detail_news.getStringExtra("WRITER").toString();

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update_news = new Intent(getApplicationContext(), UpdateNews.class);
                update_news.putExtra("TITLE", title);
                update_news.putExtra("TAG", tag);
                update_news.putExtra("MIN-AGE", age);
                update_news.putExtra("CONTENT", content);
                startActivity(update_news);
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteData();
               Intent detail_user = new Intent(getApplicationContext(), DetailUser.class);
               startActivity(detail_user);
            }
        });
    }

    private void deleteData() {
        News deleteNews = new News();
        mDatabaseReference.child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item : snapshot.getChildren()){
//                    key = item.getKey();
//                    News item_news = item.getValue(News.class);
                    if ((item.getValue(News.class)).getTitle().equals(title)){
                        key = item.getKey();
//                                deleteNews.setTitle(title);
//                                deleteNews.setMinAge(age);
//                                deleteNews.setContent(content);
                        mDatabaseReference.child(key).removeValue();
                        Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}