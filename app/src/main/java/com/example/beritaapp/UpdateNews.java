package com.example.beritaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateNews extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String SpinnerText, user_email, title, tag, content, key;
    int minAge;
    EditText input_title, input_min_age, input_content;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE").toString();
        tag = intent.getStringExtra("TAG").toString();
        minAge = intent.getIntExtra("MIN-AGE", 1);
        content = intent.getStringExtra("CONTENT").toString();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        input_title = findViewById(R.id.edt_title);
        input_title.setText(title);

        input_min_age = findViewById(R.id.edt_min_age);
        input_min_age.setText(String.valueOf(minAge).toString());
//
        input_content = findViewById(R.id.edt_content);
        input_content.setText(content);

        Spinner spinner_tag = findViewById(R.id.spinner);
        // menciptakan arrayadapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategori, android.R.layout.simple_spinner_item);
        // menampilkan adapter ke spinner
        spinner_tag.setAdapter(adapter);
        spinner_tag.setOnItemSelectedListener(this);

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
//                Intent go_to_list = new Intent(v.getContext(), listNews.class);
                Intent go_to_list = new Intent(v.getContext(), DetailUser.class);
                startActivity(go_to_list);
            }
        });
    }

    private void updateData() {
        News updateNews = new News();

        mDatabaseReference.child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item : snapshot.getChildren()){
//                    key = item.getKey();
//                    News item_news = item.getValue(News.class);
                    if ((item.getValue(News.class)).getTitle().equals(title)){
                        key = item.getKey();
                        updateNews.setTitle(input_title.getText().toString());
                        updateNews.setTag(SpinnerText);
                        updateNews.setMinAge(Integer.parseInt(input_min_age.getText().toString()));
                        updateNews.setContent(input_content.getText().toString());

                        mDatabaseReference.child(key).setValue(updateNews);
                        Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        SpinnerText = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        SpinnerText = adapterView.getItemAtPosition(0).toString();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}