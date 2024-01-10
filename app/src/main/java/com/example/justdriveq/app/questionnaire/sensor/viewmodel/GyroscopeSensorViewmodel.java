package com.example.justdriveq.app.questionnaire.sensor.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.justdriveq.app.questionnaire.sensor.repository.GyroscopeSensorListener;
import com.example.justdriveq.app.questionnaire.sensor.repository.GyroscopeSensorRepository;

import java.util.List;

public class GyroscopeSensorViewmodel extends AndroidViewModel implements GyroscopeSensorListener {

    private GyroscopeSensorRepository repository;
    private MutableLiveData<GyroscopeSensorData> gyroscopeSensorLiveData;

    public MutableLiveData<GyroscopeSensorData> getGyroscopeSensorLiveData() {
        return gyroscopeSensorLiveData;
    }

    public GyroscopeSensorViewmodel(@NonNull Application application) {
        super(application);
        repository = new GyroscopeSensorRepository(application);
        gyroscopeSensorLiveData = new MutableLiveData<>();
        repository.setGyroscopeSensorListener(this);
    }

    public void registerListener(){
        repository.registerListener();
    }
    public void unregisterListener(){
        repository.unregisterListener();
    }
    @Override
    public void onSensorChange(float x, float y, float z) {
        gyroscopeSensorLiveData.setValue(new GyroscopeSensorData(x, y, z));
    }
}
