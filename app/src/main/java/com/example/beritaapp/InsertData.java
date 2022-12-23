package com.example.beritaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertData extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String SpinnerText, user_email, key;
    EditText input_title, input_min_age, input_content;
    DatabaseReference mDatabaseReference;
//    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        Intent intent = getIntent();
        user_email = intent.getStringExtra("USER").toString();
        key = intent.getStringExtra("KEY").toString();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(News.class.getSimpleName());

        input_title = findViewById(R.id.edt_title);
        input_min_age = findViewById(R.id.edt_min_age);
        input_content = findViewById(R.id.edt_content);
        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
//                Intent go_to_list = new Intent(v.getContext(), listNews.class);
                Intent go_to_list = new Intent(v.getContext(), DetailUser.class);
                startActivity(go_to_list);
            }
        });

        Spinner spinner_tag = findViewById(R.id.spinner);
        // menciptakan arrayadapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategori, android.R.layout.simple_spinner_item);
        // menampilkan adapter ke spinner
        spinner_tag.setAdapter(adapter);
        spinner_tag.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        SpinnerText = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        SpinnerText = adapterView.getItemAtPosition(0).toString();
    }

    private void insertData() {
        News new_news = new News();
        String title = input_title.getText().toString();
        int min_age = Integer.parseInt(input_min_age.getText().toString());
        String content = input_content.getText().toString();
//        String writer = input_writer.getText().toString();

        if (title != "" && content != "" && SpinnerText != ""){
            new_news.setTitle(title);
            new_news.setTag(SpinnerText);
            new_news.setMinAge(min_age);
            new_news.setContent(content);
            new_news.setWriter(user_email);

            mDatabaseReference.push().setValue(new_news);
            Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
        }
    }
}