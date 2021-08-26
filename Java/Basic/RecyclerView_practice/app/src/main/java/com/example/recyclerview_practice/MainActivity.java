package com.example.recyclerview_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        //GridLayoutManager manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);

        adapter = new MyAdapter();

        adapter.addItem(new Person("가나다", "010-0000-0000"));
        adapter.addItem(new Person("라마바", "010-0010-0600"));
        adapter.addItem(new Person("하하하", "010-0200-0400"));

        adapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(MyAdapter.MyViewHolder viewholder, View view, int position) {
                Person item = adapter.getItem(position);
                showToast(item.getName());
            }
        });

        recyclerView.setAdapter(adapter);


    }

    public void showToast(String data){
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
    }
}