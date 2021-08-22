package com.example.fragment_practice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

         Button button = rootView.findViewById(R.id.button);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MainActivity activity = (MainActivity)getActivity();
                 activity.onFragmentChanged(0);
             }
         });


         return rootView;
    }
}