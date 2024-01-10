package com.example.justdriveq.app.questionnaire.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultsViewModel extends ViewModel {
    private MutableLiveData<Integer> counter;

    public ResultsViewModel(){
        this.counter = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter.postValue(counter);
    }
}
