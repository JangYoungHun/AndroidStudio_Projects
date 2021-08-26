package com.example.viewmodel_practice;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    MutableLiveData<Integer> cnt = new MutableLiveData<>();

    public MyViewModel() {
        this.cnt.setValue(0);
    }

    MutableLiveData<Integer> getCnt(){
        return cnt;
    }

    public void addCnt() {
        this.cnt.setValue(this.cnt.getValue()+1);
    }
}
