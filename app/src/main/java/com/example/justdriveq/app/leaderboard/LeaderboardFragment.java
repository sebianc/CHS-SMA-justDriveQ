package com.example.justdriveq.app.leaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.justdriveq.R;
import com.example.justdriveq.app.leaderboard.adapter.LeaderboardListAdapter;
import com.example.justdriveq.app.questionnaire.viewmodel.ResultsViewModelLeaderboard;
import com.example.justdriveq.models.Result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {

    private NavController navController;
    private Button goBackHomeButton;
    private RecyclerView recyclerView;
    private ResultsViewModelLeaderboard resultsViewModelLeaderboard;

    private LeaderboardListAdapter leaderboardListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultsViewModelLeaderboard = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ResultsViewModelLeaderboard.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        goBackHomeButton = view.findViewById(R.id.goBackToHomeButtonLeaderboard);
        recyclerView = view.findViewById(R.id.leaderboardList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderboardListAdapter = new LeaderboardListAdapter();

        recyclerView.setAdapter(leaderboardListAdapter);

        resultsViewModelLeaderboard.loadResultsFromFirebase();
        resultsViewModelLeaderboard.getResultsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                //Log.d("mesaj frumos", "mesaj tare");
                Map<String, Integer> resultsHashMap = new HashMap<>();
                for(Result i : results){
                    resultsHashMap.put(i.getEmail(), resultsHashMap.getOrDefault(i.getEmail(), 0) + 1);
                }
                List<String> leaderboardList = new ArrayList<>(resultsHashMap.keySet());
                leaderboardList.sort(Comparator.comparingInt(resultsHashMap::get).reversed());

                leaderboardListAdapter.setResultsViewModelLeaderboardList(leaderboardList);
                leaderboardListAdapter.notifyDataSetChanged();
            }
        });

        //inapoi in home menu
        goBackHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_leaderboardFragment_to_homeFragment);
            }
        });
    }
}