package com.example.beritaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Daftar extends AppCompatActivity {

    EditText input_uname, input_email, input_pw;
    Button register;
//    DatabaseReference mDatabaseReference;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

//        mDatabaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());

        input_uname = findViewById(R.id.edt_name);
        input_email = findViewById(R.id.edt_email);
        input_pw = findViewById(R.id.edt_password);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        register = findViewById(R.id.btn_signin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData() {
        String email = input_email.getText().toString();
        String pw = input_pw.getText().toString();
        String uname = input_uname.getText().toString();

        if (!email.matches(emailValidation)){
            input_email.setError("Email tidak cocok!");
        }else if(pw.isEmpty() || pw.length()<6){
            input_pw.setError("pw kurang dari 6");
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent go_to_detail = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(go_to_detail);
                    }
                }
            });
            progressDialog.setMessage("Harap tunggu!");
            progressDialog.setTitle("Proses registrasi");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }
}