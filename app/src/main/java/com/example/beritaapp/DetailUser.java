package com.example.beritaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class DetailUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String SpinnerText, user_email;
    EditText input_date;
    int age;

    public static final String MESSAGE_AGE = "AGE_KEY";
    public static final String MESSAGE_TAG = "TAG_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        Intent intent = getIntent();
        user_email = intent.getStringExtra("USER");

        input_date = findViewById(R.id.input_birthdate);
        input_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        Spinner spinner_tag = findViewById(R.id.spinner);
        // menciptakan arrayadapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategori, android.R.layout.simple_spinner_item);
        // menampilkan adapter ke spinner
        spinner_tag.setAdapter(adapter);
        spinner_tag.setOnItemSelectedListener(this);

        Button detail = findViewById(R.id.btn_detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent news = new Intent(view.getContext(), listNews.class);
                news.putExtra(MESSAGE_AGE, age);
                news.putExtra(MESSAGE_TAG, SpinnerText);
                news.putExtra("USER", user_email);
                startActivity(news);
            }
        });
    }

    // menampilkan tampilan calender
    public void showDatePicker(){
        DialogFragment dateFragment= new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "date-picker");
    }

    //mengolah dan menampilkan tanggal yg dipih user
    public void processDatePickerResult(int day, int month, int year) {
        String day_string = Integer.toString(day);
        String month_string = Integer.toString(month+1);
        String year_string = Integer.toString(year);

        String message = day_string + "-" + month_string + "-" + year_string;
        input_date.setText(message);

        // menghitung umur user
        final Calendar currentDate = Calendar.getInstance();
        int age_year = currentDate.get(Calendar.YEAR) - year;
        int age_month = currentDate.get(Calendar.MONTH) - month;
        int age_day = currentDate.get(Calendar.DAY_OF_MONTH) - day;

        if (age_day < 0){
            age_month -= 1;
            age_day += currentDate.getActualMaximum(Calendar.DAY_OF_MONTH); //maks hari dlm bulan tsb
        }
        if(age_month < 0){
            age_year -= 1;
            age_month += 12;
        }
        age = age_year;
    }

    @Override
    // menyimpan item yg dipilih user
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SpinnerText = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    // default item ketika user tidak memilih
    public void onNothingSelected(AdapterView<?> adapterView) {
        SpinnerText = adapterView.getItemAtPosition(0).toString();
    }
}