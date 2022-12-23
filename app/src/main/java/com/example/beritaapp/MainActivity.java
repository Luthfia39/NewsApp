package com.example.beritaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText input_uname, input_pw;
    TextView txt_for_register;
    Button login;
//    DatabaseReference mDatabaseReference;

    ProgressDialog progressDialog;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_uname = findViewById(R.id.edt_uname);
        input_pw = findViewById(R.id.edt_password);

        txt_for_register = findViewById(R.id.for_register);
        txt_for_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_for_register = new Intent(v.getContext(), Daftar.class);
                startActivity(intent_for_register);
            }
        });

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
                Intent to_detail = new Intent(v.getContext(), DetailUser.class);
            }
        });
    }

    private void loginAccount() {
        String email = input_uname.getText().toString();
        String pw = input_pw.getText().toString();

        if (!email.matches(emailValidation)){
            input_uname.setError("Email tidak sesuai!");
        }else if(pw.isEmpty() || pw.length()<6){
            input_pw.setError("Password kurang dari 6");
        }else{
            progressDialog.setMessage("Harap tunggu!");
            progressDialog.setTitle("Proses login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent go_to_detail = new Intent(getApplicationContext(), DetailUser.class);
//                    Intent go_to_detail_news = new Intent(getApplicationContext(), DetailUser.class);
                    go_to_detail.putExtra("USER", email);
//                    go_to_detail_news.putExtra("USER", email);
                    startActivity(go_to_detail);
                    go_to_detail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                }
            }
        });
    }

}