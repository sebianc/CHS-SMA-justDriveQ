package com.example.justdriveq.app.questionnaire.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ResultsViewModel extends ViewModel {
    private MutableLiveData<List<Integer>> counter;

    public ResultsViewModel(){
        this.counter = new MutableLiveData<>();
    }

    public MutableLiveData<List<Integer>> getCounter() {
        return counter;
    }

    public void setCounter(List<Integer> counter) {
        this.counter.setValue(counter);
    }
}
