package com.example.viewmodel_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyViewModel viewModel;
    MyViewModel2 viewModel2;
    int position = 0;
    FragmentManager fragmentManager;
    List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        viewModel2 = new ViewModelProvider(this,new ViewModelFactory(10)).get(MyViewModel2.class);
        fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());

        Button cnt_Btn = findViewById(R.id.cnt_Btn);
        cnt_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel2.addCnt();
            }
        });

        Button nextFrag_Btn = findViewById(R.id.nextFrag_Btn);
        nextFrag_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < fragmentList.size()-1) {
                    position++;
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentList.get(position)).commit();
                }
            }
        });

        Button preFrag_Btn = findViewById(R.id.preFrag_Btn);
        preFrag_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(0 < position ) {
                    position--;
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentList.get(position)).commit();
                }
            }
        });


    }
}