package com.example.justdriveq.data.firebase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.justdriveq.data.firebase.repository.AuthRepository;
import com.example.justdriveq.models.User;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();

    private User user;
    private AuthRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        repository = new AuthRepository(application);
        user = repository.getUser();
        firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
    }

    public void register(String email, String password){
        repository.register(email, password);
    }

    public void login(String email, String password){
        repository.login(email, password);
    }

    public void logout(){
        repository.logout();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public User getUser() {
        return user;
    }
}
