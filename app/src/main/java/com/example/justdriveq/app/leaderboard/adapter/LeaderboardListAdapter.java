package com.example.justdriveq.app.leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justdriveq.R;
import com.example.justdriveq.app.questionnaire.viewmodel.ResultsViewModelLeaderboard;
import com.example.justdriveq.models.Result;

import org.w3c.dom.Text;

import java.util.List;

public class LeaderboardListAdapter extends RecyclerView.Adapter<LeaderboardListAdapter.LeaderboardListViewHolder>{
    private List<String> resultsViewModelLeaderboardList;

    public void setResultsViewModelLeaderboardList(List<String> resultsViewModelLeaderboardList) {
        this.resultsViewModelLeaderboardList = resultsViewModelLeaderboardList;
    }

    @NonNull
    @Override
    public LeaderboardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_slot, parent, false);
        return new LeaderboardListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardListViewHolder holder, int position) {
        String model = resultsViewModelLeaderboardList.get(position);
        String[] splitModel = model.split("@");
        holder.clasamentText.setText(splitModel[0]);
    }

    @Override
    public int getItemCount() {
        if(resultsViewModelLeaderboardList == null){
            return 0;
        } else {
            return resultsViewModelLeaderboardList.size();
        }
    }

    public class LeaderboardListViewHolder extends RecyclerView.ViewHolder{
        private TextView clasamentText;
       public LeaderboardListViewHolder(@NonNull View itemView) {
           super(itemView);

           clasamentText = itemView.findViewById(R.id.clasamentText);
       }
   }
}
