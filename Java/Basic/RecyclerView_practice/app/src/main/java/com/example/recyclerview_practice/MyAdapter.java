package com.example.recyclerview_practice;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Person> itemList = new ArrayList<>();
    OnPersonItemClickListener listener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.person_item, parent,false);


        return new MyViewHolder(v, listener);
    }

    void addItem(Person person){
        itemList.add(person);
    }

    public Person getItem(int position){
        return itemList.get(position);
    }
    public void setItem(int position, Person person){
         itemList.set(position,person);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnPersonItemClickListener listener){
        this.listener = listener;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView contact_tv;
        public MyViewHolder(@NonNull View itemView, final OnPersonItemClickListener listener) {
            super(itemView);

            name_tv = itemView.findViewById(R.id.name_tv);
            contact_tv = itemView.findViewById(R.id.contact_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(MyViewHolder.this ,v, position );
                    }
                }
            });
        }



        void setItem(Person item){
            name_tv.setText(item.getName());
            contact_tv.setText(item.getContact());

        }

    }

}
