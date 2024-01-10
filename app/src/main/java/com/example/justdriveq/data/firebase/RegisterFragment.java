package com.example.justdriveq.data.firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
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

public class RegisterFragment extends Fragment {

    private AuthViewModel authViewModel;
    private NavController navController;

    private Button registerButton;
    private TextView alreadyRegisteredMessage;
    private EditText registerEmail;
    private EditText registerPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        alreadyRegisteredMessage = view.findViewById(R.id.alreadyRegisteredMessage);
        registerButton = view.findViewById(R.id.RegisterButton);
        registerEmail = view.findViewById(R.id.editEmailRegister);
        registerPassword = view.findViewById(R.id.editPasswordRegister);

        //hyperlink de la mesaj la login screen daca ai un cont creat deja
        alreadyRegisteredMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        //actiune de register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!registerEmail.getText().toString().isEmpty() && !registerPassword.getText().toString().isEmpty()){
                    authViewModel.register(registerEmail.getText().toString(), registerPassword.getText().toString());
                    authViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if(firebaseUser != null){
                                navController.navigate(R.id.action_registerFragment_to_loginFragment);
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