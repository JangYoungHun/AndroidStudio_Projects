package com.example.volley_practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<MovieData> movieList = new ArrayList<MovieData>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moview_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItems(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovie(MovieData data){
        movieList.add(data);
    }

    public void addMovie(String name, String rank, String show_Cnt){
        movieList.add(new MovieData(name,rank,show_Cnt));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView name_tv;
        TextView rank_tv;
        TextView showCnt_tv;

        public ViewHolder(@NonNull View v) {
            super(v);
            name_tv = v.findViewById(R.id.name_tv);
            rank_tv = v.findViewById(R.id.rank_tv);
            showCnt_tv = v.findViewById(R.id.showCnt_tv);
            movieImage= v.findViewById(R.id.movieImage);
        }

        void setItems(MovieData data){
            name_tv.setText(data.getName());
            rank_tv.setText(data.getRank());
            showCnt_tv.setText(data.getShow_Cnt());
        }



    }
}
