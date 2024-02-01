package com.example.firebasedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UczestnikAdapter extends RecyclerView.Adapter<UczestnikAdapter.ViewHolder>{


    Context context;

    ArrayList<Uczestnik> arrayList;

    OnItemClickListener onItemClickListener;


    public UczestnikAdapter(Context context, ArrayList<Uczestnik> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }
    @NonNull
    @Override
    public UczestnikAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.uczestnik_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UczestnikAdapter.ViewHolder holder, int position) {
        holder.imie.setText(arrayList.get(position).getImie());
        holder.nazwaZwiazku.setText(arrayList.get(position).getnazwaZwiazku());
        holder.numerLicencji.setText(arrayList.get(position).getKodLicencji());
        holder.punktacja.setText(arrayList.get(position).getPunktacjaStr());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView imie,nazwaZwiazku,numerLicencji,punktacja;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imie = itemView.findViewById(R.id.list_item_uczestnik_imie);
            nazwaZwiazku = itemView.findViewById(R.id.list_item_uczestnik_nazwa_zwiazku);
            numerLicencji = itemView.findViewById(R.id.list_item_uczestnik_numer_licencji);
            punktacja = itemView.findViewById(R.id.list_item_uczestnik_punktacja);
        }


    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Uczestnik uczestnik);
    }
}
