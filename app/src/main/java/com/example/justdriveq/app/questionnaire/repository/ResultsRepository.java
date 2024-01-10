package com.example.justdriveq.app.questionnaire.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.justdriveq.interfaces.QuestionLoadFirebase;
import com.example.justdriveq.interfaces.ResultsLoadFirebase;
import com.example.justdriveq.models.Question;
import com.example.justdriveq.models.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ResultsRepository {
    private FirebaseFirestore firebaseFirestore;
    private ResultsLoadFirebase resultsLoadFirebase;

    public ResultsRepository(ResultsLoadFirebase resultsLoadFirebase) {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.resultsLoadFirebase = resultsLoadFirebase;
    }

    public void loadResultsFromFirebase(){
        firebaseFirestore.collection("Results").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    resultsLoadFirebase.loadSuccededFromFirebase(task.getResult().toObjects(Result.class));
                } else {
                    resultsLoadFirebase.loadFailedFromFirebase(task.getException());
                }
            }
        });
    }

    public void addResultToFirebase(Result result){
        firebaseFirestore.collection("Results").add(result).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Result added successfully", result.getEmail());
            }
        });
    }
}
