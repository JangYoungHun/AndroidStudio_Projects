package com.example.viewmodel_practice;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel2 extends ViewModel {

    MutableLiveData<Integer> cnt = new MutableLiveData<>();

    MyViewModel2(int defValue){
        cnt.setValue(defValue);
    }

    void addCnt(){
        this.cnt.setValue(this.cnt.getValue() + 1);
    }

}
