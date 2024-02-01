package com.example.firebasedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ZawodyAdapter extends RecyclerView.Adapter<ZawodyAdapter.ViewHolder> {
    Context context;
    ArrayList<Zawody> arrayList;

    UczestnikAdapter uczestnikAdapter;
    OnItemClickListener onItemClickListener;

    public ZawodyAdapter(Context context, ArrayList<Zawody> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Ustawianie widokow z calej listy
        holder.title.setText(arrayList.get(position).getTitle());
        holder.subtitle.setText(arrayList.get(position).getContent());

        holder.kategoria.setText(arrayList.get(position).getKategoriaString());
        holder.dataZawodow.setText(arrayList.get(position).getDate().toString());
        //holder.dzien.setText(arrayList.get(position).getDate().toString());

        // Dodaj obsługę Spinnera z kategoriami
        ArrayAdapter<Kategoria> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Kategoria.values());
        //holder.spinnerKategoria.setAdapter("cos");
        // Ustaw wybraną kategorię w Spinnerze
        //int selectedCategoryPosition = spinnerAdapter.getPosition(arrayList.get(position).getKategoriaEnum());
        //holder.spinnerKategoria.setSelection(selectedCategoryPosition);

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        TextView kategoria;
        TextView dataZawodow;
        DatePicker datka;
        EditText dateField;
        Spinner spinnerKategoria;

        final Calendar calendar = Calendar.getInstance();


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //widoki z calej listy
            title = itemView.findViewById(R.id.list_item_title);
            subtitle = itemView.findViewById(R.id.list_item_subtitle);
            //TODO:DO poprawy
            dataZawodow = itemView.findViewById(R.id.list_item_data);
            kategoria = itemView.findViewById(R.id.list_item_kategoria);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Zawody zawody);
    }
}