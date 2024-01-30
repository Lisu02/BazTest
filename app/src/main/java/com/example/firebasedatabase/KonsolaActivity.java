package com.example.firebasedatabase;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class KonsolaActivity extends AppCompatActivity {


    private EditText editTextDate;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsola);
        Button wroc = findViewById(R.id.wrocKonsola);
        Button dodajZawody = findViewById(R.id.dodawanieZawodow);
        Button dodajZawodnika = findViewById(R.id.dodawanieZawodnika);
        Button edytujZawodnika = findViewById(R.id.edytowanieZawodnika);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button listaZawodnikowButton = findViewById(R.id.listaZawodnikow);

        Intent intentWroc = new Intent(KonsolaActivity.this, MainActivity.class);
        Intent intentListaZawodnikow = new Intent(KonsolaActivity.this, MainActivity.class);


        FirebaseApp.initializeApp(KonsolaActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();





        //GUZIK WROC
        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentWroc);
            }
        });

        listaZawodnikowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentListaZawodnikow);
            }
        });


        //DODAWANIE ZAWODOW
        dodajZawody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(KonsolaActivity.this).inflate(R.layout.add_note_dialog, null);
                //startActivity(intent);
                TextInputLayout titleLayout, contentLayout;
                TextInputEditText titleET, contentET;

                editTextDate = findViewById(R.id.editTextDate);
                calendar = Calendar.getInstance();

                //SPINNER
                Spinner categorySpinner = view1.findViewById(R.id.kategoria_spinner);
                categorySpinner.setAdapter(new ArrayAdapter<>(KonsolaActivity.this, android.R.layout.simple_spinner_item, Kategoria.values()));
                categorySpinner.getAdapter().toString();

                //NAPISY
                titleLayout = view1.findViewById(R.id.titleLayout);
                contentLayout = view1.findViewById(R.id.contentLayout);
                titleET = view1.findViewById(R.id.titleET);
                contentET = view1.findViewById(R.id.contentET);





                AlertDialog alertDialog = new AlertDialog.Builder(KonsolaActivity.this)
                        .setTitle("Dodaj")
                        .setView(view1)
                        .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(titleET.getText()).toString().isEmpty()) {
                                    titleLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(contentET.getText()).toString().isEmpty()) {
                                    contentLayout.setError("This field is required!");

                                } else {
                                    ProgressDialog dialog = new ProgressDialog(KonsolaActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Zawody zawody = new Zawody();
                                    zawody.setTitle(titleET.getText().toString());
                                    zawody.setContent(contentET.getText().toString());
                                    //zawody.setKategoria((Kategoria) categorySpinner.getAdapter().getItem(i));
                                    zawody.setKategoriaString(categorySpinner.getSelectedItem().toString());
                                    zawody.setKategoria(zawody.setKategoria(zawody.getKategoriaString()));


                                    //zawody.setKategoria();
                                    database.getReference().child("notes").push().setValue(zawody).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(KonsolaActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(KonsolaActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
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


    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEditTextDate();
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateEditTextDate() {
        String myFormat = "yyyy-MM-dd"; // Twój format daty
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        editTextDate.setText(sdf.format(calendar.getTime()));

        // Tutaj możesz przypisać datę do zmiennej typu Date
        Date selectedDate = calendar.getTime();
        // Teraz możesz użyć 'selectedDate' do dalszych operacji
    }

}
