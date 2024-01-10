package com.example.justdriveq.app.questionnaire.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.justdriveq.app.questionnaire.repository.QuestionnaireRepository;
import com.example.justdriveq.interfaces.QuestionLoadFirebase;
import com.example.justdriveq.models.Question;

import java.util.List;

public class QuestionnaireViewModel extends ViewModel implements QuestionLoadFirebase {

    private MutableLiveData<List<Question>> questionsMutableLiveData;

    private QuestionnaireRepository repository;

    public QuestionnaireViewModel(){
        questionsMutableLiveData = new MutableLiveData<>();
        repository = new QuestionnaireRepository(this);
    }

    @Override
    public void loadSuccededFromFirebase(List<Question> questions) {
        questionsMutableLiveData.setValue(questions);
    }

    @Override
    public void loadFailedFromFirebase(Exception e) {
        if(e != null && e.getMessage() != null){
            Log.d("Question error ", e.getMessage());
        }
    }

    public MutableLiveData<List<Question>> getQuestionsMutableLiveData() {
        return questionsMutableLiveData;
    }

    public void loadQuestionsFromFirebase(){
        repository.loadQuestionsFromFirebase();
    }
}
