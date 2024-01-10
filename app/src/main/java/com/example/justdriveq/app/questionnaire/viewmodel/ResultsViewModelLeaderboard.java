package com.example.justdriveq.app.questionnaire.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.justdriveq.app.questionnaire.repository.QuestionnaireRepository;
import com.example.justdriveq.app.questionnaire.repository.ResultsRepository;
import com.example.justdriveq.interfaces.ResultsLoadFirebase;
import com.example.justdriveq.models.Question;
import com.example.justdriveq.models.Result;

import java.util.List;

public class ResultsViewModelLeaderboard extends ViewModel implements ResultsLoadFirebase {

    private MutableLiveData<List<Result>> resultsMutableLiveData;

    private ResultsRepository repository;
    public ResultsViewModelLeaderboard(){
        resultsMutableLiveData = new MutableLiveData<>();
        repository = new ResultsRepository(this);
    }
    @Override
    public void loadSuccededFromFirebase(List<Result> results) {
        resultsMutableLiveData.setValue(results);
    }

    @Override
    public void loadFailedFromFirebase(Exception e) {
        if(e != null && e.getMessage() != null){
            Log.d("Results error ", e.getMessage());
        }
    }
    public void loadResultsFromFirebase(){
        repository.loadResultsFromFirebase();
    }
    public void addResultToFirebase(Result result){
        repository.addResultToFirebase(result);
    }
    public MutableLiveData<List<Result>> getResultsMutableLiveData() {
        return resultsMutableLiveData;
    }
}
