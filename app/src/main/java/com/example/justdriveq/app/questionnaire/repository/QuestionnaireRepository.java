package com.example.justdriveq.app.questionnaire.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.justdriveq.interfaces.QuestionLoadFirebase;
import com.example.justdriveq.models.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class QuestionnaireRepository {
    private FirebaseFirestore firebaseFirestore;
    private QuestionLoadFirebase questionLoadFirebase;

    public QuestionnaireRepository(QuestionLoadFirebase questionLoadFirebase){
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.questionLoadFirebase = questionLoadFirebase;
    }

    public void loadQuestionsFromFirebase(){
        firebaseFirestore.collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    questionLoadFirebase.loadSuccededFromFirebase(task.getResult().toObjects(Question.class));
                } else {
                    questionLoadFirebase.loadFailedFromFirebase(task.getException());
                }
            }
        });
    }
}
