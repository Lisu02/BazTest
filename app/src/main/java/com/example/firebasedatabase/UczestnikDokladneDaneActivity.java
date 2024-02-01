package com.example.firebasedatabase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import java.util.LinkedList;
import java.util.Objects;

public class UczestnikDokladneDaneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uczestnik_dokladne_dane);

        Intent intent = getIntent();
        Uczestnik wybranyUczestnik;
        Zawody wybraneZawody;
        //wybranyUczestnik = (Uczestnik) Objects.requireNonNull(intent.getExtras()).get("wybranyUczestnik");
        //wybraneZawody = (Zawody) Objects.requireNonNull(intent.getExtras()).get("wybraneZawodyDokladne");
        Bundle extras = intent.getExtras();
        String shit = extras.toString();
        wybranyUczestnik = Dane.uczestnikDane;
        wybraneZawody = Dane.zawodyDane;

        assert wybranyUczestnik != null;
     //   assert wybraneZawody != null;

        if(wybraneZawody == null){
            wybraneZawody = new Zawody();
            wybraneZawody.key = "zawodnik";
        }
        String klucz = wybraneZawody.key;


        Button wroc = findViewById(R.id.wrocKonsola);
        Button edytujUczestnika = findViewById(R.id.edytowanieUczestnika);
        Button nadajPunkty = findViewById(R.id.nadajPunkty);


        FirebaseApp.initializeApp(UczestnikDokladneDaneActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edytujUczestnika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(UczestnikDokladneDaneActivity.this).inflate(R.layout.dodaj_uczestnika_dialog, null);

                TextInputLayout nameLayout, zwiazekStrzeleckiLayout, numerLicencjiLayout;
                TextInputEditText nameET, zwiazekStrzeleckiET, numerLicencjiET;


                //NAPISY
                nameLayout = view1.findViewById(R.id.nameLayout);
                zwiazekStrzeleckiLayout = view1.findViewById(R.id.zwiazekStrzeleckiLayout);
                numerLicencjiLayout = view1.findViewById(R.id.numerLicencjiLayout);
                nameET = view1.findViewById(R.id.imieET);
                zwiazekStrzeleckiET = view1.findViewById(R.id.zwiazekStrzeleckiET);
                numerLicencjiET = view1.findViewById(R.id.numerLicencjiET);

                nameET.setText(wybranyUczestnik.getImie());
                zwiazekStrzeleckiET.setText(wybranyUczestnik.getnazwaZwiazku());
                numerLicencjiET.setText(wybranyUczestnik.getKodLicencji());

                ProgressDialog progressDialog = new ProgressDialog(UczestnikDokladneDaneActivity.this);

                AlertDialog alertDialog = new AlertDialog.Builder(UczestnikDokladneDaneActivity.this)
                        .setTitle("Edytuj uczestnika")
                        .setView(view1)
                        .setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(nameET.getText()).toString().isEmpty()) {
                                    nameLayout.setError("Te pole jest wymagane!");
                                } else if (Objects.requireNonNull(zwiazekStrzeleckiET.getText()).toString().isEmpty()) {
                                    zwiazekStrzeleckiLayout.setError("Te pole jest wymagane!");
                                } else if (Objects.requireNonNull(numerLicencjiET.getText()).toString().isEmpty()) {
                                    numerLicencjiLayout.setError("Te pole jest wymagane!");

                                } else {
                                    ProgressDialog dialog = new ProgressDialog(UczestnikDokladneDaneActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Uczestnik uczestnik1 = new Uczestnik();
                                    uczestnik1.setImie(nameET.getText().toString());
                                    uczestnik1.setnazwaZwiazku(zwiazekStrzeleckiET.getText().toString());
                                    uczestnik1.setKodLicencji(numerLicencjiET.getText().toString());
                                    database.getReference().child(klucz).child(wybranyUczestnik.getKey()).setValue(uczestnik1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(UczestnikDokladneDaneActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(UczestnikDokladneDaneActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
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
                                database.getReference().child(klucz).child(wybranyUczestnik.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UczestnikDokladneDaneActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
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

        nadajPunkty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(UczestnikDokladneDaneActivity.this).inflate(R.layout.dodaj_punkty, null);

                TextInputLayout[] punktyLayout = new TextInputLayout[5];
                TextInputEditText[] punktyET = new TextInputEditText[5];


                //NAPISY
                punktyLayout[0] = view1.findViewById(R.id.point1Layout);
                punktyLayout[1] = view1.findViewById(R.id.point2Layout);
                punktyLayout[2] = view1.findViewById(R.id.point3Layout);
                punktyLayout[3] = view1.findViewById(R.id.point4Layout);
                punktyLayout[4] = view1.findViewById(R.id.point5Layout);


                punktyET[0] = view1.findViewById(R.id.point1ET);
                punktyET[1] = view1.findViewById(R.id.point2ET);
                punktyET[2] = view1.findViewById(R.id.point3ET);
                punktyET[3] = view1.findViewById(R.id.point4ET);
                punktyET[4] = view1.findViewById(R.id.point5ET);
                //zwiazekStrzeleckiLayout.setError("Te pole jest wymagane!");
                //if (Objects.requireNonNull(zwiazekStrzeleckiET.getText()).toString().isEmpty())
                ProgressDialog progressDialog = new ProgressDialog(UczestnikDokladneDaneActivity.this);

                AlertDialog alertDialog = new AlertDialog.Builder(UczestnikDokladneDaneActivity.this)
                        .setTitle("Nadaj Punkty")
                        .setView(view1)
                        .setPositiveButton("Zatwierdź", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean isError = false;
                                for(int j = 0;i< 5;i++){
                                    if (Objects.requireNonNull(punktyET[j].getText()).toString().isEmpty()){
                                        punktyET[j].setError("Te pole jest wymagane!");
                                        isError = true;
                                    }
                                }

                                if(!isError) {
                                    ProgressDialog dialog = new ProgressDialog(UczestnikDokladneDaneActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Uczestnik uczestnik1 = new Uczestnik();
                                    uczestnik1.imie = wybranyUczestnik.imie;
                                    uczestnik1.nazwaZwiazku = wybranyUczestnik.nazwaZwiazku;
                                    uczestnik1.kodLicencji = wybranyUczestnik.kodLicencji;
                                    //uczestnik1.key = wybranyUczestnik.key;
                                    LinkedList<Integer> integerLinkedList = new LinkedList<>();
                                    for (int j = 0; j < 5; j++) {
                                        String tmpString = Objects.requireNonNull(punktyET[j].getText()).toString();
                                        Integer tmp = Integer.parseInt(tmpString);
                                        integerLinkedList.add(tmp);
                                    }
                                    uczestnik1.setPunktacja(integerLinkedList);
                                    database.getReference().child(klucz).child(wybranyUczestnik.getKey()).setValue(uczestnik1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(UczestnikDokladneDaneActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(UczestnikDokladneDaneActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }


        });

    }
}
