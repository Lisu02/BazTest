package com.example.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FloatingActionButton add = findViewById(R.id.addNote);
        Intent intent = new Intent(MainActivity.this, KonsolaActivity.class);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog, null);
                //startActivity(intent);
                TextInputLayout titleLayout, contentLayout;
                TextInputEditText titleET, contentET;
                DatePicker wybranaData;



                //SPINNER
                Spinner categorySpinner = view1.findViewById(R.id.kategoria_spinner);
                categorySpinner.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, Kategoria.values()));
                categorySpinner.getAdapter().toString();

                //NAPISY
                titleLayout = view1.findViewById(R.id.titleLayout);
                contentLayout = view1.findViewById(R.id.contentLayout);
                titleET = view1.findViewById(R.id.titleET);
                contentET = view1.findViewById(R.id.contentET);
               // wybranaData = view1.findViewById(R.id.editTextDate);

                //



//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Add")
//                        .setView(view1)
//                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if (Objects.requireNonNull(titleET.getText()).toString().isEmpty()) {
//                                    titleLayout.setError("This field is required!");
//                                } else if (Objects.requireNonNull(contentET.getText()).toString().isEmpty()) {
//                                    contentLayout.setError("This field is required!");
//
//                                } else {
//                                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//                                    dialog.setMessage("Storing in Database...");
//                                    dialog.show();
//                                    Zawody zawody = new Zawody();
//                                    zawody.setTitle(titleET.getText().toString());
//                                    zawody.setContent(contentET.getText().toString());
//                                    //zawody.setKategoria((Kategoria) categorySpinner.getAdapter().getItem(i));
//                                    zawody.setKategoriaString(categorySpinner.getSelectedItem().toString());
//                                    zawody.setKategoria(zawody.setKategoria(zawody.getKategoriaString()));
//                                    //Date tmpDate = new Date(wybranaData.getYear(),wybranaData.getMonth(),wybranaData.getDayOfMonth());
//                                    //Toast.makeText(MainActivity.this, tmpDate.toString(), Toast.LENGTH_LONG).show();
//
//                                    //zawody.setDate(tmpDate);
//
//
//                                    //zawody.setKategoria();
//                                    database.getReference().child("notes").push().setValue(zawody).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            dialog.dismiss();
//                                            dialogInterface.dismiss();
//                                            Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            dialog.dismiss();
//                                            Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .create();
//                alertDialog.show();
            }
        });

        TextView empty = findViewById(R.id.empty);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        database.getReference().child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Zawody> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Zawody zawody = dataSnapshot.getValue(Zawody.class);
                    Objects.requireNonNull(zawody).setKey(dataSnapshot.getKey());
                    arrayList.add(zawody);
                }

                if (arrayList.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }


                //KLIKNIECIE KONKRETNYCH ZAWODOW
                ZawodyAdapter adapter = new ZawodyAdapter(MainActivity.this, arrayList);
                recyclerView.setAdapter(adapter);
                //TODO:WYBRANIE KONKRETNYCH ZAWODÓW (ZAWODY SZCZEGÓŁY)
                adapter.setOnItemClickListener(new ZawodyAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Zawody zawody) {
                        Intent intentDoSczegolow = new Intent(MainActivity.this, ZawodyDokladneDaneActivity.class);
                        intentDoSczegolow.putExtra("wybraneZawody",zawody);
                        startActivity(intentDoSczegolow);
                    }
                    //KONIEC LISTENERA
                });
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}