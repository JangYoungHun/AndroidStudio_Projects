package com.example.viewmodel_practice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Fragment1 extends Fragment {

    MyViewModel viewModel;
    TextView count_txt;
    MyViewModel2 viewModel2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        count_txt = v.findViewById(R.id.count_txt);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
       // viewModel.getCnt().observe(getViewLifecycleOwner(), cnt -> count_txt.setText(cnt.toString()));
        viewModel2 = new ViewModelProvider(requireActivity()).get(MyViewModel2.class);
        viewModel2.cnt.observe(getViewLifecycleOwner(), cnt -> count_txt.setText(cnt.toString()));
    }
}