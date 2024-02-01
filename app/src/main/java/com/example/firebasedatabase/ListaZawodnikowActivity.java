package com.example.firebasedatabase;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ListaZawodnikowActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uczestnicy);

        FirebaseApp.initializeApp(ListaZawodnikowActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView empty = findViewById(R.id.emptyUczestnik);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView = findViewById(R.id.recyclerUczestnik);

        Intent intent = getIntent();

        Uczestnik uczestnikMockup = (Uczestnik) intent.getExtras().get("uczestnikIntent");
        Zawody wybraneZawody = (Zawody) intent.getExtras().get("zawody");
//        assert wybraneZawody != null;

        if(wybraneZawody == null){
            wybraneZawody = new Zawody();
            wybraneZawody.key = "zawodnik";
        }
        String klucz = wybraneZawody.key;




        database.getReference().child(klucz).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Uczestnik> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Uczestnik uczestnik = dataSnapshot.getValue(Uczestnik.class);
                    Objects.requireNonNull(uczestnik).setKey(dataSnapshot.getKey());
                    arrayList.add(uczestnik);
                }
                //arrayList.add(uczestnikMockup);

                if (arrayList.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }


                //KLIKNIECIE KONKRETNYCH ZAWODOW
                UczestnikAdapter adapter = new UczestnikAdapter(ListaZawodnikowActivity.this, arrayList);
                recyclerView.setAdapter(adapter);
                //TODO:EDYTOWANIE
                adapter.setOnItemClickListener(new UczestnikAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Uczestnik uczestnik) {
                        //PRZESYLANIE UCZESTNIKA
                        Intent intentUczestnikDokladneDaneActivity = new Intent(ListaZawodnikowActivity.this,UczestnikDokladneDaneActivity.class);
                        intentUczestnikDokladneDaneActivity.putExtra("wybranyUczestnik",uczestnik);
                        Dane.uczestnikDane = uczestnik;
                        startActivity(intentUczestnikDokladneDaneActivity);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
