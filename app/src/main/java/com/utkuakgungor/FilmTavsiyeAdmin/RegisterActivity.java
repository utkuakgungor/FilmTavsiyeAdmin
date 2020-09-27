package com.utkuakgungor.FilmTavsiyeAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        TextInputEditText emailText = findViewById(R.id.editEmail);
        TextInputEditText passwordText = findViewById(R.id.editPassword);
        TextInputEditText passwordText2 = findViewById(R.id.editPasswordValid);
        FloatingActionButton floatingActionButton = findViewById(R.id.registerFab);
        floatingActionButton.setOnClickListener(v -> {
            if (!passwordText.getText().toString().equals(passwordText2.getText().toString())) {
                Toast.makeText(v.getContext(), "Şifre yanlış", Toast.LENGTH_LONG).show();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            Intent registerIntent = new Intent(v.getContext(), MainActivity.class);
                            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(registerIntent);
                        });
            }
        });
    }
}