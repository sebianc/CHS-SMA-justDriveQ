package com.example.justdriveq.app.questionnaire;

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
import android.widget.TextView;

import com.example.justdriveq.R;
import com.example.justdriveq.app.questionnaire.viewmodel.ResultsViewModel;
import com.example.justdriveq.app.questionnaire.viewmodel.ResultsViewModelLeaderboard;
import com.example.justdriveq.data.firebase.viewmodel.AuthViewModel;
import com.example.justdriveq.models.Result;
import com.google.rpc.context.AttributeContext;

import java.util.List;

public class ResultsFragment extends Fragment {

    private ResultsViewModel resultsViewModel;
    private NavController navController;
    private TextView resultText;
    private TextView correctanswersText;
    private TextView wronganswersText;
    private Button goHomeButton;
    private AuthViewModel authViewModel;
    private ResultsViewModelLeaderboard resultsViewModelLeaderboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);
        authViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        resultsViewModelLeaderboard = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ResultsViewModelLeaderboard.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        resultText = view.findViewById(R.id.resultText);
        correctanswersText = view.findViewById(R.id.correctanswersText);
        wronganswersText = view.findViewById(R.id.wronganswersText);
        goHomeButton = view.findViewById(R.id.goHomeButton);

        resultsViewModel.getCounter().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                String resultString;
                if(integers.get(0) > 24){
                    resultString = "You passed";
                    Result result = new Result(authViewModel.getUser().getEmail(), true);
                    resultsViewModelLeaderboard.addResultToFirebase(result);
                } else {
                    resultString = "You failed";
                }
                String correctString = "Correct answers: " + integers.get(0);
                String wrongString = "Wrong answers: " + integers.get(1);
                resultText.setText(resultString);
                correctanswersText.setText(correctString);
                wronganswersText.setText(wrongString);
            }
        });
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resultsFragment_to_homeFragment);
            }
        });
    }
}