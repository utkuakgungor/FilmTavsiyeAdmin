package com.utkuakgungor.FilmTavsiyeAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utkuakgungor.FilmTavsiyeAdmin.utils.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private List<Movie> list;
    private List<Movie> newList;

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
        reference= FirebaseDatabase.getInstance().getReference("Filmler");
        firebaseAuth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        newList=new ArrayList<>();
        if(firebaseAuth.getCurrentUser()!=null){
            floatingActionButton.setVisibility(View.GONE);
        }
        floatingActionButton.setOnClickListener(v -> {
            Intent loginIntent = new Intent(v.getContext(),LoginActivity.class);
            startActivity(loginIntent);
        });


        btnFilm.setOnClickListener(v -> {
            list=new ArrayList<>();
            newList=new ArrayList<>();
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    list.add(snapshot.getValue(Movie.class));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Movie movie=new Movie();
                    movie.setFilm_id(editID.getText().toString());
                    movie.setFilm_tur(editTur.getText().toString());
                    movie.setFilm_tur_eng(editTurEng.getText().toString());
                    movie.setFilm_sinif(editSinif.getText().toString());
                    newList.add(movie);
                    newList.addAll(list);
                    reference.removeValue();
                    for(int i=0;i<newList.size();i++){
                        String id=reference.push().getKey();
                        reference.child(Objects.requireNonNull(id)).setValue(newList.get(i));
                    }
                    Toast.makeText(v.getContext(),"Film eklendi",Toast.LENGTH_LONG).show();
                    editID.setText("");
                    editTur.setText("");
                    editTurEng.setText("");
                    editSinif.setText("");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}