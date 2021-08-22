package com.example.recyclerview_re;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> dataList = new ArrayList<>();

    public MainAdapter(ArrayList<MainData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MainAdapter.CustomViewHolder holder, int position) {
        holder.photo.setImageResource(dataList.get(position).getPhoto());
        holder.name.setText(dataList.get(position).getName());
        holder.age.setText(dataList.get(position).getAge());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.name.getText().toString();
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAbsoluteAdapterPosition());
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public void remove(int position){
        try{
            dataList.remove(position);
            notifyItemRemoved(position);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder  {
    protected ImageView photo;
    protected TextView name;
    protected TextView age;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.photo = itemView.findViewById(R.id.photo);
            this.name = itemView.findViewById(R.id.name);
            this.age = itemView.findViewById(R.id.age);
        }
    }
}
