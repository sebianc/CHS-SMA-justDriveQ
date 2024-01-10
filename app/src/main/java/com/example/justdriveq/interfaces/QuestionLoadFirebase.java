package com.example.justdriveq.interfaces;

import com.example.justdriveq.models.Question;

import java.util.List;

public interface QuestionLoadFirebase {
    void loadSuccededFromFirebase(List<Question> questions);
    void loadFailedFromFirebase(Exception e);
}
