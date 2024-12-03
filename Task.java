package com.example.reminderappproject;

public class Task {
    private String taskName;
    private String taskDescription;
    private String taskDate;
    private String taskTime;
    private boolean isSelected;

    public Task(String taskName, String taskDescription, String taskDate, String taskTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = this.taskTime;
        this.isSelected = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
    public String getTaskDate(){
        return taskDate;
    }
    public void setDate(){
        this.taskDate = taskDate;
    }
    public String getTaskTime(){
        return taskTime;
    }
    public void setTime(){
        this.taskTime = taskTime;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

