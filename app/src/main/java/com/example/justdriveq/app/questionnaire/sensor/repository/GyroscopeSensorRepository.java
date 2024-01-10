package com.example.justdriveq.app.questionnaire.sensor.repository;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class GyroscopeSensorRepository implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private GyroscopeSensorListener gyroscopeSensorListener;

    public GyroscopeSensorRepository(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        } else {
            Log.e("Gyroscope sensor", "Error from sensorManager");
        }
    }

    public void registerListener(){
        sensorManager.registerListener(this, gyroSensor, 5000000);
    }
    public void unregisterListener(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE && gyroscopeSensorListener != null){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            gyroscopeSensorListener.onSensorChange(x,z,y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setGyroscopeSensorListener(GyroscopeSensorListener gyroscopeSensorListener) {
        this.gyroscopeSensorListener = gyroscopeSensorListener;
    }
}
