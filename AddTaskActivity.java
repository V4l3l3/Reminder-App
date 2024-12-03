package com.example.reminderappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, taskDateEditText, taskTimeEditText, taskDescriptionEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_task); // Corrected layout file name

        // Initialize the views
        taskNameEditText = findViewById(R.id.editTextTitle);
        taskDateEditText = findViewById(R.id.editTextDate2);
        taskTimeEditText = findViewById(R.id.editTextTime);
        taskDescriptionEditText = findViewById(R.id.editTextText);
        saveButton = findViewById(R.id.saveButton);

        // Set the click listener for the save button
        saveButton.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        // Retrieve data from EditText fields
        String taskName = taskNameEditText.getText().toString().trim();
        String taskDate = taskDateEditText.getText().toString().trim();
        String taskTime = taskTimeEditText.getText().toString().trim();
        String taskDescription = taskDescriptionEditText.getText().toString().trim();

        // Validate input
        if (!validateInput(taskName, taskDate, taskTime, taskDescription)) {
            return;
        }

        // Create a Task object with the input
        Task newTask = new Task(taskName, taskDescription, taskDate, taskTime);

        // Send the task data back to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("task_name", newTask.getTaskName());
        resultIntent.putExtra("task_description", newTask.getTaskDescription());
        resultIntent.putExtra("task_date", newTask.getTaskDate());
        resultIntent.putExtra("task_time", newTask.getTaskTime());

        setResult(RESULT_OK, resultIntent);
        finish(); // Close the activity
    }

    private boolean validateInput(String taskName, String taskDate, String taskTime, String taskDescription) {
        boolean isValid = true;

        if (taskName.isEmpty()) {
            taskNameEditText.setError("Please enter a task name.");
            isValid = false;
        }

        if (taskDate.isEmpty()) {
            taskDateEditText.setError("Please enter a date.");
            isValid = false;
        }

        if (taskTime.isEmpty()) {
            taskTimeEditText.setError("Please enter a time.");
            isValid = false;
        }

        if (taskDescription.isEmpty()) {
            taskDescriptionEditText.setError("Please provide a task description.");
            isValid = false;
        }

        return isValid;
    }
}
