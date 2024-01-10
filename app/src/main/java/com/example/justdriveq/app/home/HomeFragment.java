package com.example.justdriveq.app.home;

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

import com.example.justdriveq.R;
import com.example.justdriveq.data.firebase.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    private AuthViewModel authViewModel;
    private NavController navController;
    private Button logoutButton;
    private Button questionnaireButton;
    private Button howtoButton;
    private Button leaderboardButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        logoutButton = view.findViewById(R.id.logoutButtonHome);
        howtoButton = view.findViewById(R.id.howToPlayButton);
        leaderboardButton = view.findViewById(R.id.viewLeaderboardButton);
        questionnaireButton = view.findViewById(R.id.startQuestionnaireButton);

        //actiune de logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.logout();
                navController.navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

        //actiune de how to play?
        howtoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_howtoFragment);
            }
        });

        //actiune de view leaderboard
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_leaderboardFragment);
            }
        });

        //actiune de incepere chestionar
        questionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_questionnaireFragment);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }
}