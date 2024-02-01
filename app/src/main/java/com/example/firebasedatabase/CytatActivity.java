package com.example.firebasedatabase;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class CytatActivity extends AppCompatActivity {
    private TextView wyswietlCytat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cytat);
        wyswietlCytat = findViewById(R.id.wyswietl_cytat);
        final Button button = findViewById(R.id.wroc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchQuoteData();
    }

    private void fetchQuoteData() {
        QuotesService quotesService = RetrofitInstance.get().create(QuotesService.class);
        String apiKey = "Bk8B3C9O57ims+/wCdIvAQ==HBOPXDoarPLgWmIT";
        Call<List<Quote>> quotesContainerCall = quotesService.fetchQuote(apiKey);
        quotesContainerCall.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Quote> quotesList = response.body();
                    if (quotesList != null && !quotesList.isEmpty()) {
                        Quote firstQuote = quotesList.get(0);
                        String content="'";
                        content += firstQuote.getQuote()+"'";
                        wyswietlCytat.setText(content);
                    } else {

                        Log.e("API Call", "Empty quotes list");
                    }
                } else {

                    Log.e("API Call", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {

                Log.e("API Call", "Failure: " + t.getMessage());
            }
        });
    }


}

