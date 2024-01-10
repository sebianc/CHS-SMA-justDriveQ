package com.example.justdriveq.interfaces;

import com.example.justdriveq.models.Result;

import java.util.List;

public interface ResultsLoadFirebase {
    void loadSuccededFromFirebase(List<Result> results);

    void loadFailedFromFirebase(Exception e);
}
