package com.example.reminderappproject;

public class Task {
    private String taskName;
    private String taskDescription;
    private String date;
    private String time;

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.date = date;
        this.time = time;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
    public String getDate(){
        return date;
    }
    public void setDate(){
        this.date = date;
    }
    public String getTime(){
        return time;
    }
    public void setTime(){
        this.time = time;
    }
}

