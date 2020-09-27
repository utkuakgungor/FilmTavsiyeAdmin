package com.utkuakgungor.FilmTavsiyeAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utkuakgungor.FilmTavsiyeAdmin.utils.Movie;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editID=findViewById(R.id.editID);
        EditText editTur=findViewById(R.id.editTur);
        EditText editTurEng=findViewById(R.id.editTurEng);
        EditText editSinif=findViewById(R.id.editSinif);
        MaterialButton btnFilm = findViewById(R.id.btnFilm);
        FloatingActionButton floatingActionButton=findViewById(R.id.mainFab);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            floatingActionButton.setVisibility(View.GONE);
        }
        floatingActionButton.setOnClickListener(v -> {
            Intent loginIntent = new Intent(v.getContext(),LoginActivity.class);
            startActivity(loginIntent);
        });


        btnFilm.setOnClickListener(v -> {
            Movie movie=new Movie();
            movie.setFilm_id(editID.getText().toString());
            movie.setFilm_tur(editTur.getText().toString());
            movie.setFilm_tur_eng(editTurEng.getText().toString());
            movie.setFilm_sinif(editSinif.getText().toString());
            reference= FirebaseDatabase.getInstance().getReference("Filmler");
            String id=reference.push().getKey();
            reference.child(Objects.requireNonNull(id)).setValue(movie);
        });
    }
}