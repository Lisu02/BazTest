package com.example.firebasedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ZawodyDokladneDaneActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zawody_dokladne_dane);
        Intent intent = getIntent();
        Zawody wybraneZawody;
        wybraneZawody = (Zawody) Objects.requireNonNull(intent.getExtras()).get("wybraneZawody");//Z MAIN ACTIVITY

        FirebaseApp.initializeApp(ZawodyDokladneDaneActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        Button wroc = findViewById(R.id.wrocKonsola);
        Button listaZawodnikow = findViewById(R.id.listaZawodnikow);
        Button dodajZawodnika = findViewById(R.id.dodawanieZawodnika);
        Button edytujZawody = findViewById(R.id.edytowanieZawodow);

        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listaZawodnikow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListaZawodnikow = new Intent(ZawodyDokladneDaneActivity.this,ListaZawodnikowActivity.class);
                Intent intentUczestnikDokladne = new Intent(ZawodyDokladneDaneActivity.this,UczestnikDokladneDaneActivity.class);
                intentListaZawodnikow.putExtra("zawody",wybraneZawody);
                intentUczestnikDokladne.putExtra("wybraneZawodyDokladne",wybraneZawody);
                Dane.zawodyDane = wybraneZawody;



                startActivity(intentListaZawodnikow);

            }
        });

        dodajZawodnika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(ZawodyDokladneDaneActivity.this).inflate(R.layout.dodaj_uczestnika_dialog, null);

                TextInputLayout nameLayout, zwiazekStrzeleckiLayout,numerLicencjiLayout;
                TextInputEditText nameET, zwiazekStrzeleckiET,numerLicencjiET;


                //NAPISY
                nameLayout = view1.findViewById(R.id.nameLayout);
                zwiazekStrzeleckiLayout = view1.findViewById(R.id.zwiazekStrzeleckiLayout);
                numerLicencjiLayout = view1.findViewById(R.id.numerLicencjiLayout);
                nameET = view1.findViewById(R.id.imieET);
                zwiazekStrzeleckiET = view1.findViewById(R.id.zwiazekStrzeleckiET);
                numerLicencjiET = view1.findViewById(R.id.numerLicencjiET);



                AlertDialog alertDialog = new AlertDialog.Builder(ZawodyDokladneDaneActivity.this)
                        .setTitle("Dodaj uczestnika")
                        .setView(view1)
                        .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(nameET.getText()).toString().isEmpty()) {
                                    nameLayout.setError("Te pole jest wymagane!");
                                } else if (Objects.requireNonNull(zwiazekStrzeleckiET.getText()).toString().isEmpty()) {
                                    zwiazekStrzeleckiLayout.setError("Te pole jest wymagane!");
                                } else if (Objects.requireNonNull(numerLicencjiET.getText()).toString().isEmpty()) {
                                    numerLicencjiLayout.setError("Te pole jest wymagane!");

                                } else {
                                    ProgressDialog dialog = new ProgressDialog(ZawodyDokladneDaneActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Uczestnik uczestnik = new Uczestnik();
                                    uczestnik.setImie(nameET.getText().toString());
                                    uczestnik.setnazwaZwiazku(zwiazekStrzeleckiET.getText().toString());
                                    uczestnik.setKodLicencji(numerLicencjiET.getText().toString());

                                    assert wybraneZawody != null;
                                    wybraneZawody.addUczestnik(uczestnik);
                                    database.getReference().child(wybraneZawody.key).push().setValue(uczestnik).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(ZawodyDokladneDaneActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(ZawodyDokladneDaneActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }

        });

        edytujZawody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(ZawodyDokladneDaneActivity.this).inflate(R.layout.add_note_dialog, null);
                TextInputLayout titleLayout, contentLayout;
                TextInputEditText titleET, contentET;

                Spinner categorySpinner = view1.findViewById(R.id.kategoria_spinner);
                categorySpinner.setAdapter(new ArrayAdapter<>(ZawodyDokladneDaneActivity.this, android.R.layout.simple_spinner_item, Kategoria.values()));
                categorySpinner.getAdapter().toString();

                titleET = view1.findViewById(R.id.titleET);
                contentET = view1.findViewById(R.id.contentET);
                titleLayout = view1.findViewById(R.id.titleLayout);
                contentLayout = view1.findViewById(R.id.contentLayout);

                titleET.setText(wybraneZawody.getTitle());
                contentET.setText(wybraneZawody.getContent());


                ProgressDialog progressDialog = new ProgressDialog(ZawodyDokladneDaneActivity.this);

                AlertDialog alertDialog = new AlertDialog.Builder(ZawodyDokladneDaneActivity.this)
                        .setTitle("Edit")
                        .setView(view1)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(titleET.getText()).toString().isEmpty()) {
                                    titleLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(contentET.getText()).toString().isEmpty()) {
                                    contentLayout.setError("This field is required!");
                                } else {
                                    progressDialog.setMessage("Saving...");
                                    progressDialog.show();
                                    Zawody zawody1 = new Zawody();
                                    zawody1.setTitle(titleET.getText().toString());
                                    zawody1.setContent(contentET.getText().toString());
                                    zawody1.setKategoriaString(categorySpinner.getSelectedItem().toString());
                                    zawody1.setKategoria(zawody1.setKategoria(categorySpinner.getSelectedItem().toString()));
                                    database.getReference().child("notes").child(wybraneZawody.getKey()).setValue(zawody1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(ZawodyDokladneDaneActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ZawodyDokladneDaneActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                database.getReference().child(wybraneZawody.key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ZawodyDokladneDaneActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                    }
                                });
                                database.getReference().child("notes").child(wybraneZawody.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ZawodyDokladneDaneActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).create();
                alertDialog.show();
            }
        });





    }
}
