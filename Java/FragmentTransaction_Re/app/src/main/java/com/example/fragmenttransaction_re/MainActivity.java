package com.example.fragmenttransaction_re;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    Button Btn1;
    Button Btn2;
    Button Btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn1 = (Button)findViewById(R.id.Btn1);
        Btn2 = (Button)findViewById(R.id.Btn2);
        Btn3 = (Button)findViewById(R.id.Btn3);

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start_FragmentTransAction(Fragment1.newInstance());
            }
        });
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start_FragmentTransAction(Fragment2.newInstance());
            }
        });
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start_FragmentTransAction(Fragment3.newInstance());
            }
        });



    }

    void Start_FragmentTransAction(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}