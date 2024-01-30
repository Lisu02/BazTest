package com.example.firebasedatabase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UczestnikAdapter extends RecyclerView.Adapter<UczestnikAdapter.ViewHolder>{


    Context context;


    public UczestnikAdapter(Context context, ArrayList<Uczestnik> arrayList) {
        this.context = context;

    }
    @NonNull
    @Override
    public UczestnikAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UczestnikAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
