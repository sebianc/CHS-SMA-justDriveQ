package com.example.justdriveq.app.questionnaire;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.example.justdriveq.R;
import com.example.justdriveq.app.questionnaire.sensor.viewmodel.GyroscopeSensorData;
import com.example.justdriveq.app.questionnaire.sensor.viewmodel.GyroscopeSensorViewmodel;
import com.example.justdriveq.app.questionnaire.viewmodel.QuestionnaireViewModel;
import com.example.justdriveq.app.questionnaire.viewmodel.ResultsViewModel;
import com.example.justdriveq.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionnaireFragment extends Fragment implements View.OnClickListener {

    private QuestionnaireViewModel questionnaireViewModel;
    private ResultsViewModel resultsViewModel;
    private GyroscopeSensorViewmodel gyroscopeSensorViewmodel;
    private NavController navController;
    private Button firstOptionButton;
    private Button secondOptionButton;
    private Button thirdOptionButton;
    private Button forthOptionButton;
    private Button nextQuestionButton;
    private TextView questionNumber;
    private TextView questionText;
    private boolean ansSelected = FALSE;
    private int currentQuestionNumber;
    private String currentQuestionAnswer;
    private int correctAnswerCount = 0;
    private int wrongAnswerCount = 0;
    private List<Integer> alreadyGeneratedNumbers = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionnaire, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        firstOptionButton = view.findViewById(R.id.firstOptionButton);
        secondOptionButton = view.findViewById(R.id.secondOptionButton);
        thirdOptionButton = view.findViewById(R.id.thirdOptionButton);
        forthOptionButton = view.findViewById(R.id.forthOptionButton);
        nextQuestionButton = view.findViewById(R.id.nextQuestionButton);
        questionNumber = view.findViewById(R.id.questionNumber);
        questionText = view.findViewById(R.id.questionText);

        questionnaireViewModel.loadQuestionsFromFirebase();
        this.currentQuestionNumber = 1;

        //gyroscopeSensorViewmodel.getGyroscopeSensorLiveData().observe(getViewLifecycleOwner(), new Observer<GyroscopeSensorData>() {
            //@Override
            //public void onChanged(GyroscopeSensorData gyroscopeSensorData) {
                //String dateDeLaSenzor = "Data este:\nX = " + gyroscopeSensorData.getX() + "\nY = " + gyroscopeSensorData.getY() + "\nZ = " + gyroscopeSensorData.getZ();
               // Toast.makeText(requireContext(), dateDeLaSenzor, Toast.LENGTH_LONG).show();
            //}
        //});

        firstOptionButton.setOnClickListener(this);
        secondOptionButton.setOnClickListener(this);
        thirdOptionButton.setOnClickListener(this);
        forthOptionButton.setOnClickListener(this);
        nextQuestionButton.setOnClickListener(this);

        loadData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionnaireViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuestionnaireViewModel.class);
        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);
        gyroscopeSensorViewmodel = new ViewModelProvider(this).get(GyroscopeSensorViewmodel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        gyroscopeSensorViewmodel.registerListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        gyroscopeSensorViewmodel.unregisterListener();
    }

    private void loadQuestionInQuestionnaire(int i){
        questionnaireViewModel.getQuestionsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                String questionNumberText = "Question number " + currentQuestionNumber;
                questionNumber.setText(questionNumberText);
                questionText.setText(questions.get(i-1).getQuestion());
                firstOptionButton.setText(questions.get(i-1).getOption_a());
                secondOptionButton.setText(questions.get(i-1).getOption_b());
                thirdOptionButton.setText(questions.get(i-1).getOption_c());
                forthOptionButton.setText(questions.get(i-1).getOption_d());
                currentQuestionAnswer = questions.get(i-1).getAnswer();

                showAnswerButtons();
            }
        });
    }

    private void loadData(){
        loadQuestionInQuestionnaire(generateNewQuestionNumber());
    }

    private void showAnswerButtons(){
        firstOptionButton.setVisibility(View.VISIBLE);
        secondOptionButton.setVisibility(View.VISIBLE);
        thirdOptionButton.setVisibility(View.VISIBLE);
        forthOptionButton.setVisibility(View.VISIBLE);

        firstOptionButton.setEnabled(TRUE);
        secondOptionButton.setEnabled(TRUE);
        thirdOptionButton.setEnabled(TRUE);
        forthOptionButton.setEnabled(TRUE);

        nextQuestionButton.setVisibility(View.VISIBLE);
        nextQuestionButton.setEnabled(FALSE);
    }

    private void verifyAnswer(Button button){
        if(ansSelected == FALSE){
            if(currentQuestionAnswer.equals(button.getText())){
                button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkgreen));
                correctAnswerCount++;
            } else {
                button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkred));
                wrongAnswerCount++;
            }
            ansSelected = TRUE;
            nextQuestionButton.setEnabled(TRUE);
        }

    }
    private void switchToLastQuestionButton(){
        List<Integer> counter = new ArrayList<>();
        counter.add(correctAnswerCount);
        counter.add(wrongAnswerCount);
        resultsViewModel.setCounter(counter);
        navController.navigate(R.id.action_questionnaireFragment_to_resultsFragment);
    }
    private void generateNewAnswers(){
        nextQuestionButton.setVisibility(View.VISIBLE);
        nextQuestionButton.setEnabled(FALSE);

        firstOptionButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
        secondOptionButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
        thirdOptionButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
        forthOptionButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
    }

    private int generateNewQuestionNumber(){
        int range = 39; // 40 de intrebari in firebase

        Random random = new Random();
        int newNumber = random.nextInt(range);

        while (alreadyGeneratedNumbers.contains(newNumber)){
            newNumber = random.nextInt(range);
        }
        alreadyGeneratedNumbers.add(newNumber);

        return newNumber + 1;
    }

    @Override
    public void onClick(View v) {
        int currentButtonId = v.getId();

        if(currentButtonId == R.id.firstOptionButton){
            verifyAnswer(firstOptionButton);
        }
        else if(currentButtonId == R.id.secondOptionButton){
            verifyAnswer(secondOptionButton);
        }
        else if(currentButtonId == R.id.thirdOptionButton){
            verifyAnswer(thirdOptionButton);
        }
        else if(currentButtonId == R.id.forthOptionButton){
            verifyAnswer(forthOptionButton);
        }
        else if(currentButtonId == R.id.nextQuestionButton){
            if(wrongAnswerCount > 4){
                List<Integer> counter = new ArrayList<>();
                counter.add(correctAnswerCount);
                counter.add(wrongAnswerCount);
                resultsViewModel.setCounter(counter);
                navController.navigate(R.id.action_questionnaireFragment_to_resultsFragment);
            }
            if(currentQuestionNumber == 26){
                switchToLastQuestionButton();
            } else if(currentQuestionNumber < 26){
                currentQuestionNumber++;
                ansSelected = FALSE;
                loadQuestionInQuestionnaire(generateNewQuestionNumber());
                generateNewAnswers();
            }
        }
    }
}