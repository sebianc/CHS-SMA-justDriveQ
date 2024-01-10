package com.example.justdriveq.data.firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justdriveq.R;
import com.example.justdriveq.data.firebase.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private NavController navController;
    private Button loginButton;
    private TextView notRegisteredMessage;
    private EditText loginEmail;
    private EditText loginPassword;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        notRegisteredMessage = view.findViewById(R.id.notRegisteredMessage);
        loginButton = view.findViewById(R.id.LoginButton);
        loginEmail = view.findViewById(R.id.editEmailLogin);
        loginPassword = view.findViewById(R.id.editPasswordLogin);

        //hyperlink de la mesaj la register screen daca vrei sa iti faci un cont
        notRegisteredMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        //actiune de login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!loginEmail.getText().toString().isEmpty() && !loginPassword.getText().toString().isEmpty()){
                    authViewModel.login(loginEmail.getText().toString(), loginPassword.getText().toString());
                    authViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if(firebaseUser != null){
                                navController.navigate(R.id.action_loginFragment_to_homeFragment);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Enter email + password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }
}