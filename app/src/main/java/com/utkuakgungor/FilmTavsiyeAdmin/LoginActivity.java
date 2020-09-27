package com.utkuakgungor.FilmTavsiyeAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        TextInputEditText loginUsername = findViewById(R.id.loginUsername);
        TextInputEditText loginPassword = findViewById(R.id.loginPassword);
        MaterialButton loginBtn = findViewById(R.id.btn_login);
        MaterialButton registerBtn = findViewById(R.id.btn_register);
        loginBtn.setOnClickListener(v -> {
            firebaseAuth.signInWithEmailAndPassword(loginUsername.getText().toString(), loginPassword.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                    });
        });
        registerBtn.setOnClickListener(v -> {
            Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
            startActivity(registerIntent);
        });
    }
}