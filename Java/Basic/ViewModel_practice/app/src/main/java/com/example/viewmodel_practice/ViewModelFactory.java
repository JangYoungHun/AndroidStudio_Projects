package com.example.viewmodel_practice;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    int defCnt;
    ViewModelFactory(int defCnt){
        this.defCnt = defCnt;
    }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)(new MyViewModel2(defCnt));
    }
}
