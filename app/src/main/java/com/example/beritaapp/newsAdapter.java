package com.example.beritaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {
    private final ArrayList<News> values;
    private final LayoutInflater inflater;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // constructor adapter
    public newsAdapter(Context context, ArrayList<News> values) {
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    // membuat objek viewholder (list) berita
    public newsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // menghubungkan data yg diolah di listNews ke viewHolder untuk ditampilkan
    public void onBindViewHolder(@NonNull newsAdapter.ViewHolder holder, int position) {
        News news = values.get(position);
        holder.txt_judul.setText(news.title);
        holder.txt_tag.setText(news.tag);
        holder.txt_konten.setText(news.content);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailNews.class);
                intent.putExtra("TITLE", news.title);
                intent.putExtra("TAG", news.tag);
                intent.putExtra("MIN-AGE", news.minAge);
                intent.putExtra("CONTENT", news.content);
                intent.putExtra("WRITER", news.writer);
//                intent.putExtra("USER", user_email);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.bookmark.setBackground();
            }
        });
    }

    // objek untuk menampilkan list berita
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_judul, txt_tag, txt_konten, bookmark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_judul = itemView.findViewById(R.id.judul);
            txt_tag = itemView.findViewById(R.id.tag);
            txt_konten = itemView.findViewById(R.id.konten);
            bookmark = itemView.findViewById(R.id.btn_bookmark);
        }
    }

    @Override
    // menentukan jumlah item yang akan ditampilkan
    public int getItemCount() {
        return values.size();
    }
}
