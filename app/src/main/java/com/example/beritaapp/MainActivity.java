package com.example.beritaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText uname = findViewById(R.id.input_uname);
        EditText pw = findViewById(R.id.input_pw);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_uname = uname.getText().toString();
                String input_pw = pw.getText().toString();
                Masuk(input_uname, input_pw);
            }
        });
    }

    public void Masuk(String username, String password){
        if (Objects.equals(username, "pakjoko") && Objects.equals(password, "yangpentingcuan")){
            Intent intent = new Intent(this, DetailUser.class);
            startActivity(intent);
        }else{
            showAlertDialog();
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Pemberitahuan");
        alertBuilder.setMessage("Username atau password yang anda masukkan salah!");

        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertBuilder.show();
    }
}