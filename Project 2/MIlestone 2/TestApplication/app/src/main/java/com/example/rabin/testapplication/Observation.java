package com.example.rabin.testapplication;

/**
 * Created by rabin on 8/23/16.
 */
public class Observation {

    private int observationId;
    private int observationOnTask;
    private int observationOffTask;
    private String observationStudentId;

    public Observation()
    {
        observationId = -1;
        observationOnTask = -1;
        observationOffTask = -1;
        observationStudentId = "";
    }

    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }

    public int getObservationOnTask() {
        return observationOnTask;
    }

    public void setObservationOnTask(int observationOnTask) {
        this.observationOnTask = observationOnTask;
    }

    public int getObservationOffTask() {
        return observationOffTask;
    }

    public void setObservationOffTask(int observationOffTask) {
        this.observationOffTask = observationOffTask;
    }

    public String getObservationStudentId() {
        return observationStudentId;
    }

    public void setObservationStudentId(String observationStudentId) {
        this.observationStudentId = observationStudentId;
    }
}
