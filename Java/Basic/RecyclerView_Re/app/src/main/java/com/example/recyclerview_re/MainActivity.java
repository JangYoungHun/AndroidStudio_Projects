package com.example.recyclerview_re;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> dataList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();
        mainAdapter = new MainAdapter(dataList);
        recyclerView.setAdapter(mainAdapter);

        Button addBtn = (Button)findViewById(R.id.add_Btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData data = new MainData(R.drawable.ic_launcher_background,"홍길동", "1");
                dataList.add(data);
                mainAdapter.notifyDataSetChanged();
            }
        });
    }
}